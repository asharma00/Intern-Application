

package com.intern.model;

import java.io.Serializable;




//composite classes must be serializable
public class LeavesCompositeKey  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private Long uid;
	
	private java.util.Date fromDate;
	

	
	public LeavesCompositeKey() {
	}

	public LeavesCompositeKey(Long uid, java.util.Date fromDate) {
		super();
		this.uid = uid;
		this.fromDate = fromDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + (int) (uid ^ (uid >>> 32));
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
		LeavesCompositeKey other = (LeavesCompositeKey) obj;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}
	
	
	
	
	
}