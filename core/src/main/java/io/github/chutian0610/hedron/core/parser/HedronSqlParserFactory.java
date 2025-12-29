package io.github.chutian0610.hedron.core.parser;

import io.github.chutian0610.hedron.core.util.EngineType;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * The Sql Parser Factory of Hedron.
 * <p>
 * The factory is used to create the SqlParser instance for different engine
 * types.
 */
public class HedronSqlParserFactory {

    public static SqlParser.Config createParserConfig(EngineType engineType) {
        switch (engineType) {
            case MYSQL:
                return SqlParser.config().withLex(Lex.MYSQL);
            case STARROCKS:
                return SqlParser.config()
                        .withCaseSensitive(false)
                        .withConformance(StarRocksConformance.INSTANCE)
                        .withQuoting(Quoting.BACK_TICK)
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.TO_LOWER);
            case CLICKHOUSE:
                return SqlParser.config()
                        .withCaseSensitive(true)
                        .withConformance(ClickhouseConformance.INSTANCE)
                        .withQuoting(Quoting.BACK_TICK)
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.UNCHANGED);
            case HIVE:
                return SqlParser.config()
                        .withCaseSensitive(false)
                        .withConformance(HiveConformance.INSTANCE)
                        .withQuoting(Quoting.DOUBLE_QUOTE)
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.TO_LOWER);
            case GENERIC:
                return SqlParser.config()
                        .withCaseSensitive(false)
                        .withConformance(GenericConformance.INSTANCE)
                        .withQuoting(Quoting.DOUBLE_QUOTE)
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.TO_LOWER);
            default:
                throw new IllegalArgumentException("Unknown engine type: " + engineType);
        }
    }

    public static SqlParser createSqlParser(String sql, EngineType engineType) {
        return SqlParser.create(sql, createParserConfig(engineType));
    }
}
