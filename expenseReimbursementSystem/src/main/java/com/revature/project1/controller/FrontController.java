package com.revature.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

@MultipartConfig
public class FrontController extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getRootLogger();
	
	private RequestHelper requestHelper = new RequestHelper();
       
    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.warn("doGet handler is triggered");
		String path = request.getServletPath();
		
		if (path.startsWith("/static")) {
			super.doGet(request, response);
		} else {
			requestHelper.processGet(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestHelper.processPost(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		requestHelper.processPut(request, response);
	}

}
