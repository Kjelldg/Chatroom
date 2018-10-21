package xml_history;

import java.io.File;

import com.sun.xml.internal.ws.api.message.Packet;

public class Write_XML {

	public void write_Message(String user_Input_Message, String userName) {

		File xmlFile = new File("./UserMessage.xml");
		boolean xmlFileBooleanExists = xmlFile.exists();
		// String user_Input_Message_String = user_Input_Message.toString();

		/*
		 * Check if the file exists or not. If it exists, append the new message. If it
		 * doesn't exist, create a new XML file. This file is stored as
		 * "Usermessage.xml" in the root of the program.
		 */
		if (xmlFileBooleanExists == true) {
			System.out.println("File exists");

			Append_XML append_XML = new Append_XML();
			append_XML.append_XML(user_Input_Message, userName);

			System.out.println("Message appended.");
		} else {
			System.out.println("Creating XML file.");

			Create_XML_File create_XML_File = new Create_XML_File();
			create_XML_File.create_XML(user_Input_Message, userName);

			System.out.println("Done creating XML File");
		}

		Read_XML read_XML = new Read_XML();
		read_XML.Read_XML_File();

	}

}
