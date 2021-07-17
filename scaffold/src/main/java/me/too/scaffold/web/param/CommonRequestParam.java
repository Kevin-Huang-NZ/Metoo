package me.too.scaffold.web.param;

import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class CommonRequestParam {
	private int pageNum = 1;
	private int pageSize = 10;
	private String orderBy = "t_id";
	private String ascDesc = "desc";

	public String getOrder() {
		if (StringUtils.isEmpty(this.orderBy)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(this.orderBy).append(" ").append(this.ascDesc);
		return sb.toString();
	}
}
