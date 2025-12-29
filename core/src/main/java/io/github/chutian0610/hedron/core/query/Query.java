package io.github.chutian0610.hedron.core.query;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * The query object.
 * <p>
 * The query object is used to represent the query statement and the related
 * information.
 */
@Data
@Builder
public class Query {
    /**
     * The SQL statement of the query.
     */
    private String sql;

    /**
     * The views used in the query.
     * The key is the alias of the view, and the value is the view object.
     * not support nested views.
     */
    private Map<String, View> views;

    /**
     * The properties of the query.
     */
    private Map<String, String> properties;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Getter
    public enum Property {
        ;
        private final String keyName;
        private final Optional<String> defaultValue;

        Property(String keyName, Optional<String> defaultValue) {
            this.keyName = keyName;
            this.defaultValue = defaultValue;
        }
    }
}
