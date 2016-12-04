package com.pactera.pds.u2.commerce.utils;

import org.springframework.stereotype.Component;





@Component("schedulee")
public class Schedulee {
    
    public long deadtime;

	public long getDeadtime() {
		return deadtime;
	}

	public void setDeadtime(long deadtime) {
		this.deadtime = deadtime;
	}
}

