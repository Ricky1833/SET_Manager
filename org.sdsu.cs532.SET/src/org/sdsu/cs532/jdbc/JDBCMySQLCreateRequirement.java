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

public class JDBCMySQLCreateRequirement {
	
	Connection conn;
	ResultSet resultSet;
	Requirement requirement;
	String selection;
	
	public JDBCMySQLCreateRequirement() {
		try {
			this.resultSet = this.makeQuery();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public JDBCMySQLCreateRequirement(Requirement req) {
		this.requirement = req;
		try {
			this.resultSet = this.makeQuery();
		} catch (NumberFormatException e) {
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
		String query = makeStatement();//"INSERT INTO requirements VALUES (
				//'Puffball','Diane','hamster','f','1999-03-30',NULL)";
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
	
	public Requirement getRequirement() {
		return this.requirement;
	}
	
	public String makeStatement() {
		String string = "INSERT INTO requirments VALUES (";
		string.concat(Integer.toString(this.requirement.getIdNum()));
		string.concat("\", ");
		string.concat(Integer.toString(this.requirement.getProjectParentId()));
		string.concat("\", ");
		string.concat(this.requirement.getID());
		string.concat("\", ");
		string.concat(this.requirement.getDesc());
		string.concat("\", ");
		string.concat(Integer.toString(this.requirement.getSourceId()));
		string.concat("\", ");
		string.concat(Integer.toString(this.requirement.getFaSubsystemId()));
		string.concat("\", ");
		string.concat(this.requirement.getCreated());
		string.concat("\", ");
		string.concat(this.requirement.getBaselined());
		string.concat("\", ");
		string.concat(Integer.toString(this.requirement.getStatusId()));
		string.concat("\", ");
		string.concat(Integer.toString(this.requirement.getAllocationId()));
		string.concat("\"; ");
		System.out.println(string);
		return string;
	}
}
