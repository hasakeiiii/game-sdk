package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

class MyFileFilter implements FileFilter {
	  private String keyword;

	  public MyFileFilter(String keyword) {
	    this.keyword = keyword;
	  }

	  
	  public boolean accept(File pathname) {
		  String[] arr=null;
		  if(keyword.indexOf(",")>-1){
			  arr=keyword.split(",");
			  for (int i = 0; i < arr.length; i++) {
				  if(pathname.getName().toLowerCase().indexOf(arr[i]) > 0){
					  return true;
				  }
			  }
			  return false;
		  }
		  else{
			  return pathname.getName().toLowerCase().indexOf(keyword) > 0;
		  }
	  }
}
public class FileUtil {
	// File.separator只能检测"E:\\workspace\\show\\back\\ad\\adAction.jsp 这样的目录
	public FileUtil() {

	}

      public static byte[] getByteArrayFromInputstream(InputStream ins, int MAXLEN){
		
		if(MAXLEN == -1) MAXLEN = 30000;
		
		try {
			
			byte[] charset = new byte[MAXLEN];
			int ch = ins.read();
			int length = 0;
			while (ch != -1) {
				charset[length] = (byte) ch;
				ch = ins.read();
				length++;
			}
			byte[] xmlCharArray = new byte[length];
			System.arraycopy(charset, 0, xmlCharArray, 0, length);
			
			return (xmlCharArray);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public static String getFileName(String filePath) {
		if (filePath.indexOf(File.separator) == -1) {
			return filePath;
		}
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	// 将文件里面的内容读入一个List中
	public static List<String> read(String name) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(name));
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				//System.out.println(lineStr);
				list.add(lineStr);
			}
			
		} catch (Exception e) {
			System.out.println("arg1:" + name);
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				System.out.println("FileUtil.read colse br wrong:" + e);
			}
		}
		return list;
	}

	// 将文件里面的内容读入一个List中,不带换行符，除去空白行
	public static ArrayList<String> readNoEnter(String name) {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(name));
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				String temp=lineStr.trim();
				if(!"".equals(temp)){
					list.add(temp);
				}
			}
		} catch (Exception e) {
			System.out.println("arg1:" + name);
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				System.out.println("FileUtil.read colse br wrong:" + e);
			}
		}
		return list;
	}
	
	// 将文件里面的内容读出一部分，一般用于浏览内容
	public static String readPart(String name,int lineNum) {
		StringBuffer sb=new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(name));
			String lineStr = "";
			int i=0;
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				//System.out.println(lineStr);
				if(i<lineNum){
					sb.append(lineStr + "\n");
					i++;
				}
				else{
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("arg1:"+name);
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				System.out.println("FileUtil.readPart colse br wrong:" + e);
			}
		}
		return sb.toString();
	} 
	
	// 读取大文件,显示文件部分内容
	public static void printBigFile(String fromFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fromFile));
			String lineStr = "";
			int i = 0;
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				System.out.println(lineStr);
				i++;
				if (i > 5) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 读取大文件,显示文件部分内容
	public static String getBigFileTxt(String fromFile) {
		//首先判断文件是否存在，如果不存在就返回为空
		if(!exists(fromFile)){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fromFile));
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				sb.append(lineStr);
				sb.append("<br>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	// 将网络文件里面的内容读入
	public List<String> readNet(String filePath, String MID, String SID, String CID) {
		List<String> list = new ArrayList<String>();
		try {
			int HttpResult; // 服务器返回的状态
			URL url = new URL(filePath); // 创建URL
			URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
			
			/*
			 * 设置访问连接属性
			 * 
			 * httpConnection.setDoOutput(true);
			 * httpConnection.setRequestMethod("POST");
			 * httpConnection.setRequestProperty("Content-Type",
			 * "application/x-www-form-urlencoded");
			 * httpConnection.setRequestProperty("SID","5000");
			 * httpConnection.setRequestProperty("MID","00001214220716038"); int
			 * responseCode = httpConnection.getResponseCode(); if (responseCode !=
			 * 200){ System.out.println(" visit error"); return null; }
			 */
			urlconn.setRequestProperty("MID", MID);
			urlconn.setRequestProperty("SID", SID);
			urlconn.setRequestProperty("CID", CID);
			urlconn.connect();
			HttpURLConnection httpconn = (HttpURLConnection) urlconn;

			HttpResult = httpconn.getResponseCode();
			if (HttpResult != HttpURLConnection.HTTP_OK) { // 不等于HTTP_OK说明连接不成功
				System.out.println("无法连接到:" + filePath);
				return list;
			}
			InputStreamReader isReader = new InputStreamReader(urlconn.getInputStream());
			char[] buffer = new char[2048]; // 创建存放输入流的缓冲
			int num = 0; // 读入的字节数
			while (num > -1) {
				num = isReader.read(buffer); // 读入到缓冲区
				if (num < 0) {
					break; // 已经读完
				}
				list.add(new String(buffer, 0, num));
			}
			isReader.close();// 关闭输入流
		} catch (Exception e) {
			System.out.println("arg1:" + filePath);
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e) {
				System.out.println("FileUtil.readNet colse br wrong:" + e);
			}
		}
		return list;
	}

	// 运行jsp页面上传参数。参数以map形式传入
	public static void runJspParam(String url, HashMap<String,String> map) {
		PostMethod method =null;
		try {
			method = new PostMethod(url); 
			HttpClient client = new HttpClient(); 
            //设置Http Post数据 
            if (map.size()>0) { 
            	NameValuePair[] paramArr=new NameValuePair[map.size()];
                int i=0;
                for (Map.Entry<String, String> entry : map.entrySet()) { 
                	//System.out.println(entry.getKey()+"========"+entry.getValue());
                	String key=String.valueOf(entry.getKey());
                	String value=String.valueOf(entry.getValue());
                	paramArr[i]=new NameValuePair(key,value);
                	i++;
                } 
                method.setRequestBody(paramArr);
            } 
            client.executeMethod(method); 
            if (method.getStatusCode() != HttpStatus.SC_OK) { 
            	  System.out.println("无法连接到:" + url);
            } 
            System.out.println(url);
        } catch (IOException e) { 
        	e.printStackTrace();
			System.err.println("url:" + url);
        } finally { 
                method.releaseConnection();
        } 
	}
	
	// 运行jsp页面并且返回是否运行成功code=UTF-8,gbk
	public static String runJsp(String url, String param,String code) {
		StringBuffer result = new StringBuffer("");
		HttpURLConnection urlConn=null;
		BufferedReader br =null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			OutputStream os = urlConn.getOutputStream();
			os.write(param.getBytes());
			os.close();
			String line = "";
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),code));
			while ((line = br.readLine()) != null) {
				result.append(line.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("url:" + url);
			System.err.println("param:" + param);
		}
		finally{
			try {if(br!=null)br.close();if(urlConn!=null)urlConn.disconnect();} catch (IOException e) {System.out.println(e);}
		}
		return result.toString();
	}
	
	// 运行jsp页面并且返回是否运行成功code=UTF-8,gbk
	public static String runJsp(String url,String code) {
		StringBuffer result = new StringBuffer("");
		HttpURLConnection urlConn=null;
		BufferedReader br =null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();
			//urlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			//urlConn.setRequestMethod("GET");
			OutputStream os = urlConn.getOutputStream();
			os.close();
			String line = "";
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),code));
			while ((line = br.readLine()) != null) {
				result.append(line.trim());
				result.append("\n");
			}
			br.close();
			urlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("url:" + url);
			return "-1";
		}
		finally{
			try {if(br!=null)br.close();if(urlConn!=null)urlConn.disconnect();} catch (IOException e) {System.out.println(e);}
		}
		return result.toString();
	}

	// 运行jsp页面并且返回内容存入list
	public static ArrayList<String> runJspToList(String url,String code) {
		ArrayList<String> list = new ArrayList<String>();
		HttpURLConnection urlConn=null;
		BufferedReader br =null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			OutputStream os = urlConn.getOutputStream();
			os.close();
			String line = "";
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),code));
			while ((line = br.readLine()) != null) {
				line=util.StringUtil.subHtml(line.trim());
			    if(line.length()!=0){
			    	list.add(line);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("url:" + url);
		}
		finally{
			try {if(br!=null)br.close();if(urlConn!=null)urlConn.disconnect();} catch (IOException e) {System.out.println(e);}
		}
		return list;
	}
	
	//http://www.baidu.com/s?bs=13751180190&f=8&wd=13751180190&n=3
	//上面的方法打开以上网址报501错误，可以用下面的方法
	public static String getHTML(String pageURL, String encoding) {
		  StringBuilder pageHTML = new StringBuilder();
		  BufferedReader br=null;
		  try {
		   URL url = new URL(pageURL);
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setRequestProperty("User-Agent", "MSIE 7.0");
		   br = new BufferedReader(new InputStreamReader(
		     connection.getInputStream(), encoding));
		   String line = null;
		   while ((line = br.readLine()) != null) {
		    pageHTML.append(line);
		    pageHTML.append("\r\n");
		   }
		   connection.disconnect();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  finally{
				try {
					if(br!=null)br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  }
		  return pageHTML.toString();
		 }
	
	// 将远程文件读入byte数组中
	public byte[] readBuff(String filePath) {
		HttpURLConnection httpConnection = null;
		InputStream fis = null;
		byte[] tempBuffer = null;
		try {
			// 读取WML解析内容
			URL url = new URL(filePath);
			httpConnection = (HttpURLConnection) url.openConnection();
			fis = httpConnection.getInputStream();
			int totalLength = fis.available();// System.out.println(totalLength);
			tempBuffer = new byte[totalLength];
			fis.read(tempBuffer);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally{
			try {if(fis!=null)fis.close();if(httpConnection!=null)httpConnection.disconnect();} catch (IOException e) {System.out.println(e);}
		}
		return tempBuffer;
	}

	// 将一个list中的内容写入文件中,可以用追加和覆写的方式
	public static boolean write(String path, List<String> list, boolean append) {
		BufferedWriter bw = null;
		try {
			// new FileWriter(name,true)设置文件为在尾部添加模式,参数为false和没有参数都代表覆写方式
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8"));
			for (String element : list) {
				bw.write(element);
			}
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			System.out.println("arg2:" + list.size());
			System.out.println("arg3:" + append);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(bw!=null)bw.close();
			} catch (Exception e) {
				System.out.println("FileUtil.write colse bw wrong:" + e);
			}
		}
		return true;
	}

	// 在已经存在的文件后面追加写的方式
	public static boolean write(String path, String str) {
		BufferedWriter bw = null;
		try {
			// new FileWriter(name,true)设置文件为在尾部添加模式,参数为false和没有参数都代表覆写方式
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			bw.write(str);
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			System.out.println("arg2:" + str);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(bw!=null)bw.close();
			} catch (Exception e) {
				System.out.println("FileUtil.write colse bw wrong:" + e);
			}
		}
		return true;
	}

	// 用来写utf-8编码的文件，一般用来生成utf-8编码的xml文件
	// writeCodeFile("D:/workspace/show/flvxml/fileList_1.xml",str,"UTF-8");
	// 如果没有建立对应的目录则自动创建目录
	public boolean writeCodeFile(String fileName, String str, String code) throws IOException {
		if(str==null || str.equals("")){
			System.out.println("writeCodeFile input is null");
			return false;
		}
		buildPath(fileName);
		boolean result = false;
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(fileName), code);
			out.write(str);
			out.flush();
			result = true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if(out!=null)out.close();
		}
		return result;
	}

	// 将一个文件移动到另外一个目录，并且可以改变原来文件的名称
	public static void move(String input, String output) throws Exception {
		File inputFile = new File(input);
		File outputFile = new File(output);
		System.out.println(inputFile.toString()+outputFile.toString());
		try {
			inputFile.renameTo(outputFile);
		} catch (Exception ex) {
			throw new Exception("Can not mv" + input + " to " + output + ex.getMessage());
		}
	}

	// 普通的拷贝文件方法,一般拷贝较小的文件,
	// fromFile参数可以是E:\\ad\\adAction.jsp方式或者E:/ad/adAction.jsp
	// toFile参数可以是D:\\ad\\adAction.jsp方式或者E:/ad/adAction.jsp
	// 后面如果不加文件名则直接使用源文件的文件名
	public static boolean   copy(String fromFile, String toFile) {
		boolean result = false;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String tempFromFile = fromFile.replaceAll("\\\\", "/");
		String tempToFile = toFile.replaceAll("\\\\", "/");
		try {
			if (tempFromFile.lastIndexOf("/") == tempFromFile.length() - 1) {
				System.err.println("拷贝源文件路径异常:" + fromFile);
				return result;
			}
			File file_in = new File(tempFromFile);
			if (!file_in.exists()) {
				System.err.println("拷贝源文件不存在:" + fromFile);
				return result;
			}
			if (!buildPath(tempToFile)) {
				System.err.println("创建目标目录异常:" + toFile);
			}

			// 如果输出是目录没有带有文件名则自动加文件名
			if (tempToFile.lastIndexOf("/") == tempToFile.length() - 1) {
				tempToFile += tempFromFile.substring(tempFromFile.lastIndexOf("/"), tempFromFile.length());
			}
			System.out.println(tempToFile);
			File file_out = new File(tempToFile);
			fis = new FileInputStream(file_in);
			fos = new FileOutputStream(file_out);
			byte bytes[] = new byte[1024];
			int c;
			while ((c = fis.read(bytes)) != -1) {
				fos.write(bytes, 0, c);
			}

			result = true;
		} catch (Exception e) {
			System.out.println("arg1:" + fromFile);
			System.out.println("arg2:" + toFile);
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				System.out.println("FileUtil.copy fos,fis close wrong:" + e);
			}
		}

		return result;
	}

	// 每次读取一定的长度拷贝文件,一般用于拷贝大文件,size单位是字节,5M=500000
	public boolean copy(String fromFile, String toFile, int size) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);
			int readedLength = 0, tempLength = 0;
			int totalLength = fis.available();
			System.out.println("from:" + fromFile + " to:" + toFile + " read:" + tempLength + " all:" + totalLength);
			while (readedLength < totalLength) {
				// 每次读取设定的长度拷贝
				byte[] tempBuffer = new byte[size];
				tempLength = fis.read(tempBuffer);
				System.out.println("pos:" + readedLength);
				fos.write(tempBuffer, 0, tempLength);
				fos.flush();
				readedLength += tempLength;
			}
			System.out.println("from:" + fromFile + " to:" + toFile + " read:" + tempLength + " all:" + totalLength);
		} catch (Exception e) {
			System.out.println("arg1:" + fromFile);
			System.out.println("arg2:" + toFile);
			System.out.println("arg3:" + size);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				System.out.println("FileUtil.copy fos,fis close wrong:" + e);
			}
		}
		return true;
	}
	
	/**
	* 复制单个文件
	* @param oldPath String 原文件路径 如：c:/fqf.txt
	* @param newPath String 复制后路径 如：f:/fqf.txt
	* @return boolean
	*/
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldPath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}catch (Exception e) {
		System.out.println("复制单个文件操作出错");
		e.printStackTrace();
	
		}

	} 

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public boolean copyDir(String oldPath, String newPath, boolean isAllFile) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			// 如果文件夹不存在 则建立新文件夹
			if(!new File(newPath).mkdirs()){System.out.println("建立文件夹失败："+newPath);}
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					input = new FileInputStream(temp);
					output = new FileOutputStream(newPath + "/" + (temp.getName()));
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
				}
				if (temp.isDirectory() && isAllFile) {// 如果是子文件夹
					copyDir(oldPath + "/" + file[i], newPath + "/" + file[i], true);
				}
			}
		} catch (IOException ex) {
			System.out.println("FileUtil.copyDir io exption");
		} catch (Exception e) {
			System.out.println("arg1:" + oldPath);
			System.out.println("arg2:" + newPath);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (Exception e) {
				System.out.println("FileUtil.copyFolder.close error");
			}
		}
		return true;
	}

	// 检查文件是否存在
	public static boolean exists(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("输入文件路径:" + fileName + "不存在");
				return false;
			}
		} catch (Exception e) {
			System.out.println("arg1:" + fileName);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 检查文件是否存在,如果存在就删除文件
	public static boolean delete(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("输入文件:" + fileName + "不存在");
				return false;
			}
			file.delete();
		} catch (Exception e) {
			System.out.println("arg1:" + fileName);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 将需要删除的文件名称放入list中然后删除
	public static boolean delete(List<String> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				delete(list.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 如果路径不存在就创建新的路径,输入是文件带路径的名称
	public static boolean buildPath(String fileName) {
		boolean result = false;
		try {
			String filePath = fileName;
			// 输入是路径不是文件
			if (fileName.lastIndexOf(File.separator) != fileName.length() - 1) {
				filePath = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
			}
			File file = new File(filePath);
			if (!file.exists()) {
				// 注意file.mkdir只能建单层目录.file.mkdirs可以建多层目录
				result=file.mkdirs();
			} else {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("arg1:" + fileName);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新建文件
	 * 
	 * @param fileName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	
	public static boolean createFile(String fileName, String text) {
		OutputStreamWriter out=null;
		try {
			if (fileName.indexOf("/") > -1) {
				String path = fileName.substring(0, fileName.lastIndexOf("/"));
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
			out= new OutputStreamWriter(new FileOutputStream(fileName),"utf-8");
			out.write(text);
			out.flush();
		} catch (Exception ex) {
			System.err.println("fileUtil.createFile error" + ex);
			return false;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
		return true;
	}
	
	/**
	 * 删除文件夹里面的所有文件
	 * 输入路径可以是c:/xxx的,也可以是c:/xxx/的.删除后的结果是删除xxx底下的所有文件,包括子文件夹.但xxx目录会被保留
	 * key 排除带关键字的文件，如果为空就不排除
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public boolean delAllFile(String path,String key) {
		try {
			key=key.trim();
			File file = new File(path);
			if (!file.exists()){
				System.out.println("文件不存在：" + path);
				return false;
			}
			if (!file.isDirectory()){
				System.out.println("路径不存在：" + path);
				return false;
			}
			String[] tempList = file.list();
			
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				// File.separator用于判断带有"c:/xxx\\"这样的路径
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if("".equals(key)){
					if (temp.isFile()){
						if(!temp.delete()){System.out.println("删除文件失败："+temp.getName());}
				    }
				}
				else{
					if (temp.isFile() && temp.getName().indexOf(key)==-1){
						if(!temp.delete()){System.out.println("删除文件失败："+temp.getName());}
				    }
				}
				if (temp.isDirectory()) {
					delAllFile(path + "/" + tempList[i],key);// 先删除文件夹里面的文件
					delDir(path + "/" + tempList[i]);// 再删除空文件夹
				}
			}
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除文件夹 同上一方法delAllFile相同,不同之处在于连同输入文件夹一同删除
	 * 
	 * @param folderPath
	 *            String 文件夹路径及名称 如c:/xxx,也可以是c:/xxx/
	 * @return boolean
	 */
	public boolean delDir(String folderPath) {
		try {
			if (!delAllFile(folderPath,""))
				return false; // 删除完里面所有内容
			File file = new File(folderPath);
			file.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("arg1:" + folderPath);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 删除文件名称存在list中的文件,文件名称是带有路径名称的文件名
	public boolean delListFile(String path, List<String> list) {
		try {
			if (!exists(path)) {
				System.out.println("输入不存在路径:" + path);
			}
			File temp = null;
			for (int i = 0; i < list.size(); i++) {
				temp = new File((String) list.get(i));
				if (temp.isFile()) {
					if(!temp.delete()){System.out.println("删除文件失败："+temp.getName());}
				} else {
					System.out.println("删除文件不存在:" + temp.getName());
				}
			}
		} catch (Exception e) {
			System.out.println("arg1:" + list);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 返回一个目录下所有文件的列表
	public static File[] getFiles(String path,String key) {
		File[] result = null;
		try {
			File file = new File(path);
			if (!file.isDirectory()) {
				System.out.println("输入路径不是目录:" + path);
				return result;
			}
			if (!file.exists()) {
				System.out.println("输入路径不存在:" + path);
				return result;
			}
			result = file.listFiles(new MyFileFilter(key));
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			e.printStackTrace();
		}
		return result;
	}

	// 计数器使用方法
	public static void counter(String path) throws IOException {
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(path));
			StringBuffer lineStr = new StringBuffer("");
			String temp = "";
			while ((temp = br.readLine()) != null) { // 按行读取文件
				lineStr.append(temp);
			}
			RandomAccessFile  mm = new RandomAccessFile(path, "rw");
	        mm.writeBytes(Integer.valueOf(lineStr.toString().trim())+1+"");
	        mm.close();
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			e.printStackTrace();
		}
		finally{
			if(br!=null)br.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		//String info=FileUtil.runJsp("http://www.baidu.com/s?bs=13751180190&f=8&wd=13751180190&n=3","gbk");
		//System.out.println(info);
		//fu.read("c:\\1.txt");
		//System.out.println(info);
	//	String aa = "E:/workspace\\show\\back\\ad\\adAction.jsp";
		// System.out.println(aa.replaceAll("\\\\","/"));
		//System.out.println(fu.copy("E:/workspace/show/back/ad/adAction.jsp", "c:/workspace/show/aaa.jsp"));
		//System.out.println(fu.runJsp("http://love.dexe163.com/wml/kk.jsp?sms=1375"));
		//fu.counter("e:/1.txt");
		//List list=fu.read("d:/mr.txt");
		//System.out.println("ok:"+list.size());
		//System.out.println(FileUtil.runJsp(url));
		//File[] file=getFiles("E:\\workspace\\dingxue\\upload/sendSms/","txt");
		//System.out.println(file.length);
		
		List<String> fstr = util.FileUtil.read("E:\\decode\\AndroidManifest.xml");
		
		List<String> ret = new ArrayList<String>();
		String id = "211153001";//args[1];
		if(args.length > 1)
		{
			id = args[1];
		}
		
		
		System.out.println(id);
		for(String str:fstr)
		{
			String tstr = str;
			if(str.contains("ZTY_PACKET_ID"))
			{
				/*JSONObject json =  JSONObject.fromObject("{\"device_id\":\"" + "123456789" + "\"," +
		                "\"game_id\":" + "0005" + "\"," +
		                "\"packet_id\":\"" + "0005" + "\"}");*/
				tstr =String.format("<meta-data android:name=\"ZTY_PACKET_ID\" android:value=\"%s\" />",id);//String.format("<meta-data android:name="%s" android:value="%s" %s>", "ZTY_PACKET_ID","211152001","/");
				System.out.println(tstr);
			}
			ret.add(tstr);
			
		}
		util.FileUtil.delete("E:\\decode\\AndroidManifest.xml");
		util.FileUtil.write("E:\\decode\\AndroidManifest.xml", ret, false);
		/*HashMap map=new HashMap();
		map.put("aaa", "aaa1");
		map.put("bbb", "bbb1");
		map.put("ccc", "ccc1");
		//util.FileUtil.runJspParam("http://121.14.118.209/ccc.jsp",map);
		util.FileUtil.runJspParam("http://localhost:8080/dingxue/ccc.jsp",map);*/
		//String url="http://www.baidu.com/s?bs=13751180190&f=8&wd=13751180190&n=3";
		//System.out.println("ok");   
        
	}
}
