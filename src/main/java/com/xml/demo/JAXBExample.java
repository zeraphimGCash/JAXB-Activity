package com.xml.demo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.util.List;

public class JAXBExample {
    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setId(99);
        customer.setName("JC Diamante");
        customer.setAge(29);

        Customer customer2 = new Customer();
        customer2.setId(99);
        customer2.setName("Kyle Megino");
        customer2.setAge(29);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(customer, System.out);

            // generateMulti(List.of(customer, customer2));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void generateMulti(List<Customer> list) throws JAXBException {

    }
}
