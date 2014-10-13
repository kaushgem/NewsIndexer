package edu.buffalo.cse.irf14.index;

import java.util.HashMap;

public class IDFPostingDTO {
	private int idf;
	private HashMap<Integer, TermFreqPositionIndexDTO> termFreqPositionIndexDTO;
	
	
	/**
	 * @return the idf
	 */
	public int getIdf() {
		return idf;
	}
	/**
	 * @param idf the idf to set
	 */
	public void setIdf(int idf) {
		this.idf = idf;
	}
	/**
	 * @return the termFreqPositionIndexDTO
	 */
	public HashMap<Integer, TermFreqPositionIndexDTO> getTermFreqPositionIndexDTO() {
		return termFreqPositionIndexDTO;
	}
	/**
	 * @param termFreqPositionIndexDTO the termFreqPositionIndexDTO to set
	 */
	public void setTermFreqPositionIndexDTO(
			HashMap<Integer, TermFreqPositionIndexDTO> termFreqPositionIndexDTO) {
		this.termFreqPositionIndexDTO = termFreqPositionIndexDTO;
	}
}
