package com.hxlm.health.web.util;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by ky3h on 2015/9/24.
 */
public class DateID {
    /**
     * ���ɱ�ʶ��ˮ��(��ʶID)
     * ��ʽ�� ������ʱ����+3λ�����
     * ���磺150924100459123
     * @return
     */
    public static String getCurrentID(){
        Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
        String year = String.valueOf(c.get(Calendar.YEAR)).substring(2, 4);
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        if(month.length()==1){
            month = "0"+month;
        }
        String date = String.valueOf(c.get(Calendar.DATE));
        if(date.length()==1){
            date = "0"+date;
        }
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        if(hour.length()==1){
            hour = "0"+hour;
        }
        String minute = String.valueOf(c.get(Calendar.MINUTE));
        if(minute.length()==1){
            minute = "0"+minute;
        }
        String second = String.valueOf(c.get(Calendar.SECOND));
        if(second.length()==1){
            second = "0"+second;
        }
        String millisecond=String.valueOf(c.get(Calendar.MILLISECOND));
        if(millisecond.length()==1){
            millisecond = "00"+millisecond;
        }
        if(millisecond.length()==2){
            millisecond = "0"+millisecond;
        }
        String rndnumber = getRandomNumber(3);
        String currentid = year+month+date+hour+minute+second+millisecond+rndnumber;
        return currentid;
    }

    /**
     * ����3λ�����
     * @param digCount
     * @return
     */
    public static String getRandomNumber(int digCount) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for(int i=0; i < digCount; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }
}
