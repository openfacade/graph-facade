package io.github.openfacade.graph.schema;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CreateEdgeRequest {
    private String edgeName;

    private String edgeSchemaName;

    private Map<String, Object> properties = new HashMap<>();
}
