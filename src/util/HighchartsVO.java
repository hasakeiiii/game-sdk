package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Highcharts数据传输对象
 * 不需要实现Serializable
 * @author wuchengf
 * @since 2015/04/20
 */
public class HighchartsVO {
	//存放x轴(横轴)数据
	private List<String> categories = new ArrayList<>();
	//存放图表内数据
	private List<HighchartsDataHolder> series = new ArrayList<>();
	
	
	public List<HighchartsDataHolder> getSeries() {
		return series;
	}
	public void setSeries(List<HighchartsDataHolder> series) {
		this.series = series;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
