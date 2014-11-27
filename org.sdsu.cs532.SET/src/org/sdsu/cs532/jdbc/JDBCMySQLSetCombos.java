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

//import com.theopentutorials.jdbc.db.DbUtil;
import org.sdsu.cs532.set.Employee;

public class JDBCMySQLSetCombos {

	Connection conn;
	ResultSet functionalResultSet;
	ResultSet allocationResultSet;
	ResultSet statusResultSet;
	List<String> functional;
	List<String> allocation;
	List<String> status;
	List<String> queries;

	public JDBCMySQLSetCombos() {
		functional = new ArrayList<String>();
		allocation = new ArrayList<String>();
		status = new ArrayList<String>();
		queries = new ArrayList<String>();
		queries.add("SELECT ID, NAME FROM SET_SCHEMA.functional_area");
		queries.add("SELECT ID, ALLOCATION_TYPE AS NAME FROM SET_SCHEMA.ALLOCATION");
		queries.add("SELECT ID, STATUS_TYPE AS NAME FROM SET_SCHEMA.STATUS");
		try {
			this.functionalResultSet = this.makeQuery(queries.get(0));
			this.allocationResultSet = this.makeQuery(queries.get(1));
			this.statusResultSet = this.makeQuery(queries.get(2));
			this.assembleAllocationList();
			this.assembleFunctionalList();
			this.assembleStatusList();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public List<String> getFunctionalList() {
		return this.functional;
	}
	
	public List<String> getAllocationList() {
		return this.allocation;
	}
	
	public List<String> getStatusList() {
		return this.status;
	}
	
	public void assembleFunctionalList() {
		try {
			while (this.functionalResultSet.next()) {
				this.functional.add(this.functionalResultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assembleAllocationList() {
		try {
			while (this.allocationResultSet.next()) {
				this.allocation.add(this.allocationResultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assembleStatusList() {
		try {
			while (this.statusResultSet.next()) {
				this.status.add(this.statusResultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet makeQuery(String query) {
		ResultSet rs = null;
		conn = null;
		Statement statement = null;
		
		try {
			conn = JDBCMySQLConnection.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(statement.toString());
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet makeQuery() {
		ResultSet rs = null;
		conn = null;
		Statement statement = null;
		String query = "SELECT * FROM project";

		try {
			conn = JDBCMySQLConnection.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}

