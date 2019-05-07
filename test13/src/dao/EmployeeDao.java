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
import entity.Employee;

public class EmployeeDao {

	public List<Employee> search() {
		Connection conn = null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on d_id=d.id";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
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
			String sql = "select count(*) from employee";
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

	public List<Employee> search(int begin, int size) {
		Connection conn = null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from employee limit " + begin + "," + size;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
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

	public int searchCount(Employee condition) {
		List<Employee> list = new ArrayList<Employee>();
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
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}
			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			if (condition.getDep().getId() != null && condition.getDep().getId() != -1) {
				where += " and d_id='" + condition.getDep().getId() + "'";
			}
			if (condition.getPicture() != null && !condition.getPicture().equals("")) {
				where += " and e.picture='" + condition.getPicture() + "'";
			}
			String sql = "select count(*) from employee as e left join department as d on e.d_id=d.id " + where;
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

	public void add(Employee emp) {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "insert into employee (name,sex,age,d_id,picture) values (?,?,?,?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setString(5, emp.getPicture());
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

	public Employee search(int id) {
		Connection conn = null;
		Employee emp = new Employee();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from employee where id=" + id;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				emp.setDep(dep);
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
		return emp;
	}

	public List<Employee> search(Employee condition, int begin, int size) {
		List<Employee> list = new ArrayList<Employee>();
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
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}
			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			if (condition.getDep().getId() != null && condition.getDep().getId() != -1) {
				where += " and d_id='" + condition.getDep().getId() + "'";
			}
			if (condition.getPicture() != null && !condition.getPicture().equals("")) {
				where += " and e.picture='" + condition.getPicture() + "'";
			}
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on e.d_id=d.id "
					+ where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setPicture(rs.getString("picture"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
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

	public List<Employee> search(String ids) {
		Connection conn = null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			Statement stat = conn.createStatement();
			String sql = "select * from employee where id in (" + ids + ")";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				emp.setDep(dep);
				list.add(emp);
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

	public boolean update(Employee emp) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setInt(5, emp.getId());
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

	public boolean updateBatch1(String ids, Employee emp) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id in (" + ids + ")";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
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

	public boolean updateBatch2(List<Employee> list) {
		int rs = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");

			for (int i = 0; i < list.size(); i++) {
				String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
				PreparedStatement pstat = conn.prepareStatement(sql);
				pstat.setString(1, list.get(i).getName());
				pstat.setString(2, list.get(i).getSex());
				pstat.setInt(3, list.get(i).getAge());
				pstat.setObject(4, list.get(i).getDep().getId());
				pstat.setInt(5, list.get(i).getId());
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
			String sql = "delete from employee where id=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
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
			String sql = "delete from employee where id in (" + ids + ")";
			Statement stat = conn.createStatement();
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
