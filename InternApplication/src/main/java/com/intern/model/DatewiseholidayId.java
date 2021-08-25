package com.intern.model;

import java.io.Serializable;
import java.util.Date;



public class DatewiseholidayId implements Serializable {
	
	private static final long serialVersionUID = 2L;
	private long id;
	private Date holidaydate;
	
	public DatewiseholidayId(){
		
	}
	
	 public DatewiseholidayId(long id, Date holidaydate) {
		    super();
	        this.id = id;
	        this.holidaydate = holidaydate;
	    }
	 
	 
	 @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((holidaydate == null) ? 0 : holidaydate.hashCode());
			result = prime * result + (int) (id ^ (id >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DatewiseholidayId other = (DatewiseholidayId) obj;
			if (holidaydate == null) {
				if (other.holidaydate != null)
					return false;
			} else if (!holidaydate.equals(other.holidaydate))
				return false;
			if (id != other.id)
				return false;
			return true;
		}
		
		
		

	 
	
}

