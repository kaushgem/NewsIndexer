package edu.buffalo.cse.irf14.index;

import java.util.List;

public class TermFreqPositionIndexDTO {
	private int termFreq;
	List<Integer> positionIndex;
	
	public int getTermFreq() {
		return termFreq;
	}
	public void setTermFreq(int termFreq) {
		this.termFreq = termFreq;
	}
	public List<Integer> getPositionIndex() {
		return positionIndex;
	}
	public void setPositionIndex(List<Integer> positionIndex) {
		this.positionIndex = positionIndex;
	}
}
