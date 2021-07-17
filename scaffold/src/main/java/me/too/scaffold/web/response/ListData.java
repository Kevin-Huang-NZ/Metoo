package me.too.scaffold.web.response;

import java.util.List;

import lombok.Data;

@Data
public class ListData<T> {
	/**
	 * 0-没有分页；1-分页
	 */
	private String isPaged = "1";
	private PaginationInfo pagination;
	private List<T> dataList;
}
