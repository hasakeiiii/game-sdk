package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class MySocket {
	
	public static String  sendPacket(byte bufs[],String host,int port) 
	{
		Socket socket;
	    OutputStream os = null;
	    String ret = null;
	    //Util_G.debug_i("mvlog", "sendResult,sock");
	    try {
	         //socket.connect();
	    	//byte [] bufs = new byte[20];
	    	//socket = new Socket("127.0.0.1",4700);
	    	socket = new Socket(host,port);
	        os = socket.getOutputStream();
	        
	        os.write(bufs);
	        os.flush();	        
	        
	        InputStream InStream = socket.getInputStream();	  	           
	        byte [] readret = InputStream_read(InStream, -1);
	        String str = new String(readret);
	        ret = str;
	        //Util_G.debug_i("mvlog",str);
	        InStream.close();
	        os.close();
	        socket.close();
	    } catch (IOException e) {
	        //Log.e("mvlog", "Failed to write to socket", e);
	    }finally
	    {
	    	return ret;
	    }
	}
	
    public static byte[] InputStream_read(InputStream in, long len) {
		
		byte[] res = null;
		
		try {
			
			byte[] buff = new byte[100];
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			while (true) {
				int size = 0;
				if (len >= 100 || len == -1)
					size = in.read(buff);
				else
					size = in.read(buff, 0, (int) len);
				if (size == -1) {
					in.close();
					break;
				}
				bo.write(buff, 0, size);
				if (len != -1) {
					len -= size;
					if (len == 0)
						break;
				}
			}
			res = bo.toByteArray();
			bo.close();
			
		} catch (Exception e) {
			res  = null;
		}
		
		return res;
	}
}
