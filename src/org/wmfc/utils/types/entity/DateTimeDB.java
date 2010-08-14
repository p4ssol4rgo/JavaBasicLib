package org.wmfc.utils.types.entity;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.wmfc.utils.amf.types.DateTime;

public class DateTimeDB extends DateTime implements UserType {

	public DateTimeDB() {
		super();
	}
	
	public DateTimeDB(long time) {
		super(time);
	}
	
	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object obj) throws HibernateException {
		if (obj == null) {
			return null;
		}

		Date newDt = new Date();
		
		newDt.setTime(((Date)obj).getTime());
		
		return newDt;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		return (obj1 == obj2) || (obj1 != null && obj2 != null && (obj1.equals(obj1)));
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet inResultSet, String[] names, Object o)
			throws HibernateException, SQLException {
		
		//TODO
		Object nullSafeVal = Hibernate.TIMESTAMP.nullSafeGet(inResultSet, names[0]);
		
		if(nullSafeVal == null) {
			return null;
		}
		
		Timestamp val = (Timestamp)nullSafeVal;

		return new DateTimeDB(val.getTime());
	}

	@Override
	public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i)
			throws HibernateException, SQLException {

		Timestamp val = null;
		
		if(o != null)
		{
			val = new Timestamp(((Date)o).getTime());
		}
		
		inPreparedStatement.setTimestamp(i, val);
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		
		return original;
	}

	@Override
	public Class<Date> returnedClass() {
		return Date.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {Types.DATE};
	}

}
