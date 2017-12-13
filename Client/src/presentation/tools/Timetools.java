package presentation.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timetools {

	/**
	 * �z�鲢�Ԅ�����ϵͳʱ��
	 */
	public static void check() {
		SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd");  
        SimpleDateFormat simtime=new SimpleDateFormat("HH:mm:ss");  
        Date date=getNetworkTime("http://www.baidu.com");  
        String stime=simtime.format(date);  
        String sdate=simdate.format(date);   
        Runtime run=Runtime.getRuntime();     
        try{  
            run.exec("cmd /c time "+stime);  
            run.exec("cmd /c date "+sdate);
        }catch(IOException e){  
            System.out.println(e.getMessage());  
        }
	}
	/**
	 * ��õ�ǰʱ���ʾHH:mm:ss
	 * @return ʱ���ַ���
	 */
	public static String getTime() {
		SimpleDateFormat simtime=new SimpleDateFormat("HH:mm:ss");
		String stime=simtime.format(new Date());
		return stime;
	}
	/**
	 * ��õ�ǰ���ڱ�ʾyyyyMMdd
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat simdate=new SimpleDateFormat("yyyyMMdd");
		String sdate=simdate.format(new Date());
		return sdate;
	}
	
	/**
	 * ���һ�����ڶεĺϷ���<br>
	 * ���ҽ����������������ǿ��ַ����Ҿ�Ϊyyyy-MM-dd��ʽ��ǰ��from����������ڲ���������to�����������ʱ�������ڶκϷ�
	 */
	public static boolean checkDate(String from, String to){
	    if(from.length() == 0 || to.length() == 0) return false;
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	        Date fromDate = format.parse(from), toDate = format.parse(to);
	        if(fromDate.after(toDate)) return false;
	        return true;
	    }catch(ParseException e){
	        e.printStackTrace();
	        return false;
	    }
	}

	private static Date getNetworkTime(String url1){  
        try{  
            URL url=new URL(url1);  
            URLConnection urlc=url.openConnection();  
            urlc.connect();  
            long time=urlc.getDate();  
            Date date=new Date(time);  
            return date;  
        }catch(MalformedURLException e){  
            System.out.println(e.getMessage());  
        }catch(IOException e){  
            System.out.println(e.getMessage());  
        }  
        return null;  
    } 

}
