package de.hsrm.thesis.filemanagement.shared.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayUtility {

	/**
	 * Links two two-dimensional arrays. The length of array1 and array2 must be
	 * the same, length of array1[i] and array2[i] can be different.
	 * 
	 * @param array1
	 *            first array part of the concatenated array
	 * @param array2
	 *            second part of the concatenated array
	 * @return Object[][]
	 */
	public static Object[][] concatArrays(Object[][] array1, Object[][] array2) {
		if (array1.length > 0) {
			Object[][] result;

			int sizeA1 = array1[0].length;
			int sizeA2 = array2[0].length;

			result = new Object[array1.length][sizeA1 + sizeA2];

			for (int i = 0; i < array1.length; i++) {
				result[i] = Arrays.copyOf(array1[i], sizeA1 + sizeA2);
				System.arraycopy(array2[i], 0, result[i], sizeA1, sizeA2);
			}

			return result;
		} else {
			return array1;
		}
	}

	/**
	 * Creates a dictionary of an two-dimensional array on the basis of the
	 * assigned key-position and value-position in the array
	 * 
	 * @param data
	 * @param posKey
	 * @param posValue
	 * @return
	 */
	public static Map<Object, Object> getDictionary(Object[][] data,
			int posKey, int posValue) {
		Map<Object, Object> dict = new HashMap<Object, Object>();

		for (int i = 0; i < data.length; i++) {
			dict.put(data[i][posKey], data[i][posValue]);
		}

		return dict;
	}

	/**
	 * Removes all objects in b from a. Example: a[LongX, LongY, LongZ],
	 * b[LongY, LongZ, LongA], returns [LongX]
	 * 
	 * @param a
	 *            Long[]
	 * @param b
	 *            Long[]
	 * @return Long[]
	 */
	public static Long[] getDifferenz(Long[] a, Long[] b) {
		List<Long> listA = new ArrayList<Long>(Arrays.asList(a));
		listA.removeAll(Arrays.asList(b));
		return listA.toArray(new Long[listA.size()]);
	}

}
