package com.yeyintkoko.techtricity.custom_control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateFormat {

	public SimpleDateFormat DATE_FORMAT_YMD;
    public SimpleDateFormat DATE_FORMAT_YMD_TEXT;
    public SimpleDateFormat DATE_FORMAT_YMD_HMS;
    public SimpleDateFormat DATE_FORMAT_DMY;
    public SimpleDateFormat DATE_FORMAT_DMY_HMS_Z;
    public SimpleDateFormat DATE_FORMAT_DMY_HM_AAA;
    public SimpleDateFormat DATE_FORMAT_DD_MM_AAA;
    public SimpleDateFormat DATE_FORMAT_DMY_HMS_AAA;
    public SimpleDateFormat DATE_FORMAT_NOTI;

	private String result;
	
	public MyDateFormat()
	{
        DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
        DATE_FORMAT_YMD_TEXT = new SimpleDateFormat("dd MMMM, yyyy");
        DATE_FORMAT_YMD_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DATE_FORMAT_DMY = new SimpleDateFormat("dd/MM/yyyy");
        DATE_FORMAT_DMY_HMS_Z = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        DATE_FORMAT_DD_MM_AAA = new SimpleDateFormat("dd MMM, yyy   h:mm aaa");
        DATE_FORMAT_DMY_HM_AAA = new SimpleDateFormat("dd MMMM yyyy, h:mm aaa");
        DATE_FORMAT_DMY_HMS_AAA = new SimpleDateFormat("dd/MM//yyyy HH:mm:ss a");
        DATE_FORMAT_NOTI = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");

	}
    public String removeTfromServerDate(String datetime)
    {
        return  datetime.replace("T", " ");
    }

    public String getDate(SimpleDateFormat dateFormat, String dateTime){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        String newDate = "";
        if (dateTime != null){
            try {
                Date date = inputFormat.parse(dateTime);
                newDate = dateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return newDate;
    }
}
