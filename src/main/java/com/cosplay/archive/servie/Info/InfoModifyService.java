package com.cosplay.archive.servie.Info;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoChatDAO;
import com.cosplay.archive.model.InfoChatDTO;
import com.cosplay.archive.model.InfoDAO;
import com.cosplay.archive.model.InfoDTO;
import com.cosplay.archive.service.Action;

public class InfoModifyService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int info_id = Integer.parseInt(request.getParameter("id"));

		InfoDAO idao = InfoDAO.getinstance();
		InfoChatDAO icdao = InfoChatDAO.getinstance();

		InfoDTO formInfo = idao.getTweet(info_id);

		List<InfoDTO> infoList = idao.getTweet();
		for (InfoDTO info : infoList) {
			List<InfoChatDTO> comments = icdao.getTweet(info.getInfo_id());
			info.setComments(comments);
		}

		request.setAttribute("formInfo", formInfo);
		request.setAttribute("infoList", infoList);
		request.setAttribute("selectedCategory", "");

		RequestDispatcher rd = request.getRequestDispatcher("/Info/info.jsp");
		rd.forward(request, response);

	}

}
