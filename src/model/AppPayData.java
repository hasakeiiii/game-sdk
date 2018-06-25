package model;

import sdkReq.GetAmountIndex;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.CooperationDao;
import dao.DeviceInfoDao;

public class AppPayData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3170548659695530967L;
	public Integer id;
	public String appPayId;
	public Integer amount;
	public String date;


	Object xlockdata = new Object();
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAppPayId() {
		return appPayId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAppPayId(String appPayId) {
		this.appPayId = appPayId;
	}

	public void clearData()
	{
		appPayId = null;
		amount = null;
	}
	public void amountInc(int count)
	{
		synchronized(xlockdata)
		{
			if(this.amount == null)
			{
				this.amount = 0;
			}
			this.amount += count;
		}
	}	
	
}
