package com.san;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Sanitizer class with methods to clean zipcode related input data
 * 
 * @author Santosh Jhingade
 */
public class ZipCodeInputSanitizer {

	private static final Logger LOGGER = Logger.getLogger("ZipCodeInputSanitizer");

	/**
	 * Method that takes in an array of zipcode ranges and returns a new List containing
	 * dedupicated zipcode ranges
	 * 
	 * @param zipCodeRangeList
	 * @return List<Integer[]>
	 */
	public static List<Integer[]> sanitize(List<Integer[]> zipCodeRangeList) {

		List<Integer[]> finalZipCodeRangeList;

		if (zipCodeRangeList == null || zipCodeRangeList.isEmpty()) {
			String errorMsg = "Input zipcode array list is null or empty";
			LOGGER.warning(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}

		logArray("Input: ", zipCodeRangeList);
		
		if (zipCodeRangeList.size() == 1) {
			// Return input as is if list contains only one set of zipcode ranges
			finalZipCodeRangeList = zipCodeRangeList;
		} else {
			// Sanitize input if list has greater than one entry
			finalZipCodeRangeList = new ArrayList<>();

			// Sort input list based on the starting zipcode in each range
			Collections.sort(zipCodeRangeList, (s1, s2) -> {
				return s1[0].compareTo(s2[0]);
			});

			logArray("Sorted List: ", zipCodeRangeList);

			int start = zipCodeRangeList.get(0)[0];
			int end = zipCodeRangeList.get(0)[1];

			Iterator<Integer[]> it = zipCodeRangeList.iterator();
			while (it.hasNext()) {

				Integer[] next = it.next();

				int newStart = next[0];
				int newEnd = next[1];

				if (newStart == start || (newStart > start && newStart <= end)) {
					// Current ZipCode array is in range to be merged
					if (newEnd > end) {
						// New end range is higher than current so extend it
						end = newEnd;
					}
				} else {
					// Next start is greater than current range, store current range and reset start

					finalZipCodeRangeList.add(new Integer[] { start, end });

					start = newStart;
					end = newEnd;
				}

				// Add the last zip code range to the result list
				if (!it.hasNext()) {
					finalZipCodeRangeList.add(new Integer[] { start, end });
				}
			}

			logArray("Result: ", finalZipCodeRangeList);

		}

		return finalZipCodeRangeList;
	}

	/**
	 * Private utility method to print the input arrayList contents
	 * 
	 * @param message
	 * @param list
	 */
	private static void logArray(String message, List<Integer[]> list) {
		StringBuilder sb = new StringBuilder(message);
		for (Integer[] i : list) {
			sb.append("[" + i[0] + "," + i[1] + "] ");
		}

		LOGGER.info(sb.toString());
	}

}
