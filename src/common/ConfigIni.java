package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public final class ConfigIni {
	static String filePath="";
	static{
		filePath=ConfigIni.class.getResource("").toString();
		filePath=filePath.substring(6,filePath.indexOf("WEB-INF/"));
	}
	public static String getValue(String section,String variable) {
		String tempLine, result = "";
		BufferedReader bufferedReader = null;
		boolean isInSection = false;
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath+"system.ini"));
			while ((tempLine = bufferedReader.readLine()) != null) {
				tempLine = tempLine.trim();
				//System.out.println(tempLine);
				if (Pattern.compile("\\[\\s*.*\\s*\\]").matcher((tempLine)).matches()){
           					if(Pattern.compile("\\[\\s*" + section + "\\s*\\]").matcher((tempLine)).matches()){
						isInSection=true;
					}
				}
				if (isInSection) {
					if(!"".equals(tempLine)){
						String[] strArray = tempLine.split("=");
						String tempKey = strArray[0].trim();
						if (strArray.length>1 && tempKey.equalsIgnoreCase(variable)) {
							result=strArray[1].trim();
						}				
					}
				}
			}
			bufferedReader.close();
		}
		catch(Exception e){
			System.out.println("ConfigFile.getValue error:"+e);
		}
		return result;
	}

	public static boolean setValue(String section,String variable,String newValue){
		boolean result=true;
		boolean isInSection = false;	
		String tempLine,tempKey;
		StringBuffer fileContent=new StringBuffer("");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath+"system.ini"));
			while ((tempLine = bufferedReader.readLine()) != null) {
				tempLine = tempLine.trim();
					if (Pattern.compile("\\[\\s*.*\\s*\\]").matcher((tempLine)).matches()){
       					if(Pattern.compile("\\[\\s*" + section + "\\s*\\]").matcher((tempLine)).matches()){
						isInSection=true;
					}
				}
				if (isInSection) {
					String[] strArray = tempLine.split("=");
					tempKey = strArray[0].trim();
					if (tempKey.equalsIgnoreCase(variable)) {
						tempLine = tempKey+"="+newValue;
					}
				}
				fileContent.append(tempLine).append("\r\n");
			}
			bufferedReader.close();
			BufferedWriter bufferedWriter =
				new BufferedWriter(new FileWriter(filePath+"system.ini"));
			bufferedWriter.write(fileContent.toString());
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException ex) {
			result=false;
			System.out.println("ConfigFile.setValue error:"+ex);
		}
		return result;
	}
	public static boolean makeValue(String section,String variable,String newValue){
		boolean result=true;
		boolean isSectionExist = false;
		boolean isInSection = false;
		boolean isOutSection = true;
		boolean isKeyExisit=false;		
		String tempLine,tempKey;
		StringBuffer fileContent=new StringBuffer("");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath+"system.ini"));
			while ((tempLine = bufferedReader.readLine()) != null) {
				tempLine = tempLine.trim();
				if (Pattern.compile("\\[\\s*.*\\s*\\]").matcher((tempLine)).matches()){
					if(Pattern.compile("\\[\\s*" + section + "\\s*\\]").matcher((tempLine)).matches()){
						isSectionExist=true;
						isInSection=true;
						isOutSection=false;
					}
					else{
						isInSection=false;
						isOutSection=true;
					}
				}
				if (isInSection) {
					String[] strArray = tempLine.split("=");
					tempKey = strArray[0].trim();
					if (tempKey.equalsIgnoreCase(variable)) {
						tempLine = tempKey+"="+newValue;
						isKeyExisit=true;
					}
				}
				if(isSectionExist && !isInSection && isOutSection && !isKeyExisit){
					fileContent.append(variable).append("=").append(newValue).append("\r\n");
					isKeyExisit=true;
				}
				fileContent.append(tempLine).append("\r\n");
			}
			if(isSectionExist && !isKeyExisit){
				fileContent.append(variable).append("=").append(newValue).append("\r\n");
			}
     		if(!isSectionExist){
				fileContent.append("[").append(section).append("]").append("\r\n");
				fileContent.append(variable).append("=").append(newValue).append("\r\n");
			}
			bufferedReader.close();
			BufferedWriter bufferedWriter =
				new BufferedWriter(new FileWriter(filePath+"system.ini", false));
			bufferedWriter.write(fileContent.toString());
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException ex) {
			result=false;
			System.out.println("ConfigFile.setValue error:"+ex);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String cid=ConfigIni.getValue("feeCid","cid");
		System.out.println(cid);
//		 String value = ConfigIni.getValue("c:/workIni.ini", "trColor",
//		 "show", "");
//		 System.out.println(value);
		//ConfigIni("c:/workIni.ini", "tcColor","down", "#666ccc");

			//System.out.println(ConfigIni.setValue("c:/workIni.ini","trColor","downColor","#6c6bbb"));
			
//			com.util.ConfigIni.setValue(configFilePath,"trColor1","showColor","show11");
//			com.util.ConfigIni.setValue(configFilePath,"trColor","playColor","play11");
//			com.util.ConfigIni.setValue(configFilePath,"trColor","downColor","down11");
//			com.util.ConfigIni.setValue(configFilePath,"trColor","searchColor","searchId11");
//			com.util.ConfigIni.setValue(configFilePath,"trColor","typeColor","type11");
//			com.util.ConfigIni.setValue(configFilePath,"trColor","memoColor","memo11");
			
		
//			com.util.ConfigIni.makeValue(configFilePath,"trColor","playColor","play11");
//			com.util.ConfigIni.makeValue(configFilePath,"trColor","downColor","down11");
//			com.util.ConfigIni.makeValue(configFilePath,"trColor","searchColor","searchId11");
//			com.util.ConfigIni.makeValue(configFilePath,"trColor","typeColor","type11");
//			com.util.ConfigIni.makeValue(configFilePath,"trColor","memoColor","memo11");
	}
}
