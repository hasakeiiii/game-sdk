package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public final static String DATE_A="yyyy-MM-dd";
	public final static String DATE_B="yyyy-MM-dd HH:mm:ss";
	public final static String DATE_C="yyyyMMddHHmmss";
	public final static String DATE_D="yyyyMMdd-HHmmss-SS";
	public final static String DATE_E="M月d日";
	public final static String DATE_F="MM-dd";
	public final static String DATE_G="yyyyMMddHHmmss";
	

	public static boolean isInDate(String beginDay,String endDay)
	{
		boolean ret = false;
		String curDate = DateUtil.getDate();
		
		if(curDate.compareTo(beginDay)>=0 && curDate.compareTo(endDay)<=0)
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	
	public static boolean isInDays(String daystr)
	
	{
		boolean ret = false;
		
		if(!StringUtil.is_nullString(daystr))
		{
			String splitstr = daystr;
			String[] strarray=splitstr.split("_");    
			
			Date date = new Date();//星期六星期天不过滤
			int iday = date.getDay();
		    if(strarray.length > 0 )
		    {
		    	for(String day:strarray)
		    	{
		    		if(Integer.valueOf(day) == iday)
		    		{
		    			ret = true;
		    			break;
		    		}
		    	}
		    }
		}
		return ret;
	}
	public static boolean isInTimes(String timestr)
	{
		boolean ret = false;
		String beginTime1 = "";
		String endTime1 = "";
		String beginTime2 = "";
		String endTime2 = "";
		String controlTime = timestr;
		String time = DateUtil.getTime();
		
		if(!StringUtil.is_nullString(controlTime))
    	{
    		String controlTime1 = controlTime;
    		String controlTime2 = "";
    		if(controlTime.contains(";"))
    		{
    			String splitstr = controlTime;
    			String[] strarray=splitstr.split(";");     		
    			controlTime1 = strarray[0];
    			if(strarray.length > 1)
    			controlTime2 = strarray[1];
    		}
    		
    		if(controlTime1.contains("-"))
    		{
    			String splitstr = controlTime1;
    			String[] strarray=splitstr.split("-");     		
    			beginTime1 = strarray[0];
    			if(strarray.length > 1)
    			endTime1 = strarray[1];
    		}
    		if(controlTime2.contains("-"))
    		{
    			String splitstr = controlTime2;
    			String[] strarray=splitstr.split("-");     		
    			beginTime2 = strarray[0];
    			if(strarray.length > 1)
    			endTime2 = strarray[1];
    		}
    	}
    	
    	if( ((time.compareTo(beginTime1) >= 0) && (time.compareTo(endTime1) <= 0)) || ((time.compareTo(beginTime2) >= 0) && (time.compareTo(endTime2) <= 0)) )
		{
    		ret = true;
    		//DebuUtil.log("打开");
		}
    	else
    	{
    		ret = false;;
    		//DebuUtil.log("关闭");
    	}
    	
		return ret;
	}	
	//自动检测字符串形式然后转换
	public static Date strToDate(String dateStr) {
		Date date=null;
		SimpleDateFormat sdf=null;
		if (dateStr == null || dateStr.equals("")) {
			throw new RuntimeException("DateUtil.strToDate():" +dateStr);
		}
		else if(dateStr.indexOf(":")>0){
			sdf = new SimpleDateFormat(DATE_B);
		}
		else if(dateStr.indexOf("-")>0){
			sdf = new SimpleDateFormat(DATE_A);
		}
		else if(dateStr.length()==14){
			sdf = new SimpleDateFormat(DATE_G);
		}
		try {
			if(sdf!=null){
				date=sdf.parse(dateStr);
			}
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.strToDate():" +dateStr);
		}
		return date;
	}
	
	//特殊的日期格式转换
	public static Date strToDate(String dateStr, String dateFormat) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.strToDate():" + e.getMessage());
		}
	}
	
	//普通的当前时间转字符串方法，格式为yyyy-MM-dd
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_A);
		return sdf.format(new Date());
	}

	public static String getDate(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_A);
		return sdf.format(date);
	}
	
	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_B);
		return sdf.format(new Date());
	}
	
	public static String getTime() {
		String ret;
		String datestr;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_B);
		datestr = sdf.format(new Date());
		ret = datestr.substring(11, datestr.length());
		return ret;
	}
	
	public static String getTime(java.util.Date date) {
		String ret;
		String datestr;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_B);
		datestr = sdf.format(date);
		ret = datestr.substring(11, datestr.length());
		return ret;
	}
	
	public static String getDateTime(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_B);
		return sdf.format(date);
	}
	
	
	//普通的时间转字符串方法
	public static String getDate(java.util.Date date,String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static int getDaysByMonth(int year,int month) {
		int days = 30;
		if(month == 1)
		{
			days = 31;
		}
		else if(month == 2)
		{
			days = 28;
		}
		else if(month == 3)
		{
			days = 31;
		}
		else if(month == 4)
		{
			days = 30;
		}
		else if(month == 5)
		{
			days = 31;
		}
		else if(month == 6)
		{
			days = 30;
		}
		else if(month == 7)
		{
			days = 31;
		}
		else if(month == 8)
		{
			days = 31;
		}
		else if(month == 9)
		{
			days = 30;
		}
		else if(month == 10)
		{
			days = 31;
		}
		else if(month == 11)
		{
			days = 30;
		}
		else if(month == 12)
		{
			days = 31;
		}
		return days;
	}
	
	/**
	 * 时间相加
	 */
	public static Date addDate(String datepart, int number, Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		if (datepart.equals("yy")) {
			cal.add(Calendar.YEAR, number);
		} else if (datepart.equals("MM")) {
			cal.add(Calendar.MONTH, number);
		} else if (datepart.equals("dd")) {
			cal.add(Calendar.DATE, number);
		} else if (datepart.equals("HH")) {
			cal.add(Calendar.HOUR, number);
		} else if (datepart.equals("mm")) {
			cal.add(Calendar.MINUTE, number);
		} else if (datepart.equals("ss")) {
			cal.add(Calendar.SECOND, number);
		}
		return cal.getTime();
	}

	/**
	 * 两个时间相差多少月
	 */
	public static int getMonthDiff(Date startdate, Date enddate) {
		int k = 0;
		GregorianCalendar temp = new GregorianCalendar();
		temp.setTime(startdate);
		temp.set(GregorianCalendar.MILLISECOND, 0);
		temp.add(GregorianCalendar.DAY_OF_MONTH, 1);
		int day = temp.getActualMaximum(GregorianCalendar.DATE);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(enddate);
		endCal.set(GregorianCalendar.MILLISECOND, 0);
		endCal.add(GregorianCalendar.DAY_OF_MONTH, 1);
		while (temp.getTime().before(endCal.getTime())) {
			k++;
			day = temp.getActualMaximum(GregorianCalendar.DATE);
			temp.add(GregorianCalendar.DAY_OF_MONTH, day);
		}
		return k;
	}

	/**
	 * 将时间的时分秒信息清除
	 */
	public static Date clearTime(Date date) {
		if (date == null)return date;
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.clear(Calendar.MILLISECOND);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.AM_PM);
		return cal.getTime();
	}

	//需要加上时区的8个小时
	public static String millisToTime(long millisSeconds) {
		String result = "";
		long hours, minutes, seconds;
		hours = millisSeconds / 3600000%24;
		millisSeconds = millisSeconds - (hours * 3600000);
		minutes = millisSeconds / 60000%60;
		millisSeconds = millisSeconds - (minutes * 60000);
		seconds = millisSeconds / 1000%60;
		if (hours != 0) {
			result += hours + "小时";
		}
		result += minutes + "分";
		result += seconds + "秒";
		return result;
	}

	public static long timeDec(Date endDate, Date begDate) {
		return endDate.getTime() - begDate.getTime();
	}
	
	public static long timeDec(String endDate, String begDate) {
		return strToDate(endDate).getTime() - strToDate(begDate).getTime();
	}
	
	public static int getAge(java.util.Date date) {
		long temp=timeDec(new java.util.Date(),date);
		return (int) (temp/(365*24*60*60*10000));
	}
	
	//得到每个周的第一天
	public static Date getFirstDayWeek(Date date){
		Calendar cal = new GregorianCalendar();
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		cal.setTime(date);
		if (curDay == 1) {
			cal.add(GregorianCalendar.DATE, -6); // 每周第一天
		} else {
			cal.add(GregorianCalendar.DATE, 2 - curDay); // 每周第一天
		}
		return cal.getTime();
	}

	// 得到每个周的最后一天
	public static Date getLastDayWeek(Date date){
		return addDate("dd",6,getFirstDayWeek(date));
	}
	
	//得到每个月的第一天
	public static Date getFirstDayMonth(Date date){
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		int tempDate=tempCal.getActualMinimum(Calendar.DAY_OF_MONTH);
		tempCal.set(Calendar.DAY_OF_MONTH,tempDate);
		return tempCal.getTime();
	}

	// 得到每个月的最后一天
	public static Date getLastDayMonth(Date date){
		Calendar cal=Calendar.getInstance();
    	Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		int tempDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		tempCal.set(Calendar.DAY_OF_MONTH,tempDate);
		return tempCal.getTime();
	}

	//距离某个时间的时间间隔,如果起始时间小于结束时间为正值,否则为负值
	public static long getInterval(Date begDate,Date endDate){
		Calendar cal=Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		//date.setTimeInMillis(date.getTimeInMillis() + 86400000);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 10);
		cal.set(Calendar.SECOND , 0);
		long interval=0;
		//System.out.println("AAA="+hour);
		if(hour<3){
			interval=cal.getTimeInMillis()-begDate.getTime();
		}
		else{
			Date tempDate=addDate("dd",1,cal.getTime());
			//System.out.println(getDate(tempDate,DATE_B));
			interval=tempDate.getTime()-begDate.getTime();
		}
		return interval;
	}
	
	public static long getDayDiff(String begDatestr,String endDatestr){
		  
		Date begindate = DateUtil.strToDate(begDatestr);
		Date enddate = DateUtil.strToDate(endDatestr);
		
		return DateUtil.getDayDiff(begindate,enddate);
	}
	
	//两个时间间隔多少分钟
	public static long getDayDiff(Date begDate,Date endDate){
		  long interval=(endDate.getTime()-begDate.getTime())/1000;//秒
		  //long day=interval/(24*3600);//天
		  //long hour=interval%(24*3600)/3600;//小时
		  //long minute=interval%3600/60;//分钟
		  //long second=interval%60;//秒
		return interval/(24*3600);
	}
	
	//页面查询时候，只能让用户看到今天之前的收入。如果用户选择查询时间大于当前时间就返回昨天的日期，否则返回选择时间
	public static String getBeforeDay(String inputDateStr){
		Date inputDate=strToDate(inputDateStr);
		Date now=clearTime(new java.util.Date());
		if(!inputDate.before(now)) {
			inputDate=addDate("dd",-1,now);
		}
		return getDate(inputDate,DATE_A);
	}
	
	
	public static void main(String args[]) {
		try {
//			Date toDay = new Date();
//			Date begDate = addDate("dd", -10, toDay);
//			List<String> dateList = new ArrayList<String>();
//			while (compareDate(toDay, begDate) >= 0) {
//				dateList.add(dateToStr(begDate, "yyyy-MM-dd"));
//				System.out.println(dateToStr(begDate, "yyyy-MM-dd"));
//				begDate = addDate("dd", 1, begDate);
//			}
//			for (int i = 0; i < 5; i++) {
//				System.out.println(Aaa.getAllConfigInfo());
//			}
			
			
			//long aaa=1294231194828;
			/*
		    Date dateA=DateUtil.getFirstDayWeek(new java.util.Date());
		    Date dateB=DateUtil.getLastDayWeek(new java.util.Date());
		    Date dateC=DateUtil.getFirstDayMonth(new java.util.Date());
		    Date dateD=DateUtil.getLastDayMonth(new java.util.Date());
			System.out.println(getDate(dateA,DateUtil.DATE_A));
			System.out.println(getDate(dateB,DateUtil.DATE_B));
			System.out.println(getDate(dateC,DateUtil.DATE_A));
			System.out.println(getDate(dateD,DateUtil.DATE_B));
			Calendar tempCal = Calendar.getInstance();
			tempCal.set(Calendar.DAY_OF_WEEK,tempCal.getFirstDayOfWeek()); 
            */
			/*
			Calendar cal=Calendar.getInstance();
			int hour=cal.get(Calendar.HOUR_OF_DAY);
			//date.setTimeInMillis(date.getTimeInMillis() + 86400000);
			cal.set(Calendar.HOUR_OF_DAY, 3);
			cal.set(Calendar.MINUTE, 10);
			cal.set(Calendar.SECOND , 0);
			long interval=0;
			Date now=new Date();
			System.out.println("AAA="+hour);
			if(hour<3){
				interval=cal.getTimeInMillis()-now.getTime();
			}
			else{
				Date tempDate=addDate("dd",1,cal.getTime());
				//System.out.println(getDate(tempDate,DATE_B));
				interval=tempDate.getTime()-now.getTime();
			}
			*/
			Date endDate=strToDate("2011-11-01");
			Date now=clearTime(new java.util.Date());
			if(!endDate.before(now)) {
				endDate=addDate("dd",-1,now);
			}
			System.out.println(getDate(endDate,DATE_A));
			//Date tempDate=addDate("dd",1,now);
			
			/*
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 3);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND , 0);
			long t=cal.getTimeInMillis()-now.getTime();
			//t=t%86400000;
			System.out.println(t);
			System.out.println(millisToTime(t));
			Random random = new Random();
			double feeDouble=util.NumberUtil.getPosRadom(100,50)+(double)random.nextInt(10)/10;
			System.out.println(feeDouble);
			String start_time="20110512150202";
			System.out.println(start_time.length());
			Date aa=util.DateUtil.strToDate(start_time);
			System.out.println("aa="+aa);
			start_time=util.DateUtil.getDate(util.DateUtil.strToDate(start_time),util.DateUtil.DATE_B);
			System.out.println("BB="+start_time);
			*/
		} catch (Exception e) {
			System.out.println(e);
			e.getStackTrace();
		}
	}

	/**
	 * 根据两个时间计算时间差  返回时间的单位：秒；
	 * 传入时间格式：16:20:24
	 * @param time1  现在的时间   
	 * @param time2 过去的时间
	 * @return timeusage 时间差
	 */
	public static int getUsageOfTime(String time1, String time2) {
		String[] timeNow = new String[3];
		String[] timeOld = new String[3];
		timeNow = time1.split(":");
		timeOld = time2.split(":");
		int h=0;
		int m=0;
		int s=0;

		if (!timeNow[0].equals(timeOld[0])) {
			h = Integer.valueOf(timeNow[0])-Integer.valueOf(timeOld[0]);
		}
		if (!timeNow[1].equals(timeOld[1])) {
			m = Integer.valueOf(timeNow[1])-Integer.valueOf(timeOld[1]);
		}
		if (!timeNow[2].equals(timeOld[2])) {
			s = Integer.valueOf(timeNow[2])-Integer.valueOf(timeOld[2]);
		}
		int timeusage=h*3600+m*60+s;
		return timeusage;
		// TODO Auto-generated method stub

	}
	/**
	 * 针对数据库取出的datetime有小数点的问题做一个处理，如：
	 * 将2014-01-03 14:30:25.0转换为2014-01-03 14:30:25
	 * @param dateTime
	 * @return
	 */
	public static String formatDateTime(String dateTime){
		String a[] = new String[2];
		String ret = "";
		if (dateTime!=null&&!dateTime.equals("")) {
			a = dateTime.split("\\.");
			/*DebuUtil.log(dateTime);
			DebuUtil.log("数组长度："+a.length);//"分解之后的数组为："+a[0]+"  "+a[1]);
*/			ret = a[0];
		}
		return ret;
	}
	/**
	 * 从date中获取需要的内容：年 year，月 month，日 day
	 * 
	 * @param content 所需要的字符串：year或者month或者day
	 * @param date 格式必须为：2014-03-15
	 * @return
	 */
	public static String getStrFromDate(String content,String date){
		String ret = "";
		String[] a = new String[3];
		if (date!=null&&!date.isEmpty()) {
			a = date.split("-");
		}
		
		if (content.equals("year")) {
			ret = a[0];
		}else if(content.equals("month")){
			ret = a[1];
		}else if(content.equals("day")){
			ret = a[2];
		}
		return ret;
	}
	/**
	 * 获取前一日的日期
	 * @param date
	 * @return
	 */
	public static String getYesterday(String date){
		String[] dateStr;
		int year,month,day,byear,bmonth,bday;
		year = byear= 0;
		month = bmonth = 0;
		day = bday = 0;
		if (date!=null&&!date.isEmpty()) {
			dateStr = date.split("-");
			year = Integer.valueOf(dateStr[0]);
			month = Integer.valueOf(dateStr[1]);
			day = Integer.valueOf(dateStr[2]);
			if (day==1) {
				if (month==1) {
					//一月一号时，肯定要退回到上一年12月31号
					bmonth = 12;
					byear = year-1;
					bday = 31;
				}else{
					//不是一月，那么都可以直接取
					bmonth = month-1;
					byear = year;
					bday = getDaysByMonth(byear, bmonth);
				}
			}else{
				bmonth = month;
				byear = year;
				bday = day-1;
			}
			String ret = byear+"-"+bmonth+"-"+bday;
			System.out.println(ret);
			return ret;
		}
		
		return "";
	}
	
}