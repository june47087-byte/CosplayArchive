package com.cosplay.archive.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;
import com.cosplay.archive.service.Wish.WishChatDeleteService;
import com.cosplay.archive.service.Wish.WishChatModifyProService;
import com.cosplay.archive.service.Wish.WishChatWriteService;
import com.cosplay.archive.service.Wish.WishDeleteService;
import com.cosplay.archive.service.Wish.WishListService;
import com.cosplay.archive.service.Wish.WishModifyProService;
import com.cosplay.archive.service.Wish.WishWriteProService;

/**
 * Servlet implementation class WishController
 */
@WebServlet("/Wish")
public class WishController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WishController() {
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
		
		if(cmd.equals("wishList")) {
			action = new WishListService();
		}else if(cmd.equals("wishWritePro")) {
			action = new WishWriteProService();
		}else if(cmd.equals("wishModifyPro")) {
			action = new WishModifyProService();
		}else if(cmd.equals("wishDelete")) {
			action = new WishDeleteService();
		}else if(cmd.equals("wishChatWrite")) {
			action = new WishChatWriteService();
		}else if(cmd.equals("wishChatModifyPro")) {
			action = new WishChatModifyProService();
		}else if(cmd.equals("wishChatDelete")) {
			action = new WishChatDeleteService();
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
