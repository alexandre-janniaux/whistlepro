package fr.enst.pact34.whistlepro.api2.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperator {

	public static ArrayList<String> listFiles(String folderName)
	{
		ArrayList<String> files = new ArrayList<>();

		File folder = new File(folderName);

		if(folder.isDirectory()) {

			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					files.addAll(listFiles(fileEntry.getName()));
				} else {
					files.add(fileEntry.getName());
				}
			}
		}

		return files;
	}

	/*** 
	 * @param fileName is name of the file where data will be saved.
	 * @param data to save
	 */
	public static void saveToFile(String fileName, String data)
	{ 
		BufferedWriter bos;  
	    try { 
	    	bos = new BufferedWriter(new FileWriter(new File(fileName))); 
	    	 
	    	bos.write(data);
	      	 
			bos.close();
	                
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
 
	}


	public static void saveToFile(File file, String data)
	{
		BufferedWriter bos;
		try {
			bos = new BufferedWriter(new FileWriter(file));

			bos.write(data);

			bos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void appendToFile(String fileName, String data)
	{
		BufferedWriter bos;
		try {
			bos = new BufferedWriter(new FileWriter(new File(fileName),true));

			bos.write(data);

			bos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	/***
	 * Open a file a load the data in a string.
	 * @param fileName is the name of the file to read.
	 * @return a string containing all the data.
	 */
	public static String getDataFromFile(String fileName)
	{
		BufferedReader bis; 
	    String readData = ""; 
	    try { 
	    	bis = new BufferedReader(new FileReader(new File(fileName))); 
	    	
	      	char[] buf = new char[256]; 
	      	int ret;
			while ((ret = bis.read(buf)) != -1)
				readData += new String(buf,0,ret);

			bis.close();
	                
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    return readData;
	}

	public static String getDataFromFile(File file)
	{
		BufferedReader bis;
		String readData = "";
		try {
			bis = new BufferedReader(new FileReader(file));

			char[] buf = new char[256];
			int ret;
			while ((ret = bis.read(buf)) != -1)
				readData += new String(buf,0,ret);

			bis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return readData;
	}
	

	public static ArrayList<String> getLinesFromFile(String fileName)
	{  
		ArrayList<String> lines = new ArrayList<String>(); 
		
		//on ouvre le fichier pour pouvoir lire dedans
		BufferedReader fichier = null;
		try {
			fichier = new BufferedReader(
					new FileReader(fileName));
				
			//variable tmp pour stocker la ligne lue
			String line; 
			
			//boucle parcourant le fichier ligne par ligne
			//et caractere par caractere sur chaque ligne
			while ((line = fichier.readLine()) != null)
				if(line.isEmpty()==false) lines.add(line);
		
		} catch (IOException e) {
				e.printStackTrace();
		}
		finally 
		{
			//on tente de fermer le fichier
			try { fichier.close();} catch (IOException e) {}
		}
		  
		return lines;
	}
}
