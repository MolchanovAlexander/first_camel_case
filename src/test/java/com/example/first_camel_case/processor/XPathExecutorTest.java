package com.example.first_camel_case.processor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class XPathExecutorTest {

    @Test
    public void isMatchCustom() throws IOException {
        InputStream inputStream = XPathExecutor.class.getResourceAsStream("/XP_1.xml");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String xml = new String(bytes, StandardCharsets.UTF_8);
        Map<String, String> conditions = new HashMap<>();
        conditions.put("isJUNI", "mqdoc/string[@name='PRODUCT_CARD' and text()='JUNI']");
        conditions.put("isContractJUNI", "mqdoc/xml[@name='OLD_CARD_DATA']/ContractData[@prod='JUNI']");
        XPathExecutor instance = new XPathExecutor(conditions);
        boolean result1 = instance.isMatchCustom(xml, "isJUNI", "BOOLEAN");
        assertTrue(result1);

        inputStream = XPathExecutor.class.getResourceAsStream("/XP_2.xml");
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        xml = new String(bytes, StandardCharsets.UTF_8);
        boolean result2 = instance.isMatchCustom(xml, "isContractJUNI", "BOOLEAN");
        assertTrue(result2);

        inputStream = XPathExecutor.class.getResourceAsStream("/XP_3.xml");
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        xml = new String(bytes, StandardCharsets.UTF_8);
        boolean result3 = instance.isMatchCustom(xml, "isContractJUNI", "BOOLEAN");
        System.out.println(xml);
        assertFalse(result3);
    }

    @Test
    public void isMatchCustomCombine() throws IOException {
        InputStream inputStream = XPathExecutor.class.getResourceAsStream("/XP_2.xml");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String xml = new String(bytes, StandardCharsets.UTF_8);
        Map<String, String> conditions = new HashMap<>();
        conditions.put("isJUNI", "mqdoc/string[@name='PRODUCT_CARD' and text()='JUNI']");
        conditions.put("isJUNICombinedOr", "mqdoc[string[@name='PRODUCT_CARD' and text()='JUNI'] " +
                                                "or xml[@name='OLD_CARD_DATA']/ContractData[@prod='JUNI']]");
        XPathExecutor instance = new XPathExecutor(conditions);
        boolean result1 = instance.isMatchCustom(xml, "isJUNICombinedOr", "BOOLEAN");
        assertTrue(result1);
    }
}