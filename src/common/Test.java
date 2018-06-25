package common;

import java.io.UnsupportedEncodingException;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Calendar;
import java.util.Comparator;


public class Test {
	static Calendar cal = Calendar.getInstance();
	//private static Log log = LogFactory.getLog(Test.class); 
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("2009-12-25".substring(5,7));
		//System.out.println(mmspage.Tools.des("[pools]","123456",true));
		//F96076972EF0576514DC92FB4A82D778
			
		
		//String aa="%6VSMS8%1&13751180190&1DWZWX&10,&10&15&&0&2&,,&a,,&请回复,,&确认,,&";
		//System.out.println(java.net.URLEncoder.encode(aa,"UTF-8"));
	
	}
	public void execute(String cmd){
        try {
        	//log.info("bbbbbbbbbbbb");
        	System.out.println("2009-12-25".substring(5,7));
            //java.lang.Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}
class CollatorComparator implements Comparator {   
    Collator collator = Collator.getInstance();   
    public int compare(Object element1, Object element2) {    
        CollationKey key1 = collator.getCollationKey(element1.toString());    
        CollationKey key2 = collator.getCollationKey(element2.toString());    
        return key1.compareTo(key2);    
    }    
}