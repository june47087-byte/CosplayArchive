package com.cosplay.archive.servie.Info;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.InfoDAO;
import com.cosplay.archive.model.InfoDTO;
import com.cosplay.archive.service.Action;

public class InfoWriteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean showForm = true;
		request.setAttribute("showForm", showForm);
		RequestDispatcher rd = request.getRequestDispatcher("/Info/info.jsp");
		rd.forward(request, response);

	}

}
