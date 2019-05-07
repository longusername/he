package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;

public class UserDao {

	public boolean search(User user) {
		boolean flag = false;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "select * from user where username=? and password =?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			ResultSet rs = pstat.executeQuery();
			if (rs.next()) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean searchUsername(User user) {
		boolean flag = false;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "select * from user where username=? ";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			ResultSet rs = pstat.executeQuery();
			if (rs.next()) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public String searchDate(User user) {
		Connection conn = null;
		String date = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "select date from user where username=? ";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			ResultSet rs = pstat.executeQuery();
			if (rs.next()) {
				date = rs.getString(1);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	public boolean add(User user) {
		boolean flag = false;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "insert into user(username,password,date) value(?,?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			pstat.setString(3, user.getDate());
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
}
