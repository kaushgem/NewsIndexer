package edu.buffalo.cse.irf14.index;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.buffalo.cse.util.TrieNode;

public class IndicesDTO {
	public  HashMap<String, HashMap<Integer, String>> termIndex;
	public  HashMap<String, HashMap<Integer, String>> categoryIndex;
	public  HashMap<String, HashMap<Integer, String>> authorIndex;
	public  HashMap<String, HashMap<Integer, String>> placeIndex;
	public  HashMap<Integer, String> docIDLookup;
	public  HashMap<Integer, Integer> docLength;
	public float averageDocLength; 
	public  TrieNode root ;
	
	public IndicesDTO()
	{
		this.termIndex = new HashMap<String, HashMap<Integer,String>>();
		this.categoryIndex = new HashMap<String, HashMap<Integer,String>>();
		this.authorIndex = new HashMap<String, HashMap<Integer,String>>();
		this.placeIndex = new HashMap<String, HashMap<Integer,String>>();
		this.docIDLookup = new HashMap<Integer, String>();
		this.docLength = new HashMap<Integer, Integer>();
		root= new TrieNode(null, '?');
	}
	
	private void calculateAverageDocLength()
	{
		int noOfDocs = docLength.size();
		long totalLength = 0;
		for(Entry<Integer, Integer> e : docLength.entrySet()) {
			totalLength += e.getValue();
			//if(totalLength<1000)
			//System.out.println(totalLength);
		}
		//System.out.println("H"+ docLength);
		//System.out.println("$$ total "+totalLength+" no "+noOfDocs);
		averageDocLength = totalLength / noOfDocs;
	}
	
	public float getAverageDocLength(){
		calculateAverageDocLength();
		return averageDocLength;
	}
}
