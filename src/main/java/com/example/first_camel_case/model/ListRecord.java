package com.example.first_camel_case.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

@Setter
@XmlRootElement
public class ListRecord {
    String str1;
    String str2;

    public ListRecord() {
    }

    public ListRecord(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    @XmlElement
    public String getStr1() {
        return str1;
    }

    @XmlElement
    public String getStr2() {
        return str2;
    }

}
