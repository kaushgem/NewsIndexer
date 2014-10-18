/**
 * 
 */
package edu.buffalo.cse.util;

/**
 * @author Kaushik
 *
 */
public class RankCalc {
	
	// IDF
	// log10(numDocs/(double)(docFreq+1)) + 1.0);
	
	public static float calculateIDF(int totalDocs, int noOfDocsTermOccurs) {
		float idf = (float)(Math.log10(totalDocs/noOfDocsTermOccurs+1) +1) ;
		return idf;
	}
	
	
	
	// TF-IDF
	// log(tf+1) + log10(N/df)
	
	public static float calculateTFIDF(int tf, float idf) {
		float tfidf = (float) (Math.log(tf+1) + idf);
		return tfidf;
	}
	

}
