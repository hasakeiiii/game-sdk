package dao;

import java.util.ArrayList;

import model.Onlybalance;
import model.Pay;
import azul.BaseDao;

public class OnlybalanceDao extends BaseDao{
	
	public OnlybalanceDao(){
		init();
	}
	
	/**
	 * 通过游戏id与用户名查找对应的专属币余额
	 * @param username
	 * @param gameid
	 * @return
	 */
	public Onlybalance getRecord(String username,String gameid){
		String sql ="";
		ArrayList<Object> objList;
		Onlybalance olBalance = null;
		
		sql = String.format("select * from onlybalance where username='%s' and game_id='%s'",username,gameid);
		objList = getList(sql);
		if (objList.size()>0) {
			olBalance = (Onlybalance) objList.get(0);
		}
		return olBalance;
	}
	/**
	 * 添加一个新的记录
	 * @param username
	 * @param gameid
	 * @return
	 */
	public Onlybalance addNew(String username,String gameid){
		Onlybalance olBalance = null;
		olBalance = new Onlybalance();
		olBalance.setUsername(username);
		olBalance.setGameId(gameid);
		olBalance.setAccountLeft(0);
		String ret ="-1";
		ret = add(olBalance);
		if (!ret.equals("-1")) {
			return olBalance;
		}
		return null;
	}
	
	/**
	 * 根据专属币消费信息，扣除专属币，返回-1为失败
	 * @param payObj
	 * @return
	 */
	public static String minusOnlyBalance(Pay payObj) {
		OnlybalanceDao oldao = new OnlybalanceDao();
		Onlybalance olBalance = null;
		String olresult = "-1";
		olBalance = oldao.getRecord(payObj.username, payObj.game_id);
		if (olBalance!=null) {
			int temp = olBalance.getAccountLeft() - payObj.amount/100;
			olBalance.setAccountLeft(temp);
			olresult = oldao.edit(olBalance);
		}
		
		return olresult;
	}
	/**
	 * 根据拇指币消费充值信息，修改专属币余额，用于拇指币购买专属币时
	 * @param pay
	 */
	public static void addOnlyBalance(Pay pay) {
		OnlybalanceDao oldao = new OnlybalanceDao();
		Onlybalance olaccount = oldao.getRecord(pay.username, pay.game_id);
		int temp = olaccount.getAccountLeft()+pay.amount/100;
		olaccount.setAccountLeft(temp);
		oldao.edit(olaccount);
	}  
}
