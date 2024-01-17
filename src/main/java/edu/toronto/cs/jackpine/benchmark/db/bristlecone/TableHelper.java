/*
 * Jackpine Spatial Database Benchmark
 *  Copyright (C) 2010 University of Toronto
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of version 2 of the GNU General Public License as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 *
 * Developer: S. Ray
 * Initial developer(s): Robert Hodges and Ralph Hannus.
 * Contributor(s): Danylo Kravchenko
 */

package edu.toronto.cs.jackpine.benchmark.db.bristlecone;

import com.continuent.bristlecone.benchmark.BenchmarkException;
import com.continuent.bristlecone.benchmark.db.Column;
import com.continuent.bristlecone.benchmark.db.SqlDialect;
import com.continuent.bristlecone.benchmark.db.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableHelper {
    protected final String connectionUrl;
    protected final String login;
    protected final String password;
    @Getter
    protected final SqlDialect sqlDialect;

    public TableHelper(String url, String login, String password) {
        this.connectionUrl = url;
        this.login = login;
        this.password = password;
        this.sqlDialect = SqlDialectFactory.getInstance().getDialect(url);
        this.loadDriver(this.sqlDialect.getDriver());
    }

    public void loadDriver(String name) throws BenchmarkException {
        try {
            Class.forName(name);
        } catch (Exception var3) {
            throw new BenchmarkException("Unable to load JDBC driver: " + name, var3);
        }
    }

    public void execute(String sql) throws SQLException {
        Connection conn = this.getConnection();
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(sql);
        } finally {
            this.releaseStatement(stmt);
            this.releaseConnection(conn);
        }

    }

    public void create( Table table, boolean dropExisting) throws SQLException {
        if (dropExisting) {
            this.drop(table, true);
        }

        Connection conn = this.getConnection();
        String createSql = null;
        Statement stmt = conn.createStatement();

        try {
            createSql = this.sqlDialect.getCreateTable(table);
            log.debug(createSql);
            stmt.execute(createSql);

            for(int c = 0; c < table.getColumns().length; ++c) {
                Column col = table.getColumns()[c];
                if (col.isIndexed()) {
                    createSql = this.sqlDialect.getCreateIndex(table, col);
                    log.debug(createSql);
                    stmt.execute(createSql);
                }
            }
        } catch (SQLException var11) {
            log.debug("Table creation failed: " + createSql, var11);
            throw var11;
        } finally {
            this.releaseStatement(stmt);
            this.releaseConnection(conn);
        }

    }

    public void drop(Table table, boolean ignore) throws SQLException {
        Connection conn = this.getConnection();
        String dropSql = this.sqlDialect.getDropTable(table);
        log.debug(dropSql);
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(dropSql);
        } catch (SQLException var10) {
            if (!ignore) {
                log.debug("Table drop failed: " + dropSql, var10);
                throw var10;
            }

            log.debug("Table deletion failure ignored: " + dropSql);
        } finally {
            this.releaseStatement(stmt);
            this.releaseConnection(conn);
        }

    }

    public void insert(Table table, Object[] values) throws SQLException {
        Connection conn = this.getConnection();
        String insert = this.sqlDialect.getInsert(table);
        PreparedStatement ps = conn.prepareStatement(insert);

        try {
            for(int i = 0; i < values.length; ++i) {
                ps.setObject(i + 1, values[i]);
            }

            ps.execute();
        } catch (SQLException var10) {
            log.debug("Table insert failed: " + insert, var10);
            throw var10;
        } finally {
            this.releaseStatement(ps);
            this.releaseConnection(conn);
        }
    }

    public void delete(Table table, Object[] keys) throws SQLException {
        Connection conn = this.getConnection();
        String delete = this.sqlDialect.getDeleteByKey(table);
        PreparedStatement ps = conn.prepareStatement(delete);

        try {
            for(int i = 0; i < keys.length; ++i) {
                ps.setObject(i + 1, keys[i]);
            }

            ps.execute();
        } catch (SQLException var10) {
            log.debug("Table delete failed: " + delete, var10);
            throw var10;
        } finally {
            this.releaseStatement(ps);
            this.releaseConnection(conn);
        }
    }

    public Connection getConnection() throws SQLException {
        log.debug("Connecting to database: url=" + this.connectionUrl + " user=" + this.login + " password=" + this.password);
        System.out.println("Connecting to database: url=" + this.connectionUrl + " user=" + this.login + " password=" + this.password);
        Connection conn = DriverManager.getConnection(this.connectionUrl, this.login, this.password);
        log.debug("Obtained database connection: " + conn);
        return conn;
    }

    public void releaseConnection(Connection conn) {
        log.debug("Releasing database connection: " + conn);

        try {
            conn.close();
        } catch (SQLException var3) {
            log.debug("Connection release failed", var3);
        }

    }

    public void releaseStatement(Statement stmt) {
        log.debug("Releasing database statement: " + stmt);

        try {
            stmt.close();
        } catch (SQLException var3) {
            log.debug("Statement release failed", var3);
        }

    }

    public boolean testRowExistence(PreparedStatement keyQuery, String key, boolean exists, long limit, long logInterval) throws BenchmarkException, Exception {
        long limitTimer = System.currentTimeMillis();
        long logIntervalTimer = limitTimer;
        keyQuery.setString(1, key);

        do {
            ResultSet rs = null;
            int matches = 0;

            try {
                for(rs = keyQuery.executeQuery(); rs.next(); ++matches) {
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }

            }

            if (matches > 1) {
                throw new BenchmarkException("Found multiple matches when searching for record: key=" + key);
            }

            if (exists && matches == 1) {
                return true;
            }

            if (!exists && matches == 0) {
                return true;
            }

            if (System.currentTimeMillis() - logIntervalTimer > logInterval) {
                logIntervalTimer = System.currentTimeMillis();
                log.info("Waited " + logInterval / 1000L + " to test row existence: key=" + key + " existence=" + exists);
            }
        } while(System.currentTimeMillis() - limitTimer < limit);

        return false;
    }
}