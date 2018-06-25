package dao;
import javax.servlet.http.HttpServletRequest;
import azul.CacheDao;
public class SysHintDao extends CacheDao{
private static final long serialVersionUID = -7736568563908632970L;
public SysHintDao() {
	init();
}
@SuppressWarnings("unchecked")
public String getHint(HttpServletRequest request){
	String pageName=request.getServletPath();
	String result="";
	java.util.List list=getList("select * from sys_hint where ok=1");
	for(int i=0;i<list.size();i++){
		model.SysHint sysHint=(model.SysHint)list.get(i);
		String tempName=sysHint.getPageName();
		if(pageName.indexOf(tempName)>-1){
			result=azul.JspUtil.undecode(sysHint.getInfo());
			result=util.StringUtil.getHtml(result);
			break;
		}
	}
	if(!"".equals(result)){
		int width=300;
		int height=200;
		int textLength=util.StringUtil.getTextLength(result);
		if(textLength>150){
			width=400;
			height=300;
		}
		StringBuffer sb=new StringBuffer("");
		sb.append("<script type=\"text/javascript\" src=\"../_js/jquery-1.3.2.min.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"../_js/jquery.messager.js\"></script>\n");
		sb.append("<script>\n");
		sb.append("$(document).ready(function(){\n");
		sb.append("$.messager.lays(");
		sb.append(width);
		sb.append(",");
		sb.append(height);
		sb.append(");\n");
		sb.append("$.messager.anim('show', 1000);\n");
		sb.append("$.messager.show('<font color=red><b>系统提示</b></font>', '<font color=green style=\"font-size:14px;\">");
		sb.append(result);
		sb.append("</font>',6000);\n");
		sb.append("});\n");
		sb.append("</script>\n");
		result=sb.toString();
	}
	return result;
}
}