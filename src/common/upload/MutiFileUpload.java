package common.upload;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MutiFileUpload extends FileUploadBase {
	public MutiFileUpload(HttpServletRequest request) {
		try {
			parseRequest(request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
    private Map<String, FileItem> files;// 保存上传的文件
    private long filesSize = 0; // 所有文件的总大小

    public void parseRequest(HttpServletRequest request)
            throws UnsupportedEncodingException {
        files = new HashMap<String, FileItem>();
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(sizeThreshold);
        if (repository != null)
            factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding(encoding);
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String value = item.getString(encoding);
                    parameters.put(fieldName, value);
                } else {
                    String fieldName = item.getFieldName();
                    files.put(fieldName, item);
                    filesSize += item.getSize();
                }
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    /** *//**
     * 上传文件, 调用该方法之前必须先调用 parseRequest(HttpServletRequest request)
     * @param parent 文件存储的目录
     * @throws Exception
     */
    public void upload(FileItem item,String fileName) throws Exception {
        if (files.isEmpty())
            return;
        
        if (sizeMax > -1 && filesSize > super.sizeMax){
            System.out.println("MutiFileUpload size is too big,max size:"+sizeMax+"   size:"+filesSize);
            return;
        }
        buildPath(fileName);
        File file = new File(fileName);
        item.write(file);
    }

    public Map<String, FileItem> getFiles() {
        return files;
    }
    
	// 如果路径不存在就创建新的路径,输入是文件带路径的名称
	public boolean buildPath(String fileName) {
		boolean result=false;
		try {
	        String filePath = fileName;
	        //输入是路径不是文件
		    if(fileName.lastIndexOf("/")!=fileName.length()-1){
		    	filePath=fileName.substring(0, fileName.lastIndexOf("/") + 1);
		    }

			File file = new File(filePath);
			if (!file.exists()) {
				// 注意file.mkdir只能建单层目录.file.mkdirs可以建多层目录
				if(!file.mkdirs()){System.out.println("建立文件夹失败："+file.getName());}
				result=true;
			}
			else{
				result=true;
			}
		} catch (Exception e) {
			System.out.println("arg1:"+fileName);					
			e.printStackTrace();
		}
		return result;
	}

}
