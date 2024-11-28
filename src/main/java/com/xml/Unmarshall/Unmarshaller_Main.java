package com.xml.Unmarshall;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Unmarshaller_Main {
    public static void main(String[] args) {
        // Import File
        File xmlFile = new File("src/main/java/com/xml/Unmarshall/sample1.xml");

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

            // Prompt user for a dynamic label and value
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n============ Adding New Data: ============\n");
            System.out.print("\nEnter the new label: ");
            String label = scanner.nextLine();
            System.out.print("Enter the value for " + label + ": ");
            String value = scanner.nextLine();

            // Add the dynamic label and value to the customer
            Map<String, String> additionalFields = customer.getAdditionalFields();
            if (additionalFields == null) {
                additionalFields = new HashMap<>();
            }
            additionalFields.put(label, value);
            customer.setAdditionalFields(additionalFields);

            // Save the Customer object into a new XML file
            File outputXmlFile = new File("src/main/java/com/xml/Unmarshall/updated.xml");

            // Create a Document instance
            Document doc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Create root element
            Element customerElement = doc.createElement("customer");
            customerElement.setAttribute("id", String.valueOf(customer.getId()));
            doc.appendChild(customerElement);

            // Create and append age element
            Element ageElement = doc.createElement("age");
            ageElement.appendChild(doc.createTextNode(String.valueOf(customer.getAge())));
            customerElement.appendChild(ageElement);

            // Create and append name element
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(customer.getName()));
            customerElement.appendChild(nameElement);

            // Create and append dynamic elements (additional fields)
            Map<String, String> dynamicFields = customer.getAdditionalFields();
            if (dynamicFields != null) {
                for (Map.Entry<String, String> entry : dynamicFields.entrySet()) {
                    Element dynamicElement = doc.createElement(entry.getKey());  // Use dynamic label as tag
                    dynamicElement.appendChild(doc.createTextNode(entry.getValue()));
                    customerElement.appendChild(dynamicElement);
                }
            }

            // Transform the Document to XML and apply pretty printing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Write the content into the file
            StreamResult result = new StreamResult(outputXmlFile);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

            System.out.print("\n============ Output ============\n");
            System.out.println("Customer object has been saved to updated.xml.");

        } catch (Exception e) {
            // If an error occurs
            System.out.println("Error during XML processing.");
            e.printStackTrace();
        }
    }
}
