package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Project;

public class Project2DepartmentDao {

	public List<Project> search(int depId, int begin, int size) {
		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId + " limit " + begin + "," + size);
			// 6对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				list.add(pro);
			}
			// 7关闭

			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Project> search(int depId) {
		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId);
			// 6对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				list.add(pro);
			}
			// 7关闭

			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int searchCount(int depId) {
		int count = -1;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			rs = stat.executeQuery("select count(*) from v_dep_pro where d_id=" + depId);
			// 6对结果集进行处理
			if (rs.next()) {
				count = rs.getInt(1);
			}
			// 7关闭

			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public List<Project> searchByNotDepartment(int depId) {
		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			String sql = "select * from project where id not in (select p_id from v_dep_pro where d_id=" + depId + ")";
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				list.add(pro);
			}
			// 7关闭

			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean add(int depId, int proId) {
		Connection conn = null;
		Statement stat = null;
		int rs = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			String sql = "insert into r_dep_pro(d_id,p_id) values (" + depId + "," + proId + ")";
			rs = stat.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		return rs > 0;
	}

	public boolean delete(int depId, int proId) {
		Connection conn = null;
		Statement stat = null;
		int rs = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			String sql = "delete from r_dep_pro where d_id=" + depId + " and p_id=" + proId;
			rs = stat.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		return rs > 0;
	}
}
