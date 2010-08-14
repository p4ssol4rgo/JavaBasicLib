package org.wmfc.utils.amf.types;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeDTO implements IDateTime, Externalizable {
	
	private Date _date;
	
	@Override
	public Date getDate() {
		return _date;
	}

	@Override
	public void setDate(Date value) {
		_date = value;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {

		Object val = in.readObject();
		
		if(val == null) {
			_date = null;
			return;
		}
		
		long valor = ((Double)val).longValue();
		
		Calendar start = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		start.clear();
		start.setTimeInMillis(valor);

		Calendar localDate = Calendar.getInstance();
		localDate.clear();
		localDate.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE), start.get(Calendar.HOUR), start.get(Calendar.MINUTE), start.get(Calendar.SECOND));
		
		_date = localDate.getTime();
		
		System.out.println(_date);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		if(_date == null) {
			out.writeObject(null);
		}else{
			//out.writeObject(_date.getTime());
			
			int tz = TimeZone.getDefault().getOffset(_date.getTime()) * -1;
			
			out.writeObject(_date.getTime() - tz);
		}
		
	}

}
