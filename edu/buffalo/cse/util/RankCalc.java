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
		float idf = (float)(Math.log10(totalDocs/noOfDocsTermOccurs)) ;
		//idf = idf*100 / totalDocs;
		System.out.println("*1* IDF : "+idf+" = log10( "+totalDocs+" / "+noOfDocsTermOccurs+" )");
		return idf;
	}


	// TF-IDF
	// log(tf+1) * log10(N/df)
	public static float calculateTFIDF(int tf, float idf) {
		float tfidf = (float) (Math.log10(tf+1) * idf);
		//Normalization
		System.out.println("*2* TFIDF : "+tfidf+" = log10( "+tf+" + 1 ) * "+idf);
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
		int k1 = 1;
		int b = 2;
		int k3 = 1;

		float eqnPart2 = ( (k1+1)*tf )  /  ( k1*( (1-b) + b*(docLen / aveDocLen) ) + tf);
		float eqnPart3 = ( (k3+1)*tfQ )  /  ( k3+tfQ );

		System.out.println("*3* OKA PARAMS tf:"+tf+" idf:"+idf+" doclen:"+docLen+" ave:"+aveDocLen+" tfQ:"+tfQ);

		float okapi = idf * eqnPart2 * eqnPart3;

		System.out.println("*3* OKAPI = "+okapi);
		return okapi;
	}
}
