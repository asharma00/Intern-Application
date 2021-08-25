

package com.intern.model;

import java.io.Serializable;




//composite classes must be serializable
public class ResponsesCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long uid;
	
	private Integer qid;
	

	
	public ResponsesCompositeKey() {
	}

	public ResponsesCompositeKey(Long uid, Integer qid) {
		super();
		this.uid = uid;
		this.qid = qid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + (int) (qid ^ (qid >>> 32));
		return result;
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        ResponsesCompositeKey responsesCompositeKey = (ResponsesCompositeKey) o;
	        return uid.equals(responsesCompositeKey.uid) &&
	                qid.equals(responsesCompositeKey.qid);
	    }
	
	
	
	
}

