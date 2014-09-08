/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
	 */
	public static Document parse(String filepath) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS

		Document documentObj = new Document();

		// file not found todo
		InputStream inputStreamObj = new FileInputStream(filepath);
		String article = Utility.readStream(inputStreamObj);
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
		for (String line : lines) {

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

					documentObj.setField(FieldNames.AUTHOR, authorsAndOrg[0]
							.trim().split(" and "));
					modifiedArticle = modifiedArticle.replace(line, "").trim(); // author
																				// line
																				// removed
					continue;
				}

				// Date and Place
				if (line.contains(",")) {
					String[] datePlace = line.split(",");
					String place = datePlace[0];
					documentObj.setField(FieldNames.PLACE, place);
					modifiedArticle = modifiedArticle.replace(place, "").trim(); // place
																					// removed
					if (datePlace[1].contains("-")) {
						String newsDate = datePlace[1].split("-")[0];
						documentObj.setField(FieldNames.NEWSDATE, newsDate);
						modifiedArticle = modifiedArticle.replace("-", "")
								.replace(newsDate, "").trim();

					}

					// after removing all metadata, modifiedArticle holds the
					// content.
					documentObj.setField(FieldNames.CONTENT, modifiedArticle);

				}

			}

		}

		return null;
	}

}
