package com.example.first_camel_case.processor;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathExecutor {
    private static final String DEF_CONDITION = "DEFAULT_CONDITION";
    private Map<String , String > xpathConditions = new HashMap<>();

    public XPathExecutor(String xpathExp) {
        this.xpathConditions.put(DEF_CONDITION, xpathExp);
    }

    public XPathExecutor(Map<String, String> xpathConditions) {
        this.xpathConditions = xpathConditions;
    }

    public boolean isMatchCustom (String xml, String type) {
        String condition = this.xpathConditions.get(DEF_CONDITION);
        return match(condition, xml, getPathReturnType(type));
    }

    public boolean isMatchCustom (String xml, String nameCondition, String type) {
        if (!this.xpathConditions.containsKey(nameCondition)) {
            throw new RuntimeException("This  XPath condition is not founded!");
        } else {
            return match(this.xpathConditions.get(nameCondition), xml, getPathReturnType(type));
        }

    }

    private static QName getPathReturnType(String type) {
        return switch (type.trim()) {
            case "NODESET" -> XPathConstants.NODESET;
            case "BOOLEAN" -> XPathConstants.BOOLEAN;
            case "NUMBER" -> XPathConstants.NUMBER;
            default -> throw new RuntimeException("Not defined return XPath type!");
        };
    }

    private static boolean match(String xpathExp, String sourceXml, QName qName) {
        try {
            return evaluate(xpathExp, sourceXml, qName);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed XPathExecutor:match(%s,%s)", xpathExp, sourceXml), e);
        }
    }

    private static Boolean evaluate(String xpathExp, String sourceXml, QName qName) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        InputSource inputSource = new InputSource(new ByteArrayInputStream(sourceXml.getBytes(StandardCharsets.UTF_8)));
        Object resultPath = xPath.evaluate(xpathExp, inputSource, qName);
        Boolean res =null;
        if (qName.equals(XPathConstants.NODESET)) {
            NodeList nodeList = (NodeList) resultPath;
            res = nodeList != null && nodeList.getLength() > 0;
        } else  if (qName.equals(XPathConstants.BOOLEAN)) {
            NodeList nodeList = (NodeList) resultPath;
            res = (Boolean) resultPath;
        } else if (qName.equals(XPathConstants.NUMBER)) {
            Double number = (Double) resultPath;
            res = number != null && number > 0.0;
        }
        return res;
    }

    public String getXPathExp() {
        return this.xpathConditions.get(DEF_CONDITION);
    }

    public String setXPathExp(String xpathExp) {
        return this.xpathConditions.put(DEF_CONDITION, xpathExp);
    }
}
