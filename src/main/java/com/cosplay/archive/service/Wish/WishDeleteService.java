package com.cosplay.archive.service.Wish;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.WishChatDAO;
import com.cosplay.archive.model.WishDAO;
import com.cosplay.archive.service.Action;

public class WishDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int wish_id = Integer.parseInt(request.getParameter("id"));
		WishChatDAO wcdao = WishChatDAO.getinstance();
		WishDAO wdao = WishDAO.getinstance();
		wcdao.wishChatDeleteByWishId(wish_id);
		wdao.wishDelete(wish_id);
		response.sendRedirect(request.getContextPath() + "/Wish?cmd=wishList");
	}

}
