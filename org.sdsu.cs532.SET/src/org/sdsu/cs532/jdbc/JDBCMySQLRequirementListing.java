package org.sdsu.cs532.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sdsu.cs532.set.Project;
import org.sdsu.cs532.set.Requirement;

public class JDBCMySQLRequirementListing {
	
	Connection conn;
	ResultSet resultSet;
	List<Requirement> reqList;
	String selection;
	
	public JDBCMySQLRequirementListing() {
		try {
			this.resultSet = this.makeQuery();
			this.reqList = new ArrayList<Requirement>();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public JDBCMySQLRequirementListing(String navMenuSelection) {
		this.selection = navMenuSelection;
		try {
			this.resultSet = this.makeSelectedQuery(selection);
			this.reqList = new ArrayList<Requirement>();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void assembleRequirements() {
		try {
			while (this.resultSet.next()) {
				Requirement req = new Requirement();
				req.setIdNum(this.resultSet.getInt("id"));
				req.setProjectParentId(this.resultSet.getInt("project_parent_id"));
				req.setID(this.resultSet.getString("requirement_id"));
				req.setDesc(this.resultSet.getString("requirement_text"));
				req.setSourceId(this.resultSet.getInt("source"));
				req.setFaSubsystemId(this.resultSet.getInt("fa_subsystem_id"));
				req.setDateEntered(this.resultSet.getDate("date_entered"));
				req.setDateBLined(this.resultSet.getDate("date_baselined"));
				req.setStatusId(this.resultSet.getInt("status_id"));
				req.setAllocationId(this.resultSet.getInt("allocation_id"));
				
				this.reqList.add(req);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public ResultSet makeQuery() {
		ResultSet rs = null;
		conn = null;
		Statement statement = null;
		String query = "SELECT * FROM requirements";
		try {
			conn = JDBCMySQLConnection.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet makeSelectedQuery(String selection) {
		ResultSet rs = null;
		conn = null;
		Statement statement = null;
		String query = "SELECT * FROM requirements WHERE project_parent_id = " + selection + ";";
		try {
			conn = JDBCMySQLConnection.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public List<Requirement> getListOfRequirements() {
		return this.reqList;
	}
}
