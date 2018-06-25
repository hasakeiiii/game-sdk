package azul;

import java.util.ArrayList;
import dao.LinkManageDao;

public class TestUrlThread extends Thread{

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		System.out.println("线程TestUrlThread初始化");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("线程TestUrlThread启动");
		
		/*
		 *	1.首先获取数据库link_manage中cdn_url和web_url所有字段
		 *		1)区分cdn下载链接和二级页面链接
		 *		2)区分出各个游戏 
		 *		
		 *
		 *
		 */
		try {
			while(true){
				this.sleep(1000*60*30);
				LinkManageDao lmdao = new LinkManageDao();
				String[] gameIdArray= lmdao.getGameId();	//存入所有游戏id的数组
				int gameIdSum = lmdao.getGameSum();
				ArrayList<String> err_cdn = new ArrayList<String>();
				ArrayList<String> err_web = new ArrayList<String>();
				MailSendUtils msu = new MailSendUtils();
				for(int i = 0;i < gameIdSum; i++){
					//遍历所有游戏cdn
					String[] cdnUrlArray = lmdao.getCndUrl(gameIdArray[i]);
					int cdnUrlSum = cdnUrlArray.length;
					for(int j = 0; j <cdnUrlSum ; j++){
						//将各个链接发送到testLink检测
						testLink tl = new testLink();
						String testCdn_res = tl.test(cdnUrlArray[j],lmdao.getGameName(gameIdArray[i]));//测试结果,链接失效返回"2"(string);
						//定义一个存储失效链接的数组
						if(testCdn_res.equals("2")){
							err_cdn.add(cdnUrlArray[j]);
						}else{
							
						}
					}
				}
				if(err_cdn.size()>0){
					msu = new MailSendUtils();
			    	try {
						//msu.sendMail(err_cdn,"cdn链接失效","cdnUrl");
			    		for(int i = 0; i<this.getAddress().size(); i++){
			    			msu.sendMail(err_cdn,"cdn链接失效","cdnUrl",this.getAddress().get(i));
			    			
			    		}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				
				for(int i = 0;i < gameIdSum; i++){
					//遍历所有游戏二级页面
					String[] webUrlArray = lmdao.getWebUrl(gameIdArray[i]);
					int webUrlSum = webUrlArray.length;
					for(int j = 0; j <webUrlSum ; j++){
						//将各个链接发送到testLink检测
						testLink tl = new testLink();
						String testWeb_res = tl.test(webUrlArray[j],lmdao.getGameName(gameIdArray[i]));
						//定义一个存储失效链接的数组
						if(testWeb_res.equals("2")){
							err_web.add(webUrlArray[j]);
						}else{
							
						}
					}
				}
				if(err_web.size()>0){
			    	try {
						for(int i = 0; i<this.getAddress().size(); i++){
			    			msu.sendMail(err_web,"二级页面链接","webUrl",this.getAddress().get(i));
			    		}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	

	
	//添加收件人
	public ArrayList<String> getAddress(){
		ArrayList<String> Address = new ArrayList<String>();
		Address.add("2851520035@qq.com");//苏慕立
		Address.add("2851520030@qq.com");//
		Address.add("2851520000@qq.com");//谢海源
		Address.add("2851520024@qq.com");//陈浩
		Address.add("2851519967@qq.com");//林晶
		return Address;
	}
	
}
