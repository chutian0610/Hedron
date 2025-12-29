package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlDelegatingConformance;

/**
 * The conformance of Clickhouse SQL.
 * <p>
 * The conformance is used to validate the SQL statement against the Clickhouse
 * SQL syntax rules.
 */
public class ClickhouseConformance extends SqlDelegatingConformance {
    public static final ClickhouseConformance INSTANCE = new ClickhouseConformance();

    protected ClickhouseConformance() {
        super(SqlConformanceEnum.MYSQL_5);
    }

}