package com.pulsardeveloper;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.schema.SchemaInfo;

import java.util.ArrayList;
import java.util.List;

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
//                                System.out.println("Topic: " + topic
//                            + "With Schema:" + schemaInfo.getName() +
//                                    ":" + schemaInfo.getType() + ":" +
//                                    schemaInfo.getSchemaDefinition());

                                schemaDisplay = new SchemaDisplay();
                                schemaDisplay.setSchemaInfo( schemaInfo);
                                schemaDisplay.setTopic( topic );
                                schemaList.add(schemaDisplay);
                            }

                        } catch (Throwable t) {
                            // System.out.println("No schema for topic");
                        }
                    }
                    for (SchemaDisplay schema : schemaList) {
                        if ( schema != null ) {
                            System.out.println("Topic: " + schema.getTopic()
                                    + "With Schema:" + schema.getSchemaInfo().getName() +
                                    ":" + schema.getSchemaInfo().getType() );

                            // + ":" +
                            //                                    schema.getSchemaInfo().getSchemaDefinition()

                            // Can make rest call to send to a registry
                            // Send to a database
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
