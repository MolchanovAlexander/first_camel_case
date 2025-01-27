package com.example.first_camel_case.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "junicardResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class JunicardResponse {

    @XmlElement(name = "pan")
    String pan;

    @XmlElement(name = "expDate")
    String expDate;

    @XmlElement(name = "panExpDate")
    String panExpDate;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPanExpDate() {
        return panExpDate;
    }

    public void setPanExpDate(String panExpDate) {
        this.panExpDate = panExpDate;
    }

    @Override
    public String toString() {
        return "JunicardResponse{" +
                "pan='" + getPan() + '\'' +
                ", expDate='" + getExpDate() + '\'' +
                ", panExpDate='" + getPanExpDate() + '\'' +
                '}';
    }
}
