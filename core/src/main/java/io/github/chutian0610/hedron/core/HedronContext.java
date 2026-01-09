package io.github.chutian0610.hedron.core;

import javax.annotation.Nonnull;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.sql.parser.SqlParser;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import io.github.chutian0610.hedron.constant.EngineType;
import io.github.chutian0610.hedron.core.extend.HedronConnectionConfigImpl;
import io.github.chutian0610.hedron.core.parser.ClickhouseConformance;
import io.github.chutian0610.hedron.core.parser.GenericConformance;
import io.github.chutian0610.hedron.core.parser.HiveConformance;
import io.github.chutian0610.hedron.core.parser.StarRocksConformance;
import lombok.Getter;

public class HedronContext {
    @Getter
    private final HedronContextConfig config;
    @Getter
    private final CalciteSchema rootSchema;
    @Getter
    private final SqlParser.Config parserConfig;
    @Getter
    private final CalciteConnectionConfig calciteConnectionConfig;
    @Getter
    private final CalciteCatalogReader catalogReader;

    public HedronContext() {
        this(HedronContextConfig.builder().build());
    }

    public HedronContext(HedronContextConfig config) {
        Preconditions.checkNotNull(config, "config must not be null");
        this.config = config;
        // Initialize Compoents with the config

        this.rootSchema = CalciteSchema.createRootSchema(config.isAddMetadataSchema());
        this.parserConfig = createParserConfig(config.getEngineType());
        this.calciteConnectionConfig = new HedronConnectionConfigImpl(parserConfig);
        this.catalogReader = initCatalogReader();
    }

    @SuppressWarnings("null")
    private CalciteCatalogReader initCatalogReader() {
        return new CalciteCatalogReader(
                rootSchema,
                rootSchema.getName() == null ? ImmutableList.of()
                        : ImmutableList.of(rootSchema.getName()),
                config.getTypeFactory(),
                calciteConnectionConfig);
    }

    /**
     * Add a schema to the context.
     * 
     * @param name   the name of the schema
     * @param schema the schema object
     */
    public void addSchema(@Nonnull String name, @Nonnull Schema schema) {
        rootSchema.add(name, schema);
    }

    /**
     * Create a SQL parser for the given SQL string and engine type.
     * 
     * @param sql        the SQL string to parse
     * @param engineType the engine type to use for parsing
     * @return a SQL parser instance
     */
    public SqlParser createSqlParser(String sql) {
        return SqlParser.create(sql, parserConfig);
    }

    /** ========================== Utility Methods ========================== */

    /**
     * Create a parser config for the given engine type.
     * 
     * @param engineType the engine type to use for parsing
     * @return a parser config instance
     */
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
}
