package org.wmfc.utils.amf.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static TimeZone tzDefault;
	
	public static TimeZone getTimeZoneDefault() {
		if(tzDefault == null) {
			tzDefault = TimeZone.getTimeZone("America/Sao_Paulo");
		}
		
		return tzDefault;
	}
	
	public static Date obterDataHoraAtual() {
		
		return obterDataHoraAtual(getTimeZoneDefault());
	}
	
	public static Date obterDataHoraAtual(TimeZone zone) {

		Calendar cal = Calendar.getInstance(zone);
		
		long tz = zone.getOffset(cal.getTimeInMillis()) * -1;
		
		return new Date(cal.getTimeInMillis() - tz);
	}
}
