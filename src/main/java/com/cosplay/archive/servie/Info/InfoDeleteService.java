package com.cosplay.archive.servie.Info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoChatDAO;
import com.cosplay.archive.model.InfoDAO;
import com.cosplay.archive.service.Action;

public class InfoDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int info_id = Integer.parseInt(request.getParameter("id"));
		InfoChatDAO icdao = InfoChatDAO.getinstance();
		InfoDAO idao = InfoDAO.getinstance();
		idao.infoDelete(info_id);
		icdao.infoDelete(info_id);
		response.sendRedirect(request.getContextPath() + "/Info?cmd=infoList");
	}

}
