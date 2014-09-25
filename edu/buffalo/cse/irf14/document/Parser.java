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
		String article = "";
		if(filepath == null)
		{
			throw new ParserException()	;
		}
		try
		{
		article = Utility.readStream(filepath);
		}
		catch(Exception ex)
		{
			// System.out.print(filepath);
			throw new ParserException();
		}
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
		//	String title = lines[0];
		//	modifiedArticle = modifiedArticle.replace(title, "").trim(); // title

		// removed
		String newsDate = null;
		String ArticlePlace = null;
		String Content = null;
		String Title = null;
		String AuthorOrg = null;
		StringBuilder AuthorBuilder = new StringBuilder();


		boolean isTitle = false;
		boolean isNextappended = false;
		boolean isDatePlaceSet = false;

		//
		StringBuilder titleBuilder = new StringBuilder("");

		for (int i=0; i<lines.length; i++) {

			String line = lines[i];

			if (line != null
					&& !line.replace("\n", "").replace("\r", "")
					.replace(" ", "").replace("\t", "").isEmpty()) {

				// title parsing starts
				if(!isTitle)
				{
					if(!isNextappended)
					{
						titleBuilder.append(line);
						modifiedArticle = modifiedArticle.replace(line, "");
					}
					String nextLine  = null;

					if(lines.length > i+1)
					{
						nextLine = lines[i+1];

						// if next line is empty end the loop
						if(nextLine == null ||nextLine.replace("\n", "").replace("\r", "")
								.replace(" ", "").replace("\t", "").isEmpty())
						{
							isTitle = true;

						}
						else
						{
							// else append to the existing title
							modifiedArticle = modifiedArticle.replace(nextLine, "");
							titleBuilder.append(" ").append(nextLine);
							isNextappended = true;
						}

					}
					else
					{
						isTitle = true;
					}
					if(isTitle)
					{
						Title = titleBuilder.toString().trim();
						documentObj.setField(FieldNames.TITLE,titleBuilder.toString().trim());
					}

				}
				// title manipulation ends

				// Author
				if (line.contains("<AUTHOR>")) {

					// handle author with multiple lines
					String authorLine = line;
					if(!line.contains("</AUTHOR>"))
					{
						// if author is multiline, pick until </AUTHOR> tag
						authorLine = modifiedArticle.substring(modifiedArticle.indexOf("<AUTHOR>"),
								modifiedArticle.indexOf("</AUTHOR>")+9);
					}

					// contains authors and organization
					String authorsAndOrg[] = authorLine.replace("<AUTHOR>", "")
							.replace("</AUTHOR>", "").replace(" By ", "")
							.replace(" by ", "").replace(" BY ", "").split(",");


					// Authors
					String[] Authors = authorsAndOrg[0]
							.trim().split(" and ");
					for (int j = 0; j < Authors.length; j++)
					{
						Authors[j] = Authors[j].trim();
						AuthorBuilder.append(",").append(Authors[j]);
					}
					documentObj.setField(FieldNames.AUTHOR, Authors );

					// Author organization
					if (authorsAndOrg.length > 1) {
						AuthorOrg = authorsAndOrg[1].trim();
						documentObj.setField(FieldNames.AUTHORORG,AuthorOrg);
					}


					modifiedArticle = modifiedArticle.replace(authorLine, "").trim(); // author
					// line
					// removed
					continue;
				}

				else if (line.contains("-") && line.contains(",") ) {

					isDatePlaceSet = true;
					String[] datePlace = line.split("-");
					Pattern p = Pattern.compile(".*,\\s*(.*)");
					Matcher m = p.matcher(datePlace[0]); // string before '-'
					if (m.find())
					{
						newsDate = m.group(1); // anything after last comma
						documentObj.setField(FieldNames.NEWSDATE,newsDate.trim() );

						// anything before last comma
						ArticlePlace = datePlace[0].substring(0,datePlace[0].lastIndexOf(","));
						documentObj.setField(FieldNames.PLACE,ArticlePlace.trim());

						// anything after - is content
						Content = modifiedArticle.substring(modifiedArticle.indexOf('-')+1);
						documentObj.setField(FieldNames.CONTENT,Content );


						break;
					}

				}

			}

		}
		if(!isDatePlaceSet)
		{
			// incase no date place is present, everything is content
			documentObj.setField(FieldNames.CONTENT,modifiedArticle );
		}

		System.out.println("Title: "+ Title);
		System.out.println("Author org: "+ AuthorOrg);
		System.out.println("Place: "+ArticlePlace);
		System.out.println("Date: "+newsDate );
		System.out.println("Authors: " + AuthorBuilder.toString());




		return documentObj;
	}

}
