package edu.buffalo.cse.irf14.index;

import java.util.HashMap;

import edu.buffalo.cse.util.TrieNode;

public class IndicesDTO {
	public  HashMap<String, HashMap<Integer, String>> termIndex;
	public  HashMap<String, HashMap<Integer, String>> categoryIndex;
	public  HashMap<String, HashMap<Integer, String>> authorIndex;
	public  HashMap<String, HashMap<Integer, String>> placeIndex;
	public  HashMap<Integer, String> docIDLookup;
	public  TrieNode root ;
	public IndicesDTO()
	{
		this.termIndex = new HashMap<String, HashMap<Integer,String>>();
		this.categoryIndex = new HashMap<String, HashMap<Integer,String>>();
		this.authorIndex = new HashMap<String, HashMap<Integer,String>>();
		this.placeIndex = new HashMap<String, HashMap<Integer,String>>();
		this.docIDLookup = new HashMap<Integer, String>();
		root= new TrieNode(null, '?');
	}
	

}
