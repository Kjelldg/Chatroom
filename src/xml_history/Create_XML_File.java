package xml_history;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Create_XML_File {

	public void create_XML(String user_Input_Message, String userName) {

		final String xmlFilePathRelative = "./UserMessage.xml";

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// Messages element
			Element root = document.createElement("Messages");
			document.appendChild(root);

			// Message element
			Element message = document.createElement("Message");
			root.appendChild(message);

			// The created_by attribute.
			Attr author_Attribute = document.createAttribute("Created_By");
			author_Attribute.setValue(userName);
			message.setAttributeNode(author_Attribute);

			// The date attribute.
			Attr date_Attribute = document.createAttribute("Date");
			date_Attribute.setValue(dateFormat.format(date));
			message.setAttributeNode(date_Attribute);

			// Message element
			Element messageText = document.createElement("Message_text");
			messageText.appendChild(document.createTextNode(user_Input_Message));
			message.appendChild(messageText);

			// Create the XML file.
			// Transform the DOM Object to an XML File.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			StreamResult streamResult = new StreamResult(new File(xmlFilePathRelative));
			transformer.transform(domSource, streamResult);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

}
