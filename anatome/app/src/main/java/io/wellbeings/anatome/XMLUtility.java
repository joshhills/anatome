package io.wellbeings.anatome;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Provide generic access to XML
 * files for extensibility, with
 * validation against schema.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
abstract class XMLUtility implements Utility {

	// Log status of utility.
	protected STATUS utilityStatus;
	
	// Hold location streams of relevant files.
	protected InputStream xmlDocument;
	protected InputStream xmlDocumentSchema;
	
	// Hold Java representation of document for traversal.
	private Source xmlDocumentSource;
	private Source xmlDocumentSchemaSource;
	private XPath xPath;

	// Create copies of the stream to navigate.
	ByteArrayOutputStream baos = null;
	
	/**
	 * Constructor to be called with resources
	 * delivered by calling context.
	 * 
	 * @param xmlStream			The '.xml' file resource as an initialized stream.
	 * @param xmlSchemaStream 	The '.xsd' file resource as an initialized stream.
	 * @throws IOException 		Break in the case of a fatal error.
	 */
	protected XMLUtility(InputStream xmlStream, InputStream xmlSchemaStream) {
		
		this.xmlDocument = xmlStream;
		this.xmlDocumentSchema = xmlSchemaStream;
		
		// Attempt to start to set-up the utility using the arguments provided.
		try {
			utilityStatus = initialize();
		} catch (IOException e) {
			utilityStatus = STATUS.FAIL;
		}
		
	}
	
	/**
	 * Provide proper initialisation of XML
	 * source with iterative error checking.
	 */
	@Override
	public STATUS initialize() throws IOException {

		// Load files as Java Source objects to perform validation upon.
		xmlDocumentSource = new StreamSource(xmlDocument);
		xmlDocumentSchemaSource = new StreamSource(xmlDocumentSchema);
		
		// By this point, file exists, can safely set up 'XPath' for querying.
		xPath = XPathFactory.newInstance().newXPath();

		// Allow copying of input stream to reset it after queries.
		baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;
		while((len = xmlDocument.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();

		// By this point, if it has not thrown an exception, set status of Utility to active.
		return STATUS.ACTIVE;
		
	}

	/**
	 * Shutdown method ensures all components are flushed,
	 * expected to be overridden by child class if necessary.
	 */
	@Override
	public STATUS shutdown() {

		// Close streams.
		try {
			xmlDocument.close();
			xmlDocumentSchema.close();
		} catch(IOException e) {
			return STATUS.FAIL;
		}

		return STATUS.NONE;
	}
	
	/* Feature-set to inherit. */
	
	/**
	 * Static utility method allows any-time validation
	 * of a valid source of XML against a valid XSD.
	 * 
	 * @param xml	The XML source to be evaluated.
	 * @param xsd	The Schema by which to validate.
	 * @return		The validation status.
	 */
	/*public static STATUS validateAgainstSchema(Source xml, Schema xsd) {
		
		// Create a validator from the schema.
		Validator validator = xsd.newValidator();
		
		// Attempt to validate document.
		try {
			validator.validate(xml);
		} catch (SAXException | IOException e) {
			System.out.println("Validator: " + e.getMessage());
			return STATUS.FAIL;
		}
		
		return STATUS.SUCCESS;
		
	}*/
	
	/**
	 * Finds all nodes matching the XPath query.
	 * 
	 * @param xPathExpression	The expression referring to a group of nodes.
	 * @return 					Set of nodes found using XPath expression.
	 */
	protected NodeList getNodesWithXPath(String xPathExpression) {

		// Create a node-list to store results.
		NodeList results = null;

		// Copy the XML input to perform navigation upon.
		InputStream isCopy = new ByteArrayInputStream(baos.toByteArray());

		// Attempt to find the target nodes using the expression provided.
		try {
			results = (NodeList) xPath.evaluate(xPathExpression, new InputSource(isCopy), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return results;
		
	}
	
	/**
	 * Returns the first node found by XPath expression
	 * 
	 * @param xPathExpression	The expression referring to a target node.
	 * @return Node || null		The first node returned by the XPath expression.
	 */
	protected Node getNodeWithXPath(String xPathExpression) {
		
		// Return the first node.
		NodeList results =  getNodesWithXPath(xPathExpression);

		// Check for nullity.
		return (results == null) ? null : results.item(0);

	}
	
	/**
	 * Retrieve a specific node's attribute's value.
	 * 
	 * @param xPathExpression	The expression referring to a target node.
	 * @param attrName			The attribute name for which to retrieve the value.
	 * @return String || null	The value of the attribute if found.
	 */
	protected String getNodeAttrValWithXPath(String xPathExpression, String attrName) {
		
		// Retrieve the right node.
		Node target = getNodeWithXPath(xPathExpression);
		
		// So long as the target found...
		if(target != null) {
			// Loop through the node's attributes until the correct one is found.
			for(int i = 0; i < target.getAttributes().getLength(); i++) {
				if(target.getAttributes().item(i).getNodeName().equals(attrName)) {
					return target.getAttributes().item(i).getNodeValue();
				}
			}
		}
		return null;
		
	}
	
	/**
	 * Retrieve text from a specific node in the object's XML source,
	 * perform minor formatting on it.
	 * 
	 * @param xPathExpression	The expression referring to a target node.
	 * @return String || null	The textual content of the first node returned by the XPath expression.
	 */
	protected String getNodeContentWithXPath(String xPathExpression) {

		// Retrieve the target node.
		Node target = getNodeWithXPath(xPathExpression);

		// Check for nullity and format result.
		return ((target == null) ? null : target.getTextContent().trim());

	}
	
	// TODO: Implement node-writing function.
	protected STATUS writeNodeToFile() {
		return STATUS.NONE;
	}
	
	@Override
	public STATUS getState() {
		return utilityStatus;
	}

}