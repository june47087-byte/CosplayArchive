package com.cosplay.archive.service.Wish;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.WishChatDAO;
import com.cosplay.archive.model.WishChatDTO;
import com.cosplay.archive.service.Action;

// InfoChatWriteService와 같은 구조입니다.
public class WishChatWriteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		WishChatDTO dto = new WishChatDTO();
		dto.setWish_id(Integer.parseInt(request.getParameter("id")));
		dto.setWish_chat_contents(request.getParameter("wish_chat_contents"));
		dto.setWish_chat_url(request.getParameter("wish_chat_url"));

		WishChatDAO wcdao = WishChatDAO.getinstance();
		wcdao.wishChatWrite(dto);

		response.sendRedirect(request.getContextPath() + "/Wish?cmd=wishList");
	}

}
