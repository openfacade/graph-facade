package io.github.openfacade.graph.hugegraph;

import io.github.openfacade.graph.api.DataType;
import io.github.openfacade.graph.api.GraphException;

import java.util.HashMap;
import java.util.Map;

public class ConvertUtil {

    private static final Map<DataType, org.apache.hugegraph.structure.constant.DataType> DATA_TYPE_MAP = new HashMap<>();

    static {
        DATA_TYPE_MAP.put(DataType.TEXT, org.apache.hugegraph.structure.constant.DataType.TEXT);
        DATA_TYPE_MAP.put(DataType.DATE, org.apache.hugegraph.structure.constant.DataType.DATE);
        DATA_TYPE_MAP.put(DataType.UUID, org.apache.hugegraph.structure.constant.DataType.UUID);
        DATA_TYPE_MAP.put(DataType.INT, org.apache.hugegraph.structure.constant.DataType.INT);
        DATA_TYPE_MAP.put(DataType.BOOLEAN, org.apache.hugegraph.structure.constant.DataType.BOOLEAN);
        DATA_TYPE_MAP.put(DataType.BYTE, org.apache.hugegraph.structure.constant.DataType.BYTE);
        DATA_TYPE_MAP.put(DataType.BLOB, org.apache.hugegraph.structure.constant.DataType.BLOB);
        DATA_TYPE_MAP.put(DataType.DOUBLE, org.apache.hugegraph.structure.constant.DataType.DOUBLE);
        DATA_TYPE_MAP.put(DataType.FLOAT, org.apache.hugegraph.structure.constant.DataType.FLOAT);
        DATA_TYPE_MAP.put(DataType.LONG, org.apache.hugegraph.structure.constant.DataType.LONG);
    }

    public static org.apache.hugegraph.structure.constant.DataType toHugeGraphDataType(DataType dataType) {
        org.apache.hugegraph.structure.constant.DataType hugeGraphDataType = DATA_TYPE_MAP.get(dataType);
        if (hugeGraphDataType == null) {
            throw new GraphException(String.format("Unsupported data type: %s", dataType));
        }
        return hugeGraphDataType;
    }

}
