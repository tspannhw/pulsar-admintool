package com.pulsardeveloper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.pulsar.common.schema.SchemaType;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "topic",
        "schemaType",
        "schemaName",
        "schemaDefinition",
        "messageId",
        "UUID",
        "ts",
        "alertDateTime",
        "schemaVersion"
})
public class SchemaAlert implements Serializable {
    private String topic = "";
    private String schemaType = "";
    private String schemaName = "";
    private String schemaDefinition = "";
    private String messageId = "";
    private String UUID = "";
    private long ts = 0;
    private String alertDateTime = "";

    private Long schemaVersion = null;

    public Long getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(Long schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaDefinition(String schemaDefinition) {
        this.schemaDefinition = schemaDefinition;
    }

    public String getSchemaDefinition() {
        return schemaDefinition;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setUUID(String uuid) {
        this.UUID = uuid;
    }

    public String getUUID() {
        return UUID;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    public void setAlertDateTime(String alertDateTime) {
        this.alertDateTime = alertDateTime;
    }

    public String getAlertDateTime() {
        return alertDateTime;
    }

    public SchemaAlert() {
        super();
    }

    public SchemaAlert(String topic, String schemaType, String schemaName, String schemaDefinition, String messageId, String UUID, long ts, String alertDateTime, Long schemaVersion) {
        super();
        this.topic = topic;
        this.schemaType = schemaType;
        this.schemaName = schemaName;
        this.schemaDefinition = schemaDefinition;
        this.messageId = messageId;
        this.UUID = UUID;
        this.ts = ts;
        this.alertDateTime = alertDateTime;
        this.schemaVersion = schemaVersion;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SchemaAlert.class.getSimpleName() + "[", "]")
                .add("topic='" + topic + "'")
                .add("schemaType='" + schemaType + "'")
                .add("schemaName='" + schemaName + "'")
                .add("schemaDefinition='" + schemaDefinition + "'")
                .add("messageId='" + messageId + "'")
                .add("UUID='" + UUID + "'")
                .add("ts=" + ts)
                .add("alertDateTime='" + alertDateTime + "'")
                .add("schemaVersion=" + schemaVersion)
                .toString();
    }
}