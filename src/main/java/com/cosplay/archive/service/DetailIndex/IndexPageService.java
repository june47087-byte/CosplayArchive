package com.cosplay.archive.service.DetailIndex;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.CosplayDAO;
import com.cosplay.archive.model.IndexStatsDTO;
import com.cosplay.archive.service.Action;

public class IndexPageService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IndexStatsDTO stats = CosplayDAO.getinstance().getIndexStats();

		request.setAttribute("totalPosts", stats.getTotalPosts());
		request.setAttribute("totalYears", stats.getTotalYears());
		request.setAttribute("totalCosplays", stats.getTotalCosplays());

		RequestDispatcher rd = request.getRequestDispatcher("/DetailIndex/indexView.jsp");
		rd.forward(request, response);
	}

}
