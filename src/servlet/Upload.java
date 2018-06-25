package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.FileUtil;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class Upload extends HttpServlet {


 
 public void destroy() {
  super.destroy();
 }

 
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  //文件上传个数
  int count = 0;
  //文件上传地址
  String filePath = getServletContext().getRealPath("/")+"smartUpload";
  
  //如果文件夹不存在 则创建这个文件夹
  File file = new File(filePath);
  if(!file.exists())
  {
   file.mkdir();
  }
  //初始化对象
  SmartUpload su = new SmartUpload();
  su.initialize(getServletConfig(),request,response);
  
  //设置文件最大容量
  su.setMaxFileSize(1000*1024*1024);
  //设置所有文件最大容量
  //su.setTotalMaxFileSize(200*1024*1024);
  //设置上传文件类型
  su.setAllowedFilesList("rar,txt,jpg,bmp,gif,apk,zip");
  
  
  
  try {
   //设置禁止上传的文件类型
   su.setDeniedFilesList("jsp,js,html,css");
   //上传文件
   su.upload();
   count = su.save(filePath);
  } catch (SQLException e) {
   e.printStackTrace();
  } catch (SmartUploadException e) {
   e.printStackTrace();
  }
 
  for (int i = 0; i < su.getFiles().getCount(); i++) {
	   com.jspsmart.upload.File tempFile = su.getFiles().getFile(i);
	   String fromFile = filePath+File.separator+tempFile.getFileName();
	   String toFile = "D:"+File.separator+"decode"+File.separator+tempFile.getFileName();
	   FileUtil.copy(fromFile, toFile);
	   System.out.println("-------------------------------------------------");
	   System.out.println("fromFile=" + fromFile);
	   System.out.println("toFile=" + toFile);
	   System.out.println("表单项名称:" + tempFile.getFieldName());
	   System.out.println("文件名：" + tempFile.getFileName());
	   System.out.println("文件长度：" + tempFile.getSize());
	   System.out.println("文件扩展名:" + tempFile.getFileExt());
	   System.out.println("文件全名：" + tempFile.getFilePathName());
	   System.out.println("-------------------------------------------------");
  }
  System.out.println("上传成功！共" + count + "个文件！");
   
 }

 
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
	 
	 
	    response.setContentType("text/plain");  
        PrintWriter out = response.getWriter();  
        HttpSession session=request.getSession();  
        Double percentage=0.0;  
        if(session.getAttribute("percentage")!=null){  
            percentage=(Double)session.getAttribute("percentage");  
            double retVal=Math.floor(percentage*100);  
            if(retVal == 100.0D){  
                session.removeAttribute("percentage");  
            }  
            out.print("{retVal:"+retVal+"}");  
        }else{  
            out.print("{retVal:-1.0}");  
        } 
	 

  doPost(request,response);
 }

 
 public void init() throws ServletException {
  // Put your code here
 }

} 

 
