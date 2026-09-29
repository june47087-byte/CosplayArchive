package com.cosplay.archive.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;
import com.cosplay.archive.service.Plan.PlanDeleteService;
import com.cosplay.archive.service.Plan.PlanListServie;
import com.cosplay.archive.service.Plan.PlanModifyProService;
import com.cosplay.archive.service.Plan.PlanWriteProService;

/**
 * Servlet implementation class WishController
 */
@WebServlet("/Plan")
public class PlanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlanController() {
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
		
		if(cmd.equals("planList")) {
			action = new PlanListServie();
		}else if(cmd.equals("planWritePro")) {
			action = new PlanWriteProService();
		}else if(cmd.equals("planModifyPro")) {
			action = new PlanModifyProService();
		}else if(cmd.equals("planDelete")) {
			action = new PlanDeleteService();
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
