/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.util.*;

/**
 * @author nikhillo Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * 
	 * @param filename
	 *            : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException
	 *             In case any error occurs during parsing
	 * @throws IOException 
	 */
	public static Document parse(String filepath) throws ParserException, IOException {
		// TODO YOU MUST IMPLEMENT THIS

		Document documentObj = new Document();

		// file not found todo
		
		String article = Utility.readStream(filepath);
		File fileObj = new File(filepath);
		String modifiedArticle = article.trim();
		String[] lines = modifiedArticle.split("\\r?\\n");

		// FileID
		String fileID = fileObj.getName();
		documentObj.setField(FieldNames.FILEID, fileID);

		// Category
		String category = fileObj.getParentFile().getName();
		documentObj.setField(FieldNames.CATEGORY, category);

		// Title
		String title = lines[0];
		modifiedArticle = modifiedArticle.replace(title, "").trim(); // title
																		// removed

		//
		for (int i=1; i<lines.length; i++) {
			
			String line = lines[i];
		
			if (line != null
					&& !line.replace("\n", "").replace("\r", "")
							.replace(" ", "").replace("\t", "").isEmpty()) {
				
				
				
				
				// Author
				if (line.contains("<AUTHOR>")) {
					// contains authors and organization
					String authorsAndOrg[] = line.replace("<AUTHOR>", "")
							.replace("</AUTHOR>", "").replace("By ", "")
							.replace("by ", "").replace("BY ", "").split(",");

					if (authorsAndOrg.length > 1) {
						documentObj.setField(FieldNames.AUTHORORG,
								authorsAndOrg[1].trim());
					}
						
					String[] Authors = authorsAndOrg[0]
							.trim().split("and");
					
					for (int j = 0; j < Authors.length; j++)
						Authors[j] = Authors[j].trim();
					
					documentObj.setField(FieldNames.AUTHOR, Authors );
					
					modifiedArticle = modifiedArticle.replace(line, "").trim(); // author
																				// line
																				// removed
					continue;
				}

				// Date and Place
				if (line.contains("-") ) {
				
					
						String[] datePlace = line.split("-");
						Pattern p = Pattern.compile(".*,\\s*(.*)");
						Matcher m = p.matcher(datePlace[0]);
						if (m.find())
						{
							String newsDate = m.group(1);
							documentObj.setField(FieldNames.NEWSDATE,newsDate.trim() );
							String ArticlePlace = datePlace[0].substring(0,datePlace[0].lastIndexOf(","));
							documentObj.setField(FieldNames.PLACE,ArticlePlace.trim());
							String Content = modifiedArticle.substring(modifiedArticle.indexOf('-')+1);
							documentObj.setField(FieldNames.CONTENT,Content );
							break;
						}

				}

			}

		}

		return null;
	}

}
