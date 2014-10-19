package edu.buffalo.cse.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utility {
	// reference:
	// http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
	public static String readStream(String fileName) throws IOException {

		BufferedReader br = null;
		String everything = null;
		try {
			br = new BufferedReader(new FileReader(fileName));

			StringBuilder sb = new StringBuilder();
			String line;
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if(br!=null)
			br.close();
		}
		return everything;
	}
	
	
	
	public static String readStreamFileObj(File queryFile) throws IOException {

		BufferedReader br = null;
		String everything = null;
		try {
			br = new BufferedReader(new FileReader(queryFile));

			StringBuilder sb = new StringBuilder();
			String line;
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {

			br.close();
		}
		return everything;
	}
	
	
	
	static public String join(String[] list, String conjunction)
	{
	   StringBuilder sb = new StringBuilder();
	   boolean first = true;
	   for (String item : list)
	   {
	      if (first)
	         first = false;
	      else
	         sb.append(conjunction);
	      sb.append(item);
	   }
	   return sb.toString();
	}
	
	public static boolean createEmptyDir(String filepath)
	{
		File f = new File(filepath);
		try{
		    if(f.mkdir()) { 
		        return true;
		    } else {
		        return false;
		    }
		} catch(Exception e){
		    e.printStackTrace();
		} 
		return false;
	}
	
	
}
