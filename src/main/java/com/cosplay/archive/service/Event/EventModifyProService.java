package com.cosplay.archive.service.Event;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.EventDAO;
import com.cosplay.archive.model.EventDTO;
import com.cosplay.archive.service.Action;

public class EventModifyProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String region = request.getParameter("region"); // rightbar 목록으로 되돌아가기 위해서만 씀

		EventDTO dto = new EventDTO();
		dto.setEvent_id(Integer.parseInt(request.getParameter("id")));
		dto.setEvent_name(request.getParameter("event_name"));
		dto.setEvent_day(request.getParameter("event_day")); // "yyyy-MM-dd" 형식 기대
		dto.setEvent_place(request.getParameter("event_place"));

		EventDAO dao = EventDAO.getinstance();
		dao.eventModify(dto);

		String encodedRegion = URLEncoder.encode(region, "UTF-8");
		response.sendRedirect(request.getContextPath() + "/Event?cmd=eventList&region=" + encodedRegion);
	}

}
