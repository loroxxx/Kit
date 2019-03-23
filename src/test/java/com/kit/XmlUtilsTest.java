package com.kit;

import com.kit.xmldto.Score;
import com.kit.xmldto.Student;
import com.luo.kit.XmlUtils;

import java.io.IOException;

public class XmlUtilsTest {
    public static void main(String[] args) throws IOException {
        String xml = "<?xmldto version=\"1.0\" encoding=\"gb2312\" standalone=\"yes\"?>" +
                "<student>" +
                "<dizhi>北京三里屯</dizhi>" +
                "<studentName>张三</studentName>" +
                "<nainling>25</nainling>" +
                "<dh>18858863333</dh>" +
                "<rs>" +
                "<array>" +
                "<acc01>89.50</acc01>" +
                "<acc02>100.00</acc02>" +
                "<acc03>91.00</acc03>" +
                "</array>" +
                "<array>" +
                "<acc01>78.50</acc01>" +
                "<acc02>69.00</acc02>" +
                "<acc03>88.90</acc03>" +
                "</array>" +
                "</rs>" +
                "</student>";

        // util测试
        Student<Score> student2 = XmlUtils.xmlString2Object(xml, Student.class);
        System.out.println(student2);

        String xmlString = XmlUtils.Object2XmlString(student2);
        System.out.println(xmlString);
    }
}