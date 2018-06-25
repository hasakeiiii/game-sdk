package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.DebugUtil;

import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;

import model.AnalysisData;
import azul.BaseDao;

public class AnalysisDataDao extends BaseDao {
	public List<ArrayList> getData(Date fromDate,Date toDate,String channelNo,String gameNo){
		
		String fromDateStr = DateUtil.getDate(fromDate);
		String toDateStr = DateUtil.getDate(toDate);
		
		
		String sql = "";
			
		
		if(StringUtil.is_nullString(gameNo.trim())){
			//如果游戏ID为null,渠道不为null        则找出该渠道下所有游戏产品
			
			sql= "SELECT app.name AS gameName,sum(mm_pay/100) AS mmPay,app.`no` FROM app,channel,channel_data " +
					"WHERE channel.no = channel_data.channel AND channel_data.game_id = app.no " +
					"AND channel_data.mm_pay > 0 AND app.game_type = 'off_line' " +
					"AND channel_data.date between '%s' and '%s' " +
					"AND channel.no = '%s' GROUP BY app.`no` ORDER BY sum(mm_pay/100) DESC";
			sql = String.format(sql, fromDateStr,toDateStr,channelNo);
		}else if(StringUtil.is_nullString(channelNo.trim())){
			//如果游戏渠道为空,游戏ID不为NULL      则找出该游戏产品对应的所有渠道
			sql= "SELECT channel.name AS channelName,sum(mm_pay/100) AS mmPay,app.`no` FROM app,channel,channel_data " +
					"WHERE channel.no = channel_data.channel AND channel_data.game_id = app.no " +
					"AND channel_data.mm_pay > 0 AND app.game_type = 'off_line' " +
					"AND channel_data.date between '%s' and '%s' " +
					"AND app.no = '%s' GROUP BY channel.no ORDER BY sum(mm_pay/100) DESC";
			
			sql = String.format(sql, fromDateStr,toDateStr,gameNo);
		}
		
		
		DebuUtil.log(sql);
		
		
		List<ArrayList> list = this.getObjectList(sql);
		
		
		return list;
	}
}
