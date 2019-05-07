package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DepartmentDao;
import dao.ProjectDao;
import dao.ScoreDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.Constant;
import util.Pagination;

public class ScoreServlet extends HttpServlet {
	public static final String path = "WEB-INF/score/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("save".equals(type)) {
				save(request, response);
			}
		} catch (

		UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	// List<Employee> list = new ArrayList<Employee>();
	// EmployeeDao empDao = new EmployeeDao();
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = empDao.searchCount();
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	// list = empDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
	// request.setAttribute("p", p);
	// request.setAttribute("empList", list);
	// request.getRequestDispatcher(path+"emps.jsp").forward(request, response);
	// } catch (ServletException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			String empName = request.getParameter("empName");
			Integer depId = null;
			if (request.getParameter("depId") != null && !"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			Integer proId = null;
			if (request.getParameter("proId") != null && !"".equals(request.getParameter("proId"))) {
				proId = Integer.parseInt(request.getParameter("proId"));
			}

			Integer value = null;
			if (request.getParameter("value") != null && !"".equals(request.getParameter("value"))) {
				value = Integer.parseInt(request.getParameter("value"));
			}
			String grade = request.getParameter("grade");
			Employee emp = new Employee();
			emp.setName(empName);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);
			Project pro = new Project();
			pro.setId(proId);
			Score condition = new Score();
			condition.setValue(value);
			condition.setGrade(grade);
			condition.setEmp(emp);
			condition.setPro(pro);
			ScoreDao scoDao = new ScoreDao();
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = depDao.search();
			ProjectDao proDao = new ProjectDao();
			List<Project> listOfProject = proDao.search();
			List<Score> list = new ArrayList<Score>();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = scoDao.searchCount(condition);
			if (count == 0) {
				count = 1;
			}
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			list = scoDao.searchByCondition(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			request.setAttribute("c", condition);
			request.setAttribute("p", p);
			request.setAttribute("scoreList", list);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.setAttribute("listOfProject", listOfProject);
			request.getRequestDispatcher(path + "score.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save(HttpServletRequest request, HttpServletResponse response) {
		try {
			ScoreDao scoDao = new ScoreDao();
			int empId = Integer.parseInt(request.getParameter("empId"));
			Integer depId = Integer.parseInt(request.getParameter("depId"));
			Integer proId = Integer.parseInt(request.getParameter("proId"));
			Integer value = Integer.parseInt(request.getParameter("value"));
			String grade;
			Score sco = new Score();
			Employee emp = new Employee();
			Project pro = new Project();
			emp.setId(empId);
			pro.setId(proId);
			sco.setValue(value);
			sco.setEmp(emp);
			sco.setPro(pro);
			scoDao.save(sco);
			grade = scoDao.searchGrade(sco);
			PrintWriter out = response.getWriter();
			out.print(grade);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
