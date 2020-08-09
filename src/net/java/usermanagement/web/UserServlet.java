package net.java.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import net.java.usermanagement.dao.UserDao;
import net.java.usermanagement.model.User;

@WebServlet(name = "UserServlet", urlPatterns = "/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
	}

	

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
				
			case "/insert":
				insertUser(request, response);
				break;
				
			case "/update":
				updateUser(request, response);
				break;
				
			case "/delete":
				deleteUser(request, response);
				break;
				
			case "/edit":
				showEditForm(request, response);
				break;
				
			case "/view":
				descUser(request, response);
				break;
				
			case "/api":
				listUserJson(request, response);
				break;

			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	// return list of users to vies layer
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> users = new ArrayList<>();

		users = userDao.selectAllUsers();
		request.setAttribute("listUser", users);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listUserJson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<User> users = new ArrayList<>();
		users = userDao.selectAllUsers();		
		
		String usersJson = new Gson().toJson(users);
		
		response.setContentType("application/json");
		response.getWriter().print(usersJson);				
	}

	// display user-form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
		dispatcher.forward(request, response);
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		userDao.insertUser(new User(name, email, country));
		response.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	
	private void descUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);
		
		String existingUserJson = new Gson().toJson(existingUser);
		response.setContentType("application/json");
		response.getWriter().print(existingUserJson);
		System.out.println(existingUserJson);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/show-user.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
		
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

	
		userDao.updateUser(new User(id, name, email, country));
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		userDao.deleteUser(id);
		response.sendRedirect("list");
	}
}
