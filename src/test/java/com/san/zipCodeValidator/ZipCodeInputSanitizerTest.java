package com.san.zipCodeValidator;

import org.junit.jupiter.api.Test;

import com.san.ZipCodeInputSanitizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test to validate ZipCodeInputSanitizer.
 * 
 * @author Santosh Jhingade
 */
public class ZipCodeInputSanitizerTest {

	List<Integer[]> zipCodeRangeArrayList;

	@BeforeEach
	void initializeTestData() {
		zipCodeRangeArrayList = new ArrayList<>();

		zipCodeRangeArrayList.add(new Integer[] { 2, 4 });
		zipCodeRangeArrayList.add(new Integer[] { 5, 5 });
		zipCodeRangeArrayList.add(new Integer[] { 10, 12 });
	}

	/**
	 * Input:  [2,4], [5,5], [10,12] 
	 * Output: [2,4], [5,5], [10,12]
	 */
	@Test
	void testNoIntersection() {
		List<Integer[]> expected = new ArrayList<>();
		expected.add(new Integer[] { 2, 4 });
		expected.add(new Integer[] { 5, 5 });
		expected.add(new Integer[] { 10, 12 });

		List<Integer[]> result = ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);

		Assertions.assertArrayEquals(result.toArray(), expected.toArray());
	}

	/**
	 * Input:  [2,4], [5,5], [10,12], [8,10] 
	 * Output: [2,4], [5,5], [8,12]
	 */
	@Test
	void testBeforeIntersection() {
		zipCodeRangeArrayList.add(new Integer[] { 8, 10 });

		List<Integer[]> expected = new ArrayList<>();
		expected.add(new Integer[] { 2, 4 });
		expected.add(new Integer[] { 5, 5 });
		expected.add(new Integer[] { 8, 12 });

		List<Integer[]> result = ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);

		Assertions.assertArrayEquals(result.toArray(), expected.toArray());
	}

	/**
	 * Input:  [2,4], [5,5], [10,12], [5,7] 
	 * Output: [2,4], [5,7], [10,12]
	 */
	@Test
	void testAfterIntersection() {
		zipCodeRangeArrayList.add(new Integer[] { 5, 7 });

		List<Integer[]> expected = new ArrayList<>();
		expected.add(new Integer[] { 2, 4 });
		expected.add(new Integer[] { 5, 7 });
		expected.add(new Integer[] { 10, 12 });

		List<Integer[]> result = ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);

		Assertions.assertArrayEquals(result.toArray(), expected.toArray());
	}

	/**
	 * Input:  [2,4], [5,5], [10,12], [4,5] 
	 * Output: [2,5], [10,12]
	 */
	@Test
	void testMergeIntermediate() {
		zipCodeRangeArrayList.add(new Integer[] { 4, 5 });

		List<Integer[]> expected = new ArrayList<>();
		expected.add(new Integer[] { 2, 5 });
		expected.add(new Integer[] { 10, 12 });

		List<Integer[]> result = ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);

		Assertions.assertArrayEquals(result.toArray(), expected.toArray());
	}

	/**
	 * Input:  [2,4], [5,5], [10,12], [4,5], [5,10] 
	 * Output: [2,12]
	 */
	void testMergeAll() {
		zipCodeRangeArrayList.add(new Integer[] { 4, 5 });
		zipCodeRangeArrayList.add(new Integer[] { 5, 10 });

		List<Integer[]> expected = new ArrayList<>();
		expected.add(new Integer[] { 2, 12 });

		List<Integer[]> result = ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);

		Assertions.assertArrayEquals(result.toArray(), expected.toArray());
	}

	/**
	 * Input: Null input 
	 * Output: IllegalArgumentException
	 */
	@Test
	void testFailWithEmptyInput() {
		zipCodeRangeArrayList = null;

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ZipCodeInputSanitizer.sanitize(zipCodeRangeArrayList);
		});
	}
}
