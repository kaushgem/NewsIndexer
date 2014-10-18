/**
 * 
 */
package edu.buffalo.cse.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @author kaush
 *
 */

// Reference - http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java

public class SortMapUsingValueCompare implements Comparator<Integer> {

	Map<Integer, Float> base;
	public SortMapUsingValueCompare(Map<Integer, Float> base) {
		this.base = base;
	}

	public int compare(Integer a, Integer b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}
}