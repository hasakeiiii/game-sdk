package dao;

import java.util.ArrayList;

import model.Olrecords;
import model.Onlybalance;
import azul.BaseDao;

public class OlrecordsDao extends BaseDao {
	public OlrecordsDao(){
		init();
	}
	
	public Olrecords getRecoderByPayNo(String payNo){
		Olrecords ol = null;
		ArrayList<Object> objList;
		String sql = String.format("select * from %s where pay_no='%s'",baseTableName,payNo);
		objList = getList(sql);
		if (objList.size()>0) {
			ol = (Olrecords) objList.get(0);
		}
		return ol;
	}

}
