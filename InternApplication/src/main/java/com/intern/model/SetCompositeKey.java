package com.intern.model;

import java.io.Serializable;


public class SetCompositeKey  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private Integer questionids;
	
	private long setid;
	

	
	public SetCompositeKey() {
	}

	public SetCompositeKey(Integer questionids, long setid) {
		super();
		this.questionids = questionids;
		this.setid = setid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionids == null) ? 0 : questionids.hashCode());
		result = prime * result + (int) (setid ^ (setid>>> 32));
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
		SetCompositeKey other = (SetCompositeKey) obj;
		if (questionids == null) {
			if (other.questionids != null)
				return false;
		} else if (!questionids.equals(other.questionids))
			return false;
		if (setid != other.setid)
			return false;
		return true;
	}
	
	
	
	
	
}