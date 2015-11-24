package io.wellbeings.anatome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

/**
 * Provide specific access to main content
 * file, with bespoke functions.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
public class UserFileManager {

	private static UserFileManager instance;
	
	/**
	 * Method models the Singleton design pattern, which
	 * serves to ensure that only one class is interpolating
	 * with local user files.
	 * 
	 * @return	The only available instance of 'UserFileManager'.
	 */
	public static UserFileManager getInstance() {
		
		// If one does not exist.
		if(instance == null) {
			// Create one.
			instance = new UserFileManager();
		}
		return instance;
		
	}
	
	protected UserFileManager() {
		
	}
	
	// Global 'comment' to be applied as a header to stored settings files.
	final static String COMMENT = null;

	/**
	 * This method evaluates whether a file exists to be acted upon.
	 * 
	 * @param filePath	A string pointing to the file location.
	 * @return			Boolean value indicating file's existence.
	 */
	public static boolean fileExists(String filePath) {

		// Returns true if the file is a determined as proper by system criteria.
		return new File(filePath).isFile();
		
	}
	
	/**
	 * This method asserts that a configuration file exists for
	 * the specified path, and if it does not it creates one.
	 * 
	 * @param filePath	A string pointing to the file location.
	 * @return file	A file located at the specified file path.
	 */
	public static File assertFile(String filePath) {
		
		// Create a new file object pointing to the path specified.
		File file = new File(filePath);
				
		// If the file does not exist...
		if(!file.isFile()) {
			
			// Attempt to create it.
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
			
		}
		
		// Return it.
		return file;
		
	}
	
	/**
	 * This method retrieves a value from a configuration file; it compounds checking for
	 * the existence of a property with its return.
	 * 
	 * @param name						The name of the property to retrieve.
	 * @param file						The file from which to retrieve the property.
	 * @return							The value of the property if it exists.
	 * @throws IOException 				If it cannot interact with the specified file.
	 * @throws FileNotFoundException	If the specified file cannot be found.
	 */
	public static String getProperty(String name, File file) throws FileNotFoundException, IOException {
		
		// Properties object to act upon.
		Properties settingsFile = new Properties();
		
		// Load a specific file into it.
		settingsFile.load(new FileInputStream(file));
		
		// If the file contains the property...
		if(settingsFile.containsKey(name)) {
			
			// Return it as a string to be cast.
			return settingsFile.getProperty(name);
		
		}
		else {
			
			// Otherwise return null to imply a failure.
			return null;
			
		}
		
	}
	
	/**
	 * This method writes a property to a configuration file.
	 * 
	 * @param name						The name of the property to set.
	 * @param property					The property itself.
	 * @param file						The file upon which to apply the property.
	 * @throws IOException 				If it cannot interact with the specified file.
	 * @throws FileNotFoundException	If the specified file cannot be found.		
	 */
	public static <E> void setProperty(String name, E property, File file) throws FileNotFoundException, IOException {
		
		// Properties object to act upon.
		Properties settingsFile = new Properties();
		
		// Load a specific file into it.
		settingsFile.load(new FileInputStream(file));
		
		// Write a property to it.
		settingsFile.setProperty(name, property.toString());
		
		// Save the file.
		settingsFile.store(new FileOutputStream(file), COMMENT);
		
	}
	
	/**
	 * This method loads an object stored at the specified file path.
	 * 
	 * @param filePath					The determined path to the stored object.
	 * @return							The object read from the file.
	 * @throws IOException 				If it cannot interact with the specified file.
	 * @throws FileNotFoundException	If the specified file cannot be found.
	 * @throws ClassNotFoundException	If there is no such class pertaining to the object.
	 */
	public static Object loadObject(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		// Create an object input stream pointing to the specified path.
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
		
		// Store temporarily.
		Object obj = in.readObject();
		
		// Close the resource stream.
		in.close();
		
		// Read and return the object from that locale.
		return obj;
		
	}
	
	/**
	 * This method writes a 'serializable' object to a specified file path. 
	 * 
	 * @param object					The object to be stored.
	 * @param filePath					The determined path to the stored object.
	 * @throws IOException 				If it cannot interact with the specified file.
	 * @throws FileNotFoundException	If the specified file cannot be found.
	 */
	public static <E> void saveObject(E object, String filePath) throws FileNotFoundException, IOException {
		
		// Create an object output stream pointing to the specified path.
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
		
		// Write an object to that locale.
		out.writeObject(object);
		
		// Release resources allocated to this object.
		out.flush();
		out.close();
		
	}
	
}
