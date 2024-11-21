package com.xml.demo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class DemoApplicationTests {

	private boolean isValidXML(File xmlFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.unmarshal(xmlFile);
			return true; // Valid XML
		} catch (JAXBException e) {
			return false; // Invalid XML
		}
	}

	private Customer parseXML(File xmlFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (Customer) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			return null; // Return null for invalid XML
		}
	}

	@Test
	void testValidXMLFile() {
		File validXMLFile = new File("src/main/java/com/xml/demo/sample1.xml");
		Assertions.assertTrue(isValidXML(validXMLFile), "The XML file should be valid.");
	}

	@Test
	void testInvalidXMLFile() {
		File invalidXMLFile = new File("src/main/java/com/xml/demo/invalid.xml");
		Assertions.assertFalse(isValidXML(invalidXMLFile), "The XML file should be invalid.");
	}

	@Test
	void testContent() {
		File validXMLFile = new File("src/main/java/com/xml/demo/sample1.xml");
		Customer customer = parseXML(validXMLFile);
		Assertions.assertNotNull(customer, "The XML file should be valid.");
		Assertions.assertEquals(99, customer.getId(), "Customer ID: 99");
		Assertions.assertEquals(29, customer.getAge(), "Customer Age: 29");
		Assertions.assertEquals("JC Diamante", customer.getName(), "Customer Name: JC Diamante");
	}
}
