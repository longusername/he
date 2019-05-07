package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;

public class ScoreDao {
	public List<Score> search() {
		List<Score> list = new ArrayList<Score>();
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
			rs = stat.executeQuery(
					"select s.id,e.name as e_name,d_name,v.p_id,p_name,value,grade from employee as e left join v_dep_pro as v on e.d_id=v.d_id\r\n"
							+ "LEFT JOIN score as s on v.p_id=s.p_id and e.id=s.e_id");
			// 6对结果集进行处理
			while (rs.next()) {
				Score sco = new Score();
				Employee emp = new Employee();
				Project pro = new Project();
				Department dep = new Department();
				sco.setId(rs.getInt("s.id"));
				emp.setName(rs.getString("e_name"));
				dep.setName(rs.getString("d_name"));
				pro.setId(rs.getInt("v.p_id"));
				pro.setName(rs.getString("p_name"));
				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setValue((Integer) rs.getObject("value"));
				sco.setGrade(rs.getString("grade"));
				list.add(sco);
			}
			// 7关闭

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

	public String searchGrade(Score sco) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String grade = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4建立statement sql语句执行器
			stat = conn.createStatement();
			// 5执行sql语句并得到结果
			rs = stat.executeQuery(
					"select grade from score where e_id=" + sco.getEmp().getId() + " and p_id=" + sco.getPro().getId());
			// 6对结果集进行处理
			if (rs.next()) {
				grade = rs.getString(1);
			}
			// 7关闭

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grade;
	}

	public List<Score> searchByCondition(Score condition) {
		List<Score> list = new ArrayList<Score>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (!condition.getEmp().getName().equals("")) {
				where += " and e.name='" + condition.getEmp().getName() + "'";
			}
			if (!condition.getEmp().getDep().getName().equals("")) {
				where += " and d_name='" + condition.getEmp().getDep().getName() + "'";
			}
			if (!condition.getPro().getName().equals("")) {
				where += " and p_name='" + condition.getPro().getName() + "'";
			}
			if (condition.getValue() != -1) {
				where += " and value=" + condition.getValue();
			}
			if (!condition.getGrade().equals("")) {
				where += " and grade='" + condition.getGrade() + "'";
			}
			String sql = "select e.id,s.id,e.name as e_name,d_name,p_name,value,grade from employee as e left join v_dep_pro as v on e.d_id=v.d_id "
					+ " LEFT JOIN score as s on v.p_id=s.p_id and e.id=s.e_id " + where;
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			while (rs.next()) {
				Score sco = new Score();
				Employee emp = new Employee();
				Project pro = new Project();
				Department dep = new Department();
				sco.setId(rs.getInt("s.id"));
				emp.setId(rs.getInt("e.id"));
				emp.setName(rs.getString("e_name"));
				dep.setName(rs.getString("d_name"));
				pro.setName(rs.getString("p_name"));
				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setValue((Integer) rs.getObject("value"));
				sco.setGrade(rs.getString("grade"));
				list.add(sco);
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

	public List<Score> searchByCondition(Score condition, int begin, int size) {
		List<Score> list = new ArrayList<Score>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (condition.getEmp().getName() != null && !condition.getEmp().getName().equals("")) {
				where += " and e.name='" + condition.getEmp().getName() + "'";
			}
			if (condition.getEmp().getDep().getId() != null && condition.getEmp().getDep().getId() != -1) {
				where += " and e.d_id=" + condition.getEmp().getDep().getId();
			}
			if (condition.getPro().getId() != null && condition.getPro().getId() != -1) {
				where += " and v.p_id=" + condition.getPro().getId();
			}
			if (condition.getValue() != null && condition.getValue() != -1) {
				where += " and value=" + condition.getValue();
			}
			if (condition.getGrade() != null && !condition.getGrade().equals("")) {
				where += " and grade='" + condition.getGrade() + "'";
			}
			String sql = "select  e.id,s.id,e.name as e_name,e.d_id,d_name,v.p_id,p_name,value,grade from employee as e left join v_dep_pro as v on e.d_id=v.d_id "
					+ " LEFT JOIN score as s on v.p_id=s.p_id and e.id=s.e_id " + where + " limit " + begin + ","
					+ size;
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			while (rs.next()) {
				Score sco = new Score();
				Employee emp = new Employee();
				Project pro = new Project();
				Department dep = new Department();
				sco.setId(rs.getInt("s.id"));
				emp.setName(rs.getString("e_name"));
				emp.setId(rs.getInt("e.id"));
				dep.setName(rs.getString("d_name"));
				dep.setId(rs.getInt("e.d_id"));
				pro.setId(rs.getInt("v.p_id"));
				pro.setName(rs.getString("p_name"));
				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setValue((Integer) rs.getObject("value"));
				sco.setGrade(rs.getString("grade"));
				list.add(sco);
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

	public int searchCount(Score condition) {
		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (condition.getEmp().getName() != null && !condition.getEmp().getName().equals("")) {
				where += " and e.name='" + condition.getEmp().getName() + "'";
			}
			if (condition.getEmp().getDep().getId() != null && condition.getEmp().getDep().getId() != -1) {
				where += " and e.d_id=" + condition.getEmp().getDep().getId();
			}
			if (condition.getPro().getId() != null && condition.getPro().getId() != -1) {
				where += " and v.p_id=" + condition.getPro().getId();
			}
			if (condition.getValue() != null && condition.getValue() != -1) {
				where += " and value=" + condition.getValue();
			}
			if (condition.getGrade() != null && !condition.getGrade().equals("")) {
				where += " and grade='" + condition.getGrade() + "'";
			}
			String sql = "select count(*) from employee as e left join v_dep_pro as v on e.d_id=v.d_id "
					+ " LEFT JOIN score as s on v.p_id=s.p_id and e.id=s.e_id " + where;
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

	public boolean count(Score sco) {
		boolean flag = false;
		int count = -1;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			stat = conn.createStatement();
			String where = "where 1=1 ";
			if (sco.getEmp().getId() != -1) {
				where += " and e.id=" + sco.getEmp().getId();
			}
			if (sco.getPro().getId() != null && sco.getPro().getId() != -1) {
				where += " and v.p_id=" + sco.getPro().getId();
			}
			String sql = "select count(value) from employee as e left join v_dep_pro as v on e.d_id=v.d_id "
					+ " LEFT JOIN score as s on v.p_id=s.p_id and e.id=s.e_id " + where;
			rs = stat.executeQuery(sql);
			// 6对结果集进行处理
			if (rs.next()) {
				count = rs.getInt(1);
			}
			// 7关闭
			if (count == 1) {
				flag = true;
			} else if (count == 0) {
				flag = false;
			}
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
		return flag;
	}

	public boolean add(Score sco) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "insert into score(e_id,p_id,value) values (?,?,?) ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sco.getEmp().getId());
			pstat.setInt(2, sco.getPro().getId());
			pstat.setInt(3, sco.getValue());
			rs = pstat.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs > 0;
	}

	public boolean update(Score sco) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			String sql = "update score set value=? where e_id=? and p_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sco.getValue());
			pstat.setInt(2, sco.getEmp().getId());
			pstat.setInt(3, sco.getPro().getId());
			rs = pstat.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs > 0;
	}

	public boolean save(Score sco) {
		boolean flag = count(sco);
		boolean flag1 = false;
		if (flag == true) {
			flag1 = update(sco);
		} else {
			flag1 = add(sco);
		}
		return flag1;
	}

}
