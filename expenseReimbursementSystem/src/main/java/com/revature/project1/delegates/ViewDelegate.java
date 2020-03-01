package com.revature.project1.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDelegate {

	public void resolveView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		switch(path) {
		case "/login":
			request.getRequestDispatcher("/static/Views/login.html").forward(request, response);
			break;
		case "/manager-pending":
			request.getRequestDispatcher("/static/Views/managerPendingReq.html").forward(request, response);
			break;
		case "/view-employees":
			request.getRequestDispatcher("/static/Views/managerViewEmpl.html").forward(request, response);
			break;
		case "/employee-pending":
			request.getRequestDispatcher("/static/Views/employeePendingReq.html").forward(request, response);
			break;
		case "/employee-resolved":
			request.getRequestDispatcher("/static/Views/employeeResolvedReq.html").forward(request, response);
			break;
		case "/employee-profile":
			request.getRequestDispatcher("/static/Views/employeeProfileEdit.html").forward(request, response);
			break;
		case "/":
			response.sendRedirect("/login");
			break;
		default:
			response.sendError(404, "Static Resource Not Found");
		}
	}
}
