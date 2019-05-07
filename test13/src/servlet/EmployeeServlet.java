package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import net.sf.json.JSONArray;
import util.Constant;
import util.Pagination;

public class EmployeeServlet extends HttpServlet {
	public static final String path = "WEB-INF/employee/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("showAdd2".equals(type)) {
				showAdd2(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("add2".equals(type)) {
				add2(request, response);
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
			} else if ("upload".equals(type)) {
				upload(request, response);
			} else if ("deletePicture".equals(type)) {
				deletePicture(request, response);
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
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = -1;
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			Integer depId = null;
			if (request.getParameter("depId") != null && !"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			String picture = request.getParameter("picture");
			Department dep = new Department();
			dep.setId(depId);
			Employee condition = new Employee();
			condition.setName(name);
			condition.setSex(sex);
			condition.setAge(age);
			condition.setPicture(picture);
			condition.setDep(dep);
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();
			List<Employee> list = new ArrayList<Employee>();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = empDao.searchCount(condition);
			if (count == 0) {
				count = 1;
			}
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			list = empDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			request.setAttribute("c", condition);
			request.setAttribute("p", p);
			request.setAttribute("empList", list);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "emps.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "empAdd.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd2(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "empAdd2.jsp").forward(request, response);
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
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String name = "";
			String sex = "";
			int age = -1;
			Integer depId = null;
			String picture = "";
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("name")) {
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("sex")) {
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("age")) {
					age = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));// 实际上拿数字不会乱码，可以不写转换
				} else if (item.getFieldName().equals("depId")) {
					depId = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));
				} else if (item.getFieldName().equals("picture")) {
					UUID uuid = UUID.randomUUID();
					picture = uuid.toString() + item.getName().substring(item.getName().lastIndexOf("."));// 生成文件名
					File savedFile = new File("d:/test/employeePicture", picture);
					item.write(savedFile);
				}
			}
			Employee emp = new Employee();
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);
			emp.setPicture(picture);
			EmployeeDao empDao = new EmployeeDao();
			empDao.add(emp);
			response.sendRedirect("empView");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 通过以上三行代码，将表单发送过来的数据都放进items集合里面去了
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = new Employee();
			Department dep = new Department();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			String depId = request.getParameter("depId");
			Integer depId1 = null;
			String picture = request.getParameter("pictureName");
			if (!"".equals(depId)) {
				depId1 = Integer.parseInt(depId);
			}
			dep.setId(depId1);
			int age1 = Integer.parseInt(age);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age1);
			emp.setDep(dep);
			emp.setPicture(picture);
			empDao.add(emp);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String picture = "";
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("picture")) {
					UUID uuid = UUID.randomUUID();
					picture = uuid.toString() + item.getName().substring(item.getName().lastIndexOf("."));
					File savedFile = new File("d:/test/employeePicture", picture);
					item.write(savedFile);
				}
			}
			PrintWriter out = response.getWriter();
			out.print(picture);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 通过以上三行代码，将表单发送过来的数据都放进items集合里面去了
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			Employee emp = new Employee();
			emp = empDao.search(id);
			request.setAttribute("emp", emp);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "empUpdate.jsp").forward(request, response);
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
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = new Employee();
			Department dep = new Department();
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			int age1 = Integer.parseInt(age);
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			dep.setId(depId);
			emp.setDep(dep);
			emp.setId(id);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age1);
			empDao.update(emp);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = new ArrayList<Employee>();
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			list = empDao.search(ids);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.setAttribute("emp", list.get(0));
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "empUpdateBatch1.jsp").forward(request, response);
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
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = new Employee();
			Department dep = new Department();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			int age1 = Integer.parseInt(age);
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			dep.setId(depId);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age1);
			emp.setDep(dep);
			empDao.updateBatch1(ids, emp);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = new ArrayList<Employee>();
			DepartmentDao depDao = new DepartmentDao();
			List<Department> listOfDepartment = new ArrayList<Department>();
			listOfDepartment = depDao.search();
			list = empDao.search(ids);
			request.setAttribute("list", list);
			request.setAttribute("ids", ids);
			request.setAttribute("listOfDepartment", listOfDepartment);
			request.getRequestDispatcher(path + "empUpdateBatch2.jsp").forward(request, response);
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
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = new ArrayList<Employee>();
			String emps = request.getParameter("emps");
			String[] array = emps.split(";");
			for (int i = 0; i < array.length; i++) {
				Employee emp = new Employee();
				Department dep = new Department();
				String[] temp = array[i].split(",");
				emp.setId(Integer.parseInt(temp[0]));
				emp.setName(temp[1]);
				emp.setSex(temp[2]);
				emp.setAge(Integer.parseInt(temp[3]));
				if (Integer.parseInt(temp[4]) == -1) {
					dep.setId(null);
				} else {
					dep.setId(Integer.parseInt(temp[4]));
				}
				emp.setDep(dep);
				list.add(emp);
			}
			empDao.updateBatch2(list);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch3(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = new ArrayList<Employee>();
			List<Employee> list1 = new ArrayList<Employee>();
			String emps = request.getParameter("emps");
			JSONArray jsonArray = JSONArray.fromObject(emps);
			list = (List<Employee>) jsonArray.toCollection(jsonArray, Employee.class);
			for (int i = 0; i < list.size(); i++) {
				Employee emp = new Employee();
				Department dep = new Department();
				emp.setId(list.get(i).getId());
				emp.setName(list.get(i).getName());
				emp.setSex(list.get(i).getSex());
				emp.setAge(list.get(i).getAge());
				if (list.get(i).getDep().getId() == -1) {
					dep.setId(null);
				} else {
					dep.setId(list.get(i).getDep().getId());
				}
				emp.setDep(dep);
				list1.add(emp);
			}
			empDao.updateBatch2(list1);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			int id = Integer.parseInt(request.getParameter("id"));
			empDao.delete(id);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			String ids = request.getParameter("ids");
			empDao.deleteBatch(ids);
			response.sendRedirect("empView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletePicture(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pictureName = request.getParameter("pictureName");
			File delPic = new File("d:/test/employeePicture/" + pictureName);
			boolean flag = delPic.delete();
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
