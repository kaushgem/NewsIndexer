package edu.buffalo.cse.irf14.index;

import java.util.Comparator;
import java.util.HashMap;

public class HashSizeComparator implements Comparator<HashMap<String,Integer>> {
	
	
	@Override
	public int compare(HashMap<String, Integer> o1, HashMap<String, Integer> o2) {
		// TODO Auto-generated method stub
		if(o1!=null && o2!=null )
			return o1.size() -o2.size();
		return 0;
	}

}
