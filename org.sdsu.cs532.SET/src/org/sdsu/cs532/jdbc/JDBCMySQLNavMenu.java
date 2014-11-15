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

public class JDBCMySQLNavMenu {

	Connection conn;
	ResultSet resultSet;
	List<Project> projList;

	public JDBCMySQLNavMenu() {
		int projectId;
		try {
			this.resultSet = this.makeQuery();
			this.projList = new ArrayList<Project>();
//			assembleProjects();
//			for (Project project : projList) {
//				System.out.println(project.getID() + project.getTitle()
//						+ project.getManagerID() + project.getStartDate()
//						+ project.getEndDate() + "\n");
//			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public void assembleProjects() {
		try {
			while (this.resultSet.next()) {
				Project pro = new Project();
				pro.setID(this.resultSet.getInt("ID"));
				pro.setTitle(this.resultSet.getString("title"));
				pro.setManagerID(this.resultSet.getInt("manager_ID"));
				pro.setStartDate(this.resultSet.getDate("start_date"));
				pro.setEndDate(this.resultSet.getDate("end_date"));

				this.projList.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public List<Project> getListOfProjects() {
		return this.projList;
	}
}
