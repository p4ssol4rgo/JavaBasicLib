package org.wmfc.utils.amf.types;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTime extends Date implements Externalizable {

	public DateTime(){
		super();
	}
	
	public DateTime(long time) {
		super(time);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		
        Object val = in.readObject();
        
        long valor = ((Double)val).longValue();
        
        Calendar start = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        start.clear();
        start.setTimeInMillis(valor);
        
        Calendar dataLocal = Calendar.getInstance();
        dataLocal.clear();
        dataLocal.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE), start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), start.get(Calendar.SECOND));
        dataLocal.set(Calendar.MILLISECOND, start.get(Calendar.MILLISECOND));
        
        this.setTime(dataLocal.getTimeInMillis());
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException 
	{
        int tz = TimeZone.getDefault().getOffset(this.getTime()) * -1;
        
        out.writeObject(this.getTime() - tz);
	}

}
