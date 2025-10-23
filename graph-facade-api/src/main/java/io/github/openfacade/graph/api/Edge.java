package io.github.openfacade.graph.api;

import lombok.Data;

import java.util.Map;

/**
 * Edge entity representing a graph edge
 */
@Data
public class Edge {
    
    /**
     * Edge ID
     */
    private String id;
    
    /**
     * Edge schema/label
     */
    private String schema;
    
    /**
     * Source node ID
     */
    private String sourceId;
    
    /**
     * Target node ID
     */
    private String targetId;
    
    /**
     * Edge properties
     */
    private Map<String, Object> properties;
}