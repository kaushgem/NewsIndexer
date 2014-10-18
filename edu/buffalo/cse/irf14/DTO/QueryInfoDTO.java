/**
 * 
 */
package edu.buffalo.cse.irf14.DTO;

import edu.buffalo.cse.irf14.index.IndexType;

/**
 * @author kaush
 *
 */
public class QueryInfoDTO {
	private String queryTerm;
	private IndexType type;
	private int freq;
	
	/**
	 * @return the queryTerm
	 */
	public String getQueryTerm() {
		return queryTerm;
	}
	/**
	 * @param queryTerm the queryTerm to set
	 */
	public void setQueryTerm(String queryTerm) {
		this.queryTerm = queryTerm;
	}
	/**
	 * @return the type
	 */
	public IndexType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(IndexType type) {
		this.type = type;
	}
	/**
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}
	/**
	 * @param freq the freq to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
}
