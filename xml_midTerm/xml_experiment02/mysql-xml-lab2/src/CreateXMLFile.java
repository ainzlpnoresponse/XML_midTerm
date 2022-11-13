import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;

public class CreateXMLFile{

   static final String DB_URL = "jdbc:mysql://localhost:3306/midTerm";
   static final String USER = "adolph";
   static final String PASS = "123456";
   static final String QUERY = "SELECT student_id, xml_class, data_structure, algorithm, network FROM class";
   public static String estimateGPA(String scoreInput) {
	   
	   int score = Integer.parseInt(scoreInput);
	   if(score >= 90 && score <= 100) return "A+";
	   else if(score >= 85 && score <= 89) return "A";
	   else if(score >= 80 && score <= 84) return "A-";
	   else if(score >= 77 && score <= 79) return "B+";
	   else if(score >= 73 && score <= 76) return "B";
	   else if(score >= 70 && score <= 72) return "B-";
	   else if(score >= 67 && score <= 69) return "C+";
	   else if(score >= 63 && score <= 66) return "C";
	   else if(score >= 60 && score <= 62) return "C-";
	   else if(score >= 50 && score <= 59) return "D";
	   else if(score < 50) return "E";
	   else return null;

   }
   
   public static void main(String argv[]) {
	   
	   
      try {
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();

         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);

         Element rootElement = doc.createElement("class");
         doc.appendChild(rootElement);
         while(rs.next()) {
      		 String ID = rs.getString("student_id");
             String course1 = rs.getString("xml_class");
             String course2 = rs.getString("data_structure");
             String course3 = rs.getString("algorithm");
             String course4 = rs.getString("network");
	         // student element
	         Element student = doc.createElement("student");
	         rootElement.appendChild(student);

	         	 // student ID
         		 Attr attr = doc.createAttribute("student_id");
	   	         attr.setValue(ID);
	   	         student.setAttributeNode(attr);
	   	
	   	         // xml_class
	   	         Element xml_class = doc.createElement("xml_class");
	   	         xml_class.appendChild(doc.createTextNode(course1));
	   	         student.appendChild(xml_class);
	   	         
	   	         	 // xml_class GPA
		   	         String gpaAttr_decided = estimateGPA(course1);
		   	         attr.setValue(gpaAttr_decided);
		   	         xml_class.setAttributeNode(attr);
		   	         
	   	         // data_structure
	   	         Element data_structure = doc.createElement("data_structure");
	   	         data_structure.appendChild(doc.createTextNode(course2));
	   	         student.appendChild(data_structure);
			   	     // data_structure GPA
		   	         gpaAttr_decided = estimateGPA(course2);
		   	         attr.setValue(gpaAttr_decided);
		   	         data_structure.setAttributeNode(attr);
	   	         
	   	         // algorithm
	   	         Element algorithm = doc.createElement("algorithm");
	   	         algorithm.appendChild(doc.createTextNode(course3));
	   	         student.appendChild(algorithm);
			   	   	 // algorithm GPA
		   	         gpaAttr_decided = estimateGPA(course3);
		   	         attr.setValue(gpaAttr_decided);
		   	         algorithm.setAttributeNode(attr);
	   	         // network
	   	         Element network = doc.createElement("network");
	   	         network.appendChild(doc.createTextNode(course4));
	   	         student.appendChild(network);
				   	// network GPA
		   	         gpaAttr_decided = estimateGPA(course4);
		   	         attr.setValue(gpaAttr_decided);
		   	         network.setAttributeNode(attr);
         		
         	}
	         

         // write the content into xml file
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         //StreamResult result = new StreamResult(new File("C:\\cars.xml"));
         StreamResult result = new StreamResult(new File("test.xml"));
         transformer.transform(source, result);
         
         // Output to console for testing
         StreamResult consoleResult = new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}