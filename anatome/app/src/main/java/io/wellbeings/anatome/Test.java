package io.wellbeings.anatome;

import java.io.IOException;

public class Test {

	public static void main (String[] args) {
		ContentLoader contentLoader = null;
		try {
			contentLoader = new ContentLoader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print(contentLoader.getNodeAttrValWithXPath("application/content[@lang='en']/section[@name='brain']/information[@id='title']", "id"));		
	}
	
	
	
}
