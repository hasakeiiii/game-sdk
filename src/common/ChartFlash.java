package common;

import java.util.ArrayList;

import org.openflashchart.Graph;

public class ChartFlash {
	
	//饼状图页面调用方法
	public static String getUrl(String flashType,String title,String info,String colStr,String rowStr){
		StringBuffer sb=new StringBuffer();
		sb.append(azul.CacheSystem.WEB_PATH);
		sb.append("_js/open-flash-chart.swf?data=");
		sb.append(azul.CacheSystem.WEB_PATH);
		sb.append("_js/open-flash-chart.jsp?flashType=");
		sb.append(flashType);
		sb.append("%26title=");
		sb.append(azul.JspUtil.decode(title));
		sb.append("%26info=");
		sb.append(azul.JspUtil.decode(info));
		sb.append("%26colStr=");
		sb.append(azul.JspUtil.decode(colStr));
		sb.append("%26rowStr=");
		sb.append(azul.JspUtil.decode(rowStr));
		return sb.toString();
	}
	
	//柱状图页面调用方法
	public static String getUrl(String flashType,String title,String info,int max,String colStr,String rowStr){
		StringBuffer sb=new StringBuffer();
		sb.append(azul.CacheSystem.WEB_PATH);
		sb.append("_js/open-flash-chart.swf?data=");
		sb.append(azul.CacheSystem.WEB_PATH);
		sb.append("_js/open-flash-chart.jsp?flashType=");
		sb.append(flashType);
		sb.append("%26title=");
		sb.append(azul.JspUtil.decode(title));
		sb.append("%26info=");
		sb.append(azul.JspUtil.decode(info));
		sb.append("%26max=");
		sb.append(max);
		sb.append("%26colStr=");
		sb.append(azul.JspUtil.decode(colStr));
		sb.append("%26rowStr=");
		sb.append(azul.JspUtil.decode(rowStr));
		return sb.toString();
	}
	
	public static Graph chartBar(String title,String info,String max,String rowStr,String colStr){
		Graph g = new Graph();
		if(rowStr.length()<1 || colStr.length()<1){
			return g;
		}
		ArrayList<String> row=new ArrayList<String>();
		ArrayList<String> col=new ArrayList<String>();
		String[] rowArr=azul.JspUtil.undecode(rowStr).split("ZvZ");
		String[] colArr=azul.JspUtil.undecode(colStr).split("ZvZ");
		for (int i = 0; i < rowArr.length; i++) {
			row.add(rowArr[i]);
		}
		for (int i = 0; i < colArr.length; i++) {
			col.add(colArr[i]);
		}
		
        g.title(azul.JspUtil.undecode(title),"{font-size: 22px;}");
		g.set_data(row);
		g.bar_3D("75", "#D54C78", azul.JspUtil.undecode(info), 14);
		g.set_x_labels(col);
		g.set_x_label_style("10", "#9933CC", 2, 2, "");
		g.set_x_axis_3d(5);
		g.x_axis_colour("#909090", "#ADB5C7");
		g.set_y_max(Integer.valueOf(max));
		g.y_label_steps(10);
		return g;
	}
	public static Graph chartPie(String title,String info,String rowStr,String colStr){
		Graph g = new Graph();
		if(rowStr.length()<1 || colStr.length()<1){
			return g;
		}
		ArrayList<String> row=new ArrayList<String>();
		ArrayList<String> col=new ArrayList<String>();
		ArrayList<String> links=new ArrayList<String>();
		//为了防止乱码前端默认字符串加密
		String[] rowArr=azul.JspUtil.undecode(rowStr).split("ZvZ");
		String[] colArr=azul.JspUtil.undecode(colStr).split("ZvZ");
		for (int i = 0; i < rowArr.length; i++) {
			row.add(rowArr[i]);
		}
		for (int i = 0; i < colArr.length; i++) {
			col.add(colArr[i]);
		}
		g.title(azul.JspUtil.undecode(title), "{font-size: 26px;}");
		g.pie(60, "#505050", "{font-size: 12px; color: #404040;}");
		g.pie_values(row, col, links);
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("#d9db35");
		colors.add("#487daf");
		colors.add("#d00000");
		colors.add("#4ae331");
		g.pie_slice_colours(colors);
		g.set_tool_tip("#val#"+azul.JspUtil.undecode(info)+"<br>");
		return g;
	}
}
