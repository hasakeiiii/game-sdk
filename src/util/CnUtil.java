package util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;


//@Function:汉字处理工具类，提供有关汉字操作方面的方法
@SuppressWarnings("unchecked")
public class CnUtil {
	
	public static String unescape(String s) {
	    StringBuffer sbuf = new StringBuffer () ;
	    int l  = s.length() ;
	    int ch = -1 ;
	    int b, sumb = 0;
	    for (int i = 0, more = -1 ; i < l ; i++) {
	      /* Get next byte b from URL segment s */
	      switch (ch = s.charAt(i)) {
	        case '%':
	          ch = s.charAt (++i) ;
	          int hb = (Character.isDigit ((char) ch) 
	                    ? ch - '0'
	                    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
	          ch = s.charAt (++i) ;
	          int lb = (Character.isDigit ((char) ch)
	                    ? ch - '0'
	                    : 10+Character.toLowerCase ((char) ch)-'a') & 0xF ;
	          b = (hb << 4) | lb ;
	          break ;
	        case '+':
	          b = ' ' ;
	          break ;
	        default:
	          b = ch ;
	      }
	      /* Decode byte b as UTF-8, sumb collects incomplete chars */
	      if ((b & 0xc0) == 0x80) {                        // 10xxxxxx (continuation byte)
	        sumb = (sumb << 6) | (b & 0x3f) ;        // Add 6 bits to sumb
	        if (--more == 0) sbuf.append((char) sumb) ; // Add char to sbuf
	      } else if ((b & 0x80) == 0x00) {                // 0xxxxxxx (yields 7 bits)
	        sbuf.append((char) b) ;                        // Store in sbuf
	      } else if ((b & 0xe0) == 0xc0) {                // 110xxxxx (yields 5 bits)
	        sumb = b & 0x1f;
	        more = 1;                                // Expect 1 more byte
	      } else if ((b & 0xf0) == 0xe0) {                // 1110xxxx (yields 4 bits)
	        sumb = b & 0x0f;
	        more = 2;                                // Expect 2 more bytes
	      } else if ((b & 0xf8) == 0xf0) {                // 11110xxx (yields 3 bits)
	        sumb = b & 0x07;
	        more = 3;                                // Expect 3 more bytes
	      } else if ((b & 0xfc) == 0xf8) {                // 111110xx (yields 2 bits)
	        sumb = b & 0x03;
	        more = 4;                                // Expect 4 more bytes
	      } else /*if ((b & 0xfe) == 0xfc)*/ {        // 1111110x (yields 1 bit)
	        sumb = b & 0x01;
	        more = 5;                                // Expect 5 more bytes
	      }
	      /* We don't test if the UTF-8 encoding is well-formed */
	    }
	    return sbuf.toString() ;
	  }
	
	@SuppressWarnings("unchecked")
	public static String[] cnSort(String[] arr){
		String[] tempArr=new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			tempArr[i]=getFullSpell(arr[i]);
		}
		Arrays.sort(tempArr);
		azul.JspUtil.p(tempArr);
		return arr;
	}
	
	private static LinkedHashMap spellMap = null;
	static {
		if (spellMap == null) {
			spellMap = new LinkedHashMap(400);
		}
		initialize();
	}

	private static Character getAlphaByAscii(int ascii) {
		char retValue;
		if ((ascii >= -20319) && (ascii < -20283)) {
			retValue = 'A';
		} else if ((ascii >= -20283) && (ascii < -19775)) {
			retValue = 'B';
		} else if ((ascii >= -19775) && (ascii < -19218)) {
			retValue = 'C';
		} else if ((ascii >= -19218) && (ascii < -18710)) {
			retValue = 'D';
		} else if ((ascii >= -18710) && (ascii < -18526)) {
			retValue = 'E';
		} else if ((ascii >= -18526) && (ascii < -18239)) {
			retValue = 'F';
		} else if ((ascii >= -18239) && (ascii < -17922)) {
			retValue = 'G';
		} else if ((ascii >= -17922) && (ascii < -17417)) {
			retValue = 'H';
		} else if ((ascii >= -17417) && (ascii < -16474)) {
			retValue = 'J';
		} else if ((ascii >= -16474) && (ascii < -16212)) {
			retValue = 'K';
		} else if ((ascii >= -16212) && (ascii < -15640)) {
			retValue = 'L';
		} else if ((ascii >= -15640) && (ascii < -15165)) {
			retValue = 'M';
		} else if ((ascii >= -15165) && (ascii < -14922)) {
			retValue = 'N';
		} else if ((ascii >= -14922) && (ascii < -14914)) {
			retValue = 'O';
		} else if ((ascii >= -14914) && (ascii < -14630)) {
			retValue = 'P';
		} else if ((ascii >= -14630) && (ascii < -14149)) {
			retValue = 'Q';
		} else if ((ascii >= -14149) && (ascii < -14090)) {
			retValue = 'R';
		} else if ((ascii >= -14090) && (ascii < -13318)) {
			retValue = 'S';
		} else if ((ascii >= -13318) && (ascii < -12838)) {
			retValue = 'T';
		} else if ((ascii >= -12838) && (ascii < -12556)) {
			retValue = 'W';
		} else if ((ascii >= -12556) && (ascii < -11847)) {
			retValue = 'X';
		} else if ((ascii >= -11847) && (ascii < -11055)) {
			retValue = 'Y';
		} else if ((ascii >= -11055) && (ascii < -10247)) {
			retValue = 'Z';
		} else {
			return null;
		}
		return new Character(retValue);
	}
	

	 //方法说明：获得单个汉字的Ascii cn:char:汉字字符 错误返回 0,否则返回ascii
	private static int getCnAscii(char cn) {
		byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // 错误
			return 0;
		}
		if (bytes.length == 1) { // 英文字符
			return bytes[0];
		}
		if (bytes.length == 2) { // 中文字符
			// 以下是utf8转ascii的算法
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];

			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			// System.out.println("ASCII=" + ascii);
			return ascii;
		}

		return 0; // 错误
	}


//方法说明：返回字符串的拼音首字母 cnStr:字符串 return 转换成首字母的字符串
	public static String getFirstLatter(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}

		char[] chars = cnStr.toCharArray();
		StringBuffer retuBuf = new StringBuffer();
		for (int i = 0, Len = chars.length; i < Len; i++) {
			int ascii = getCnAscii(chars[i]);
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(chars[i]);
			} else {
				Character alpha = getAlphaByAscii(ascii);
				if (alpha == null) {
					retuBuf.append(chars[i]);
				} else {
					retuBuf.append(alpha);
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for

		return retuBuf.toString();
	}

	/**
	 * 方法说明：返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换 
	 * cnStr String:字符串
	 * return String 转换成全拼后的字符串
	 */
	public static String getFullSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}

		char[] chars = cnStr.toCharArray();
		StringBuffer retuBuf = new StringBuffer();
		for (int i = 0, Len = chars.length; i < Len; i++) {
			int ascii = getCnAscii(chars[i]);
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(chars[i]);
			} else {
				String spell = getSpellByAscii(ascii);
				if (spell == null) {
					retuBuf.append(chars[i]);
				} else {
					retuBuf.append(spell);
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for

		return retuBuf.toString();
	}

	//和上一方法不同的是每个字的首字母大写
	public static String getFullSpellBig(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		char[] chars = cnStr.toCharArray();
		StringBuffer retuBuf = new StringBuffer();
		for (int i = 0, Len = chars.length; i < Len; i++) {
			int ascii = getCnAscii(chars[i]);
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(chars[i]);
			} else {
				String spell = getSpellByAscii(ascii);
				if (spell == null) {
					retuBuf.append(chars[i]);
				} else {
					spell=spell.substring(0, 1).toUpperCase()+ spell.substring(1, spell.length());
					retuBuf.append(spell);
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for
		return retuBuf.toString();
	}
	
	/**
	 * 方法说明：根据ASCII码到SpellMap中查找对应的拼音
	 * 
	 * @param ascii--int:字符对应的ASCII
	 * @return String 拼音,首先判断ASCII是否>0&<160,如果是返回对应的字符, 
	 * 否则到SpellMap中查找,如果没有找到拼音,则返回null,如果找到则返回拼音.
	 */
	private static String getSpellByAscii(int ascii) {
		if (ascii > 0 && ascii < 160) { // 单字符
			return String.valueOf((char) ascii);
		}

		if (ascii < -20319 || ascii > -10247) { // 不知道的字符
			return null;
		}

		Set keySet = spellMap.keySet();
		Iterator it = keySet.iterator();
		String spell0 = null;
		String spell = null;
		int asciiRang0 = -20319;
		int asciiRang;
		while (it.hasNext()) {
			spell = (String) it.next();
			Object valObj = spellMap.get(spell);
			if (valObj instanceof Integer) {
				asciiRang = ((Integer) valObj).intValue();
				if (ascii >= asciiRang0 && ascii < asciiRang) { // 区间找到
					return (spell0 == null) ? spell : spell0;
				} else {
					spell0 = spell;
					asciiRang0 = asciiRang;
				}
			}
		}

		return null;

	}

	private static void initialize() {
		spellPut("a", -20319);
		spellPut("ai", -20317);
		spellPut("an", -20304);
		spellPut("ang", -20295);
		spellPut("ao", -20292);
		spellPut("ba", -20283);
		spellPut("bai", -20265);
		spellPut("ban", -20257);
		spellPut("bang", -20242);
		spellPut("bao", -20230);
		spellPut("bei", -20051);
		spellPut("ben", -20036);
		spellPut("beng", -20032);
		spellPut("bi", -20026);
		spellPut("bian", -20002);
		spellPut("biao", -19990);
		spellPut("bie", -19986);
		spellPut("bin", -19982);
		spellPut("bing", -19976);
		spellPut("bo", -19805);
		spellPut("bu", -19784);
		spellPut("ca", -19775);
		spellPut("cai", -19774);
		spellPut("can", -19763);
		spellPut("cang", -19756);
		spellPut("cao", -19751);
		spellPut("ce", -19746);
		spellPut("ceng", -19741);
		spellPut("cha", -19739);
		spellPut("chai", -19728);
		spellPut("chan", -19725);
		spellPut("chang", -19715);
		spellPut("chao", -19540);
		spellPut("che", -19531);
		spellPut("chen", -19525);
		spellPut("cheng", -19515);
		spellPut("chi", -19500);
		spellPut("chong", -19484);
		spellPut("chou", -19479);
		spellPut("chu", -19467);
		spellPut("chuai", -19289);
		spellPut("chuan", -19288);
		spellPut("chuang", -19281);
		spellPut("chui", -19275);
		spellPut("chun", -19270);
		spellPut("chuo", -19263);
		spellPut("ci", -19261);
		spellPut("cong", -19249);
		spellPut("cou", -19243);
		spellPut("cu", -19242);
		spellPut("cuan", -19238);
		spellPut("cui", -19235);
		spellPut("cun", -19227);
		spellPut("cuo", -19224);
		spellPut("da", -19218);
		spellPut("dai", -19212);
		spellPut("dan", -19038);
		spellPut("dang", -19023);
		spellPut("dao", -19018);
		spellPut("de", -19006);
		spellPut("deng", -19003);
		spellPut("di", -18996);
		spellPut("dian", -18977);
		spellPut("diao", -18961);
		spellPut("die", -18952);
		spellPut("ding", -18783);
		spellPut("diu", -18774);
		spellPut("dong", -18773);
		spellPut("dou", -18763);
		spellPut("du", -18756);
		spellPut("duan", -18741);
		spellPut("dui", -18735);
		spellPut("dun", -18731);
		spellPut("duo", -18722);
		spellPut("e", -18710);
		spellPut("en", -18697);
		spellPut("er", -18696);
		spellPut("fa", -18526);
		spellPut("fan", -18518);
		spellPut("fang", -18501);
		spellPut("fei", -18490);
		spellPut("fen", -18478);
		spellPut("feng", -18463);
		spellPut("fo", -18448);
		spellPut("fou", -18447);
		spellPut("fu", -18446);
		spellPut("ga", -18239);
		spellPut("gai", -18237);
		spellPut("gan", -18231);
		spellPut("gang", -18220);
		spellPut("gao", -18211);
		spellPut("ge", -18201);
		spellPut("gei", -18184);
		spellPut("gen", -18183);
		spellPut("geng", -18181);
		spellPut("gong", -18012);
		spellPut("gou", -17997);
		spellPut("gu", -17988);
		spellPut("gua", -17970);
		spellPut("guai", -17964);
		spellPut("guan", -17961);
		spellPut("guang", -17950);
		spellPut("gui", -17947);
		spellPut("gun", -17931);
		spellPut("guo", -17928);
		spellPut("ha", -17922);
		spellPut("hai", -17759);
		spellPut("han", -17752);
		spellPut("hang", -17733);
		spellPut("hao", -17730);
		spellPut("he", -17721);
		spellPut("hei", -17703);
		spellPut("hen", -17701);
		spellPut("heng", -17697);
		spellPut("hong", -17692);
		spellPut("hou", -17683);
		spellPut("hu", -17676);
		spellPut("hua", -17496);
		spellPut("huai", -17487);
		spellPut("huan", -17482);
		spellPut("huang", -17468);
		spellPut("hui", -17454);
		spellPut("hun", -17433);
		spellPut("huo", -17427);
		spellPut("ji", -17417);
		spellPut("jia", -17202);
		spellPut("jian", -17185);
		spellPut("jiang", -16983);
		spellPut("jiao", -16970);
		spellPut("jie", -16942);
		spellPut("jin", -16915);
		spellPut("jing", -16733);
		spellPut("jiong", -16708);
		spellPut("jiu", -16706);
		spellPut("ju", -16689);
		spellPut("juan", -16664);
		spellPut("jue", -16657);
		spellPut("jun", -16647);
		spellPut("ka", -16474);
		spellPut("kai", -16470);
		spellPut("kan", -16465);
		spellPut("kang", -16459);
		spellPut("kao", -16452);
		spellPut("ke", -16448);
		spellPut("ken", -16433);
		spellPut("keng", -16429);
		spellPut("kong", -16427);
		spellPut("kou", -16423);
		spellPut("ku", -16419);
		spellPut("kua", -16412);
		spellPut("kuai", -16407);
		spellPut("kuan", -16403);
		spellPut("kuang", -16401);
		spellPut("kui", -16393);
		spellPut("kun", -16220);
		spellPut("kuo", -16216);
		spellPut("la", -16212);
		spellPut("lai", -16205);
		spellPut("lan", -16202);
		spellPut("lang", -16187);
		spellPut("lao", -16180);
		spellPut("le", -16171);
		spellPut("lei", -16169);
		spellPut("leng", -16158);
		spellPut("li", -16155);
		spellPut("lia", -15959);
		spellPut("lian", -15958);
		spellPut("liang", -15944);
		spellPut("liao", -15933);
		spellPut("lie", -15920);
		spellPut("lin", -15915);
		spellPut("ling", -15903);
		spellPut("liu", -15889);
		spellPut("long", -15878);
		spellPut("lou", -15707);
		spellPut("lu", -15701);
		spellPut("lv", -15681);
		spellPut("luan", -15667);
		spellPut("lue", -15661);
		spellPut("lun", -15659);
		spellPut("luo", -15652);
		spellPut("ma", -15640);
		spellPut("mai", -15631);
		spellPut("man", -15625);
		spellPut("mang", -15454);
		spellPut("mao", -15448);
		spellPut("me", -15436);
		spellPut("mei", -15435);
		spellPut("men", -15419);
		spellPut("meng", -15416);
		spellPut("mi", -15408);
		spellPut("mian", -15394);
		spellPut("miao", -15385);
		spellPut("mie", -15377);
		spellPut("min", -15375);
		spellPut("ming", -15369);
		spellPut("miu", -15363);
		spellPut("mo", -15362);
		spellPut("mou", -15183);
		spellPut("mu", -15180);
		spellPut("na", -15165);
		spellPut("nai", -15158);
		spellPut("nan", -15153);
		spellPut("nang", -15150);
		spellPut("nao", -15149);
		spellPut("ne", -15144);
		spellPut("nei", -15143);
		spellPut("nen", -15141);
		spellPut("neng", -15140);
		spellPut("ni", -15139);
		spellPut("nian", -15128);
		spellPut("niang", -15121);
		spellPut("niao", -15119);
		spellPut("nie", -15117);
		spellPut("nin", -15110);
		spellPut("ning", -15109);
		spellPut("niu", -14941);
		spellPut("nong", -14937);
		spellPut("nu", -14933);
		spellPut("nv", -14930);
		spellPut("nuan", -14929);
		spellPut("nue", -14928);
		spellPut("nuo", -14926);
		spellPut("o", -14922);
		spellPut("ou", -14921);
		spellPut("pa", -14914);
		spellPut("pai", -14908);
		spellPut("pan", -14902);
		spellPut("pang", -14894);
		spellPut("pao", -14889);
		spellPut("pei", -14882);
		spellPut("pen", -14873);
		spellPut("peng", -14871);
		spellPut("pi", -14857);
		spellPut("pian", -14678);
		spellPut("piao", -14674);
		spellPut("pie", -14670);
		spellPut("pin", -14668);
		spellPut("ping", -14663);
		spellPut("po", -14654);
		spellPut("pu", -14645);
		spellPut("qi", -14630);
		spellPut("qia", -14594);
		spellPut("qian", -14429);
		spellPut("qiang", -14407);
		spellPut("qiao", -14399);
		spellPut("qie", -14384);
		spellPut("qin", -14379);
		spellPut("qing", -14368);
		spellPut("qiong", -14355);
		spellPut("qiu", -14353);
		spellPut("qu", -14345);
		spellPut("quan", -14170);
		spellPut("que", -14159);
		spellPut("qun", -14151);
		spellPut("ran", -14149);
		spellPut("rang", -14145);
		spellPut("rao", -14140);
		spellPut("re", -14137);
		spellPut("ren", -14135);
		spellPut("reng", -14125);
		spellPut("ri", -14123);
		spellPut("rong", -14122);
		spellPut("rou", -14112);
		spellPut("ru", -14109);
		spellPut("ruan", -14099);
		spellPut("rui", -14097);
		spellPut("run", -14094);
		spellPut("ruo", -14092);
		spellPut("sa", -14090);
		spellPut("sai", -14087);
		spellPut("san", -14083);
		spellPut("sang", -13917);
		spellPut("sao", -13914);
		spellPut("se", -13910);
		spellPut("sen", -13907);
		spellPut("seng", -13906);
		spellPut("sha", -13905);
		spellPut("shai", -13896);
		spellPut("shan", -13894);
		spellPut("shang", -13878);
		spellPut("shao", -13870);
		spellPut("she", -13859);
		spellPut("shen", -13847);
		spellPut("sheng", -13831);
		spellPut("shi", -13658);
		spellPut("shou", -13611);
		spellPut("shu", -13601);
		spellPut("shua", -13406);
		spellPut("shuai", -13404);
		spellPut("shuan", -13400);
		spellPut("shuang", -13398);
		spellPut("shui", -13395);
		spellPut("shun", -13391);
		spellPut("shuo", -13387);
		spellPut("si", -13383);
		spellPut("song", -13367);
		spellPut("sou", -13359);
		spellPut("su", -13356);
		spellPut("suan", -13343);
		spellPut("sui", -13340);
		spellPut("sun", -13329);
		spellPut("suo", -13326);
		spellPut("ta", -13318);
		spellPut("tai", -13147);
		spellPut("tan", -13138);
		spellPut("tang", -13120);
		spellPut("tao", -13107);
		spellPut("te", -13096);
		spellPut("teng", -13095);
		spellPut("ti", -13091);
		spellPut("tian", -13076);
		spellPut("tiao", -13068);
		spellPut("tie", -13063);
		spellPut("ting", -13060);
		spellPut("tong", -12888);
		spellPut("tou", -12875);
		spellPut("tu", -12871);
		spellPut("tuan", -12860);
		spellPut("tui", -12858);
		spellPut("tun", -12852);
		spellPut("tuo", -12849);
		spellPut("wa", -12838);
		spellPut("wai", -12831);
		spellPut("wan", -12829);
		spellPut("wang", -12812);
		spellPut("wei", -12802);
		spellPut("wen", -12607);
		spellPut("weng", -12597);
		spellPut("wo", -12594);
		spellPut("wu", -12585);
		spellPut("xi", -12556);
		spellPut("xia", -12359);
		spellPut("xian", -12346);
		spellPut("xiang", -12320);
		spellPut("xiao", -12300);
		spellPut("xie", -12120);
		spellPut("xin", -12099);
		spellPut("xing", -12089);
		spellPut("xiong", -12074);
		spellPut("xiu", -12067);
		spellPut("xu", -12058);
		spellPut("xuan", -12039);
		spellPut("xue", -11867);
		spellPut("xun", -11861);
		spellPut("ya", -11847);
		spellPut("yan", -11831);
		spellPut("yang", -11798);
		spellPut("yao", -11781);
		spellPut("ye", -11604);
		spellPut("yi", -11589);
		spellPut("yin", -11536);
		spellPut("ying", -11358);
		spellPut("yo", -11340);
		spellPut("yong", -11339);
		spellPut("you", -11324);
		spellPut("yu", -11303);
		spellPut("yuan", -11097);
		spellPut("yue", -11077);
		spellPut("yun", -11067);
		spellPut("za", -11055);
		spellPut("zai", -11052);
		spellPut("zan", -11045);
		spellPut("zang", -11041);
		spellPut("zao", -11038);
		spellPut("ze", -11024);
		spellPut("zei", -11020);
		spellPut("zen", -11019);
		spellPut("zeng", -11018);
		spellPut("zha", -11014);
		spellPut("zhai", -10838);
		spellPut("zhan", -10832);
		spellPut("zhang", -10815);
		spellPut("zhao", -10800);
		spellPut("zhe", -10790);
		spellPut("zhen", -10780);
		spellPut("zheng", -10764);
		spellPut("zhi", -10587);
		spellPut("zhong", -10544);
		spellPut("zhou", -10533);
		spellPut("zhu", -10519);
		spellPut("zhua", -10331);
		spellPut("zhuai", -10329);
		spellPut("zhuan", -10328);
		spellPut("zhuang", -10322);
		spellPut("zhui", -10315);
		spellPut("zhun", -10309);
		spellPut("zhuo", -10307);
		spellPut("zi", -10296);
		spellPut("zong", -10281);
		spellPut("zou", -10274);
		spellPut("zu", -10270);
		spellPut("zuan", -10262);
		spellPut("zui", -10260);
		spellPut("zun", -10256);
		spellPut("zuo", -10254);
	}

	/**
	 * 方法说明：将金额数字转换为中文表示
	 * 
	 * @param 此方法可以处理带小数点的数据，如123.45
	 */
	public static String moneyToStr(double val) {
		String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆","柒", "捌", "玖" };
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;
		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999) {
			return "数值位数过大!";
		}
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "角";
			if (integer == 0 && jiao == 0) // 零元后不写零几分
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "分";
		}
		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;
		return SignStr + strToMoney(String.valueOf(integer)) + "元" + TailStr;
	}

	@SuppressWarnings("unchecked")
	private static void spellPut(String spell, int ascii) {
		spellMap.put(spell, ascii);
	}

	/**
	 * 方法说明：将中文金额数字转换为数字类型表示
	 * 
	 * @param 输入字符串必须正整数，如123等。只允许前导空格(必须右对齐)，不宜有前导零
	 */
	public static String strToMoney(String NumStr) { // 
		String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆",
				"柒", "捌", "玖" };
		String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰",
				"仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰",
				"仟", "万", "拾", "佰", "仟" };
		StringBuffer RMBStrBuffer = new StringBuffer("");
		boolean lastzero = false;
		// 亿、万进位前有数值标记
		boolean hasvalue = false;
		int len, n;
		len = NumStr.length();
		if (len > 15) {
			return "数值过大!";
		}
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "输入含非数字字符!";
			if (n != 0) {
				if (lastzero)
					RMBStrBuffer.append(HanDigiStr[0]); // 若干零后若跟非零值，只显示一个零
				// 除了亿万前的零不带到后面
				// 如十进位前有零也不发壹音用此行
				// if(!(n==1 && (i%4)==1 && (lastzero || i==len-1)))
				// 十进位处于第一位不发壹音
				if (!(n == 1 && (i % 4) == 1 && i == len - 1))
					RMBStrBuffer.append(HanDigiStr[n]);
				RMBStrBuffer.append(HanDiviStr[i]); // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				// 亿万之间必须有非零值方显示万
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue))
					RMBStrBuffer.append(HanDiviStr[i]); // “亿”或“万”
			}
			if (i % 8 == 0)
				hasvalue = false; // 万进位前有值标记逢亿复位
			lastzero = (n == 0) && (i % 4 != 0);
		}
		if (RMBStrBuffer.toString().length() == 0)
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		return RMBStrBuffer.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getFirstLatter("啊吧承担而非个"));
		System.out.println(getFullSpellBig("啊吧承担而非个"));
		System.out.println(moneyToStr(3478));
		System.out.println(strToMoney("1267"));
		
		String[] arr=cnSort(common.Constant.AREA);
		azul.JspUtil.p(arr);
	}
}
