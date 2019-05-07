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
import dao.Project2DepartmentDao;
import dao.ProjectDao;
import entity.Department;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.Pagination;

public class Project2DepartmentServlet extends HttpServlet {
	public static final String path = "WEB-INF/project2department/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("m2".equals(type)) {
				search2(request, response);
			} else if ("add2".equals(type)) {
				add2(request, response);
			} else if ("delete2".equals(type)) {
				delete2(request, response);
			} else if ("m3".equals(type)) {
				search3(request, response);
			} else if ("addBatch".equals(type)) {
				addBatch(request, response);
			} else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			} else if ("m4".equals(type)) {
				search4(request, response);
			}
		} catch (

		UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	// List<Project> list = new ArrayList<Project>();
	// ProjectDao proDao = new ProjectDao();
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = proDao.searchCount();
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	// list = proDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
	// request.setAttribute("p", p);
	// request.setAttribute("proList", list);
	// request.getRequestDispatcher("WEB-INF/pros.jsp").forward(request, response);
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
			List<Project> list = new ArrayList<Project>();
			List<Project> listOfNoProject = new ArrayList<Project>();
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			int depId = Integer.parseInt(request.getParameter("depId"));
			dep = depDao.search(depId);
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = pro2depDao.searchCount(depId);
			if (count == 0) {
				count = 1;
			}
			Pagination p = new Pagination(ye, count, Constant.PRO_NUM_IN_PAGE, Constant.PRO_NUM_OF_PAGE);
			list = pro2depDao.search(depId, p.getBegin(), Constant.PRO_NUM_IN_PAGE);
			listOfNoProject = pro2depDao.searchByNotDepartment(depId);
			request.setAttribute("p", p);
			request.setAttribute("dep", dep);
			request.setAttribute("proList", list);
			request.setAttribute("listOfNoProject", listOfNoProject);
			request.getRequestDispatcher(path + "pro2dep.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void search2(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Project> list = new ArrayList<Project>();
			List<Project> listOfNoProject = new ArrayList<Project>();
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			int depId = Integer.parseInt(request.getParameter("depId"));
			dep = depDao.search(depId);
			list = pro2depDao.search(depId);
			listOfNoProject = pro2depDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("proList", list);
			request.setAttribute("listOfNoProject", listOfNoProject);
			request.getRequestDispatcher(path + "pro2dep2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search3(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Project> list = new ArrayList<Project>();
			List<Project> listOfNoProject = new ArrayList<Project>();
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			int depId = Integer.parseInt(request.getParameter("depId"));
			dep = depDao.search(depId);
			list = pro2depDao.search(depId);
			listOfNoProject = pro2depDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("proList", list);
			request.setAttribute("listOfNoProject", listOfNoProject);
			request.getRequestDispatcher(path + "pro2dep3.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void search4(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Project> list = new ArrayList<Project>();
			List<Project> listOfNoProject = new ArrayList<Project>();
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			int depId = Integer.parseInt(request.getParameter("depId"));
			dep = depDao.search(depId);
			list = pro2depDao.search(depId);
			listOfNoProject = pro2depDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("proList", list);
			request.setAttribute("listOfNoProject", listOfNoProject);
			request.getRequestDispatcher(path + "pro2dep4.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			pro2depDao.add(depId, proId);
			response.sendRedirect("pro2DepView?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			String proIds = request.getParameter("proIds");
			String[] temp = proIds.split(",");
			boolean flag = false;
			for (int i = 0; i < temp.length; i++) {
				int proId = Integer.parseInt(temp[i]);
				flag = pro2depDao.add(depId, proId);
			}
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			boolean flag = pro2depDao.add(depId, proId);
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			pro2depDao.delete(depId, proId);
			response.sendRedirect("pro2DepView?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete2(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			boolean flag = pro2depDao.delete(depId, proId);
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project2DepartmentDao pro2depDao = new Project2DepartmentDao();
			int depId = Integer.parseInt(request.getParameter("depId"));
			String proIds = request.getParameter("proIds");
			String[] temp = proIds.split(",");
			boolean flag = false;
			for (int i = 0; i < temp.length; i++) {
				int proId = Integer.parseInt(temp[i]);
				flag = pro2depDao.delete(depId, proId);
			}
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
