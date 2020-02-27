package com.revature.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.delegates.ViewDelegate;

public class RequestHelper {
	
	private ViewDelegate viewDelegate = new ViewDelegate();

	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if (path.startsWith("/api/")) {
			
		} else {
			viewDelegate.resolveView(request, response);
		}
	}
}
