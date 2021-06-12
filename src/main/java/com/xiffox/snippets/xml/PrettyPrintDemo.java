package com.xiffox.snippets.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class PrettyPrintDemo {
    public static void main(String[] args) {
        String xmlStringIn = "<EMAIL><SUBJECT>Online Store - Welcome</SUBJECT><BODY>Dear Customer,<br><br>Welcome to Pretty Print Demo. Please contact us, if you have questions or concerns.<br><br><br>Thank you,<br>Online Store<br></BODY></EMAIL>";
        String xmlStringOut = null;
        xmlStringIn = xmlStringIn.replace("<br>","<br/>");
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "html");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StreamResult result = new StreamResult(new StringWriter());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlStringIn));
            Document doc = db.parse(is);

            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            xmlStringOut = result.getWriter().toString();
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        System.out.println(xmlStringOut);
    }
}
