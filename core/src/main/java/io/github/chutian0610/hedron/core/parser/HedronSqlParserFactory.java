package io.github.chutian0610.hedron.core.parser;

import io.github.chutian0610.hedron.core.util.EngineType;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * The Sql Parser Factory of Hedron.
 */
public class HedronSqlParserFactory {

    public static SqlParser.Config createParserConfig(EngineType engineType) {
        switch (engineType) {
            case MYSQL:
                return SqlParser.config().withLex(Lex.MYSQL);
            case STARROCKS:
                return SqlParser.config()
                        .withCaseSensitive(false) // StarRocks 表名/列名不区分大小写
                        .withConformance(StarRocksConformance.INSTANCE) // StarRocks 自定义合规性
                        .withQuoting(Quoting.BACK_TICK) // 使用反引号（兼容 MySQL）
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.TO_LOWER);
            case CLICKHOUSE:
                return SqlParser.config()
                        .withCaseSensitive(true) // Clickhouse 表名/列名区分大小写
                        .withConformance(ClickhouseConformance.INSTANCE) // Clickhouse 自定义合规性
                        .withQuoting(Quoting.BACK_TICK) // 使用反引号
                        .withQuotedCasing(Casing.UNCHANGED) // 引号内的标识符保持原样
                        .withUnquotedCasing(Casing.UNCHANGED); // 未引用的标识符保持原样
            case HIVE:
                return SqlParser.config()
                        .withCaseSensitive(false) // 表名/列名不区分大小写
                        .withConformance(HiveConformance.INSTANCE) // Hive 自定义合规性
                        .withQuoting(Quoting.DOUBLE_QUOTE) // 使用双引号（兼容 Hive）
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withUnquotedCasing(Casing.TO_LOWER);
            case GENERIC:
                return SqlParser.config()
                        .withCaseSensitive(false)
                        .withConformance(GenericConformance.INSTANCE) // 自定义合规性
                        .withQuoting(Quoting.DOUBLE_QUOTE) // 使用双引号（兼容 Presto）
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
