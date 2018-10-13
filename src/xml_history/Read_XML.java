package xml_history;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Read_XML {

	public void Read_XML_File() {

		File file = new File("./UserMessage.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		Document document;

		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName("Message");

			for (int i = 0; i < nodeList.getLength(); i++) {

				System.out.println(
						"Created by: " + nodeList.item(i).getAttributes().getNamedItem("Created_By").getNodeValue());
				System.out.println(
						"Date created: " + nodeList.item(i).getAttributes().getNamedItem("Date").getNodeValue());

				System.out.println("Message: " + document.getElementsByTagName("Message").item(i).getTextContent());
				System.out.println("---------------------------------");

			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

}
