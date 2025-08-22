package io.github.openfacade.graph.api;

public enum DataType {
    OBJECT("object"),
    BOOLEAN("boolean"),
    BYTE("byte"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    TEXT("text"),
    BLOB("blob"),
    DATE("date"),
    UUID("uuid");

    private final String name;

    private DataType(String name) {
        this.name = name;
    }
}
