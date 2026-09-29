package com.cosplay.archive.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;
import com.cosplay.archive.servie.Info.InfoChatDeleteService;
import com.cosplay.archive.servie.Info.InfoChatModifyProService;
import com.cosplay.archive.servie.Info.InfoChatWriteService;
import com.cosplay.archive.servie.Info.InfoDeleteService;
import com.cosplay.archive.servie.Info.InfoDetailService;
import com.cosplay.archive.servie.Info.InfoListElseService;
import com.cosplay.archive.servie.Info.InfoListMakeService;
import com.cosplay.archive.servie.Info.InfoListPoseService;
import com.cosplay.archive.servie.Info.InfoListService;
import com.cosplay.archive.servie.Info.InfoListSettingService;
import com.cosplay.archive.servie.Info.InfoListShopService;
import com.cosplay.archive.servie.Info.InfoModifyProService;
import com.cosplay.archive.servie.Info.InfoModifyService;
import com.cosplay.archive.servie.Info.InfoWriteProService;
import com.cosplay.archive.servie.Info.InfoWriteService;

/**
 * Servlet implementation class WishController
 */
@WebServlet("/Info")
public class InfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoController() {
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

		if(cmd == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "cmd parameter is required");
			return;
		}

		if(cmd.equals("infoList")) {
			action = new InfoListService();
		}else if(cmd.equals("infoDetail")) {
			action = new InfoDetailService();
		}else if(cmd.equals("infoListSetting")) {
			action = new InfoListSettingService();
		}else if(cmd.equals("infoListMake")) {
			action = new InfoListMakeService();
		}else if(cmd.equals("infoListPose")) {
			action = new InfoListPoseService();
		}else if(cmd.equals("infoListShop")) {
			action = new InfoListShopService();
		}else if(cmd.equals("infoListElse")) {
			action = new InfoListElseService();
		}else if(cmd.equals("infoModify")) {
			action = new InfoModifyService();
		}else if(cmd.equals("infoModifyPro")) {
			action = new InfoModifyProService();
		}else if(cmd.equals("infoDelete")) {
			action = new InfoDeleteService();
		}else if(cmd.equals("infoWrite")) {
			action = new InfoWriteService();
		}else if(cmd.equals("infoWritePro")) {
			action = new InfoWriteProService();
		}else if(cmd.equals("infoChatWrite")) {
			action = new InfoChatWriteService();
		}else if(cmd.equals("infoChatDelete")) {
			action = new InfoChatDeleteService();
		}else if(cmd.equals("infoChatModifyPro")) {
			action = new InfoChatModifyProService();
		}

		if(action == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "unknown cmd: " + cmd);
			return;
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
