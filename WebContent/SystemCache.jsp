<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>cache</title>
<style type="text/css">
<!--
.STYLE1 {
	color: #0066FF;
	font-weight: bold;
}
.STYLE2 {color: #FF6600; font-weight: bold; }
.STYLE3 {color: #9900FF; font-weight: bold; }
-->
</style>
</head>
<body onLoad="init()">
<h1>Cache Status</h1>
<UL>
	<li><h2>System Cache</h2>
		<UL>
		<%
		int fold=0;
		try {
		   java.util.HashMap cacheMap=azul.CacheData.getAllCache();
		   java.util.Iterator cacheIt = cacheMap.keySet().iterator();
		   while (cacheIt.hasNext()) {
				String key=(String)cacheIt.next();
				Object obj=cacheMap.get(key);
				if (obj == null) {
					out.print(key+" is null<br>");
				}
				if (obj instanceof int[]) {
					int[] arr = (int[]) obj;
					out.println("<a href=\"javascript:fold("+fold+")\"><span class=STYLE1>" + key+"</span></a><br>");
					out.print("<table id=table"+fold+">");
					out.print("<tr><td>int[] size:" + arr.length+"</td></tr>");
					for (int i = 0; i < arr.length; i++) {
						out.print("<tr><td>"+i + ":<span class=STYLE2>" + arr[i]+"</span></td></tr>");
						i++;
					}
					fold++;
				} else if (obj instanceof Object[]) {
					Object[] arr = (Object[]) obj;
					out.println("<a href=\"javascript:fold("+fold+")\"><span class=STYLE1>" + key+"</span></a><br>");
					out.print("<table id=table"+fold+">");
					out.print("<tr><td>Object[] size:" + arr.length+"</td></tr>");
					for (int i = 0; i < arr.length; i++) {
						out.print("<tr><td>"+i + ":<span class=STYLE2>" + arr[i]+"</span></td></tr>");
						i++;
					}
					fold++;
				}
				else if (obj instanceof List) {
					int i = 0;
					List list = (List) obj;
					out.println("<a href=\"javascript:fold("+fold+")\"><span class=STYLE1>" + key+"</span></a><br>");
					out.print("<table id=table"+fold+">");
					out.print("<tr><td>List size:" + list.size()+"</td></tr>");
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						Object element = iter.next();
						out.print("<tr><td>"+i + ":<span class=STYLE2>" + element+"</span></td></tr>");
						i++;
					}
					fold++;
				}
				else if (obj instanceof Set) {
					int i = 0;
					Set set = (Set) obj;
					out.println("<a href=\"javascript:fold("+fold+")\"><span class=STYLE1>" + key+"</span></a><br>");
					out.print("<table id=table"+fold+">");
					out.print("<tr><td>Set size:" + set.size()+"</td></tr>");
					for (Iterator iter = set.iterator(); iter.hasNext();) {
						Object element = iter.next();
						out.print("<tr><td>"+i + ":<span class=STYLE2>" + element+"</span></td></tr>");
						i++;
					}
					fold++;
				}
				else if (obj instanceof Map) {
					int i = 0;
					Map map = (Map) obj;
					out.println("<a href=\"javascript:fold("+fold+")\"><span class=STYLE1>" + key+"</span></a><br>");
					Iterator it = map.keySet().iterator();
					out.print("<table id=table"+fold+">");
					out.print("<tr><td>Map size:" + map.size()+"</td></tr>");
					while (it.hasNext()) {
						Object keyId = it.next();
						Object value = map.get(keyId);
						out.print("<tr><td>"+i + ":<span class=STYLE2>" + keyId + "</span>  <span class=STYLE3>" + value+"</span></td></tr>");
						i++;
					}
					fold++;
					out.print("</table>");
				}
				else{
					out.print(key+":"+obj.toString());
				}
				out.print("<br>");
		   }
		} catch (Exception e) {
			out.println(e);
		}
		%>
		</UL>
		<br>
	</li>
</UL>
</body>
</html>
<script type="text/javascript">
function fold(index){
    var elem=document.getElementById("table"+index);
    if(elem.style.display=='none'){
	elem.style.display='block';
    }
    else{
	elem.style.display='none';
    }
}
function init(){
	for(var i=0;i<<%=fold%>;i++){
	   fold(i);
	}
}
</script>