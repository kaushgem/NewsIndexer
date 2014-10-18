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

	
	// Okapi
	public static float calculateOkapi(
			int tf,
			float idf,
			int docLen,
			float aveDocLen,
			int tfQ)
	{
		int k1 = 0;
		int b = 0;
		int k3 = 0;

		float eqnPart2 = ( (k1+1)*tf )  /  ( k1*( (1-b) + b*(docLen / aveDocLen) ) + tf);
		float eqnPart3 = ( (k3+1)*tfQ )  /  ( k3+tfQ );

		float okapi = idf * eqnPart2 * eqnPart3;

		return okapi;
	}
}
