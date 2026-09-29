package com.cosplay.archive.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;
import com.cosplay.archive.service.DetailIndex.IndexPageService;
import com.cosplay.archive.service.DetailIndex.LoginProService;
import com.cosplay.archive.service.DetailIndex.LoginService;
import com.cosplay.archive.service.DetailIndex.LogoutService;

/**
 * Servlet implementation class CosplayController
 */
@WebServlet("/Cos")
public class CosplayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CosplayController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		Action action = null;
		
		if(cmd.equals("index")) {
			action = new IndexPageService();
		}else if(cmd.equals("login")) {
			action = new LoginService();
		}else if(cmd.equals("loginPro")) {
			action = new LoginProService();
		}else if(cmd.equals("logout")) {
			action = new LogoutService();
		}
		action.process(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
