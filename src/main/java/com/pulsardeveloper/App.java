package com.pulsardeveloper;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        String url = "http://localhost:8080";
        boolean useTls = false;
        boolean tlsAllowInsecureConnection = false;
        String tlsTrustCertsFilePath = null;
        try {
            PulsarAdmin admin = PulsarAdmin.builder()
                    .serviceHttpUrl(url)
                    .allowTlsInsecureConnection(true)
                    .build();

            String namespace = "public/default";
            try {
                List<String> list = admin.topics().getList(namespace);

                System.out.println("Stats:" + admin.brokerStats().getTopics().toString());

                System.out.println("=====topics: " + list.size() );
                List<SchemaDisplay> schemaList = new ArrayList<>(list.size());
                SchemaDisplay schemaDisplay = null;
                for (String topic : list) {
                    if ( topic != null ) {
                        try {
                            SchemaInfo schemaInfo = admin.schemas().getSchemaInfo(topic);

                            if ( schemaInfo != null && schemaInfo.getName() !=null  &&
                                 schemaInfo.getType() != null && schemaInfo.getSchemaDefinition() != null) {

                                schemaDisplay = new SchemaDisplay();
                                schemaDisplay.setSchemaInfo( schemaInfo);
                                schemaDisplay.setTopic( topic );
                                schemaDisplay.setSchemaVersion( admin.schemas().getVersionBySchema(topic, schemaInfo) );
                                schemaList.add(schemaDisplay);
                            }

                        } catch (Throwable t) {
                            // System.out.println("No schema for topic");
                        }
                    }

                    /**
                     * consume alerts
                     *
                     * bin/pulsar-client consume "persistent://public/default/schema-alert" -s schema-alert-reader -n 0
                     */
                    if ( schemaList.size() > 0) {
                        PulsarClient client = PulsarClient.builder()
                                .serviceUrl(url)
                                .build();

                        Producer<SchemaAlert> producer = client.newProducer(Schema.JSON(SchemaAlert.class))
                                .topic("persistent://public/default/schema-alert")
                                .create();

                        // TODO:   cache current schema definition and version and check if change
                        for (SchemaDisplay schema : schemaList) {
                            if ( schema != null ) {
                                UUID uuidKey = UUID.randomUUID();
                                SchemaAlert schemaAlert = new SchemaAlert();
                                schemaAlert.setTopic ( schema.getTopic());
                                schemaAlert.setSchemaType ( schema.getSchemaInfo().getType().toString() );
                                schemaAlert.setSchemaName ( schema.getSchemaInfo().getName() );
                                schemaAlert.setSchemaDefinition(  schema.getSchemaInfo().getSchemaDefinition() );
                                schemaAlert.setMessageId( admin.topics().getLastMessageId(schema.getTopic()).toString() );
                                schemaAlert.setUUID(uuidKey.toString() );
                                schemaAlert.setSchemaVersion( schema.getSchemaVersion() );

                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                schemaAlert.setTs( timestamp.getTime() );

                                Locale loc = new Locale("en", "US");
                                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);

                                schemaAlert.setAlertDateTime( dateFormat.format(new Date()) );
                                MessageId schemaAlertMessageID = producer.newMessage().
                                        key(uuidKey.toString())
                                        .value(schemaAlert)
                                        .send();

                                System.out.println("Sent Schema Alert " + schemaAlertMessageID.toString());
                                // TODO: Can make rest call to send to a registry
                                // TODO: Send to a database
                            }
                        }
                    }
                }
            } catch (PulsarAdminException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
