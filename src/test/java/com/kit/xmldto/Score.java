package com.kit.xmldto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Score {

    @JacksonXmlProperty(localName = "acc01")
    private Double langue;

    @JacksonXmlProperty(localName = "acc02")
    private Double math;

    @JacksonXmlProperty(localName = "acc03")
    private Double english;


}