package io.github.chutian0610.hedron.test.parser;

import io.github.chutian0610.hedron.constant.EngineType;
import io.github.chutian0610.hedron.core.HedronContext;
import io.github.chutian0610.hedron.core.HedronContextConfig;

import org.apache.calcite.sql.parser.SqlParser;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Integration tests for SqlParser across different engine types.
 * Tests read SQL statements from resource files and validate them.
 */
public class HedronParserIntegrationTest {

    /**
     * Test data provider that reads SQL files from resources/parser directory.
     * Each file name corresponds to an engine type (e.g., generic.sql -> GENERIC).
     *
     * @return stream of test cases
     */
    private static Stream<TestCase> provideTestCases() {
        List<TestCase> testCases = new ArrayList<>();

        // Test each engine type
        for (EngineType engineType : EngineType.values()) {
            String fileName = engineType.name().toLowerCase() + ".sql";
            List<String> sqlStatements = readSqlFile(fileName);

            for (String sql : sqlStatements) {
                testCases.add(new TestCase(engineType, sql.trim()));
            }
        }

        return testCases.stream();
    }

    /**
     * Reads SQL statements from a resource file.
     *
     * @param fileName the name of the SQL file
     * @return list of SQL statements (non-empty lines)
     */
    private static List<String> readSqlFile(String fileName) {
        List<String> sqlStatements = new ArrayList<>();
        String resourcePath = "parser/" + fileName;

        try (InputStream inputStream = HedronParserIntegrationTest.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException("Resource file not found: " + resourcePath);
            }

            String content = new String(IOUtils.toByteArray(inputStream), StandardCharsets.UTF_8);
            String[] queries = content.split(";");

            for (String query : queries) {
                String trimmedQuery = query.trim();
                // Skip empty lines and comments
                if (!trimmedQuery.isEmpty() && !trimmedQuery.startsWith("--")) {
                    sqlStatements.add(trimmedQuery);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read SQL file: " + fileName, e);
        }

        return sqlStatements;
    }

    /**
     * Parameterized test that validates SQL parsing for each engine type.
     *
     * @param testCase the test case containing engine type and SQL statement
     */
    @ParameterizedTest(name = "Test {index}: {0}")
    @MethodSource("provideTestCases")
    public void testSqlParsing(TestCase testCase) {
        EngineType engineType = testCase.getEngineType();
        String sql = testCase.getSql();
        HedronContextConfig config = HedronContextConfig.builder()
                .engineType(engineType)
                .build();
        HedronContext context = new HedronContext(config);
        SqlParser parser = context.createSqlParser(sql);
        // Verify that parsing does not throw any exception
        Assertions.assertThatNoException()
                .as("Failed to parse SQL for engine type %s: %s", engineType, sql)
                .isThrownBy(() -> parser.parseQuery());
    }

    /**
     * Test case holder class.
     */
    private static class TestCase {
        private final EngineType engineType;
        private final String sql;

        public TestCase(EngineType engineType, String sql) {
            this.engineType = engineType;
            this.sql = sql;
        }

        public EngineType getEngineType() {
            return engineType;
        }

        public String getSql() {
            return sql;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s", engineType, sql);
        }
    }
}
