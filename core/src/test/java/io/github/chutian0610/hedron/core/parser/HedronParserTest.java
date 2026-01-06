package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.parser.SqlParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.chutian0610.hedron.core.util.EngineType;

/**
 * The test class for {@link HedronSqlParserFactory}.
 */
public class HedronParserTest {

    @Test
    public void testSimpleQuery() {
        String query = "SELECT * FROM mysql.tbl";
        SqlParser parser = HedronSqlParserFactory.createSqlParser(query, EngineType.GENERIC);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }

    @Test
    public void testQueryWithLimitOffset() {
        String query = "SELECT * FROM mysql.tbl LIMIT 10 OFFSET 0";
        SqlParser parser = HedronSqlParserFactory.createSqlParser(query, EngineType.GENERIC);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }

    @Test
    public void testQueryWithWhereClause() {
        String query = "SELECT * FROM mysql.tbl WHERE id = 1";
        SqlParser parser = HedronSqlParserFactory.createSqlParser(query, EngineType.GENERIC);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }
}
