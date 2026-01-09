package io.github.chutian0610.hedron.core.extend;

import java.util.Properties;

import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlConformance;

/**
 * The connection config of Hedron.
 * 
 * overwrite the behavior of {@link CalciteConnectionConfigImpl}
 * use {@link SqlParser.Config} to config the parser.
 * 
 * @see CalciteConnectionConfigImpl
 */
public class HedronConnectionConfigImpl extends CalciteConnectionConfigImpl {
    private final SqlParser.Config sqlParserConfig;

    public HedronConnectionConfigImpl(SqlParser.Config sqlParserConfig) {
        super(new Properties());
        this.sqlParserConfig = sqlParserConfig;
    }

    /**
     * Whether the parser is case sensitive.
     * Used in {@link CalciteCatalogReader}
     */
    @Override
    public boolean caseSensitive() {
        return sqlParserConfig.caseSensitive();
    }

    /**
     * The conformance of the parser.
     * Used in {@link CalciteCatalogReader}
     */
    @Override
    public SqlConformance conformance() {
        return sqlParserConfig.conformance();
    }

}
