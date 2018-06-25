package util;

import model.Register;
import dao.MzchargeDao;
import dao.PayDao;
import dao.RegisterDao;

/**
 * 1、根据订单号的不同  修改各充值订单的完成状态，
 * 2、对游戏币充值做进一步的通知
 * 3、拇指币充值无需做通知处理
 * 
 *
 */
public class HandleChargePay {
	
	/**
	 * 修改充值订单状态为成功，对于非拇指币充值同事通知订单对应的游戏cp
	 * @param pay_no  拇指订单号
	 * @param thir_pay_id   第三方订单号
	 * 
	 */
	public static void handleChargePay(String pay_no,String thir_pay_id ){
		
		if (pay_no.contains("mz")) {
			MzchargeDao mzchargeDao = new MzchargeDao();
			mzchargeDao.chargeNotify(pay_no, thir_pay_id, 1);
			
		}else{
			PayDao paydao = new PayDao();
			paydao.payNotisfy(pay_no, thir_pay_id, 1);
		}
		
		
	}

}
