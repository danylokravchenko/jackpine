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
 * Contributor(s):
 */

package edu.toronto.cs.jackpine.benchmark.scenarios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lombok.extern.slf4j.Slf4j;

import edu.toronto.cs.jackpine.benchmark.db.SpatialSqlDialect;

/**
 * Implements a scenario that repeatedly inserts into one or more tables.  
 * Inserts are non-conflicting (i.e., should never deadlock).  <p>
 *
 * @author sray
 */
@Slf4j
public class WriteSpatialLineScenario extends SpatialScenarioBase {

    static final int MAXNUM = 1000;
    static int maxRowId = -1;
    protected PreparedStatement[] pstmtArray;
    protected PreparedStatement cleanUpPstmt;
    SpatialSqlDialect dialect = null;


    /** Create a prepared statement array. */
    public void prepare() throws Exception {
        dialect = helper.getSpatialSqlDialect();

        if ( dialect.getSqlDialectType() == SpatialSqlDialect.SupportedSqlDialect.Informix ) {
            String mriSql = dialect.getMaxRowidFromSpatialTableEdgesMerge();
            PreparedStatement maxRowIdPstmt = conn.prepareStatement( mriSql );
            log.warn( maxRowIdPstmt.toString() );

            try {
                log.warn( maxRowIdPstmt.toString() );
                ResultSet rs = maxRowIdPstmt.executeQuery();
                if ( rs.next() ) {
                    maxRowId = rs.getInt( 1 );
                    log.warn( "Max row count :" + maxRowId );
                }
                rs.close();
                maxRowIdPstmt.close();
            } catch ( Exception e ) {
                e.printStackTrace();
                log.error( e.toString() );
                throw new Exception( "Cleanup WriteSpatialLineScenario" );
            }

        }

        pstmtArray = new PreparedStatement[MAXNUM];
        for ( int i = 0; i < MAXNUM; i++ ) {
            String sql = dialect.getInsertIntoEdgesMerge();
            pstmtArray[i] = conn.prepareStatement( sql );
        }

        String sql = dialect.getSpatialWriteCleanupEdgesMerge();
        cleanUpPstmt = conn.prepareStatement( sql );

        try {
            log.warn( cleanUpPstmt.toString() );
            cleanUpPstmt.executeUpdate();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.debug( e.toString() );
            throw new Exception( "Cleanup WriteSpatialPolygonScenario" );
        }
    }


    /** Execute an interation. */
    public void iterate( long iterationCount ) throws Exception {

        try {
            int index = 0;
            for ( int i = 0; i < MAXNUM; i++ ) {
                PreparedStatement pstmt = pstmtArray[index];
                if ( dialect.getSqlDialectType() == SpatialSqlDialect.SupportedSqlDialect.Informix ) {
                    pstmt.setInt( 1, ++maxRowId );
                }
				if ( i == 0 ) {
					log.warn( pstmt.toString() );
				}
                pstmt.executeUpdate();
            }
            // Do the query.

        } catch ( Exception e ) {
            e.printStackTrace();
            log.error( e.toString() );
            throw new Exception( "WriteSpatialLineScenario" );
        }
    }


    /** Clean up resources used by scenario. */
    public void cleanup() throws Exception {
        // clean up the inserted records
        try {
            log.warn( cleanUpPstmt.toString() );
            cleanUpPstmt.executeUpdate();
            cleanUpPstmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error( e.toString() );
            throw new Exception( "Cleanup WriteSpatialLineScenario" );
        }

        // Clean up connections.
		for ( int i = 0; i < pstmtArray.length; i++ ) {
			pstmtArray[i].close();
		}
		if ( conn != null ) {
			conn.close();
		}
    }

}