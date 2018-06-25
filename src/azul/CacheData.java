package azul;

import java.util.HashMap;

public class CacheData {
	@SuppressWarnings("unchecked")
	protected static final HashMap map = new HashMap();
	private static final Object lock = new Object();
    //防止在外部实例化 
	private CacheData() {
	}   

	public static Object getData(Object key) {
		Object v = map.get(key);
		if (v == null) {
			synchronized (lock) {
				v = map.get(key); //   Check   again   to   avoid   re-load   
				//if (v == null)
					//loadDataSource(key);
				//v = map.get(key); //   retrieves   data.   
			}
		}
		return v;
	}

	@SuppressWarnings("unchecked")
	public static void setData(Object key,Object value) {
		/*synchronized (lock) {
			map.put(key, value);  
		}*///lsl,不用使用cash
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap getAllCache() {
		return map;
	}
	
	/*   
	 *Load   data   from   data   source.   
	 */
	@SuppressWarnings("unchecked")
	protected static synchronized void loadDataSource(Object key) {
		Object value = new Object(); //   Load   value   from   data   source 
		System.out.println("没有对应缓存内容");
		map.put(key, value);
	}
}
