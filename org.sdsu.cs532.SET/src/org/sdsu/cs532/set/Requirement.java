package org.sdsu.cs532.set;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.DateTime;

public class Requirement {
	//from the ui
	private String title;
	private String reqID;
	private String functionalArea;
	private String source;
	private String dateCreated;
	private String dateBaselined;
	private String allocation;
	private String status;
	private String components;
	private String module;
	private String tests;
	//from the db
	private int id;
	private int projectParentId;
	private String reqDesc;
	private int sourceId;
	private int faSubsystemId;
	private Date dateEntered;
	private Date dateBLined;
	private int statusId;
	private int allocationId;
	
	public Requirement(String name, String reqID, String functionalArea,
			String dateCreated, String dateBaselined, String allocation,
			String status, String components, String source, 
			String module, String tests) {
		
		this.title = name;
		this.reqID = reqID;
		this.functionalArea = functionalArea;
		this.dateCreated = dateCreated;
		this.dateBaselined = dateBaselined;
		this.allocation = allocation;
		this.status = status;
		this.components = components;
		this.source = source;
		this.module = module;
		this.tests = tests;
	}
	
	public Requirement() {
		
	}
	//Getters and Setters below
	public String getName() {
		return this.title;
	}
	
	public void setName(String n) {
		this.title = n;
	}
	
	public String getID() {
		return this.reqID;
	}
	
	public void setID(String id) {
		this.reqID = id;
	}
	
	public int getIdNum() {
		return this.id;
	}
	
	public void setIdNum(int i) {
		this.id = i;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setSource(String s) {
		this.source = s;
	}
	
	public int getProjectParentId() {
		return this.projectParentId;
	}
	
	public void setProjectParentId(int i) {
		this.projectParentId = i;
	}
	
	public String getDesc() {
		return this.reqDesc;
	}
	
	public void setDesc(String s) {
		this.reqDesc = s;
	}
	
	public int getSourceId() {
		return this.sourceId;
	}
	
	public void setSourceId(int i) {
		this.sourceId = i;
	}
	
	public int getFaSubsystemId() {
		return this.faSubsystemId;
	}
	
	public void setFaSubsystemId(int i) {
		this.faSubsystemId = i;
	}
	
	public Date getDateEntered() {
		return this.dateEntered;
	}
	
	public void setDateEntered(Date d) {
		this.dateEntered = d;
		this.dateCreated = d.toString();
	}
	
	public Date getDateBLined() {
		return this.dateBLined;
	}
	
	public void setDateBLined(Date d) {
		this.dateBLined = d;
		if (this.dateBLined != null) {
			this.dateBaselined = this.dateBLined.toString();
		} else {
			this.dateBaselined = "Not set";
		}
	}
	
	public int getStatusId() {
		return this.statusId;
	}
	
	public void setStatusId(int i) {
		this.statusId = i;
	}
	
	public int getAllocationId() {
		return this.allocationId;
	}
	
	public void setAllocationId(int i) {
		this.allocationId = i;
	}
	
	public String getFunctionalArea() {
		return this.functionalArea;
	}
	
	public void setFunctionalArea(String fArea) {
		this.functionalArea = fArea;
	}
	
	public String getCreated() {
		return this.dateCreated;
	}
	
	public void setCreated(String d) {
		this.dateCreated = d;
	}
	
	public String getBaselined() {
		if (this.dateBaselined != null) { 
			return this.dateBaselined;
		} else {
			return "not set";
		}
	}
	
	public void setBaselined(String b) {
		this.dateBaselined = b;
	}
	
	public String getAllocation() {
		return this.allocation;
	}
	
	public void setAllocation(String al) {
		this.allocation = al;
	}
	
	public String getStaus() {
		return this.status;
	}
	
	public void setStatus(String s) {
		this.status = s;
	}
	
	public String getComponents() {
		return this.components;
	}
	
	public void setComponents(String comp) {
		this.components = comp;
	}
	
	public String getModule() {
		return this.module;
	}
	
	public void setModule(String mod) {
		this.module = mod;
	}
	
	public String getTests() {
		return this.tests;
	}
	
	public void setTests(String test) {
		this.tests = test;
	}
}
