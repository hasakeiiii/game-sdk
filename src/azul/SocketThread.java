package azul;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import util.DebuUtil;
import util.StringUtil;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;

/**
 * ���𽫿ͻ������� �������Ӧ��socket������m_SocketList��
 * 
 * @author Administrator
 * 
 */
public class SocketThread extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<MYSocket> m_SocketList = new ArrayList<MYSocket>();

	@Override
	public void run() {
		super.run();
		try {
			serverSocket = new ServerSocket(10000);
			DebuUtil.log("listen 10000");

			do {
				//System.out.println("�ܵ����߳������ˣ����û����ʾ��һ�����Ѿ�������");

				Socket sc = serverSocket.accept();

				System.out.println("has connect");
				MYSocket mySocket = new MYSocket();
				mySocket.setSc(sc);
                String payID = mySocket.Read();
                mySocket.SetPayID(payID);
                
                if(!StringUtil.is_nullString(payID))
                {
                	mySocket.setValid(true);
                }
                
                MYSocket oldSock = delSocket(payID);
				synchronized(m_SocketList)
				{
					m_SocketList.add(mySocket);
				}
				if(oldSock != null)
				{
					DebuUtil.log("关闭旧socket");
					oldSock.closeSocket();
				}
			} while (true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<MYSocket> getAvailibleSocketList() {

		return m_SocketList;
	}

	public MYSocket delSocket(String socketID) {

		MYSocket ret = null;
	
		
		//DeleteInvalidSocket();
		DebuUtil.log("socketID="+socketID);
		synchronized(m_SocketList)
		{
			Iterator iterator  = m_SocketList.iterator();  
			while(iterator.hasNext())
			{  
				MYSocket socket = (MYSocket)iterator.next();  
				if (socket.GetPayID().equals(socketID))
				{					
					ret = socket;
					DebuUtil.log("删除旧socket");
					iterator.remove();
					break;
					
				}
				
			        		            
			 }  
		}
		return ret;
	}
	
	public MYSocket getAvailibleSocket(String socketID) {

		MYSocket ret = null;
		/*CheckSocket();
		if (!m_SocketList.isEmpty()) {
			for (MYSocket mSocket : m_SocketList) {

				if (!mSocket.getSc().isClosed()) {
					// ����æ��ʱ��״̬Ϊfalse���������ڴ���״̬���Ǿͷ���ȥ.
					//System.out.println("m_SocketList�ĸ����ǣ�"
							//+ m_SocketList.size());
					if (!mSocket.isBusy()) {
						ret = mSocket;
						break;
					}
				}
			}
		}*/
		
		DeleteInvalidSocket();
		DebuUtil.log("socketID="+socketID);
		synchronized(m_SocketList)
		{
			Iterator iterator  = m_SocketList.iterator();  
			while(iterator.hasNext())
			{  
				MYSocket socket = (MYSocket)iterator.next();  
				if (socket.isValid()&&socket.GetPayID().contains(socketID)) {
					DebuUtil.log("socket有效");
					if (!socket.isBusy()) {
						DebuUtil.log("socket空闲");
						ret = socket;
						break;
					}
				}
				else
				{
				   //DebuUtil.log("删除无效socket");
				   //iterator.remove();
				}
			        		            
			 }  
		}
		return ret;
	}

	private void DeleteInvalidSocket()
	{
		synchronized(m_SocketList)
		{
			Iterator iterator  = m_SocketList.iterator();  
			while(iterator.hasNext())
			{  
				MYSocket socket = (MYSocket)iterator.next();  
				if(!socket.isValid())
				{				
				   DebuUtil.log("删除无效socket");
				   socket.closeSocket();
				   iterator.remove();
				}
			        		            
			 }  
		}
	}
	
	/**
	 * ���m_SocketList�е������Ƿ���ã����������Ƴ�
	 * 
	 */
	private void CheckSocket() {
		ArrayList<Integer> avai_item = new ArrayList<Integer>();

		for (MYSocket mSocket : m_SocketList) {
			// ////////////////////////////////
			boolean kk = mSocket.getSc().isClosed();
			System.out.println("kk:  " + kk);
			// ////////////////////////////////
			if (mSocket.getSc().isClosed()) {
				avai_item.add(m_SocketList.indexOf(mSocket));
			}
		}
		boolean status = avai_item.isEmpty();

		if (!status) {
			for (int j = 0; j < avai_item.size(); j++) {
				int i = avai_item.get(j);
				m_SocketList.remove(i);
			}
			avai_item = null;
		}

	}
}

/*
 * try { ServerSocket serverSocket = new ServerSocket(10000); Socket sc =
 * serverSocket.accept(); System.out.println("���ӳɹ���"); DataOutputStream dos =
 * null; DataInputStream dis = null; dos = new
 * DataOutputStream(sc.getOutputStream()); dos.writeUTF("���Ƿ�������");
 * 
 * dis = new DataInputStream( sc.getInputStream());
 * 
 * String str_msg = dis.readUTF(); System.out.println("��ȡ�ַ�����"+str_msg);
 * 
 * 
 * dos.close(); dis.close(); serverSocket.close();
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 */

