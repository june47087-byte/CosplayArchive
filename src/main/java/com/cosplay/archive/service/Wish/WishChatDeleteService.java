package com.cosplay.archive.service.Wish;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.WishChatDAO;
import com.cosplay.archive.service.Action;

public class WishChatDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int wish_chat_id = Integer.parseInt(request.getParameter("wish_chat_id"));
		int wish_id = Integer.parseInt(request.getParameter("wish_id"));

		WishChatDAO wcdao = WishChatDAO.getinstance();
		wcdao.wishChatDelete(wish_chat_id);

		response.sendRedirect(request.getContextPath() + "/Wish?cmd=wishList&wishId=" + wish_id);
	}

}
