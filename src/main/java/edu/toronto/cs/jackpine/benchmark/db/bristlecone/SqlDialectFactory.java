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

import com.continuent.bristlecone.benchmark.db.SqlDialect;
import com.continuent.bristlecone.benchmark.db.SqlDialectForDerby;
import com.continuent.bristlecone.benchmark.db.SqlDialectForHSQLDB;
import com.continuent.bristlecone.benchmark.db.SqlDialectForInformix;
import com.continuent.bristlecone.benchmark.db.SqlDialectForIngres;
import com.continuent.bristlecone.benchmark.db.SqlDialectForMCluster;
import com.continuent.bristlecone.benchmark.db.SqlDialectForMysql;
import com.continuent.bristlecone.benchmark.db.SqlDialectForOracle;
import com.continuent.bristlecone.benchmark.db.SqlDialectForPCluster;
import com.continuent.bristlecone.benchmark.db.SqlDialectForPostgreSQL;
import edu.toronto.cs.jackpine.benchmark.db.SqlDialectForPolypheny;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlDialectFactory {

    @Getter
    private static final SqlDialectFactory instance = new SqlDialectFactory();
    private static final List<SqlDialect> dialects = Arrays.asList( new SqlDialectForDerby(), new SqlDialectForHSQLDB(), new SqlDialectForInformix(), new SqlDialectForIngres(), new SqlDialectForMysql(), new SqlDialectForOracle(), new SqlDialectForPostgreSQL(), new SqlDialectForPolypheny(), new SqlDialectForPCluster(), new SqlDialectForMCluster() );


    public SqlDialectFactory() {
    }


    public SqlDialect getDialect( String url ) {
        for ( SqlDialect dialect : dialects ) {
            if ( dialect.supportsJdbcUrl( url ) ) {
                String className = dialect.getClass().toString();
                className = className.substring( className.lastIndexOf( ".SqlDialectFor" ) + 1 );
                System.out.println( "\nRunning scenario for " + className );
                log.warn( "\nRunning scenario for " + className );
                return dialect;
            }
        }
        return null;
    }

}
