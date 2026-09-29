package com.cosplay.archive.service.Archive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.PictureDAO;
import com.cosplay.archive.service.Action;

public class PictureDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int pic_id = Integer.parseInt(request.getParameter("id"));
		PictureDAO dao = PictureDAO.getinstance();
		dao.pictureDelete(pic_id);
		response.sendRedirect(request.getContextPath() + "/Archive?cmd=archiveList");
	}

}
