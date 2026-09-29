package com.cosplay.archive.servie.Info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoChatDAO;
import com.cosplay.archive.model.InfoChatDTO;
import com.cosplay.archive.service.Action;

public class InfoChatModifyProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		InfoChatDTO dto = new InfoChatDTO();
		dto.setInfo_chat_id(Integer.parseInt(request.getParameter("chat_id")));
		dto.setInfo_chat_comment(request.getParameter("info_chat_comment"));
		dto.setInfo_chat_url(request.getParameter("info_chat_url"));

		InfoChatDAO icdao = InfoChatDAO.getinstance();
		icdao.infoChatModify(dto);

		int info_id = Integer.parseInt(request.getParameter("info_id"));
		response.sendRedirect(request.getContextPath() + "/Info?cmd=infoDetail&id=" + info_id);
	}

}
