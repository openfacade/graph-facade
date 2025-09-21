package io.github.openfacade.graph.schema;

import io.github.openfacade.graph.api.DataType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CreateEdgeSchemaRequest {

    private String name;

    private String sourceNodeSchemaName;

    private String targetNodeSchemaName;

    private Map<String, DataType> propertyKeys = new HashMap<>();

    private Map<String, Object> additionalSettings = new HashMap<>();
}
