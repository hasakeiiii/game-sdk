package util;

import java.util.ArrayList;
import java.util.List;



/**
 * Highcharts数据存放对象
 * 不需要实现Serializable
 * @author wuchengf
 * @since 2015/04/20
 */
public class HighchartsDataHolder{
	
	private String name;
	
	private List<Object> data = new ArrayList<>();

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
