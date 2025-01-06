package com.example.first_camel_case.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.example.first_camel_case.model.ListContainer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class XMLService {
    public static void main(String[] args) {
        byte[] bytes;
        try (InputStream is = XMLService.class.getResourceAsStream("form.xml")) {
            bytes = new byte[is.available()];
            is.read(bytes); // without this bytes [] would be 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
            String xmlContent = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("XML Content:\n" + xmlContent);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            // Create JAXB context and initialize Unmarshaller
            JAXBContext context = JAXBContext.newInstance(ListContainer.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Unmarshalling: Convert XML byte[] to Java object
            ListContainer listContainer = (ListContainer) unmarshaller.unmarshal(byteArrayInputStream);
            List<ListContainer.StringElement> strings = listContainer.getStrings();
            for (ListContainer.StringElement stringElement : strings) {
                System.out.println("Name: " + stringElement.getName() + ", Value: " + stringElement.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}























