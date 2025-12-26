package io.github.chutian0610.hedron.core.query;

import io.github.chutian0610.hedron.core.util.EngineType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class View {

    private ViewType type;

    /**
     * The engine type of the view.
     */
    private EngineType engineType;

    /**
     * The content of the view.
     * For sub-query, it is the SQL statement of the sub-query.
     * For TABLE, it is the DB.TABLE
     */
    private String content;

    /**
     * The column metadata of the view.
     */
    private List<ColumnMeta> columnMetas;

    /**
     * The properties of the view.
     */
    private Map<String,String> properties;

    public Optional<String> getProperty(Property property){
       if(properties.containsKey(property.getKeyName())){
           return Optional.of(properties.get(property.getKeyName()));
       }
       return property.getDefaultValue();
    }

    public static final String CLUSTER_KEY = "engine.cluster";

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Getter
    public enum Property{
        /**
         * The cluster of the view.
         */
        CLUSTER(CLUSTER_KEY,Optional.empty())
        ;
        private final String keyName;
        private final Optional<String> defaultValue;
        Property(String keyName, Optional<String> defaultValue){
            this.keyName = keyName;
            this.defaultValue = defaultValue;
        }
    }
}
