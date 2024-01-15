/**
 * Jackpine Spatial Database Benchmark
 * Copyright (C) 2010 University of Toronto
 *
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
 * Contributor(s):
 */

package edu.toronto.cs.jackpine.benchmark.db;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sray
 */
@Slf4j
public class SpatialSqlDialectFactory {

    @Getter
    private static final SpatialSqlDialectFactory instance = new SpatialSqlDialectFactory();

    // Declare and initialized dialect support.
    private static final List<SpatialSqlDialect> dialects = Arrays.asList( new SpatialSqlDialectForInformix(), new SpatialSqlDialectForIngres(), new SpatialSqlDialectForMysql(), new SpatialSqlDialectForPostgreSQL(), new SpatialSqlDialectForPolypheny() );


    /**
     * Return a dialect that processes the given JDBC URL or null if no match
     * can be found.
     *
     * @param url A JDBC URL
     */
    public SpatialSqlDialect getDialect( String url ) {
        for ( SpatialSqlDialect dialect : dialects ) {
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