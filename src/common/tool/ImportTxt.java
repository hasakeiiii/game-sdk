package common.tool;

import java.util.List;

//导入sp导出的日志文件
public class ImportTxt {
	public static void insertDb(){System.out.println("11111111111111111");
		//HashMap<String,String> map=new HashMap<String,String>();
		List list=util.FileUtil.read("E://zty/293_20110925.txt");System.out.println("222222222222");
		//azul.JspUtil.p(list);
		for (int i = 0; i < list.size(); i++) {
			String info=(String)list.get(i);System.out.println(info);
			//状态报告
			if(info.indexOf(" 0003 ")>-1){
				String[] arr=info.split(" ");
				//azul.SpTool.mr("1004",arr[6],arr[2],"",arr[5],"DELIVRD");//lsl
			}
			else{
				String[] arr=info.split(" ");
				//azul.SpTool.mo(arr[6],"1004",1,arr[2],arr[3],arr[4]);//lsl
			}
		}
		System.out.println("ok");
	}
}
