package converter;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlConverter {
	public static void convertXML(){
		try{
			File file = new File("/home/milkyklim/Documents/experiment/Deconvolved-mamut-raw.xml");
			DocumentBuilderFactory dbFactrory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactrory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Spot"); 			
			System.out.println("Spots processed: " + nList.getLength());

			// how to retrieve the values here?!?!!?

			double [] position = new double[3]; // stores x,y,z coordinates 
			String [] coordinates = new String[]{"POSITION_X", "POSITION_Y", "POSITION_Z"};


			double[] min = new double[] {57, 486, 86};
			//double[] max  = new double[] {667, 1510, 764};


			for (int j = 0; j < nList.getLength(); j++){
				Element eElement = (Element) nList.item(j); // get the j'th Spot
				
				for (int d = 0; d < position.length; ++d){
					position[d] = Double.valueOf(eElement.getAttribute(coordinates[d]));
					System.out.println("Before: " + d + " "+ position[d] + " ");
					// position[d] *= 2;
					//position[d] += min[d];
					
					position[d] += min[d];
					//position[d] *= 2;
					
					eElement.setAttribute(coordinates[d], String.valueOf(position[d]));
					System.out.println("After:  " + d + " "+ Double.valueOf(eElement.getAttribute(coordinates[d])) + " ");
				}	
			}
			

			// FIXME: changes the order of the attributes
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("/home/milkyklim/Documents/experiment/Deconvolved-mamut-raw-modified.xml"));
			transformer.transform(source, result);
			

		}

		catch(Exception e){

		}



	}

	public static void main(String[] args){
		XmlConverter.convertXML();
		System.out.println("Doge!");
	}
}
