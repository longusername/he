package servlet;

import java.io.IOException;
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
import entity.Department;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.Pagination;

public class DepartmentServlet extends HttpServlet {
	public static final String path = "WEB-INF/department/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			} else if ("showUpdateBatch1".equals(type)) {
				showUpdateBatch1(request, response);
			} else if ("updateBatch1".equals(type)) {
				updateBatch1(request, response);
			} else if ("showUpdateBatch2".equals(type)) {
				showUpdateBatch2(request, response);
			} else if ("updateBatch2".equals(type)) {
				updateBatch2(request, response);
			} else if ("updateBatch3".equals(type)) {
				updateBatch3(request, response);
			}
		} catch (

		UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	// List<Department> list = new ArrayList<Department>();
	// DepartmentDao depDao = new DepartmentDao();
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = depDao.searchCount();
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	// list = depDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
	// request.setAttribute("p", p);
	// request.setAttribute("depList", list);
	// request.getRequestDispatcher("WEB-INF/deps.jsp").forward(request, response);
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
			String name = request.getParameter("name");
			int empCount = -1;
			if (request.getParameter("empCount") != null && !"".equals(request.getParameter("empCount"))) {
				empCount = Integer.parseInt(request.getParameter("empCount"));
			}
			Department condition = new Department();
			condition.setName(name);
			condition.setEmpCount(empCount);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> list = new ArrayList<Department>();
			List<Department> listOfDepartment = new ArrayList<Department>();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = depDao.searchCount(condition);
			if (count == 0) {
				count = 1;
			}
			Pagination p = new Pagination(ye, count, Constant.DEP_NUM_IN_PAGE, Constant.DEP_NUM_OF_PAGE);
			list = depDao.search(condition, p.getBegin(), Constant.DEP_NUM_IN_PAGE);
			listOfDepartment = depDao.search();
			request.setAttribute("c", condition);
			request.setAttribute("p", p);
			request.setAttribute("depList", list);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "dep.jsp").forward(request, response);
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
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			String name = request.getParameter("name");
			dep.setName(name);
			depDao.add(dep);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path + "depAdd.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			dep.setId(id);
			dep.setName(name);
			depDao.update(dep);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(id);
			request.setAttribute("dep", dep);
			request.getRequestDispatcher(path + "depUpdate.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			DepartmentDao depDao = new DepartmentDao();
			List<Department> list = new ArrayList<Department>();
			list = depDao.search(ids);
			request.setAttribute("dep", list.get(0));
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "depUpdateBatch1.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			String name = request.getParameter("name");
			dep.setName(name);
			depDao.updateBatch1(ids, dep);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			DepartmentDao depDao = new DepartmentDao();
			List<Department> list = new ArrayList<Department>();
			list = depDao.search(ids);
			request.setAttribute("list", list);
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "depUpdateBatch2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> list = new ArrayList<Department>();
			String deps = request.getParameter("deps");
			String[] array = deps.split(";");
			for (int i = 0; i < array.length; i++) {
				Department dep = new Department();
				String[] tdep = array[i].split(",");
				dep.setId(Integer.parseInt(tdep[0]));
				dep.setName(tdep[1]);
				list.add(dep);
			}
			depDao.updateBatch2(list);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch3(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> list = new ArrayList<Department>();
			String deps = request.getParameter("deps");
			JSONArray jsonArray = JSONArray.fromObject(deps);
			list = (List<Department>) jsonArray.toCollection(jsonArray, Department.class);
			depDao.updateBatch2(list);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			int id = Integer.parseInt(request.getParameter("id"));
			depDao.delete(id);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			String ids = request.getParameter("ids");
			depDao.deleteBatch(ids);
			response.sendRedirect("depView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
