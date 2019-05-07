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
				user.setPassword(CreateMD5.getMd5(password + date));// 加盐这个 字符串固定不好，可以换成用日期，在数据库在建一张表存注册日期，然后加上这个
				boolean flag = userDao.search(user);
				if (flag) {
					// 拿到当前会话 session的典型应用场景：做权限
					// 服务器对一个新会话生成一个sessionId的cookie，其值是一个在服务器中唯一的字符串。然后把这个cookie的时效设成是只能存在于内存中（浏览器关闭则消失）。那每次请求该网站的时候，浏览器都会把该cookie发送到服务器里，服务器里面有一个容器存着所有访问该网站的会话（该cookie的sessionid对应的一个map）。
					// session的值可以为任意（例如：字符串，对象），cookie的值只能为字符串
					session.setAttribute("username", username);
					// cookie不是内置对象 两者之所以有缺别，是因为session是java层面建立的东西(java可以任意类)，cookie是在浏览器建立的。cookie
					// 服务器端生成的（名，值），响应回客户端浏览器（内存或硬盘）上，可以设置时效。每次发送对该网站的请求的时候，都会把该网站存在本浏览器上的所有cookie都一并发送到服务器
					Cookie cookie = new Cookie("name", username);
					cookie.setMaxAge(30);// 时效，多少秒后放入的cookie清空 设为-1表示只存在于内存中
					response.addCookie(cookie);
					response.sendRedirect("index");
				} else {
					request.setAttribute("mes", "用户名或密码错误");
					request.getRequestDispatcher(path + "login.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("mes", "验证码错误");
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
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			ValidateCode vc = rn.generateImage();
			ServletOutputStream outStream = response.getOutputStream();
			// 输出图像到页面
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			String date = dateFormat.format(date1);
			System.out.println(date);
			UserDao userDao = new UserDao();
			User user = new User();
			user.setUsername(username);
			user.setPassword(CreateMD5.getMd5(password + date));
			user.setDate(date);
			boolean flag = userDao.searchUsername(user);
			if (flag) {
				request.setAttribute("mes", "用户名重复");
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
