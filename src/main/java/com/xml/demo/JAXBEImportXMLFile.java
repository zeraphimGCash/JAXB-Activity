package com.xml.demo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class JAXBEImportXMLFile {
    public static void main(String[] args) {
        // Import File
        File xmlFile = new File("src/main/java/com/xml/demo/sample1.xml");

        if (!xmlFile.exists() || !xmlFile.isFile()) {
            System.out.println("The file 'sample1.xml' does not exist in the expected location: " + xmlFile.getAbsolutePath());
            return;
        }

        try {
            // Validate the XML file
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Attempt to unmarshal the XML
            Customer customer = (Customer) unmarshaller.unmarshal(xmlFile);

            // If successful
            System.out.println("Valid XML File.");
            System.out.println("Checking values...");

            // Check specific values
            if (customer.getId() == 99) {
                System.out.println("Customer ID: 99");
            } else {
                System.out.println("Customer ID does not match.");
            }

            if (customer.getAge() == 29) {
                System.out.println("Customer Age: 29");
            } else {
                System.out.println("Customer age does not match.");
            }

            if ("JC Diamante".equals(customer.getName())) {
                System.out.println("Customer Name: JC Diamante");
            } else {
                System.out.println("Customer name does not match.");
            }

        } catch (JAXBException e) {
            // If an error occurs
            System.out.println("Invalid XML file format.");
        }
    }
}