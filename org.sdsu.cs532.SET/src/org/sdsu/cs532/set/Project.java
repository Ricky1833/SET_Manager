package org.sdsu.cs532.set;

import java.util.Date;

public class Project {

	private int id;
	private String title;
	private int managerID;
	private Date startDate;
	private Date endDate;
	
	public Project() {
		
	}
	
	public Project(int id, String title, int mID, Date start, Date end) {
		this.id = id;
		this.title = title;
		this.managerID = mID;
		this.startDate = start;
		this.endDate = end;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int i) {
		this.id = i;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String t) {
		this.title = t;
	}
	
	public int getManagerID() {
		return this.managerID;
	}
	
	public void setManagerID(int i) {
		this.managerID = i;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(Date start) {
		this.startDate = start;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date end) {
		this.endDate = end;
	}
}
