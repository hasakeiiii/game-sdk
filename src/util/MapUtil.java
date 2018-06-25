package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import servlet.CommonOperationAction;
import model.App;
import net.sf.json.JSONException;
import common.ObjectVO;

public class MapUtil {
	//将集合里面的元素以，相连
	@SuppressWarnings("unchecked")
	public static List strToList(String str,String key) {
		List list=new ArrayList();
		String[] arr=str.split(key);
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}
	
	//将集合里面的元素以，相连
	@SuppressWarnings("unchecked")
	public static final String getLinkStr(Collection coll) {
		String temp="";
		StringBuffer result = new StringBuffer("");
		try {
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				String element = iter.next().toString();
				result.append(element).append(",");
			}
			temp=result.toString();
			if (!"".equals(temp)) {
				temp = temp.substring(0, temp.length()-1);
			}
		} catch (Exception e) {
			System.out.println("StringUtil.getLinkStr error:" + e);
		}
		return temp;
	}
	
	//Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);  
	@SuppressWarnings("unchecked")
	public static List arrayToList(Object[] array) {
		if (array != null) {
			ArrayList<Object> list = new ArrayList<Object>(array.length);
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
			return list;
		} else {
			return null;
		}
	}
	
	//返回包含key和value的两个list
	@SuppressWarnings("unchecked")
	public ObjectVO sortMap(Map map) {
		ObjectVO result = new ObjectVO();
		List keyList = null;
		List valueList = null;
		try {
			Object[] keySet = map.keySet().toArray();
			Arrays.sort(keySet);
			keyList = new ArrayList();
			valueList = new ArrayList();
			for (Object obj : keySet) {
				keyList.add(obj);
				valueList.add(map.get(obj));
			}
			result.setObj1(keyList);
			result.setObj2(valueList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//将map的key按照str或者int，double值排序，返回排序后的list
	//kind:排序使用key还是用value
	//type:排序用的key或value对应类型是什么,String,int,long,double
	//order: desc降序，asc升序
	/*
	List list=getSortList(map,"key","int","asc");
    for (int i=0;i<list.size();i++){
    	Map.Entry aaa=(Map.Entry)list.get(i);
		System.out.println(aaa.getKey());
		System.out.println(aaa.getValue());
    }
	*/
	@SuppressWarnings("unchecked")
	public static List getSortList(Map map,final String kind,final String type,final String order) {
		List sortList = new ArrayList(map.entrySet());
		try {
			Collections.sort(sortList, new Comparator() {
				public int compare(Object o1, Object o2) {
					Map.Entry obj1 = (Map.Entry) o1;
	                Map.Entry obj2 = (Map.Entry) o2;
	                Object temp1;
	                Object temp2;
	                if("value".equals(kind)){
	                	temp1=obj1.getValue();
	                	temp2=obj2.getValue();
	                }
	                else{
	                	temp1=obj1.getKey();
	                	temp2=obj2.getKey();
	               }
                   if("double".equals(type)){
                		double value1=(Double)temp1;
                		double value2=(Double)temp2;
                		if("desc".equals(order)){
                			return value2-value1>0?1:-1;
                		}
                		else{
                			return value1-value2>0?1:-1;
                		}
                	}
                	else if("long".equals(type)){
                		long value1=(Long)temp1;
                		long value2=(Long)temp2;
                		if("desc".equals(order)){
                			return value2-value1>0?1:-1;
                		}
                		else{
                			return value1-value2>0?1:-1;
                		}
                	}
                	else if("int".equals(type)){
                		int value1=(Integer)temp1;
                		int value2=(Integer)temp2;
                		if("desc".equals(order)){
                			return value2-value1;
                		}
                		else{
                			return value1-value2;
                		}
                	}
                	else{
                		if("desc".equals(order)){
                			return temp2.toString().compareTo(temp1.toString());  
                		}
                		else{
                			return temp1.toString().compareTo(temp2.toString());  
                		}
                	}
				}
			});
		} catch (Exception e) {
			System.out.println(e);
		}
		return sortList;
	}
	
	/*
	//将map的value按照str排序，返回排序后的list
	@SuppressWarnings("unchecked")
	public static List getSortListByStrValue(Map map) {
		List sortList = new ArrayList(map.entrySet());
		try {
			Collections.sort(sortList, new Comparator() {
				public int compare(Object o1, Object o2) {
					Map.Entry obj1 = (Map.Entry) o1;
	                Map.Entry obj2 = (Map.Entry) o2;
	                return obj1.getValue().toString().compareTo(obj2.getValue().toString());  
				}
			});
		} catch (Exception e) {
			System.out.println(e);
		}
		return sortList;
	}
*/
	//实现对map按照key排序
    @SuppressWarnings("unchecked")
    public static Map.Entry[] sortByKey(Map map) {
        Set set = map.entrySet();
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
        Arrays.sort(entries, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                Object key1 = ((Map.Entry) arg0).getKey();
                Object key2 = ((Map.Entry) arg1).getKey();
               // ToPinYinString(a1.getName()).compareTo(ToPinYinString(a2.getName()));
                return ((Comparable) key1).compareTo(key2);
            }
        });
        
        return entries;
    }
    
  //实现对map按照key排序
    @SuppressWarnings("unchecked")
    public static Map.Entry[] sortByValuePinyin(Map map) {
        Set set = map.entrySet();
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
      
        Arrays.sort(entries, new Comparator() {
        	//return CommonOperationAction.ToPinYinString((String)a1.getValue()).compareTo(CommonOperationAction.ToPinYinString((String)a2.getValue()));
			@Override
			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				return CommonOperationAction.ToPinYinString((String) ((Map.Entry) arg0).getValue()).compareTo(CommonOperationAction.ToPinYinString((String) ((Map.Entry) arg1).getValue()));
			}
        });
        
        return entries;
    }
     /*
     * Map.Entry[] entries=teacherServiceDao.sortByValue(sortMap);

     * Iterator<Entry<Integer, common.ObjectVO>> it=sortMap.entrySet().iterator();
			while(it.hasNext()){
			    Entry<Integer,common.ObjectVO> entry = it.next();
				int keyId=entry.entry.getKey();
				common.ObjectVO vo=entry.getValue();
				String name=(String)vo.getObj1();
				int point=(Integer)vo.getObj2();
				int time=(Integer)vo.getObj3();
				int avg=(Integer)vo.getObj4();
			}
	   }
     */
    @SuppressWarnings("unchecked")
    public static Map.Entry[] sortByValue(Map h) {
        Set set = h.entrySet();
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
        Arrays.sort(entries, new Comparator() {
            public int compare(Object arg0, Object arg1) {
//              int key1 = (Integer)((common.ObjectVO)((Map.Entry)arg0).getValue()).getObj4();
//              int key2 =(Integer)((common.ObjectVO)((Map.Entry)arg1).getValue()).getObj4();
//              return key2-key1;
            	Double key1 = (Double)((ObjectVO)((Map.Entry)arg0).getValue()).getObj4();
            	Double key2 = (Double)((ObjectVO)((Map.Entry)arg1).getValue()).getObj4();
                return key1.compareTo(key2);
            }
        });
        return entries;
    }
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
        HashMap map=new HashMap();
        map.put(1,9.2);
        map.put(2,20.4);
        map.put(3,4.45);
        map.put(7,11.65);
        map.put(5,7.15);
        map.put(4,0.56);
        List list=getSortList(map,"value","long","desc");
        for (int i=0;i<list.size();i++){
        	Map.Entry aaa=(Map.Entry)list.get(i);
    		//System.out.println(aaa.getKey());
    		System.out.println(aaa.getValue());
        }

//----------------------

	}
}
