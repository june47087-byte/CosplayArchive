package com.cosplay.archive.servie.Info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoDAO;
import com.cosplay.archive.model.InfoDTO;
import com.cosplay.archive.service.Action;

public class InfoWriteProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		InfoDTO dto = new InfoDTO();
		dto.setInfo_div(request.getParameter("info_div"));
		dto.setInfo_name(request.getParameter("info_name"));
		dto.setInfo_contents(request.getParameter("info_contents"));
		dto.setInfo_url(request.getParameter("info_url"));

		InfoDAO idao = InfoDAO.getinstance();
		idao.infoWrite(dto);

		response.sendRedirect(request.getContextPath() + "/Info?cmd=infoList");
	}

}
