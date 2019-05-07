package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import util.CreateMD5;
import util.RandomNumber;
import util.ValidateCode;

public class UserServlet extends HttpServlet {
	public static final String path = "WEB-INF/user/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type == null) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("showLogin".equals(type)) {
			showLogin(request, response);
		} else if ("randomImage".equals(type)) {
			randomImage(request, response);
		} else if ("showRegister".equals(type)) {
			showRegister(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		}
	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			UserDao userDao = new UserDao();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String random = request.getParameter("random");
			if (session.getAttribute("rand").equals(random)) {
				User user = new User();
				user.setUsername(username);
				String date = userDao.searchDate(user);
				user.setPassword(CreateMD5.getMd5(password + date));// ������� �ַ����̶����ã����Ի��������ڣ������ݿ��ڽ�һ�ű��ע�����ڣ�Ȼ��������
				boolean flag = userDao.search(user);
				if (flag) {
					// �õ���ǰ�Ự session�ĵ���Ӧ�ó�������Ȩ��
					// ��������һ���»Ự����һ��sessionId��cookie����ֵ��һ���ڷ�������Ψһ���ַ�����Ȼ������cookie��ʱЧ�����ֻ�ܴ������ڴ��У�������ر�����ʧ������ÿ���������վ��ʱ�����������Ѹ�cookie���͵��������������������һ�������������з��ʸ���վ�ĻỰ����cookie��sessionid��Ӧ��һ��map����
					// session��ֵ����Ϊ���⣨���磺�ַ��������󣩣�cookie��ֵֻ��Ϊ�ַ���
					session.setAttribute("username", username);
					// cookie�������ö��� ����֮������ȱ������Ϊsession��java���潨���Ķ���(java����������)��cookie��������������ġ�cookie
					// �����������ɵģ�����ֵ������Ӧ�ؿͻ�����������ڴ��Ӳ�̣��ϣ���������ʱЧ��ÿ�η��ͶԸ���վ�������ʱ�򣬶���Ѹ���վ���ڱ�������ϵ�����cookie��һ�����͵�������
					Cookie cookie = new Cookie("name", username);
					cookie.setMaxAge(30);// ʱЧ�������������cookie��� ��Ϊ-1��ʾֻ�������ڴ���
					response.addCookie(cookie);
					response.sendRedirect("index");
				} else {
					request.setAttribute("mes", "�û������������");
					request.getRequestDispatcher(path + "login.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("mes", "��֤�����");
				request.getRequestDispatcher(path + "login.jsp").forward(request, response);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie[] cookies = request.getCookies();
			String username = "";
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if ("name".equals(cookies[i].getName())) {
						username = cookies[i].getValue();
					}
				}
			}
			request.setAttribute("username", username);
			request.getRequestDispatcher(path + "login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void randomImage(HttpServletRequest request, HttpServletResponse response) {
		RandomNumber rn = new RandomNumber();
		try {
			// ����ҳ�治����
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			ValidateCode vc = rn.generateImage();
			ServletOutputStream outStream = response.getOutputStream();
			// ���ͼ��ҳ��
			ImageIO.write(vc.getImage(), "JPEG", outStream);
			outStream.close();
			request.getSession().setAttribute("rand", vc.getRand());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path + "register.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Date date1 = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			String date = dateFormat.format(date1);
			System.out.println(date);
			UserDao userDao = new UserDao();
			User user = new User();
			user.setUsername(username);
			user.setPassword(CreateMD5.getMd5(password + date));
			user.setDate(date);
			boolean flag = userDao.searchUsername(user);
			if (flag) {
				request.setAttribute("mes", "�û����ظ�");
				request.getRequestDispatcher(path + "register.jsp").forward(request, response);
			} else {
				userDao.add(user);
				response.sendRedirect("user");
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
