package com.study.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期工具类
 * @author 何小文
 *
 */
public class DateUtil {
	public  final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	public final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	public final static SimpleDateFormat sdfDays = new SimpleDateFormat(
	"yyyyMMdd");

	public final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");


	public  final static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

	public  final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyyMM");
	
	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}
	public static String getYearMonth(){
		return sdfYearMonth.format(new Date());
	}
	
	public static String getMonth(){
		return sdfMonth.format(new Date());
	}
	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date,String fomat) {
		DateFormat fmt = new SimpleDateFormat(fomat);
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String fomatDate(Date date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String fomatDate(Date date,String fomat) {
		DateFormat fmt = new SimpleDateFormat(fomat);
		try {
			return fmt.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    /**
     * 根据该时间提前 second秒
     * @param date
     * @param second
     * @return
     */
    public static Date getDaySub(Date date,long seconds){
        long day=0;
        	day = date.getTime()-seconds*1000;
        	Date retrunDate = new Date(day);
            //day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
        return retrunDate;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之前的日期
     * @param days
     * @return
     */
    public static String getBeforeDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, -daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之前的月和日
     * @param days
     * @return
     */
    public static String getBeforeDayMandD(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, -daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("M-d");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days,SimpleDateFormat format) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = format;
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    
    
    
    
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static Date getAfterDayWeek(Date date ,Integer days) {
    	 long time = date.getTime(); 
    	 days = days*24*60*60*1000; 
    	  time+=days; 
    	  return new Date(time);
    }
    
    public static void main(String[] args) {
    	System.out.println(getBeforeDayDate("29"));
    	System.out.println(getBeforeDayDate("0"));
    	
    	 Calendar canlendar = Calendar.getInstance(); // java.util包
         canlendar.add(Calendar.MONTH,-6); // 日期减 如果不够减会将月变动
         Date date = canlendar.getTime();
         
         SimpleDateFormat sdfd = new SimpleDateFormat("Y-M");
         String dateStr = sdfd.format(date);
    	
    	
    	System.out.println(dateStr);
	}
	/**
	  * 得到本周周一
	  * 
	  * @return yyyy-MM-dd 00:00:00
	  */
	 public static String getMondayOfThisWeek() {
		 SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		   day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 1);
		  Date d = c.getTime();
		  return sdf.format(d)+" 00:00:00";
	 }
	 /**
	  * 得到该日的周一
	  * 
	  * @return yyyy-MM-dd HH:mm:ss
	  */
	 public static String getMondayOfThisWeek(Date date) {
		 SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Calendar c = Calendar.getInstance();
		  c.setTime(date);
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		   day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 1);
		  return sdfTime.format(c.getTime());
	 }
	 /**
	  * 得到本周周日
	  * 
	  * @return yyyy-MM-dd
	  */
	 public static String getSundayOfThisWeek() {
		 SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		   day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 7);
		  return sdfTime.format(c.getTime());
	 }
	 /**
	  * 得到改日的周日
	  * 
	  * @return yyyy-MM-dd
	  */
	 public static String getSundayOfThisWeek(Date date) {
		 SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Calendar c = Calendar.getInstance();
		  c.setTime(date);
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		   day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 7);
		  return sdfTime.format(c.getTime());
	 }
	 public static Date getExpirationtime(Integer Day){
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			int d = calendar.get(Calendar.DATE);
			int month = 0;
			if(d>26){
				month = calendar.get(Calendar.MONTH)+Day/30;
			}else{
				month = calendar.get(Calendar.MONTH)+Day/30-1;
			}
			calendar.set(year, month, 26,0,0,0);
			return new Date(calendar.getTimeInMillis());
		}
}
