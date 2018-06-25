package azul;

import java.util.ArrayList;
import java.util.HashMap;


public class CacheDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public ArrayList getList() {
		Object obj = CacheData.getData("LIST_" + baseTableName);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getList();
			CacheData.setData("LIST_" + baseTableName, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList getList(String pageSql) {
		Object obj = CacheData.getData("LIST_" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getList(pageSql);
			CacheData.setData("LIST_" + pageSql, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList getList(int pageIndex, int size, String pageSql){
		Object obj = CacheData.getData("LIST_" + pageIndex + "_" + size+ "_" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getList(pageIndex, size, pageSql);
			CacheData.setData("LIST_" + pageIndex + "_" + size + "_"+ pageSql, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList getObject(String pageSql) {
		Object obj = CacheData.getData("LIST_OBJECT_" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getObject(pageSql);
			CacheData.setData("LIST_OBJECT_" + pageSql, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList getObjectList(String pageSql) {
		Object obj = CacheData.getData("LIST_OBJECT_LIST" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getObjectList(pageSql);
			CacheData.setData("LIST_OBJECT_LIST" + pageSql, list);
			return list;
		}
	}
	
	//经常变动的表不使用缓存，只是将特殊查询结果缓存下来.不熟悉本方法请不要调用
	//如果sql语句中带的时间大于当前时间就不使用缓存防止读不到最新数据。
	//由于缓存原因可能导致查询数据的当天数据不完全
	@SuppressWarnings("unchecked")
	public ArrayList getObjectListCache(String pageSql) {
		Object obj=CacheData.getData(pageSql);
		//首先看sql语句中是否有超过今天的日期条件，如果有就不使用缓存
		boolean userCache=true;
		int index=pageSql.indexOf("date_time<");
		if(index==-1){
			userCache=false;
		}
		else{
			String subSql=pageSql.substring(index,pageSql.lastIndexOf("'"));
			String date_time=subSql.substring(subSql.indexOf("'")+1,subSql.indexOf("'")+11);
			if(!util.DateUtil.strToDate(date_time).before(new java.util.Date())){
				userCache=false;
			}
		}
		if (obj != null && userCache) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getObjectList(pageSql);
			CacheData.setData(pageSql,list);
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList getObjectList(int pageIndex, int size, String pageSql) {
		Object obj = CacheData.getData("LIST_OBJECT_LIST" + "_"
				+ pageIndex + "_" + size + "_" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getObjectList(pageIndex, size, pageSql);
			CacheData.setData("LIST_OBJECT_LIST" + "_" + pageIndex + "_"
					+ size + "_" + pageSql, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList getValueList(String pageSql) {
		Object obj = CacheData.getData("LIST_VALUE_LIST_" + pageSql);
		if (obj != null) {
			return (ArrayList)obj;
		} else {
			ArrayList list = super.getValueList(pageSql);
			CacheData.setData("LIST_VALUE_LIST_" + pageSql, list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public String getValueStr(String pageSql,String key){
		String result="";
		Object obj = CacheData.getData("STR_VALUE_" + pageSql);
		if (obj != null) {
			return (String) obj;
		} else {
			result = super.getValueStr(pageSql,key);
			CacheData.setData("STR_VALUE_" + pageSql, result);
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object loadBySql(String pageSql) {
		Object obj = CacheData.getData("OBJECT_" + baseTableName + "_"
				+ pageSql);
		if (obj != null) {
			return obj;
		} else {
			obj = super.loadBySql(pageSql);
			CacheData.setData("OBJECT_" + baseTableName + "_" + pageSql,
					obj);
			return obj;
		}
	}

	@SuppressWarnings("unchecked")
	public Object getById(int id) {
		Object obj = CacheData.getData("OBJECT_" + baseTableName + "_"
				+ id);
		if (obj != null) {
			return obj;
		} else {
			obj = super.getById(id);
			CacheData.setData("OBJECT_" + baseTableName + "_" + id, obj);
			return obj;
		}
	}

	public Object getValue(String pageSql) {
		Object obj = CacheData.getData("OBJECT_" + pageSql);
		if (obj != null) {
			return obj;
		} else {
			obj = super.getValue(pageSql);
			CacheData.setData("OBJECT_" + pageSql, obj);
			return obj;
		}
	}

	public int getRecordCount(String pageSql) {
		int i = 0;
		Object obj = CacheData.getData("INT_" + pageSql);
		if (obj != null) {
			return (Integer) obj;
		} else {
			i = super.getRecordCount(pageSql);
			CacheData.setData("INT_" + pageSql, i);
			return i;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<Object,Object> getMap() throws ClassNotFoundException {
		Object obj = CacheData.getData("MAP_" + baseTableName);
		if (obj != null) {
			return (HashMap<Object,Object>) obj;
		} else {
			HashMap<Object,Object> map = super.getMap();
			CacheData.setData("MAP_" + baseTableName, map);
			return map;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<Object,Object> getSelectMap(){
		Object obj = CacheData.getData("MAP_SELECT_" + baseTableName);
		if (obj != null) {
			return (HashMap<Object,Object>) obj;
		} else {
			HashMap<Object,Object> map = super.getSelectMap();
			CacheData.setData("MAP_SELECT_" + baseTableName, map);
			return map;
		}
	}
	
	public HashMap<String,String> getSelectMap(String pageSql) {
		Object obj = CacheData.getData("MAP_" + baseTableName + "_"
				+ pageSql);
		if (obj != null) {
			return (HashMap<String,String>) obj;
		} else {
			HashMap<String,String> map = super.getSelectMap(pageSql);
			CacheData.setData("MAP_" + baseTableName + "_" + pageSql,
					map);
			return map;
		}
	}
}
