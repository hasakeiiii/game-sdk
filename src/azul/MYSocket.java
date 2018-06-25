package azul;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import util.DebuUtil;

public class MYSocket {

	private Socket sc;
	private boolean m_bBusy;// busy==true��ʾ���ڴ��� ״̬��æ
	private boolean m_bvValie;
	private DataOutputStream dos;
	private DataInputStream dis;
	private String mPayID;
	
	public MYSocket()
	{
		m_bBusy=false;
		m_bvValie=true;
		dos = null;
		dis = null;
	}
	
	public String GetPayID() {
		return mPayID;
	}

	public void SetPayID(String payID) {
		this.mPayID = payID;
	}
	
	public Socket getSc() {
		return sc;
	}
	
	public void setSc(Socket sc) {
		
		this.sc = sc;
	}

	public boolean isValid() {
		return m_bvValie;
	}

	public void setValid(boolean bvValie) {
		this.m_bvValie = bvValie;
	}
	
	public boolean isBusy() {
		return m_bBusy;
	}

	public void setBusy(boolean m_bBusy) {
		this.m_bBusy = m_bBusy;
	}

	public void closeSocket()
	{
		DebuUtil.log("关闭socket");
		try {
			if(dos != null)
			dos.close();
			if(dis != null)
			dis.close();
			if(sc != null)
			sc.close();
			dos = null;
			dis = null;
			sc = null;
			m_bBusy=false;
			m_bvValie=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String Read() 
	{
		String str = "";
		DebuUtil.log("reading");
		
		if(dis == null)
		{
			try {
				dis = new DataInputStream(sc.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			str = dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DebuUtil.log("read="+str);
		
		return str;
	}
	public String manageMSG(String str)
	{
		//DataOutputStream dos = null;
		//DataInputStream dis = null;
		String str_rep = "";
		setBusy(true);
		try {
			if(dos == null)
			{
				dos = new DataOutputStream(sc.getOutputStream());
			}
			DebuUtil.log("now sending "+str+" to client");
			dos.writeUTF(str);
			dos.flush();
			
			if(dis == null)
			{
				dis = new DataInputStream(sc.getInputStream());
			}
			str_rep = dis.readUTF();
			//System.out.println("��ȡ�ַ�����" + str_rep);

			//dos.close();
			//dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setValid(false);
			closeSocket();
			DebuUtil.log("设置socket无效");
		}finally
		{
			setBusy(false);
			return str_rep;
		}
	}

}
