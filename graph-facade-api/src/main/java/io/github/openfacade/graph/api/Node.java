package io.github.openfacade.graph.api;

import lombok.Data;

import java.util.Map;

/**
 * Node entity representing a graph node
 */
@Data
public class Node {
    
    /**
     * Node ID
     */
    private String id;
    
    /**
     * Node schema/label
     */
    private String schema;
    
    /**
     * Node properties
     */
    private Map<String, Object> properties;
}