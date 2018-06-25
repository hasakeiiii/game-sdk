package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Activate;
import model.CardPay;
import model.Cooperation;
import model.Mzcharge;
import model.Register;
import sdkReq.YeepayOrder;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class MzchargeDao extends BaseDao {

	public MzchargeDao() {
		init();
	}

	public int addCharge(Mzcharge obj) {
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'",
				 baseTableName,obj.getPayNo());
		
		DebuUtil.log("添加sql="+sql);
		
		int count = getRecordCount(sql);
		
		if (count < 1) {
			setObj(obj);

			add(obj);

			ret = ConstValue.OK;
			DebuUtil.log("添加成功");

		}
		return ret;
	}
	
	public int addChargeByPc(Mzcharge obj) {
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'",
				 baseTableName,obj.getPayNo());
		
		DebuUtil.log("添加sql="+sql);
		
		int count = getRecordCount(sql);
		
		if (count < 1) {

			add(obj);

			ret = ConstValue.OK;
			DebuUtil.log("添加成功");

		}
		return ret;
	}

	/**
	 * 设置充值对象的归属包体号以及对应商务、渠道信息
	 * @param obj
	 */
	private void setObj(Mzcharge obj) {
		RegisterDao regDao = new RegisterDao();
		Register reg = null;

		if (!StringUtil.is_nullString(obj.getUsername())) {
			reg = regDao.getRegisterRecord(obj.getUsername());
		}

		if (reg != null) {
			String device_id = reg.getDeviceId();
			ActivateDao activateDao = new ActivateDao();
			Activate activate = activateDao.getActivateRecord(device_id,
					obj.getGameId());
			String packetId = reg.packet_id;
			String packetId2 = obj.getPacketId();
			if (activate == null) {
				activate = activateDao.getActivateRecord(obj.getDeviceId(),
						obj.getGameId());
			}

			if (activate != null) {
				packetId = activate.getPacketId();
			}
			// 以下处理packetid格式不对的情况
			CooperationDao coDao = new CooperationDao();
			Cooperation cooperation = coDao.getRecord(obj.getPacketId());
			if (cooperation == null) {
				packetId = "100" + obj.getGameId() + "001";
				cooperation = coDao.getRecord(packetId);
			} else {
				String game_id = cooperation.getAppNo();
				if (!game_id.equals(obj.getGameId())) {
					packetId = "100" + obj.getGameId() + "001";
					cooperation = coDao.getRecord(packetId);
				}
			}

			if (cooperation != null) {
				String business_no = cooperation.getBusinessNo();
				String channel_no = cooperation.getChannelNo();
				String game_id = cooperation.getAppNo();
				if (game_id.equals(obj.getGameId())) {
					obj.setBusinessNo(business_no);
					obj.setChannelNo(channel_no);
				} else {
					packetId = "100" + obj.getGameId() + "001";
				}
			}
			
			DebuUtil.log("修改导入渠道:"+packetId2+"->"+packetId);
			   obj.setPacketId2(packetId2);
			   obj.setPacketId(packetId);

		}

	}

	public Mzcharge getPayRecord(String payNo) {
		String sql;
		Mzcharge charge = null;
		ArrayList<Object> objList = new ArrayList<Object>();
		sql = String.format("select * from %s where pay_no='%s'",
				 baseTableName,payNo);
		
		DebuUtil.log("添加sql="+sql);
		
		objList = getList(sql);
		if(objList.size() > 0)
		{
			charge = (Mzcharge)objList.get(0);
		}
		
		return charge;
	}

	public Mzcharge update(Mzcharge obj) {
		MzchargeDao paydao = new MzchargeDao();
		Mzcharge charge = paydao.getPayRecord(obj.getPayNo());
	    if(charge != null)
	    {
	    	DebuUtil.log("找到修改记录 ");
	    	//pay.packet_id = obj.channel;
	    	charge.setThirPayId(obj.getThirPayId());
	    	charge.setStatus(obj.getStatus());
	    	if(charge.getSend() != 1)//lsl
	    	{
	    		charge.setSend(obj.getSend());
	    	}
	    	charge.setNcount(obj.getNcount());
	    	edit(charge);
	    }
	    return charge;
		
	}
   
	
	
	/**
	 * 修改拇指币充值订单的 充值结果 状态  以及 个人账户的月修改
	 * @param pay_no  拇指订单号
	 * @param thir_pay_id
	 * @param i =1 表示订单状态为成功
	 */
	public void chargeNotify(String pay_no, String thir_pay_id, int i) {
		// TODO Auto-generated method stub
		Mzcharge charge = getPayRecord(pay_no);
		if (charge==null) {
			return ;
		}
		if (charge.getStatus()==i) {
			return;
		}
		
		CardPayDao  cardPayDao = new CardPayDao();
	   	CardPay cardpay = cardPayDao.getPayRecordByPayNO(pay_no);
	   	if(charge.getAmount() > cardpay.amount*100)
	   	{
	   	    charge.setAmount(cardpay.amount*100);
	   	}
	   	
		RegisterDao redao = new RegisterDao();
		//Register register = new Register();
		
		//register = redao.getRegisterRecord(charge.getUsername());
		
		int f = 0;
		int temp = 0;
		String chargeWay = "";
		chargeWay = charge.getChargeWay();
		if (chargeWay.equals("")) {
			return;
		}
		 
		if (chargeWay.equals("zycard")||chargeWay.equals("jcard")||chargeWay.equals("tscard")) {
			DebuUtil.log2("**************************************");
			DebuUtil.log2("使用的是骏网充值"+"  ");
			DebuUtil.log2("**************************************");
			temp = (int) (charge.getAmount()/100*0.8);
			//f = temp+register.getAccountBalance();
		}else{
			DebuUtil.log2("**************************************");
			DebuUtil.log2("未能判断使用的是骏网充值"+"  ");
			DebuUtil.log2("**************************************");
			temp = charge.getAmount()/100;
			//f = temp+register.getAccountBalance();
		}
		
		//register.setAccountBalance(f);
		//redao.edit(register);
		
		redao.charge(charge.getUsername(),temp);
		
		charge.setMzAmount(temp*100);
		charge.setStatus(i);
		charge.setSend(i);
		charge.setThirPayId(thir_pay_id);
		edit(charge);
		return ;
		
	}

	public Map<String, Object> findRecordsByUsername(String username, int page, int rows) {
		String sql = "select * from mzcharge mc where mc.username='%s' and mc.status = 1 order by concat(mc.date,mc.time) desc";
		sql = String.format(sql, username);
		ArrayList list = this.getList(page, rows, sql);
		
		int count = this.getRecordCount(sql);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", count);
		
		return map;
	}


}
