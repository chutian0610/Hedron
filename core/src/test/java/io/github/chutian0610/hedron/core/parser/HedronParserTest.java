package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.parser.SqlParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.chutian0610.hedron.core.HedronContext;

/**
 * The test class for {@link HedronSqlParserFactory}.
 */
public class HedronParserTest {

    @Test
    public void testSimpleQuery() {
        HedronContext context = new HedronContext();
        String query = "SELECT * FROM mysql.tbl";
        SqlParser parser = context.createSqlParser(query);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }

    @Test
    public void testQueryWithLimitOffset() {
        HedronContext context = new HedronContext();
        String query = "SELECT * FROM mysql.tbl LIMIT 10 OFFSET 0";
        SqlParser parser = context.createSqlParser(query);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }

    @Test
    public void testQueryWithWhereClause() {
        HedronContext context = new HedronContext();
        String query = "SELECT * FROM mysql.tbl WHERE id = 1";
        SqlParser parser = context.createSqlParser(query);
        Assertions.assertThatNoException().isThrownBy(() -> parser.parseQuery());
    }
}
