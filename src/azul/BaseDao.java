package azul;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
<<<<<<< .mine
=======
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
>>>>>>> .r833
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
<<<<<<< .mine
=======
import java.sql.Time;
>>>>>>> .r833
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
<<<<<<< .mine
=======
import java.util.Set;
>>>>>>> .r833

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.App;
import model.ChannelDataReq;
import model.DeviceInfo;
import model.Pay;
import model.ProvinceData;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import viewmodel.SearchCom;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.unionpay.upmp.sdk.examples.QueryExample;
//import com.alipay.sign.Base64;
import com.yeepay.HttpUtils;

import common.ObjectVO;
<<<<<<< .mine
import dao.DeviceInfoDao;
=======
>>>>>>> .r833
import dao.PayDao;
<<<<<<< .mine
import dao.ProvinceDataDao;
=======
>>>>>>> .r833
import db.ConnectionFactory;
import db.ConnectionFactoryBk;

public class BaseDao {

	private static final ThreadLocal<HttpServletRequest> context = new ThreadLocal<HttpServletRequest>();

	public static void setContext(HttpServletRequest request) {
		context.set(request);
	}

	public static HttpServletRequest getContext() {
		return context.get();
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String baseModelName = "";
	public String baseTableName = "";
	private static String PACKEG_NAME = "model.";
	public static boolean AUTO_PRINT = false;
	public static void setAutoPrint(boolean autoPrint){
		AUTO_PRINT=autoPrint;
	}
	@SuppressWarnings("unchecked")
	public void init() {
		// 前2位是Thread和BaseDao,所以从第三位开始
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		for (int i = 2; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			baseModelName = ste.getFileName();
			//如果tomcat报tableName=  modelName= 1.没有在XxxDao中调用init();方法 2.数据库结构和表结构不对应
			if (baseModelName.indexOf("Dao.java") > -1) {
				baseModelName = baseModelName.replace("Dao.java", "");
				break;
			}
		}
		if (CacheSystem.tableMap.containsKey(baseModelName)) {
			baseTableName = (String) CacheSystem.tableMap.get(baseModelName);
		}
	}

	@SuppressWarnings("unchecked")
	public int executeUpdate(String sql) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		try {
			printSql(sql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			if (result > 0) {
				//如果不是页面操作,此时得不到操作session,不记录操作日志
				HttpServletRequest request = getContext();
				if(request==null){
					return result;
				}
				/*HttpSession session=request.getSession();//lsl
				if (session != null && session.getAttribute("sysUser") != null && sql.indexOf("sys_logs") == -1) {
					SysUser sysUser = (SysUser) session.getAttribute("sysUser");
					int sys_user_id = sysUser.getSysUserId();
					String act = "";
					sql = sql.toLowerCase();
					if (sql.indexOf("insert") > -1) {
						act = "ADD";
					} else if ((sql.indexOf("update") > -1)) {
						act = "EDIT";
					} else if ((sql.indexOf("delete") > -1)) {
						act = "DELETE";
					}
					StringBuffer sb = new StringBuffer();
					sb.append("insert into sys_logs (sys_user_id,ip,act_type,act) values (");
					sb.append(sys_user_id);
					sb.append(",'");
					sb.append(request.getRemoteAddr());
					sb.append("','");
					sb.append(act);
					sb.append("','");
					sb.append(sql.replace("'", "\\'"));
					sb.append("')");
					stmt.executeUpdate(sb.toString());
					clearCache(baseTableName);
				}*/
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println(sql);
		} finally {
			ConnectionFactory.close(stmt,conn);
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int executeUpdateBk(String sql) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		try {
			printSql(sql);
			conn = ConnectionFactoryBk.getInstance().getConnection();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			if (result > 0) {
				HttpServletRequest request = getContext();
				if(request==null){
					return result;
				}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println(sql);
		} finally {
			ConnectionFactoryBk.close(stmt,conn);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getList() {
		ArrayList<Object> list = new ArrayList<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String pageSql = "select * from " + baseTableName;
		try {
			printSql(pageSql);
			Class cls = Class.forName(PACKEG_NAME + baseModelName);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				Object obj = cls.newInstance();
				setMethodValue(obj, rs);
				list.add(obj);
			}
		} catch (SQLException e) {
			System.err.println("------------BaseDao.getList() exception:"
					+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "  modelName="
					+ baseModelName);
			System.err.println(pageSql);
			System.err.println(e.toString());
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getList(String pageSql) {
		ArrayList<Object> list = new ArrayList<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			Class cls = Class.forName(PACKEG_NAME + baseModelName);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				Object obj = cls.newInstance();
				setMethodValue(obj, rs);
				list.add(obj);
			}
		} catch (SQLException ex) {
			System.err
					.println("-------------BaseDao.getList(String) exception:"
							+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "  modelName="
					+ baseModelName);
			System.err.println(pageSql);
			System.err.println(ex.toString());
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getList(int pageIndex, int size, String pageSql) {
		ArrayList<Object> list = new ArrayList<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer("");
		try {
			sb.append(pageSql).append(" limit ").append((pageIndex - 1) * size)
					.append(",").append(size);
			printSql(sb.toString());
			Class cls = Class.forName(PACKEG_NAME + baseModelName);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Object obj = cls.newInstance();
				setMethodValue(obj, rs);
				list.add(obj);
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getList(int,int,String)  exception:"
							+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "  modelName="
					+ baseModelName);
			System.err.println(sb.toString());
			System.err.println(e.toString());
			try {
				throw new Exception(e);
			} catch (Exception e1) {
			}
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}
	

	public <E> List<E> getTemplateList(Class<E> clz) {
		List<E> list = new ArrayList<E>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer sb = new StringBuffer("");
		try {
			
			printSql(sb.toString());
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			rsmd = rs.getMetaData();
			
			//得到结果集每一列的名字,以及对应的java ClassName
			Map<String,String> map = new HashMap<>();
			
			for(int i = 1 ,rsmdCount = rsmd.getColumnCount(); i <= rsmdCount ; i ++){
				map.put(rsmd.getColumnName(i), rsmd.getColumnClassName(i));
			}
			
			while (rs.next()) {
				E element = setMethodValue(map,clz,rs);
				list.add(element);
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getList(int,int,String)  exception:"
							+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "  modelName="
					+ baseModelName);
			System.err.println(sb.toString());
			System.err.println(e.toString());
			try {
				throw new Exception(e);
			} catch (Exception e1) {
			}
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}
	

	
	/**
	 * 根据需要的viewModel模版返回list   
	 * 需要注意:如果数据库下划线字段名和model字段名一致则不用处理
	 * 如果是自定义字段,sql语句一定要起别名,且别名跟model字段名一致(首字母小写,驼峰标识)
	 * @param clz
	 * @param pageIndex
	 * @param size
	 * @param pageSql
	 * @return
	 */

	public <E> List<E> getTemplateList(Class<E> clz,int pageIndex, int size, String pageSql) {
		List<E> list = new ArrayList<E>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer sb = new StringBuffer("");
		try {
			sb.append(pageSql).append(" limit ").append((pageIndex - 1) * size)
					.append(",").append(size);
			printSql(sb.toString());
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			rsmd = rs.getMetaData();
			
			//得到结果集每一列的名字,以及对应的java ClassName
			Map<String,String> map = new HashMap<>();
			
			for(int i = 1 ,rsmdCount = rsmd.getColumnCount(); i <= rsmdCount ; i ++){
				map.put(rsmd.getColumnName(i), rsmd.getColumnClassName(i));
			}
			
			while (rs.next()) {
				E element = setMethodValue(map,clz,rs);
				list.add(element);
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getList(int,int,String)  exception:"
							+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "  modelName="
					+ baseModelName);
			System.err.println(sb.toString());
			System.err.println(e.toString());
			try {
				throw new Exception(e);
			} catch (Exception e1) {
			}
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	private <E> E setMethodValue(Map<String,String> map,Class<E> elementClass, ResultSet rs) throws InstantiationException, IllegalAccessException{
		E element = elementClass.newInstance();
		Set<Map.Entry<String, String>> set = map.entrySet();
		
		for(Map.Entry<String, String> entry : set){
			String columnName = entry.getKey();
			String columnClass = entry.getValue();
			
			//对列名进行处理 去除下划线,转成驼峰标识
			String[] columnNames = columnName.split("_");
			StringBuilder builder = new StringBuilder();
			
			for(String temp : columnNames){
				//name = name.substring(0, 1).toUpperCase() + name.substring(1);
				char[] cs = temp.toCharArray();
		        cs[0] -= 32;
				builder.append(String.valueOf(cs));
			}
			
			char[] cs = builder.toString().toCharArray();
			cs[0] += 32;
			String columnNameWithoutSetter = String.valueOf(cs);
			String columnSetterName = "set" + builder.toString();
			
			
			try {
				//获取字段的class类型
				Class<?> clz = Class.forName(columnClass);
				Method method = null;
				Object temp = null;
				
				
				if(clz.equals(Long.class)){
					Field field = elementClass.getDeclaredField(columnNameWithoutSetter);
					Class<?> fieldClass = field.getType();
					if(!fieldClass.equals(clz)){
						temp = rs.getInt(columnName);
						clz = fieldClass;
					}else
						temp = rs.getLong(columnName);
				}else if (clz.equals(Integer.class)){
					temp = rs.getInt(columnName);
				}else if (clz.equals(String.class)){
					temp = rs.getString(columnName);
				}else if (clz.equals(BigDecimal.class)){
					temp = rs.getBigDecimal(columnName);
				}else if (clz.equals(java.sql.Date.class)){
					
					Field field = elementClass.getDeclaredField(columnNameWithoutSetter);
					Class<?> fieldClass = field.getType();
					
					java.sql.Date date = rs.getDate(columnName);
					long time = date.getTime();
					Date d = new java.util.Date(time);
					
					//以yyyy-MM-dd返回
					if(fieldClass.equals(String.class)){
						temp = DateUtil.getDate(d);
					}else if (fieldClass.equals(java.util.Date.class)){
						temp = d;
					}
					clz = fieldClass;
				}else if (clz.equals(java.sql.Time.class)){
					
					Field field = elementClass.getDeclaredField(columnNameWithoutSetter);
					Class<?> fieldClass = field.getType();
					
					Time time = rs.getTime(columnName);
					long t = time.getTime();
					
					Date d = new java.util.Date(t);
					
					//以HH:mm:ss返回
					if(fieldClass.equals(String.class)){
						temp = new SimpleDateFormat("HH:mm:ss").format(d);
					}else if (fieldClass.equals(Time.class)){
						temp = time;
					}
					clz = fieldClass;
				}else if(clz.equals(Double.class)){
					temp = rs.getDouble(columnName);
				}else
					temp = rs.getObject(columnName);
				
				method = elementClass.getDeclaredMethod(columnSetterName, clz);
				method.invoke(element, temp);
				
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e2) {
				e2.printStackTrace();
			} catch (NoSuchMethodException e1) {
				continue;
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (NoSuchFieldException e) {
				continue;
			}
			
		}
		
		return element;
		   
	}

	/*
	 * sql="select teacher_id,nickname,count(teacher_id) as num from vote";
	 * List tempList=voteDao.getObject(sql); 
	 * int tempTeacherId=(Integer)tempList.get(0); 
	 * String nickName=""+tempList.get(1); 
	 * long num=(Long)tempList.get(2); }
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getObject(String pageSql) {
		ArrayList<Object> result = new ArrayList<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			ResultSetMetaData meta = rs.getMetaData();
			if (rs.next()) {
				
				for (int i = 0; i < meta.getColumnCount(); i++) {
					Object obj = rs.getObject(i + 1);
					if (obj != null) {
						if (obj.toString().equals("true")) {
							result.add(1);
						} else if (obj.toString().equals("false")) {
							result.add(0);
						} else {
							result.add(obj);
						}
					} else {
						result.add(null);
					}
				}
				
			}

		} catch (SQLException e) {
			System.err.println("-------------BaseDao.getObject(String) exception:"+ sdf.format(new Date()));
			System.err.println(pageSql);
			System.err.println(e.toString());
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return result;
	}

	/*
	 * sql="select teacher_id,nickname,count(teacher_id) as num from vote"; List
	 * voteList=(ArrayList)voteDao.getObjectList(sql); 
	 * for(int i=0;i<voteList.size();i++){
	 * List tempList=(List)voteList.get(i); 
	 * int tempTeacherId=(Integer)tempList.get(0); String
	 * nickName=""+tempList.get(1); long num=(Long)tempList.get(2); }
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList> getObjectList(String pageSql) {
		ArrayList<ArrayList> list = new ArrayList<ArrayList>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				ArrayList<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					Object obj = rs.getObject(i + 1);
					if (obj != null) {
						if (obj.toString().equals("true")) {
							tempList.add(1);
						} else if (obj.toString().equals("false")) {
							tempList.add(0);
						} else {
							tempList.add(obj);
						}
					} else {
						tempList.add(null);
					}
				}
				list.add(tempList);
			}
		} catch (SQLException e) {
			System.err
					.println("-------------BaseDao.getObjectList(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
			System.err.println(e.toString());
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList> getObjectList(int pageIndex, int size, String pageSql) {
		pageSql = pageSql + " limit " + (pageIndex - 1) * size + "," + size;
		ArrayList<ArrayList> list = new ArrayList<ArrayList>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				ArrayList<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					Object obj = rs.getObject(i + 1);
					if (obj != null) {
						if (obj.toString().equals("true")) {
							tempList.add(1);
						} else if (obj.toString().equals("false")) {
							tempList.add(0);
						} else {
							tempList.add(obj);
						}
					} else {
						tempList.add(null);
					}
				}
				list.add(tempList);
			}
		} catch (SQLException e) {
			System.err.println("-------------BaseDao.getObjectList(int,int,String) exception:"+ sdf.format(new Date()));
			System.err.println("pageIndex:" + pageIndex + "  size:" + size);
			System.err.println(pageSql);
			System.err.println(e.toString());
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Object loadBySql(String pageSql) {
		Object obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			Class cls = Class.forName(PACKEG_NAME + baseModelName);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			if (rs.next()) {
				obj = cls.newInstance();
				setMethodValue(obj, rs);
			}
		} catch (Exception e) {
			System.err.println("-------------BaseDao.loadBySql(String) exception:"+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName
					+ "   baseModelName=" + baseModelName);
			System.err.println(pageSql);
			e.printStackTrace();
			System.err.println(e.toString());
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public Object getById(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Object obj = null;
		StringBuffer pageSql = new StringBuffer("");
		try {
			String indexName = CacheSystem.indexMap.get(baseTableName);
			pageSql.append("select * from ").append(baseTableName).append(
					" where ").append(indexName).append("=").append(id);
			printSql(pageSql.toString());
			Class cls = Class.forName(PACKEG_NAME + baseModelName);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql.toString());
			if (rs.next()) {
				obj = cls.newInstance();
				setMethodValue(obj, rs);
			}
		} catch (SQLException e) {
			System.err.println("-------------BaseDao.getById(int) exception:"
					+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName
					+ "   baseModelName=" + baseModelName + "   id=" + id);
			System.err.println(pageSql);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return obj;
	}

	public Object getValue(String pageSql) {
		Object obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				obj = rs.getObject(1);
			}
		} catch (SQLException e) {
			System.err
					.println("-------------BaseDao.getValue(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getValueList(String pageSql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.err
					.println("-------------BaseDao.getValueList(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return list;
	}

	//得到表中所有记录的具体值,并以特定符号相连
	@SuppressWarnings("unchecked")
	public String getValueStr(String pageSql, String key) {
		String result = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			StringBuffer sb = new StringBuffer();
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				sb.append(rs.getString(1));
				sb.append(key);
			}
			result = sb.toString();
			if (result.length() > 0) {
				result = result.substring(0, result.length() - key.length());
			}
		} catch (SQLException e) {
			System.err
					.println("-------------BaseDao.getValueList(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String add(Object obj) {
		String result = "-1";
		StringBuffer sb = new StringBuffer();
		//DebuUtil.log("添加");
		try {
			sb.append("insert into ");
			sb.append(baseTableName);
			sb.append(" (");
			StringBuffer sbName = new StringBuffer();
			StringBuffer sbValue = new StringBuffer();
			ArrayList<ObjectVO> list =  CacheSystem.paramMap.get(baseTableName);
			for (int i = 1; i < list.size(); i++) {
				ObjectVO vo = list.get(i);
				String name = (String) vo.getObj1();
				Class type = (Class) vo.getObj2();
				Method getMethod = (Method) vo.getObj4();
				Object temp = getMethod.invoke(obj, new Object[]{});
				if (temp == null && !"date_time".equals(name)) {
					continue;
				}
				sbName.append(name);
				sbName.append(",");
				if (type == Integer.class || type == Double.class) {
					sbValue.append(temp);
				} else {
					if ("date_time".equals(name)) {
						sbValue.append("now()");
						;
					} else {
						sbValue.append("'");
						sbValue.append(temp);
						sbValue.append("'");
					}
				}
				sbValue.append(",");
			}
			// 由于最后一个属性有可能为空所以不能以是否是最后属性来截取","号
			sb.append(sbName.substring(0, sbName.length() - 1));
			sb.append(") values (");
			sb.append(sbValue.substring(0, sbValue.length() - 1));
			sb.append(")");
			//DebuUtil.log("添加SQL="+sb.toString());
			int k = executeUpdate(sb.toString());
			if (k == 1) {
				result = "1";
			} else {
				System.err.println("BaseDao.add(Class) error");
				System.err.println("tableName=" + baseTableName);
				
				DebuUtil.log("BaseDao.add(Class) error");
				DebuUtil.log("tableName=" + baseTableName);
			}
		} catch (NullPointerException ex) {
			System.err.println("-------------BaseDao.add(Class) exception:"
					+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName);
			
			DebuUtil.log("-------------BaseDao.add(Class) exception:"
					+ sdf.format(new Date()));
			DebuUtil.log("tableName=" + baseTableName);
			
		} catch (Exception e) {
			DebuUtil.log("添加异常");
			System.out.println(e);
			System.out.println(e.getStackTrace());
		}
		return result;
	}
	public boolean Edit(Object obj)
	{
		boolean ret = false;
		String retStr = edit(obj);
		if(retStr.equals("1"))
		{
			ret = true;
		}
		return ret;
	}
	@SuppressWarnings("unchecked")
	public String edit(Object obj) {
		String result = "-1";
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("update ");
			sb.append(baseTableName);
			sb.append(" set ");
			StringBuffer setValue = new StringBuffer();
			StringBuffer whereValue = new StringBuffer();
			ArrayList<ObjectVO> list = (ArrayList<ObjectVO>) CacheSystem.paramMap.get(baseTableName);
			for (int i = 0; i < list.size(); i++) {
				ObjectVO vo = list.get(i);
				String name = (String) vo.getObj1();
				Class type = (Class) vo.getObj2();
				Method getMethod = (Method) vo.getObj4();
				Object temp = getMethod.invoke(obj, new Object[]{});
				if (temp == null) {
					continue;
				}
				if (i == 0) {
					whereValue.append(" where ");
					whereValue.append(name);
					whereValue.append("=");
					whereValue.append(temp);
				} else {
					setValue.append(name);
					setValue.append("=");
					if (type == Integer.class) {
						setValue.append(temp);
					} else if (type == Double.class) {
						setValue.append(temp);
					} else {
						setValue.append("'");
						setValue.append(temp);
						setValue.append("'");
					}
					setValue.append(",");
				}
			}
			sb.append(setValue.substring(0, setValue.length() - 1)).append(
					whereValue);
			//System.out.println("edit=" + sb.toString());
			int k = executeUpdate(sb.toString());
			if (k == 1) {
				result = "1";
			}
		} catch (StringIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			System.out
					.println("BaseDao.edit(Class) StringIndexOutOfBoundsException");
		} catch (ClassCastException ex) {
			System.err
					.println("-------------BaseDao.edit(Class) null point exception error");
			System.err.println("tableName=" + baseTableName);
		} catch (Exception e) {
			System.err.println("-------------BaseDao.edit(Class) error");
			System.err.println("tableName=" + baseTableName);
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String delete(int id) {
		String result = "-1";
		StringBuffer pageSql = new StringBuffer("");
		try {
			String tableKey = CacheSystem.indexMap.get(baseTableName);
			pageSql.append("delete from ").append(baseTableName).append(
					" where ").append(tableKey).append("=").append(id);
			int k = executeUpdate(pageSql.toString());
			if (k == 1) {
				result = "1";
			}
		} catch (Exception e) {
			System.err.println("-------------BaseDao.delete(int) exception:"
					+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + " id=" + id);
			System.err.println(pageSql);
		}
		return result;
	}

	@SuppressWarnings( { "unused", "unchecked" })
	public String delete(String idStr) {
		if ("".equals(idStr)) {
			return "-1";
		}
		String result = "-1";
		StringBuffer pageSql = new StringBuffer("delete from ");
		pageSql.append(baseTableName);
		try {
			String tableKey = CacheSystem.indexMap.get(baseTableName);
			if (idStr.indexOf(",") == -1) {
				if (idStr.indexOf("and") > 3) {
					pageSql.append(" where 1=1 ").append(idStr);
				} else {
					pageSql.append(" where 1=1 and ").append(idStr);
				}
			} else {
				pageSql.append(" where ").append(tableKey).append("in (")
						.append(idStr).append(")");
			}
			int k = executeUpdate(pageSql.toString());
			if (k == 1) {
				result = "1";
			}
		} catch (Exception e) {
			System.err.println("-------------BaseDao.delete(String) exception:"
					+ sdf.format(new Date()));
			System.err.println("tableName=" + baseTableName + "   idStr="
					+ idStr);
			System.err.println(pageSql);
		}
		return result;
	}

	// String sql="select max(sort) from product where classid=2"
	public int getNextSort(String pageSql, int size) {
		int result = 1;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getNextSort(Strin,int) exception:"
							+ sdf.format(new Date()));
			System.err.println("pageSql=" + pageSql + "   size=" + size);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return result + size;
	}


	
	public int getCount(String pageSql) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//sum
		if(pageSql.contains("sum") && (pageSql.contains("2200164436") || pageSql.contains("3003984748")))
		{
			DebuUtil.printfStack();
		}
		//StringBuffer sb = new StringBuffer("");//
		try {
			
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			printSql(pageSql);
			rs = stmt.executeQuery(pageSql);
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err
					.println("-------------BaseDao.getRecordCount(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
			//System.err.println(sb.toString());
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return result;
	}
	
	public int getRecordCount(String pageSql) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer("");
		try {
			String tmpSQL = pageSql.toLowerCase();
			int i = tmpSQL.indexOf("group by");
			if (i == -1) {
				i = tmpSQL.indexOf(" order by ");
				if (i > 0) {
					pageSql = pageSql.substring(0, i);
				}
				i = tmpSQL.indexOf(" from ");
				sb.append("select count(*) ").append(pageSql.substring(i));
			} else {
				sb.append("select count(*) from (").append(tmpSQL).append(
						") tab");
			}
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			printSql(sb.toString());
			//DebuUtil.log(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err
					.println("-------------BaseDao.getRecordCount(String) exception:"
							+ sdf.format(new Date()));
			System.err.println(pageSql);
			System.err.println(sb.toString());
			result = -1;
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
			//return result;
		}
		
		if(pageSql.contains("sum") && (pageSql.contains("2200164436") || pageSql.contains("3003984748")))
		{
			DebuUtil.printfStack();
		}
		
		return result;
	}

	// 取得对象的普通map key是id value是对象
	@SuppressWarnings("unchecked")
	public HashMap<Object,Object> getMap() throws ClassNotFoundException {
		HashMap<Object,Object> result = new HashMap<Object,Object>();
		try {
			ArrayList list = getList();
			ArrayList volist = CacheSystem.paramMap.get(baseTableName);
			ObjectVO vo = (ObjectVO) volist.get(0);
			Method getMethod = (Method) vo.getObj4();
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object idValue = getMethod.invoke(obj, new Object[]{});
				result.put(idValue, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 取得只有2个属性的对象map key是第一属性 value是第二属性
	@SuppressWarnings("unchecked")
	public HashMap<Object,Object> getSelectMap() {
		HashMap<Object,Object> result = new HashMap<Object,Object>();
		try {
			ArrayList list = getList();
			ArrayList volist = CacheSystem.paramMap.get(baseTableName);
			ObjectVO voA = (ObjectVO) volist.get(0);
			Method getMethodA = (Method) voA.getObj4();
			ObjectVO voB = (ObjectVO) volist.get(1);
			Method getMethodB = (Method) voB.getObj4();
			// System.out.println(getMethodA+" "+getMethodB);
			for (int i = 0; i < list.size(); i++) {
				Object obj = (Object) list.get(i);
				Object idValueA = getMethodA.invoke(obj, new Object[]{});
				Object idValueB = getMethodB.invoke(obj, new Object[]{});
				result.put(idValueA, idValueB);
				// System.out.println(idValueA+" "+idValueB);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 取得sql语句只有2个属性的对象map key是第一属性 value是第二属性
	@SuppressWarnings("unchecked")
	public HashMap<String,String> getSelectMap(String pageSql) {
		HashMap<String,String> map = new HashMap<String,String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getSelectMap(String) exception:"
							+ sdf.format(new Date()));
			System.err.println("wrong sql:" + pageSql);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String,String> getSelectMap1(String pageSql) {
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			printSql(pageSql);
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(pageSql);
			while (rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			System.err
					.println("-------------BaseDao.getSelectMap(String) exception:"
							+ sdf.format(new Date()));
			System.err.println("wrong sql:" + pageSql);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		return map;
	}

	public String execute(ArrayList<String> list) {
		String msg = "";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			for (int j = 0; j < list.size(); j++) {
				msg = list.get(j);
				stmt.addBatch(msg);
			}
			int[] resultNum = stmt.executeBatch();
			if (resultNum != null && resultNum.length > 0) {
				for (int j = 0; j < list.size(); j++) {
					clearCacheBySql(list.get(j));
				}
				msg = "1";
			}
		} catch (Exception e) {
			System.err.println("-------------BaseDao.execute(List) exception:"
					+ sdf.format(new Date()));
			System.out.println(msg);
		} finally {
			ConnectionFactory.close(stmt,conn);
		}
		return msg;
	}

	//baseTableName=null 和baseModelName=null,注意看tomcat启动时候报错信息
	// 如果代码报错java.lang.IllegalArgumentException: argument type mismatch
	// 一般情况下是数据库字段设置类型为datetime，此处为了处理方便datetime对应类型为string
	// 当调用setDateTime(String)时,rs.getObject()得到的对象传入invoke会报这个错，解决方法是数据库表改成varchar
	// 报Column 'fee_stat_null_id' not found,检查sql语句是否没有选择这个字段
	@SuppressWarnings("unchecked")
	private void setMethodValue(Object obj, ResultSet rs) {
		Object value = null;
		Method setMethod = null;
		try {
			ArrayList<ObjectVO> list = CacheSystem.paramMap.get(baseTableName);
			for (int i = 0; i < list.size(); i++) {
				ObjectVO vo = list.get(i);
				Class fieldTypeClass = (Class) vo.getObj2();
				if (fieldTypeClass == String.class) {
					String temp = rs.getString((String) vo.getObj1());
					value = temp == null ? "" : temp;
				} else if
				(fieldTypeClass == Integer.class) {
					value = rs.getInt((String) vo.getObj1());
				} else if (fieldTypeClass == Long.class) {
					value = rs.getLong((String) vo.getObj1());
				} else if (fieldTypeClass == Double.class) {
					value = rs.getDouble((String) vo.getObj1());
				}
				// System.out.println((String)vo.getObj1()+" "+value);
				setMethod = (Method) vo.getObj3();
				//System.out.println(setMethod);
				setMethod.invoke(obj, new Object[] { value });
			}
		} catch (Exception e) {
			System.err.println("-------------BaseDao.setMethodValue exception");
			System.err.println("table:" + baseTableName + " method:"
					+ setMethod + " value:" + value);
			System.out.println(e);
		}
	}

	private void printSql(String sql) {
		if (AUTO_PRINT) {
			System.out.println(sql);
		}
	}

	@SuppressWarnings("unchecked")
	public static void clearCache(String key) {
		HashMap map=CacheData.getAllCache();
		Iterator<Entry> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			String name = (String)entry.getKey();
			if (name.indexOf(key) > -1) {
				// System.out.println("clear name:" + name);
				CacheData.setData(name, null);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void clearCacheBySql(String sql) {
		sql = sql.toLowerCase();
		String key = "";
		if (sql.indexOf("update") > -1) {
			String[] arr = sql.split(" ");
			key = arr[1];
		} else if (sql.indexOf("delete from") > -1) {
			String[] arr = sql.split(" ");
			key = arr[2];
		}
		HashMap map=CacheData.getAllCache();
		Iterator<Entry> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			String name = (String)entry.getKey();
			if (name.indexOf(key) > -1) {
				// System.out.println("clear name:" + name);
				CacheData.setData(name, null);
			}
		}
	}

	public   void test()
	{
		 
     	CacheSystem.initTable();
		
     	//MmpayRsq.isZhangzhong("300008776172");
     	
		//ActivateDao activateDao = new ActivateDao();
		//activateDao.test();
		//RegisterDao dao = new RegisterDao();
		//dao.test();
		//PayDao payDao = new PayDao();
		//payDao.test2();
		
		//MmPayDao mmPayDao = new MmPayDao();
		//mmPayDao.notisymm();
		//OperaterDao operaterDao = new OperaterDao();
		//operaterDao.test();
		//AppDao appDao = new AppDao();
		//appDao.test();
		//PayDao payDao = new PayDao();
		//payDao.test2();
		//CpDao cpDao = new CpDao();
		
		//cpDao.test();
		//MmPayDao mmPayDao = new MmPayDao();
		//mmPayDao.test();
		//PayDao payDao = new PayDao();
		//payDao.test2();
		//AliPayDao ali_payDao = new AliPayDao();
		//ali_payDao.test();
		//baseDao.add(r);
		/*RegisterDao dao = new RegisterDao();
		dao.test();
		
		
		PayDao payDao = new PayDao();
		payDao.test();
		ActivateDao activateDao = new ActivateDao();
		activateDao.test();
		
		AliPayDao ali_payDao = new AliPayDao();
		ali_payDao.test();
		
		AppDao appDao = new AppDao();
		appDao.test();
		
		CardPayDao cardPayDao = new CardPayDao();
		cardPayDao.test();
		
		ChannelDao channelDao = new ChannelDao();
		channelDao.test();
		
		CooperationDao cooperationDao = new CooperationDao();
		cooperationDao.test();
		
		CpDao cpDao = new CpDao();
		cpDao.test();
		
		LoginDao loginDao = new LoginDao();
		loginDao.test();
		
		MmPayDao mmPayDao = new MmPayDao();
		mmPayDao.test();
		
		//PayDao payDao = new PayDao();
		//payDao.test();
		
		UserinfoDao userinfoDao = new UserinfoDao();
		userinfoDao.test();*/
		
	}
	

		public static  void parseMMRsq(String xmlstr) 
		{
			// step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			
			// step 2:获得具体的dom解析器
			DocumentBuilder db=null;
			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// step3: 解析一个xml文档，获得Document对象（根结点）
			Document document=null;
			try {
				InputStream is = new ByteArrayInputStream(xmlstr.getBytes()); 
				document = db.parse(is);//new File("candidate.xml")
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			NodeList list = document.getElementsByTagName("SyncAppOrderReq");
			
			for(int i = 0; i < list.getLength(); i++)
			{
				Element element = (Element)list.item(i);
				
				String content = element.getElementsByTagName("AppID").item(0).getFirstChild().getNodeValue();
				
				System.out.println("AppID:" + content);
				
				content = element.getElementsByTagName("PayCode").item(0).getFirstChild().getNodeValue();
				
				System.out.println("PayCode:" + content);
				
				content = element.getElementsByTagName("TradeID").item(0).getFirstChild().getNodeValue();
				
				System.out.println("TradeID:" + content);
				
				content = element.getElementsByTagName("ChannelID").item(0).getFirstChild().getNodeValue();
				
				System.out.println("ChannelID:" + content);
				
				content = element.getElementsByTagName("TotalPrice").item(0).getFirstChild().getNodeValue();
				
				System.out.println("TotalPrice:" + content);
				
				System.out.println("--------------------------------------");
			}
		}

   public static void tmepdsf()
   {
	   
	   //JSONObject json = JSONObject.fromObject(reqStr);
	  // CooperationDao cooperationDao= new CooperationDao();
	  // cooperationDao.chngRitio("239152001", "2014-03-05",50);
		
	   /*PayDao paydao = new PayDao();
	    Pay pay = paydao.getPayRecord("2014052313077815","alipay");
	    if(pay != null)
	    {
	    	int result = pay.rsqCallbackUrl2(0);
		    DebuUtil.log("通知结果,result="+result);
		    if( result== 1)
	        {
	    	   DebuUtil.log("保存通知结果,pay.id="+pay.id);
	    	   paydao.update(pay);
	        }
	    }*/
	   
		//if(CPA != 1)
		//realregisterNum += registerDao.getRealRegisterNum(game_id, packet_id, date);
		/*try {
			json.getString("imei");
		} catch (JSONException e) {
			//e.printStackTrace();
			DebuUtil.log("时间dsfdf:"+"");
		}finally
		{
			DebuUtil.log("时间:"+"");
		}*/
		
		
		//rsqjson.put("device_id",json.getString("imei"));//imei
		
		
	  //for(int i=0; i <1024*10; i ++)
	  //{
	   //dao.login(obj);
     /*  String responseStr="SUCCESS";
	   if(responseStr.equalsIgnoreCase("success"))
	   {
		   DebuUtil.log("responseStr:"+responseStr);
	   }
	   JSONObject rsqjson = new JSONObject(); 
	  
	   rsqjson.put("afdf1", "96472130");
	   rsqjson.put("afdf2", 14);
	   rsqjson.put("ver", "1.00.1");
       
	   String reqStr=rsqjson.toString();
		JSONObject json = JSONObject.fromObject(reqStr);
		String Username = json.getString("afdf1");
		int time = json.getInt("afdf2");*/
	   /*DebuUtil.log("tmepdsf:");
	   JSONObject rsqjson = new JSONObject(); 
	   String str = "";
	   
	   rsqjson.put("device_id", "646556454");
	   rsqjson.put("packet_id", "100152001");
	   rsqjson.put("payway", "yeepay");
	   rsqjson.put("game_id", "152");
	   rsqjson.put("game_server_id", 2008);
	   rsqjson.put("cp_order_id", "");
	   rsqjson.put("user_id", "15914051609");
	   rsqjson.put("total_fee", 1);
	   rsqjson.put("ratio", 10);
	   rsqjson.put("coin_name", "元宝");
	   rsqjson.put("tradeID", "");
	   rsqjson.put("ver", "1.00");
	   rsqjson.put("card_no", "13686221192101626");
	   rsqjson.put("card_pass", "221957584795424239");
       
	 
       
	   try {
		   //
		   //str = HttpUtils.URLPostUTF8(ConstValue.RDRServerUrl+"/register",rsqjson.toString());
		   //ConstValue.ServerUrl="http://gm.91muzhi.com:8080";
		   ConstValue.ServerUrl="http://183.232.69.61:8080"; 
		   str = HttpUtils.URLPostUTF8(ConstValue.ServerUrl+"/sdk/yeepay_sign",rsqjson.toString());
		   DebuUtil.log("str:"+ConstValue.ServerUrl+"/sdk/yeepay_sign");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   DebuUtil.log("str:"+str);*/
	
	   
	 /*  RegisterDao registerDao = new RegisterDao();
	   Register register = null;
	   ArrayList<Object> list = new ArrayList<Object>();
	   //ArrayList<Object>
	    list=registerDao.getRegisterList("username","");
	       
	    		
	    for(int i=0;i<list.size();i++)
	     {
	    	   String time="";
	    	   String onlinetime="";
	    	   String level="";
	    	   register=(Register)list.get(i);
	    	   time=register.getDate()+register.getTime();
	    	   onlinetime = String.format("%d", register.getOnlinetime());
	    	   level = String.format("%d", register.getLevel());
	     }*/
	  // ActivateDao activateDao = new ActivateDao();
	  // activateDao.test_temp_54();
	   /*PayDao payDao = new PayDao();
		 payDao.test_temp();
		 
		 ActivateDao activateDao = new ActivateDao();
		 activateDao.test_temp();
		 
		 LoginDao loginDao = new LoginDao();
		 loginDao.test_temp();
		 
		 RegisterDao registerDao2 = new RegisterDao();
		 registerDao2.test_temp();*/
	  /* TenPay tenpay = new TenPay();
		tenpay.setPayNo("5487546877");
		tenpay.setGoodsName("元宝");
		tenpay.setGoodsDetails("游戏币");
		tenpay.setPrice(100);//
		
			/////////////////////////////////////
			ConstValue.ServerUrl = "http://www.91muzhi.com:8080";
			/////////////////////////////////////////
		String nosify_url =ConstValue.ServerUrl+ConstValue.path+"/tennotify_url.jsp";
		String return_url =ConstValue.ServerUrl+ConstValue.path+"/tenpayReturnUrl.jsp";
		
		//DebuUtil.log("url="+url);
		String oder_info = tenpay.GetOrderString(request,response,nosify_url,return_url,obj.getRatio());
		//DebuUtil.log("回应数据"+oder_info);
		DebuUtil.log("oder_info="+oder_info);*/
   }
   public static void tempS()
   {
	   //ArrayList<Gamedata> list = Gamedata.get_GamedataList("005","001","CPS","","2014-02-11","2014-02-12");
	  // channel_nogame_no=152business_no=101channel_no=211distype=beginDate=2014-04-01endDate=2014-04-30
	   ArrayList<ChannelDataReq> list = ChannelDataReq.get_GamedataList("","","","206153001","","A","2014-06-01","2014-06-30");
	   int recordcount=list.size();
	   int reg= 0;
	   
	 
		String time = DateUtil.getTime();
		DebuUtil.log("时间:"+time);
		
	   for(int i=0;i<list.size();i++){
		   ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
			String datestr = gamedata.date;
			String actStr = String.format("%d",gamedata.activityNum);
			String registerStr = String.format("%d",gamedata.registerNum);
			String loginStr = String.format("%d",gamedata.loginNum);
			String payRatio = String.format("%.2f%s",gamedata.allPayAccoutNumBefore*100.0/gamedata.activityNum,"%");
			float value = gamedata.activityRegNum;
			value = value/gamedata.activityNum;
			
			reg += gamedata.registerNum;
			String actRegistrStr = String.format("%.2f",value);
			
			value = gamedata.activityNum;
			value = gamedata.day1Num/value;
			String date1R = String.format("%.2f",value);
			
			value = gamedata.activityNum;
			value = gamedata.day7Num/value;
			String date7R = String.format("%.2f",value);
			
			value = gamedata.activityNum;
			value = gamedata.day30Num/value;
			String date30R = String.format("%.2f",value);
			
			
			String mmpaystr = String.format("%d",gamedata.mmPay/100);
			String yeepaystr = String.format("%d",gamedata.yeePay/100);
			String alipaystr = String.format("%d",gamedata.aliPay/100);
			
			String newmmpaystr = String.format("%d",gamedata.newmmPay/100);
			String newyeepaystr = String.format("%d",gamedata.newyeePay/100);
			String newalipaystr = String.format("%d",gamedata.newaliPay/100);
			
			DebuUtil.log("日期:"+datestr);
			DebuUtil.log("激活用户:"+actStr);
			DebuUtil.log("付费率:"+payRatio);
			DebuUtil.log("激活注册用户:"+gamedata.activityRegNum);
			DebuUtil.log("付费用户总数:"+gamedata.allPayAccoutNumBefore);
			DebuUtil.log("注册用户总数:"+gamedata.allRegisterNumBefore);
			DebuUtil.log("注册用户:"+registerStr);
			DebuUtil.log("登录用户:"+loginStr);
			DebuUtil.log("激活注册率:"+actRegistrStr);
			DebuUtil.log("次日留存率:"+date1R);
			DebuUtil.log("周留存率:"+date7R);
			DebuUtil.log("月留存率:"+date30R);
			DebuUtil.log("MM移动:"+mmpaystr);
			DebuUtil.log("易宝:"+yeepaystr);
			DebuUtil.log("支付宝:"+alipaystr);
			DebuUtil.log("新增MM移动:"+newmmpaystr);
			DebuUtil.log("新增易宝:"+newyeepaystr);
			DebuUtil.log("新增支付宝:"+newalipaystr);//
			DebuUtil.log("支付总数:"+gamedata.allPayBefore/100);
			
	   }
		 
	   
   }
   
   static private int current = 1;
   private static synchronized int getCurrentCount()
   {
   	List<String> list = FileUtil.read("C:\\mzcount.txt");
   	String content="";
   	JSONObject json = null;
   	
   	for(String str:list)
   	{
   		content += str;
   	}
   	if(content.length() > 0)
   	{
   	    json = JSONObject.fromObject(content);
   	    current = json.getInt("count");
   	}
   	else
   	{
   		json = new JSONObject();
   		
   	}
   	current++;
   	json.put("count", current);
   	list = new ArrayList<String>();
   	list.add(json.toString());
   	FileUtil.write("C:\\mzcount.txt",list,false);
       return current;
   }
   
   public static void editfile(String [] args)
   {
	   String filepath = "D:\\decode\\AndroidManifest.xml";
	   List<String> fstr = util.FileUtil.read(filepath);
		
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
		util.FileUtil.delete(filepath);
		util.FileUtil.write(filepath, ret, false);//
   }
   
   synchronized public static String getOutTradeNo(Integer aaaa) {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		int va = r.nextInt();
		if(va < 0)
		{
			va = 0-va;
		}
		key += va;
		key = key.substring(0, 15);
		
		//synchronized(aaaa)
		{
			DebuUtil.log("begin111"+aaaa);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DebuUtil.log("end111");
		}
    	/*synchronized(a) {  
            int i = 5;  
            DebuUtil.log("begin111"+a);
            while( i-- > 0) {  
                 System.out.println(Thread.currentThread().getName() + " : " + i);  
                 try {  
                      Thread.sleep(500);  
                 } catch (InterruptedException ie) {  
                 }  
            }  
            DebuUtil.log("end111");
       } */ 
		//key = "010217153010087";//lsl
		//Log.d(TAG, "outTradeNo: " + key);
		return "";
	}
   
    
    synchronized public static String getOutTradeNo2(Integer aaaa) {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		
		java.util.Random r = new java.util.Random();
		int va = r.nextInt();
		if(va < 0)
		{
			va = 0-va;
		}
		key += va;
		key = key.substring(0, 15);
		
		//synchronized(aaaa)
		{
			DebuUtil.log("begin2222");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DebuUtil.log("end2222");
		}
    	/*
    	synchronized(a) {  
            int i = 5;  
            DebuUtil.log("begin2222");
            while( i-- > 0) {  
                 System.out.println(Thread.currentThread().getName() + " : " + i);  
                 try {  
                      Thread.sleep(500);  
                 } catch (InterruptedException ie) {  
                 }  
            }  
            DebuUtil.log("end2222");
       }  */
		//key = "010217153010087";//lsl
		//Log.d(TAG, "outTradeNo: " + key);
		return "";
	}
   public static void getOutTra(Map<String, String> resp) {
		
	   resp.put("dfsadf", "24544");
	}
   public static void getOutTra(StringBuffer resp) {
		
	   resp.append("adfdasfds");
	}
   public static void getOutTra(App app) {
		
	   app.cp_no += "afdas";
	}
   
   public static void m4t1(Integer a) {  
	   SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		a = 15;
		java.util.Random r = new java.util.Random();
		int va = r.nextInt();
		if(va < 0)
		{
			va = 0-va;
		}
		key += va;
		key = key.substring(0, 15);
		
       synchronized(a) {  
            int i = 5;  
            while( i-- > 0) {  
                 System.out.println(Thread.currentThread().getName() + " : " + i);  
                 try {  
                      Thread.sleep(500);  
                 } catch (InterruptedException ie) {  
                 }  
            }  
       }  
  }  
  public static void m4t2(Integer a) {  
	  SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		a = 15;
		java.util.Random r = new java.util.Random();
		int va = r.nextInt();
		if(va < 0)
		{
			va = 0-va;
		}
		key += va;
		key = key.substring(0, 15);
       int i = 5;  
       synchronized(a) {  
	         while( i-- > 0) {  
	              System.out.println(Thread.currentThread().getName() + " : " + i);  
	              try {  
	                   Thread.sleep(500);  
	              } catch (InterruptedException ie) {  
	              }  
	         }  
       }
  } 
   
  static void handle_union_que()
  {
  	Date curdate= new Date();
  	//Date quedate = DateUtil.addDate("mm", -10, date);
  	
  	ArrayList<Object> objList;
  	
		String sql = "select * from pay where state=0 and pay_type='unionpay' and ncount<2 and date='"+ DateUtil.getDate()+"'";//and date>='2014-07-16'
		//objList = getList(sql);
		PayDao paydao = new PayDao();
		objList = paydao.getPayList(sql);
		//objList = paydao.getAllList();
		DebuUtil.log2("补发个数="+objList.size());
		
		for(int i=0;i<objList.size();i++){
			Pay pay=(Pay)objList.get(i);
			String datestr = pay.date+" "+pay.time;
			Date payDate = DateUtil.strToDate(datestr);
			long milSencods = curdate.getTime()-payDate.getTime();
			if((milSencods)/(1000*60) > 10)
			{
				QueryExample.Query(pay);
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					DebuUtil.log2(e.toString());
				}
			};
		}
  }
  
  public static String B()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Object localObject = new Date();
    Date localDate = null;
    String ret = "";
    try
    {
     
		try {
			localDate = localSimpleDateFormat.parse("2010-01-01 0:0:0");
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      localObject = localDate;
      ret = a((new Date().getTime() - ((Date)localObject).getTime()) / 1000L);
    }
    catch (ParseException localParseException)
    {
     // while (true)
        localParseException.printStackTrace();
    }
    finally
    {
    	return ret;
    }
  }
  
  public static String a(long paramLong)
  {
   // d.c("MMBillingSDk", "t10=  " + paramLong);
    String str1 = paramLong + "";
    //d.c("MMBillingSDk", "tt=  " + str1);
    long l1 = paramLong / 36L;
    long l2 = paramLong % 36L;
    String str2 = "" + b(l2);
    while (l1 > 0L)
    {
      long l3 = l1 / 36L;
      long l4 = l1 % 36L;
      str2 = str2 + b(l4);
      l1 = l3;
    }
    StringBuffer localStringBuffer = new StringBuffer(str2);
    localStringBuffer.reverse().toString();
   // d.c("MMBillingSDk", "result=  " + str2);
    //d.c("MMBillingSDk", "reresult=  " + localStringBuffer);
   // d.c("MMBillingSDk", "result.length()=  " + str2.length());
    return localStringBuffer.toString();
  }
  
  public static String b(long paramLong)
  {
    if (paramLong < 10L);
    for (String str = paramLong + ""; ; str = String.valueOf((char)(int)(55L + paramLong)))
      return str;
  }
  //
  //
  public static String getMSM(String chn,String imei,String imsi,String uData)
  {
	  String strUrl = "http://124.202.141.81:8080/MMSocketSer/MMServlet";   
      
      Map<String,String> map=new HashMap<String,String>();    
      map.put("chn", chn);    
      map.put("imei", imei);   
      map.put("imsi", imsi);  
      map.put("xexdata", uData);   
      String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebuUtil.log("msmrsq="+msmrsq);
		return msmrsq;
	}
  
 //
  //http://api.haoservice.com/api/getlbs?mcc=460&mnc=0&cell_id=28655&lac=17695&key=您申请的KEY
    public static void testsvr()
    {
    	 String turl = "http://sa.91muzhi.com:8080/sdk/getcon?amount=1&packet_id=2087&imei=499000316129545&imsi=460029999154959&orderNO=45554411";//
    	 //String turl = "http://pay.gzmtx.cn:8080/mm?appKey=0407C1F2D2F92D7D19807DEABF57AC6A&imei=499000316129545&imsi=460029999154959&code=30000870526301&data=2141";
    	//data
    	// for(int i = 0; i < 11; i ++)
    	 {
    		 new GetTestThread(turl, 100).start();
    	 }
    }
    
    public static int genRan()
    {
        java.util.Random r = new java.util.Random();		
		int ran = r.nextInt();
		if(ran < 0)
		{
			ran = 0- ran;
		}
		ran = ran%100;
		return ran;
    }
    
   static String curdatestr = "";
     
   public static Map<String, String> HandleReqsa(Long LMoney){
	   Map<String, String> maps = new LinkedHashMap<String, String>();   
       //maps.put("boy","1");   
	     maps.put("cat", "7");   
	     maps.put("dog", "1");   
	     maps.put("apple", "5");   
	 
	     Iterator i = maps.entrySet().iterator();   
	     while (i.hasNext()) {   
	         Map.Entry<String, String> entry1 = (Map.Entry<String, String>) i.next();   
	     }   
	     
	     List<Map.Entry<String, String>> info = new ArrayList<Map.Entry<String, String>>(maps.entrySet());   
	     
	     Collections.sort(info, new Comparator<Map.Entry<String, String>>() {   
	         public int compare(Map.Entry<String, String> obj1, Map.Entry<String, String> obj2) {   
	             return obj1.getValue().compareTo(obj2.getValue()); 
	         }   
	     });    
	     return maps;
   }
	public static void main(String[] args) {
		//if(msmrsq.compareTo("1.03.2") > 0)
		
		//jfreeChartUtil.createLineTest();//"company.jpeg"
		
	    
		// String str = getDBName(JdbcUrl);
        //DebuUtil.log("str="+str);
		/*List list=FileUtil.read("D:/logs/CERT.SF");
		for(int i = 0; i < list.size() ; i++)
		{
			 str = (String) list.get(i);
			msmrsq += str;
		}*/	
		//mcc=460&mnc=0&cell_id=28655&lac=17695
		
		/*JSONObject json = JSONObject.fromObject(msmrsq);
		int ErrCode = json.getInt("ErrCode");
		
		if(ErrCode == 0)
		{
			JSONObject location=(JSONObject)json.get("location");
			JSONObject address = (JSONObject)location.get("address");
			
			String region = address.getString("region");
			String city = address.getString("city");
			String county = address.getString("county");
			
			
			DebuUtil.log(region+city+county);
		}*/
		//MSMConten.test();//a3#1T84F#1DOHKN0#4J2DJ5D7D8#3HZZ6BSOG1#2MKVW7#AMU7N6BL9#0588F72A4B2DF4E7#11E0BF6ED2A1EE25#D7B83F67B12AA7F9#2368
		//testsvr();
		//GetAmountIndex.initData();
		/*String str="http://www.91muzhi.com:8080/sdk/unionnotify_url.jsp";
		DebuUtil.log(URLEncoder.encode(str));
		 str="http://www.91muzhi.com:8080/sdk/front_url.jsp";
		DebuUtil.log(URLEncoder.encode(str));
		 str="20晶石";
		DebuUtil.log(URLEncoder.encode(str));*/
		
		//Map<String, String> resp = new HashMap<String, String>();
		//String a= "str";
		//getOutTradeNo(a);
		
		/*String datestr = "2014-02-11";
		String actStr = "";
		
		Date now=new java.util.Date();
		String curdate = DateUtil.getDate();
		long datedif = 0;
		datedif = DateUtil.getDayDiff(datestr, curdate);
		if(datedif == 0)
		{
			actStr = "-";
		}
		else if(datedif == 1)
		{
			if(now.getHours()>= 11)
			{
				//actStr = String.format("%d",gamedata.activityNum);
			}
			else
			{
				actStr = "-";
			}
		}
		else
		{
		   //actStr = String.format("%d",gamedata.activityNum);
		}*/

		//CacheSystem sys = new CacheSystem();
		//getCurrentCount();
		//String str = "eyJwYXlfbm8iOiIwNTIzMDEwNDI4LTEwMDMiLCJ1c2VybmFtZSI6ItDsuqOIkCIsInVzZXJfaWQiOjE0ODg2MCwiZGV2aWNlX2lkIjoiODYzNzM4MDI5ODE2NTkzIiwic2VydmVyX2lkIjoiNDEwIiwicGFja2V0X2lkIjoiMjgwMTU4MDAyIiwiZ2FtZV9pZCI6IjE1OCIsImNwX29yZGVyX2lkIjoiMTQwNTIzMDEwNDQ3MTI4MTAwMDEwNzQxIiwicGF5X3R5cGUiOiJhbGlwYXkiLCJhbW91bnQiOjEwMDAsInBheVN0YXR1cyI6MH0%3D";
		//str = new String(Base64.decode(str));
		//System.out.println(" "+str);;
		//IdentifyServlet.sendSMS("15914051609","转换低");//
		 //for(int i = 0; i < 100; i ++)
		 //DebuUtil.log("time="+NumberUtil.getRandom(0, 10)+"");
		//long days = DateUtil.getDayDiff("2015-07-04",util.DateUtil.getDate());
		//DebuUtil.log("days="+days);
<<<<<<< .mine
		
/*		String a = "5C";
		String b = "5c";
		if(a.equalsIgnoreCase("5c")){
			System.out.println("1111");
		}else{
			System.out.println("2222");
		}*/
	   BaseDao baseDao = new BaseDao();		
	   baseDao.test();

	  // MyTask.main(args);	  
		String date = "2015-08-16";
		ProvinceDataDao proDao = new ProvinceDataDao();
	String sql = "select packet_id,sum(mobile_pay+unicom_pay+telecom_pay+other_pay ) from province_data where date = '"+date+"'  group by packet_id";
	CacheDao dao = new CacheDao();
	List<ArrayList>  list = dao.getObjectList(sql);
	for(int i = 1;i<list.size();i++){
		String provinceSql = "select sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay+box_pay) from channel_data where date = '"+date+"' and packet_id = '"+list.get(i).get(0).toString() +"' ";
		List<ArrayList> provinceAmount = dao.getObjectList(provinceSql);
		int abc = 0;
		if(provinceAmount.get(0).get(0) ==null){
			abc = 0;
		}else{
		    abc =Integer.parseInt(String.valueOf(provinceAmount.get(0).get(0))) - Integer.parseInt(list.get(i).get(1).toString());
		}
//		if(abc >0){
//			ProvinceData pro = proDao.getRecord(date,list.get(i).get(0).toString());
//			if(pro!=null){
//			pro.other_pay += abc;
//			proDao.edit(pro);
//			}
//		}
		System.out.print(list.get(i).get(0));
		System.out.println("========"+abc);
	}
	
	  // pay(obj,true);
=======
	    BaseDao baseDao = new BaseDao();		
	    baseDao.test();
	    
	    SearchCom.getList("2015", "08", "289", "756", "", -1, null,"");
	  // CpManageDao cpmanageDao = new CpManageDao();
	   //DebuUtil.log(cpmanageDao.getCpNo()+"");
>>>>>>> .r833
	   //FeeDataDao dao = new FeeDataDao();
	   // AppDao appDao=new AppDao();
	    //List<Gamepack> list=appDao.getList(1,0,"");
	    //RegisterDao redao = new RegisterDao();
	    //redao.pay("15914051608", 100);
	   // UserLevelList.get_UserLevelList("265","105","741","",null,"","2015-07-01","2015-07-01");//741265001
		
	  // channelDataDao.changeIMEI(sql);
	  // channelDataDao.changeTime(sql);
	   //AnalysisData feeDataDao = new AnalysisData();
	   //AnalysisData.getDataList("2015-06-16", "2015-06-16", "5233","");
	   /*for(int i=0;i<objList.size();i++){
	    * 
		   FeeData pay=(FeeData)objList.get(i);
		   DebuUtil.log(pay.game_id+" " + pay.getFeeName()+" " + pay.getAcount()+" "+pay.getOrderTimers()+" "+pay.getFeeTimers());
				
			
		}*/
		
	   //MmPayDao dao = new MmPayDao();
	   //dao.notisymm();
	   //ActivateDao activateDao = new ActivateDao();
	   //activateDao.reviseActivate();
	   
	    //ActivateDao activateDao = new ActivateDao();
	    //activateDao.reviseAccount();
	    //String ret = IP.getAddr("223.67.59.119");
	    //System.out.println(ret);;
	   //ArrayList<ChannelDataReq> list = new ArrayList<ChannelDataReq>();
	   //int recordcount = 0;
	   //list = ChannelDataReq.get_GamedataList("5233","","440","","","A","2015-06-01","2015-06-30");
	  
	    //PayDao dao = new PayDao();
	   //dao.revisenewpay();
		//List<Pay> list = dao.getPayInfoList("5028", channelNo, businessNo, "2015-04-01", "2015-04-15");
	    /*model.MmPay obj = new model.MmPay();
	    obj.app_id = "300008991839";
	    obj.app_code = "30000899183908";
	    obj.order_no = "11150518171000637405";
	    obj.trade_no = "21D1BBAE4D";
	    obj.amount = 1;
	    obj.channel = "3003995940";
	    
	    obj.ret = 1;
	    obj.ExData = "0136f351cbef48";//"a9_5457874_a";//"0411000332101";
	    obj.app_key = "0136f351cbef48";//"a9_5457874_a";//"0411000332101";
        
	    
	    model.Activate m_activate = MmpayRsq.handleExData(obj.app_key,obj);//864376025473637
	    MmpayRsq.handle(m_activate, "", obj);
	    */
	   // list = ChannelDataReq.get_GamedataList("","",channel_no,"","","A",beginDate,endDate);
	    
		//DateUtil.isInDays("1dsf");
		//System.out.println("超额");
	    
		/*String time =  "24:00:00";//DateUtil.getTime();
	
		
					     
		Date date = new Date();//星期六星期天不过滤
		
	    String beginTime = "00:00:00";
	    String endTime = "24:00:00";
	    String controlTime = "";
	 
		if( (time.compareTo(beginTime) >= 0) || (time.compareTo(endTime) <= 0))
		{
			DebuUtil.log("打");
		}*/
/*		  model.Pay obj = new model.Pay();
		   obj.app_pay_id = "123";
		   obj.amount = 100;
		   obj.state = 1;
		   obj.pay_type = "mmpay";
		   obj.packet_id = "510K";
		   obj.date = "2015-07-29";
		   obj.ext = "广东_";
		   PayDao.pay(obj,null,true);*/
		//DebuUtil.log("关闭");
		//IdentifyServlet.sendSMS("18025341241","渠道"+"测试"+"转化低");
		
	   /* Date lastdate =DateUtil.addDate("dd", -1, new Date());
		 String lastdatestr = DateUtil.getDate(lastdate);
		 DebuUtil.log2("lastdatestr="+lastdatestr);
		 if(!curdatestr.equals(lastdatestr))
		 {
			 DebuUtil.log2("单机每天算一次");
			 curdatestr = lastdatestr;
			 ChannelDataDao channelDataDao = new ChannelDataDao();
		     channelDataDao.settleXingXingPay(lastdatestr);
		 }*/
		 
	    /*DevicePayDao devicePayDao = new DevicePayDao();
	    if(devicePayDao.isExceed("355458054168002", 600, "2015-03-03") == false)
	    {
	    	System.out.println("没有超额");
	    	 devicePayDao.addPay("355458054168002", 600, "2015-03-03");
	    }
	    else
	    {
	    	System.out.println("超额");
	    }*/
	    //MMUser.doInitUser("355458054168002","460027099285388", "", "2368"); 
	    //DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
	    //deviceInfoDao.doMMUser();
		//{"sid":"4ee0523e6795f7db","packageName":"com.yykjmm.xccs.apk","sendTime":1423637430734,"versionCode":1,"mac":"jP:JS:Rk:Or:75:CR","pid":1,"versionName":"1.0","cid":"e9af2437df4d7de8fe","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","cpuRatioCur":"1728000","logJsonAry":[{"flowConsumpRev":0,"flowConsumpSnd":0,"sid":"4ee0523e6795f7db","logs":"onResume|com.kai.popstar.PopStar|1423637430306|0|\n"}],"protocolVersion":"3.1.0","menoryRatio":"1893832","cpuRatioMax":"2265600","manufacturer":"Coolpad","accessPoint":"wifi","deviceDetail":"Coolpad 8089","channel":"3003973308","deviceId":"355458054168002","appKey":"300008763441"}
        // "sid":"d79ea91807d760a5","packageName":"com.pansen.zumalocal","sendTime":1423119850146,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","cpuRatioCur":"2265600","logJsonAry":[{"flowConsumpRev":382,"flowConsumpSnd":2110,"sid":"d79ea91807d760a5","logs":"onPause|mobi.shoumeng.game.demo.MainActivity|1423119800666|60516|mobi.shoumeng.game.demo.MainActivity\n"}],"protocolVersion":"3.1.0","menoryRatio":"1893832","cpuRatioMax":"2265600","manufacturer":"Xiaomi","accessPoint":"cmnet","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
        //
	    //deviceInfoDao.doMMUser();
	    //deviceInfoDao.doMMUserAct();
	    //payNoticeThread.handle_bk();
	     //String ret=LBS.getLBS("460","0","177063008","42356");
	  //  FilterCellDao lBSDao = new FilterCellDao();
	   // msmrsq = lBSDao.getLBS(3613,9371);
	    //System.out.println(msmrsq);
	    //GetAmountIndex.HandleReq_test(100,"355458054168001","460027099285388","2368","175","4214454");// 363011047
	   
	 	 //System.out.print(GetAmountIndex.getRegionCode("", "e"));
	     //String str =appid.substring(7, 7+5)+"_"+channel.substring(6, 6+4);
	     //System.out.print(str);
	 	
	   // GetAmountIndex.getMSM4("http://121.14.38.23:25194/if_mtk/service","300007450508","30000745050811","865308021063756","460001014262556","","561","",false,200);
	    /*    ChannelDataDao channelDataDao = new ChannelDataDao();
	        channelDataDao.smsReq("405208001",state);
	        System.out.print("1111111111111");*/
	     
	 	 
		// String str = GetAmountIndex.getIMEI();
		// System.out.println(" "+str);;
	     //BlackOrderDao blackOrderDao = new BlackOrderDao();
	     //blackOrderDao.genBlackDeviceListByOrder();*/
		//for(int i=0; i < 1000; i++ )
		//System.out.println(Pay.getOutTradeNo());
	     //
	    //ChannelDataDao channelDataDao = new ChannelDataDao();
		//channelDataDao.settleDota();
	    //ChannelDataDao channelDataDao = new ChannelDataDao();
	    //channelDataDao.settleXingXingPay();
		//String respXmlformat="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp xmlns=\"http://www.monternet.com/dsmp/schemas/\"><TransactionID>CSSP16122856</TransactionID><MsgType>SyncAppOrderResp</MsgType><Version>1.0.0</Version><hRet>0</hRet></SyncAppOrderResp>";
		//System.out.println(respXmlformat);;
	    //gameExit.test();
		/*String pid = "6381754877-7126887100";
    	String channel = "3003971774";
    	String rsq = GetAmountIndex.getMSM2(pid,"30000848148101",channel, "863061026970440", "460027032618389", "100172001");    	
		//String rsq = GetAmountIndex.getMSM2("30000848148101","", "863061026970440", "460027032618389", "100172001");
		MsmList obj = new MsmList();
		GetAmountIndex.parseMMRsq(rsq, obj) ;	*/
	    // GetAmountIndex.getMSM3("http://pay.gzmtx.cn:8080/mm","2C9292B3E39886B7","30000838149101","546566643","546455633","64654564");
	    //amount=200&packet_id=405224001&imei=499000316129005&imsi=460029999154009
	    // GetAmountIndex.HandleReq_test(200,"863908029393097","460029187280591","390182001","175","4214454");
		//DebuUtil.log("str1="+B());
	    // GamePayDataGen.requryAll();
	     // GamePayDataGen.settleAll();
		/////////////////////////////
		/*Login obj = new Login();
		obj.packet_id="211152001";
		obj.date="2014-08-21";
		obj.device_id = "865009026264361";
		obj.game_id="152";
		//////////////////////////
	     LoginDao.loginData(obj, null, 1) ;*/
	     //handle_union_que();
	     /*UnionPay unionpay = new UnionPay();
			unionpay.setPayNo("08091100164137");
			unionpay.setGoodsName("元宝");
			unionpay.setGoodsDetails("游戏币");
			unionpay.setPrice(100);
			
				/////////////////////////////////////
				//ConstValue.ServerUrl = "http://www.91muzhi.com:8080";
				/////////////////////////////////////////
			//String nosify_url = ConstValue.ServerUrl+ConstValue.path+"/tennotify_url.jsp";
			//String return_url = ConstValue.ServerUrl+ConstValue.path+"/tenpayReturnUrl.jsp";
			
			//DebuUtil.log("url="+url);
			String oder_info = unionpay.getTNNO(10);*/
			
	/*	ArrayList<Object> objList;
		String sql = "select * from pay where state=1 and notisfy=0 ";//and date>='2014-07-16'
		//objList = getList(sql);
		PayDao paydao = new PayDao();
		objList = paydao.getPayList(sql);;//getList(sql);
		for(int i=0;i<objList.size();i++){
			Pay pay=(Pay)objList.get(i);
			if(pay.getNcount() < 10)
			{
				DebuUtil.log("自动补发充值通知");
				int ret = pay.rsqCallbackUrl(0);
				pay.ncount ++;
				//if(ret == 1)
				{
				   paydao.update(pay);
				}
			}
		}*/
		//payNoticeThread pthread = new payNoticeThread();
		//pthread.start();
		//tmepdsf();
		///tempS();
		//http://124.202.141.81:8080/MMSocketSer/MMServlet?chn=2501&imei=869630019000979&imsi=460026894200239&xexData=234234234234234
		 //getMSM("2501","869630019000979","460026894200239","234234234234234");
		//DebuUtil.log(ret);
		//PayDao.test2();
		//DebuUtil.log(util.Rsa.getMD5("66caa0b0b8922591d03ebd6250eb5c03"+"#"+"61428cdb68be7017f0abba0fbcb950f4"));
		//BaseDao.test();
		//tmepdsf();
		//tempS();
		/*DebuUtil.log("begin:"+DateUtil.getTime());
		for(int i=0; i <10000; i++)
		{
			tempS();
		}
		DebuUtil.log("end:"+DateUtil.getTime());*/
		//tmepdsf();
		//editfile(args);
		//tempS();
		//Map map = new HashMap();   
		//CardPay pay = new CardPay();
		//pay.test();
		//map.put( "device_id", "123456789" ); 
		//map.put( "game_id", "0005" ); 
		
		/*JSONObject json =  JSONObject.fromObject("{\"device_id\":\"" + "123456789" + "\"," +
                "\"game_id\":" + "0005" + "\"," +
                "\"packet_id\":\"" + "0005" + "\"}");*/
		//System.out.println(json.toString());
		//System.out.println(json.getString("device_id"));
		
		//String xmlstr = "<SyncAppOrderReq><AppID>12444</AppID><PayCode>dfjfdsjdfs</PayCode><TradeID>3343234</TradeID><TotalPrice>200</TotalPrice><ChannelID>000000</ChannelID></SyncAppOrderReq>";
		//parseMMRsq( xmlstr) ;
		//String sql = "insert into sys_logs (sys_user_id,ip,act_type,act) values ('aa','12','add','sdfsdfsdf')";
		//String ;
		//String sql = "insert into register (username,pass,device_id,packet_id,game_id) values ('15914051609','6670575','123456789','0001','001')";
		/*String sql = "insert into tes (id,name) values (0,'元宝')";
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
ResultSet rs =stmt.executeQuery("select * from tes");
while(rs.next()){

String message=rs.getString("name");
System.out.println(" "+message);}
rs.close();
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.toString());
			System.err.println(sql);
		}*/
	}
/*	public void pay(Pay obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		String code = GetAmountIndex.getRegionCode(obj.ext, "");
		String province = GetAmountIndex.getRegionCode("", code);
		
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
				
				DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
				DeviceInfo deviceInfo = deviceInfoDao.getRecord(obj.device_id);
				String payWay = "";
				if(deviceInfo != null)
				{
					 payWay = deviceInfo.getSIMType(deviceInfo.getImsi());
				}
				provinceData.PayFeeNumInc(1, payWay);
				tempData.mobile_fee_num = provinceData.mobile_fee_num;
				tempData.unicom_fee_num = provinceData.unicom_fee_num;
				tempData.telecom_fee_num = provinceData.telecom_fee_num;
			
			
				edit(tempData);
			}//if(obj.state == 1)
			
			
		}*///if(provinceData != null)
		
	
}