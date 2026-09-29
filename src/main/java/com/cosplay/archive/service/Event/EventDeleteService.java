package com.cosplay.archive.service.Event;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.EventDAO;
import com.cosplay.archive.service.Action;

public class EventDeleteService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String region = request.getParameter("region"); // rightbar 목록으로 되돌아가기 위해서만 씀
		int event_id = Integer.parseInt(request.getParameter("id"));
		EventDAO dao = EventDAO.getinstance();
		dao.eventDelete(event_id);
		String encodedRegion = URLEncoder.encode(region, "UTF-8");
		response.sendRedirect(request.getContextPath() + "/Event?cmd=eventList&region=" + encodedRegion);
	}

}
