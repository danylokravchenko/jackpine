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
 * Developer: Danylo Kravchenko
 */

package edu.toronto.cs.jackpine.benchmark.db;

import com.continuent.bristlecone.benchmark.db.AbstractSqlDialect;
import com.continuent.bristlecone.benchmark.db.Column;

public class SqlDialectForPolypheny extends AbstractSqlDialect {

    public SqlDialectForPolypheny() {
    }

    public String getDriver() {
        return "org.polypheny.jdbc.Driver";
    }

    public boolean supportsJdbcUrl(String url) {
        return url.startsWith("jdbc:polypheny");
    }


    @Override
    public String implementationColumnSpecification( Column col) {
        StringBuilder sb = new StringBuilder();
        sb.append(col.getName());
        sb.append(" ");
        if (col.isAutoIncrement()) {
            sb.append("serial");
        } else {
            sb.append(this.implementationTypeName(col.getType()));
        }

        if (col.getLength() > 0) {
            sb.append("(").append(col.getLength());
            if (col.getPrecision() > 0) {
                sb.append(",").append(col.getPrecision());
            }

            sb.append(")");
        }

        if (col.isPrimaryKey()) {
            sb.append(" primary key");
        }

        return sb.toString();
    }

    @Override
    public boolean implementationUpdateRequiresTransaction(int type) {
        return switch ( type ) {
            case 2004, 2005 -> true;
            default -> false;
        };
    }


    @Override
    public String implementationTypeName(int type) {
        return switch ( type ) {
            case 2004 -> "bytea";
            case 2005 -> "text";
            default -> super.implementationTypeName( type );
        };
    }

}
