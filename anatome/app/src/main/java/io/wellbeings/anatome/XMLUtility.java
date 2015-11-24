package io.wellbeings.anatome;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
	private STATUS utilityStatus;
	
	// Hold locations of relevant files.
	protected String xmlDocumentPath;
	protected String xmlDocumentSchemaPath;
	
	// Hold Java representation of document for traversal.
	private Source xmlDocument;
	private XPath xPath;
	
	/**
	 * Constructor to be called with literal
	 * values by children to ensure no changes
	 * after compilation. 
	 * 
	 * @param xmlDocumentPath		Relative or absolute path to document.
	 * @param xmlDocumentSchemaPath Relative or absolute path to linked schema.
	 * @throws IOException 			Break in the case of a fatal error.
	 */
	protected XMLUtility(final String xmlDocumentPath, final String xmlDocumentSchemaPath) throws IOException {
		
		this.xmlDocumentPath = xmlDocumentPath;
		this.xmlDocumentSchemaPath = xmlDocumentSchemaPath;
		
		// Attempt to start to set-up the utility using the arguments provided.
		utilityStatus = initialize();
		
	}
	
	/**
	 * Provide proper initialisation of XML
	 * source with iterative error checking.
	 */
	@Override
	public STATUS initialize() throws IOException {
		
		/* Ensure both necessary files point somewhere. */
		
		// Create XML File object representation.
		File tempFile = new File(xmlDocumentPath);
		// Check main criteria necessary to act upon it.
		if(!tempFile.exists() || !tempFile.isFile() || !tempFile.canRead()
				|| !tempFile.getName().contains(".xml")) {
			throw new IOException("File '" + xmlDocumentPath + "'is inaccessible."
					+ "Please ensure that the file's path is correctly formed and that it is readable '.xml'.");
		}
		
		// Load '.xml' file as Java Source object to perform validation upon.
		xmlDocument = new StreamSource(new File(xmlDocumentPath));
		
		// By this point, file exists, can safely set up 'XPath' for querying.
		xPath = XPathFactory.newInstance().newXPath();
		
		// Create XSD Schema object representation.
		Schema schema = null;
		try {
			schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
					.newSchema(new File(xmlDocumentSchemaPath));
		} catch (SAXException e) {
			return STATUS.FAIL;
		}		
		
		return validateAgainstSchema(xmlDocument, schema);
		
	}

	/**
	 * Shutdown method ensures all components are flushed,
	 * expected to be overridden by child class if necessary.
	 */
	@Override
	public STATUS shutdown() {
		return STATUS.INACTIVE;
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
	public static STATUS validateAgainstSchema(Source xml, Schema xsd) {
		
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
		
	}
	
	/**
	 * Finds all nodes matching the XPath query.
	 * 
	 * @param xPathExpression	The expression referring to a group of nodes.
	 * @return 					Set of nodes found using XPath expression.
	 */
	protected NodeList getNodesWithXPath(String xPathExpression) {
		
		// Create a node-list to store results.
		NodeList results = null;
		
		// Attempt to find the target nodes using the expression provided.
		try {
			results = (NodeList) xPath.evaluate(xPathExpression, new InputSource(xmlDocumentPath), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			System.out.println(e.getMessage());
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
		return getNodesWithXPath(xPathExpression).item(0);
		
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
		return getNodeWithXPath(xPathExpression).getTextContent().trim();
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