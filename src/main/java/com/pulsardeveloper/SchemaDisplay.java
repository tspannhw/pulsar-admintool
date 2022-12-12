package com.pulsardeveloper;

import org.apache.pulsar.common.schema.SchemaInfo;

import java.util.StringJoiner;

public class SchemaDisplay {

    private SchemaInfo schemaInfo;

    private String topic;

    public SchemaDisplay() {
        super();
    }

    public SchemaDisplay(SchemaInfo schemaInfo, String topic) {
        super();
        this.schemaInfo = schemaInfo;
        this.topic = topic;
    }

    public SchemaInfo getSchemaInfo() {
        return schemaInfo;
    }

    public void setSchemaInfo(SchemaInfo schemaInfo) {
        this.schemaInfo = schemaInfo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SchemaDisplay.class.getSimpleName() + "[", "]")
                .add("schemaInfo=" + schemaInfo)
                .add("topic='" + topic + "'")
                .toString();
    }
}
