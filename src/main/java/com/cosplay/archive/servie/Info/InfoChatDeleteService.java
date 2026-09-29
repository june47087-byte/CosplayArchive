package com.cosplay.archive.servie.Info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoChatDAO;
import com.cosplay.archive.service.Action;

public class InfoChatDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int info_chat_id = Integer.parseInt(request.getParameter("info_chat_id"));
		int info_id = Integer.parseInt(request.getParameter("info_id"));
		InfoChatDAO icdao = InfoChatDAO.getinstance();
		icdao.infoChatDelete(info_chat_id);
		response.sendRedirect(request.getContextPath() + "/Info?cmd=infoDetail&id=" + info_id);
	}

}
