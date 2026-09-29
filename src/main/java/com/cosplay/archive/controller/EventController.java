package com.cosplay.archive.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;
import com.cosplay.archive.service.Event.EventDeleteService;
import com.cosplay.archive.service.Event.EventListService;
import com.cosplay.archive.service.Event.EventModifyProService;
import com.cosplay.archive.service.Event.EventWriteProService;

/**
 * Servlet implementation class EventController
 */
@WebServlet("/Event")
public class EventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		System.out.println("요청 파라미터 : " + cmd);
		Action action = null;
		
		if(cmd.equals("eventList")) {
			action = new EventListService();
		}else if(cmd.equals("seoul")) {
			action =  new EventListService();
		}else if(cmd.equals("busan")) {
			action = new EventListService();
		}else if(cmd.equals("jeolla")) {
			action = new EventListService();
		}else if(cmd.equals("eventWritePro")) {
			action = new EventWriteProService();
		}else if(cmd.equals("eventModifyPro")) {
			action = new EventModifyProService();
		}else if(cmd.equals("eventDelete")) {
			action = new EventDeleteService();
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
