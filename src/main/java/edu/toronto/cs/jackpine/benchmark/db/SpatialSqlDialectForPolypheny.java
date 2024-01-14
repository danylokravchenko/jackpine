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

package edu.toronto.cs.jackpine.benchmark.db;

import com.continuent.bristlecone.benchmark.db.SqlDialectForPostgreSQL;
import edu.toronto.cs.jackpine.benchmark.scenarios.macroscenario.VisitScenario;

public class SpatialSqlDialectForPolypheny extends SqlDialectForPostgreSQL implements SpatialSqlDialect
{


    @Override
    public SupportedSqlDialect getSqlDialectType() {
        return null;
    }


    /**
     * Returns the name of the JDBC driver class.
     */
    @Override
    public String getSelectAllFeaturesWithinADistanceFromPoint() {
        return null;
    }


    @Override
    public String getSelectTotalLength() {
        return null;
    }


    @Override
    public String getSelectTotalArea() {
        return null;
    }


    @Override
    public String getSelectLongestLine() {
        return null;
    }


    @Override
    public String getSelectLargestArea() {
        return null;
    }


    @Override
    public String getSelectDimensionPolygon() {
        return null;
    }


    @Override
    public String getSelectBufferPolygon() {
        return null;
    }


    @Override
    public String getSelectConvexHullPolygon() {
        return null;
    }


    @Override
    public String getSelectEnvelopeLine() {
        return null;
    }


    @Override
    public String getSelectBoundingBoxSearch() {
        return null;
    }


    @Override
    public String[] getSelectLongestLineIntersectsArea() {
        return new String[0];
    }


    @Override
    public String[] getSelectLineIntersectsLargestArea() {
        return new String[0];
    }


    @Override
    public String[] getSelectAreaOverlapsLargestArea() {
        return new String[0];
    }


    @Override
    public String[] getSelectLargestAreaContainsPoint() {
        return new String[0];
    }


    @Override
    public String getSelectAreaOverlapsArea() {
        return null;
    }


    @Override
    public String getSelectAreaContainsArea() {
        return null;
    }


    @Override
    public String getSelectAreaWithinArea() {
        return null;
    }


    @Override
    public String getSelectAreaTouchesArea() {
        return null;
    }


    @Override
    public String getSelectAreaEqualsArea() {
        return null;
    }


    @Override
    public String getSelectAreaDisjointArea() {
        return null;
    }


    @Override
    public String getSelectLineIntersectsArea() {
        return null;
    }


    @Override
    public String getSelectLineCrossesArea() {
        return null;
    }


    @Override
    public String getSelectLineWithinArea() {
        return null;
    }


    @Override
    public String getSelectLineTouchesArea() {
        return null;
    }


    @Override
    public String getSelectLineOverlapsArea() {
        return null;
    }


    @Override
    public String getSelectLineOverlapsLine() {
        return null;
    }


    @Override
    public String getSelectLineCrossesLine() {
        return null;
    }


    @Override
    public String getSelectPointEqualsPoint() {
        return null;
    }


    @Override
    public String getSelectPointWithinArea() {
        return null;
    }


    @Override
    public String getSelectPointIntersectsArea() {
        return null;
    }


    @Override
    public String getSelectPointIntersectsLine() {
        return null;
    }


    @Override
    public String getMaxRowidFromSpatialTableEdgesMerge() {
        return null;
    }


    @Override
    public String getMaxRowidFromSpatialTableArealmMerge() {
        return null;
    }


    @Override
    public String getInsertIntoEdgesMerge() {
        return null;
    }


    @Override
    public String getInsertIntoArealmMerge() {
        return null;
    }


    @Override
    public String getSpatialWriteCleanupEdgesMerge() {
        return null;
    }


    @Override
    public String getSpatialWriteCleanupArealmMerge() {
        return null;
    }


    @Override
    public String getCityStateForReverseGeocoding() {
        return null;
    }


    @Override
    public String getStreetAddressForReverseGeocoding() {
        return null;
    }


    @Override
    public String getGeocodingQuery() {
        return null;
    }


    @Override
    public String getMapSearchSiteSearchQuery( VisitScenario visitScenario ) {
        return null;
    }


    @Override
    public String[] getMapSearchScenarioQueries( VisitScenario visitScenario ) {
        return new String[0];
    }


    @Override
    public String[] getMapBrowseBoundingBoxQueries() {
        return new String[0];
    }


    @Override
    public String[] getLandUseQueries() {
        return new String[0];
    }


    @Override
    public String[] getEnvHazardQueries() {
        return new String[0];
    }

}