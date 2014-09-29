package edu.buffalo.cse.util;

import java.io.BufferedReader;
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

			br.close();
		}
		return everything;
	}
}
