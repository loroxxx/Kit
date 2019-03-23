package com.kit.xmldto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "student")
public class Student<T> {

    @JacksonXmlProperty(localName = "dizhi")
    private String address;

    @JacksonXmlProperty(localName = "studentName")
    private String name;

    @JacksonXmlProperty(localName = "nainling")
    private Integer age;

    @JacksonXmlProperty(localName = "dh")
    private String phone;

    @JacksonXmlProperty(localName = "array")
    @JacksonXmlElementWrapper(localName = "rs")
    private List<T> grades;




}