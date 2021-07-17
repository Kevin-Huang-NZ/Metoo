package me.too.scaffold.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	/**
	 * 将原列表分为n个列表
	 * 
	 * @param source 原列表
	 * @param num 份数n
	 * @return
	 */
	public static <T> List<List<T>> splitListForNum(List<T> source, int num) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remainder = source.size() % num; // (先计算出余数)
		int number = source.size() / num; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < num; i++) {
			List<T> value = null;
			if (remainder > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remainder--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	/**
	 * 将原列表分为多个最大容量为c的列表
	 * 
	 * @param source 原列表
	 * @param capacity 划分后单个列表的最大容量c
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> splitListByCapacity(List<T> source, int capacity) {
		List<List<T>> result = new ArrayList<List<T>>();
		if (source != null) {
			int size = source.size();
			if (size > 0) {
				for (int i = 0; i < size;) {
					List<T> value = null;
					int end = i + capacity;
					if (end > size) {
						end = size;
					}
					value = source.subList(i, end);
					i = end;

					result.add(value);
				}

			} else {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}
	
	public static <T> boolean isEmpty(List<T> l) {
		return l == null || l.size() == 0;
	}
}
