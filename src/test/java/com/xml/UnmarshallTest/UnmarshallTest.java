package com.xml.UnmarshallTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class UnmarshallTest {

    @Test
    void testUnmarshalValidXML() throws Exception {
        // Setup
        File validXMLFile = new File("src/main/java/com/xml/Unmarshall/sample1.xml");

        Unmarshall unmarshall = new Unmarshall();

        // Act
        Customer customer = unmarshall.unmarshalXML(validXMLFile);

        // Assert
        Assertions.assertNotNull(customer, "The customer object should not be null.");
        Assertions.assertEquals(99, customer.getId(), "Customer ID should match.");
        Assertions.assertEquals(29, customer.getAge(), "Customer age should match.");
        Assertions.assertEquals("JC Diamante", customer.getName(), "Customer name should match.");
    }

    @Test
    void testUnmarshalInvalidXML() {
        // Setup
        File invalidXMLFile = new File("src/test/resources/invalid.xml");
        Unmarshall unmarshall = new Unmarshall();

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> unmarshall.unmarshalXML(invalidXMLFile),
                "An exception should be thrown for invalid XML.");
    }
}
