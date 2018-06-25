package mmuser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class d
{
  private static d a = new d();
  private SecretKeySpec b;
  
  public static d a()
  {
    return a;
  }
  
  private static byte[] b(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
    	//{"sid":"","packageName":"com.pansen.zumalocal","sendTime":1422511917327,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","logJsonAry":"_pay_init|0000000000%40%40460029140953268%40%40Xiaomi%40%40MI+3C%40%40android%2B4.4.4%40%40d4%3A97%3A0b%3A69%3Ac7%3A07%40%40wifi%40%401080*1920%40%40300008702230%2540%2540com.pansen.zumalocal%2540%25401.0%2540%25400000000000%2540%254099999999%2540%2540460029140953268%2540%2540865903029745048%2540%2540MI%2B3C%2540%25404.4.4%2540%25401080*1920%2540%2540SMS%2540%2540%2540%2540%2540%25401.2.3%2540%25401422511917%2540%25401422511917%2540%25401|0|1422511917195\n","protocolVersion":"3.1.0","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
    	//{"sid":"","packageName":"com.pansen.zumalocal","sendTime":1422511917327,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","logJsonAry":"_pay_init|0000000000%40%40460029140953268%40%40Xiaomi%40%40MI+3C%40%40android%2B4.4.4%40%40d4%3A97%3A0b%3A69%3Ac7%3A07%40%40wifi%40%401080*1920%40%40300008702230%2540%2540com.pansen.zumalocal%2540%25401.0%2540%25400000000000%2540%254099999999%2540%2540460029140953268%2540%2540865903029745048%2540%2540MI%2B3C%2540%25404.4.4%2540%25401080*1920%2540%2540SMS%2540%2540%2540%2540%2540%25401.2.3%2540%25401422441170%2540%25401422441170%2540%25401|0|1422441170152\n","protocolVersion":"3.1.0","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}

      //System.out.println("paramString="+paramString);//31,-117,8,0,0,0,0,0,0,0,-53,75,-9,52,54,40,118,14,-79,52,-83,42,12,113,44,9,54,-115,-116,-56,74,13,116,-50,15,14,-45,-81,74,49,-13,45,50,-11,77,43,72,-12,78,11,48,52,50,117,50,-54,53,-11,78,-84,42,8,-15,-113,-56,-85,-12,50,-54,41,15,11,-42,118,-50,44,-82,50,-52,-53,51,14,47,15,50,-56,3,0,-19,100,11,61,76,0,0,0,
      GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
      byte[] bts = paramString.getBytes("utf-8");
      //System.out.println(byteToStr(bts));
      localGZIPOutputStream.write(bts);
      localGZIPOutputStream.close();
       bts =  localByteArrayOutputStream.toByteArray();
      //System.out.println(byteToStr(bts));
       
      return bts;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  public static String  byteToStr2(byte[] bts)
  {
  	String str = "";
  	for(byte bt:bts)
  	{
  		String t = String.format("%d,", bt);
  		str += t;
  	}
  	return str;
  }
  
  public final byte[] a(String paramString)
  {
    byte[] arrayOfByte2 = null;
    byte[] arrayOfByte3 = null;
    //if (this.b == null)
    {
      arrayOfByte2 = "134e3265829ff82daf16e7b740a600b5".getBytes();
      arrayOfByte3 = new byte[16];
    }
    for (int i = 0;; i++)
    {
      if ((i >= arrayOfByte2.length) || (i >= arrayOfByte3.length))
      {
        this.b = new SecretKeySpec(arrayOfByte3, "AES");
        Cipher localCipher = null;
		try {
			localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        IvParameterSpec localIvParameterSpec = new IvParameterSpec("0102030405060708".getBytes());
        try {
			localCipher.init(1, this.b, localIvParameterSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] arrayOfByte1 = null;
		try {
			arrayOfByte1 = localCipher.doFinal(paramString.getBytes());//OK
			//System.out.println("str="+byteToStr(arrayOfByte1));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return b(new h().a(arrayOfByte1));
      }
      arrayOfByte3[i] = arrayOfByte2[i];
    }
  }
}


/* Location:           classes_dex2jar.jar
 * Qualified Name:     com.chinaMobile.d
 * JD-Core Version:    0.7.0.1
 */