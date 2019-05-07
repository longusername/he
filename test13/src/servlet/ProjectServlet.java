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

import dao.ProjectDao;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.Pagination;

public class ProjectServlet extends HttpServlet {
	public static final String path = "WEB-INF/project/";

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
			String name = request.getParameter("name");
			int proCount = -1;
			if (request.getParameter("proCount") != null && !"".equals(request.getParameter("proCount"))) {
				proCount = Integer.parseInt(request.getParameter("proCount"));
			}
			Project condition = new Project();
			condition.setName(name);
			ProjectDao proDao = new ProjectDao();
			List<Project> list = new ArrayList<Project>();
			List<Project> listOfProject = new ArrayList<Project>();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = proDao.searchCount(condition);
			if (count == 0) {
				count = 1;
			}
			Pagination p = new Pagination(ye, count, Constant.PRO_NUM_IN_PAGE, Constant.PRO_NUM_OF_PAGE);
			list = proDao.search(condition, p.getBegin(), Constant.PRO_NUM_IN_PAGE);
			listOfProject = proDao.search();
			request.setAttribute("c", condition);
			request.setAttribute("p", p);
			request.setAttribute("proList", list);
			request.setAttribute("listOfProject", listOfProject);
			request.getRequestDispatcher(path + "pro.jsp").forward(request, response);
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
			ProjectDao proDao = new ProjectDao();
			Project pro = new Project();
			String name = request.getParameter("name");
			pro.setName(name);
			proDao.add(pro);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path + "proAdd.jsp").forward(request, response);
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
			ProjectDao proDao = new ProjectDao();
			Project pro = new Project();
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			pro.setId(id);
			pro.setName(name);
			proDao.update(pro);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			Project pro = new Project();
			pro = proDao.search(id);
			request.setAttribute("pro", pro);
			request.getRequestDispatcher(path + "proUpdate.jsp").forward(request, response);
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
			ProjectDao proDao = new ProjectDao();
			List<Project> list = new ArrayList<Project>();
			list = proDao.search(ids);
			request.setAttribute("pro", list.get(0));
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "proUpdateBatch1.jsp").forward(request, response);
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
			ProjectDao proDao = new ProjectDao();
			Project pro = new Project();
			String name = request.getParameter("name");
			pro.setName(name);
			proDao.updateBatch1(ids, pro);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			ProjectDao proDao = new ProjectDao();
			List<Project> list = new ArrayList<Project>();
			list = proDao.search(ids);
			request.setAttribute("list", list);
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "proUpdateBatch2.jsp").forward(request, response);
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
			ProjectDao proDao = new ProjectDao();
			List<Project> list = new ArrayList<Project>();
			String pros = request.getParameter("pros");
			String[] array = pros.split(";");
			for (int i = 0; i < array.length; i++) {
				Project pro = new Project();
				String[] tpro = array[i].split(",");
				pro.setId(Integer.parseInt(tpro[0]));
				pro.setName(tpro[1]);
				list.add(pro);
			}
			proDao.updateBatch2(list);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch3(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProjectDao proDao = new ProjectDao();
			List<Project> list = new ArrayList<Project>();
			String pros = request.getParameter("pros");
			JSONArray jsonArray = JSONArray.fromObject(pros);
			list = (List<Project>) jsonArray.toCollection(jsonArray, Project.class);
			proDao.updateBatch2(list);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProjectDao proDao = new ProjectDao();
			int id = Integer.parseInt(request.getParameter("id"));
			proDao.delete(id);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProjectDao proDao = new ProjectDao();
			String ids = request.getParameter("ids");
			proDao.deleteBatch(ids);
			response.sendRedirect("proView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
