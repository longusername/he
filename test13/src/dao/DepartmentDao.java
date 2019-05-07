package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;

public class DepartmentDao {

	public List<Department> search() {
		Connection conn = null;
		List<Department> list = new ArrayList<Department>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from department";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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
		return list;
	}

	public int searchCount() {
		Connection conn = null;
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select count(*) from department";
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public List<Department> search(int begin, int size) {
		Connection conn = null;
		List<Department> list = new ArrayList<Department>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from department limit " + begin + "," + size;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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
		return list;
	}

	public int searchCount(Department condition) {
		List<Department> list = new ArrayList<Department>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			if (condition.getEmpCount() != -1) {
				where += " and emp_count=" + condition.getEmpCount();
			}
			String sql = "select count(*) from department " + where;
			rs = stat.executeQuery(sql);
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

	public void add(Department dep) {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "insert into department (name) values (?)";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			int rs = pstat.executeUpdate();

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
	}

	public Department search(int id) {
		Connection conn = null;
		Department dep = new Department();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from department where id=" + id;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
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
		return dep;
	}

	public List<Department> search(Department condition, int begin, int size) {
		List<Department> list = new ArrayList<Department>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			if (condition.getEmpCount() != -1) {
				where += " and emp_count=" + condition.getEmpCount();
			}
			String sql = "select * from department " + where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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

	public List<Department> search(String ids) {
		Connection conn = null;
		List<Department> list = new ArrayList<Department>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from department where id in (" + ids + ")";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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
		return list;
	}

	public boolean update(Department dep) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "update department set name=? where id=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			pstat.setInt(2, dep.getId());
			rs = pstat.executeUpdate();

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
		return rs > 0;
	}

	public boolean updateBatch1(String ids, Department dep) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "update department set name=? where id in (" + ids + ")";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			rs = pstat.executeUpdate();

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
		return rs > 0;
	}

	public boolean updateBatch2(List<Department> list) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");

			for (int i = 0; i < list.size(); i++) {
				String sql = "update department set name=? where id=?";
				PreparedStatement pstat = conn.prepareStatement(sql);
				pstat.setString(1, list.get(i).getName());
				pstat.setInt(2, list.get(i).getId());
				rs = pstat.executeUpdate();
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
		return rs > 0;
	}

	public boolean delete(int id) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "delete from department where id=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			pstat.close();
			sql = "update employee set d_id=null where d_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.close();
			sql = "delete from r_dep_pro where d_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
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
		return rs > 0;
	}

	public boolean deleteBatch(String ids) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "delete from department where id in (" + ids + ")";
			Statement stat = conn.createStatement();
			rs = stat.executeUpdate(sql);
			stat.close();
			sql = "update employee set d_id=null where d_id in (" + ids + ")";
			stat = conn.createStatement();
			rs = stat.executeUpdate(sql);
			stat.close();
			sql = "delete from r_dep_pro  where d_id in (" + ids + ")";
			stat = conn.createStatement();
			rs = stat.executeUpdate(sql);

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
		return rs > 0;
	}

}
