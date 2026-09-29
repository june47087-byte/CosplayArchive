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

public class InfoListMakeService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String info_div = "제작";
		InfoDAO idao = InfoDAO.getinstance();
		InfoChatDAO icdao = InfoChatDAO.getinstance();
		List<InfoDTO> infoList = idao.getSelectedCategory(info_div);

		for (InfoDTO info : infoList) {
			List<InfoChatDTO> comments = icdao.getTweet(info.getInfo_id());
			info.setComments(comments);
		}

		request.setAttribute("infoList", infoList);
		request.setAttribute("selectedCategory", info_div);
		RequestDispatcher rd = request.getRequestDispatcher("/Info/info.jsp");
		rd.forward(request, response);

	}

}
