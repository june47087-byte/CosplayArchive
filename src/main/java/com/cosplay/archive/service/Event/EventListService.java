package com.cosplay.archive.service.Event;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.EventDAO;
import com.cosplay.archive.model.EventDTO;
import com.cosplay.archive.service.Action;

public class EventListService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("activeMenu", "event");

		// region이 있을 때만 eventList를 채웁니다 — 안 채우면 rightbar.jsp가
		// "지역을 클릭하세요" 안내를 보여줍니다.
		String region = request.getParameter("region");
		if (region != null && !region.isEmpty()) {
			EventDAO dao = EventDAO.getinstance();
			List<EventDTO> eventList = dao.getEventsByRegion(region);
			request.setAttribute("region", region);
			request.setAttribute("eventList", eventList);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/Event/event.jsp");
		rd.forward(request, response);
	}

}
