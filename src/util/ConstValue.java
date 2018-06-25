package util;

public class ConstValue {
    public static int OK = 0;
    public static int Fail = 1;
    public static int PasswordFail = 22;
    public static int AccountNoExistFail = 33;
    public static int AccountLocked = 44;
    public static int LOCKED = 1;
    public static int UNLOCK = 0;
    
    public static int PAY_SERVER = 0;//单机服务器等于1,
    public static int DEBUG = 1;
    public static int bNew = 1;
    
    
    public static int RDR = 0;//福永1,广州0
    
    public static int OPTIMIZE = 1;//优化开关
    public static int STAND_ALONE = 1;//支持单机//
    public static String cp_no="CP";
    public static String discontent="客服电话:0710-3711950 客服QQ:2851519970";
    public static String discontent2="客服电话:0710-3711950 客服QQ:2851519970";
    
    public static String ServerUrl = "http://game.91muzhi.com:8080";//192.168.1.107//game.91muzhi.com
    public static String DEBUG_SERVER_URL = "http://119.29.15.79:8999";
    //public static String path = "/sdk";//192.168.1.107//福永
    
    //public static String ServerUrl = "http://sa.91muzhi.com:8091";//"http://192.168.95.1:8080";//192.168.1.107//lsl
    //public static String ServerUrl = "http://sa2.91muzhi.com:8080";//备份测试
    public static String path = "/sdk";////广州
    
 
    
    public static String ServerUrlDebug = "http://sa.91muzhi.com:8091";//"http://sa.91muzhi.com:8090";//"http://www.91muzhi.com:8080";//测试,//
    
    //public static String RDRServer = ServerUrlDebug;
    //public static String RDRServerUrl = RDRServer+"/sdk2";
    //public static String CPPACKETServerUrl = "http://119.29.15.79/sdk";
    public static String CPPACKETServerUrl = "http://119.29.15.70:8091/sdk";
    
    public static String RDRServer = "http://gm.91muzhi.com:8080";
    public static String RDRServerUrl = RDRServer+"/sdk";
    
    public static int getPAY_SERVER(){
		return PAY_SERVER;
	}

}
