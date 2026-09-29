package com.cosplay.archive.service.Wish;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.WishChatDAO;
import com.cosplay.archive.model.WishChatDTO;
import com.cosplay.archive.model.WishDAO;
import com.cosplay.archive.model.WishDTO;
import com.cosplay.archive.service.Action;

public class WishListService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("activeMenu", "wish");

		WishDAO dao = WishDAO.getinstance();

		String status = request.getParameter("status");
		List<WishDTO> wishList;
		if (status != null && !status.isEmpty() && !"전체".equals(status)) {
			wishList = dao.getWishListByStatus(status);
			request.setAttribute("selectedStatus", status);
		} else {
			wishList = dao.getWishList();
		}
		request.setAttribute("wishList", wishList);

		// wishId가 있을 때만 wish/wishChatList를 채웁니다 — 안 채우면 rightbar.jsp가
		// 안내 문구를 보여줍니다.
		String wishIdParam = request.getParameter("wishId");
		if (wishIdParam != null && !wishIdParam.isEmpty()) {
			int wish_id = Integer.parseInt(wishIdParam);
			WishDTO wish = dao.getWish(wish_id);
			request.setAttribute("wish", wish);

			WishChatDAO wcdao = WishChatDAO.getinstance();
			List<WishChatDTO> wishChatList = wcdao.getWishChatList(wish_id);
			request.setAttribute("wishChatList", wishChatList);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/Wish/wish.jsp");
		rd.forward(request, response);
	}

}
