package com.intern.model;

import java.util.ArrayList;

public class CustomRoaster {

	ArrayList<Object>  dayList;
	ArrayList<Object> internList;
	public CustomRoaster()
	{
		
	}
	public CustomRoaster(ArrayList<Object> dayList, ArrayList<Object> internList) {
		super();
		this.dayList = dayList;
		this.internList = internList;
	}
	public ArrayList<Object> getDayList() {
		return dayList;
	}
	public void setDayList(ArrayList<Object> dayList) {
		this.dayList = dayList;
	}
	public ArrayList<Object> getInternList() {
		return internList;
	}
	public void setInternList(ArrayList<Object> internList) {
		this.internList = internList;
	}
	
	
}
