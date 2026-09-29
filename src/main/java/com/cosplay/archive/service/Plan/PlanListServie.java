package com.cosplay.archive.service.Plan;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.PlanDAO;
import com.cosplay.archive.model.PlanDTO;
import com.cosplay.archive.service.Action;

public class PlanListServie implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("activeMenu", "plan");

		PlanDAO dao = PlanDAO.getinstance();

		String status = request.getParameter("status");
		List<PlanDTO> planList;
		if (status != null && !status.isEmpty() && !"전체".equals(status)) {
			planList = dao.getPlanListByStatus(status);
			request.setAttribute("selectedStatus", status);
		} else {
			planList = dao.getPlanList();
		}
		request.setAttribute("planList", planList);

		// planId가 있을 때만 plan을 채웁니다 — 안 채우면 rightbar.jsp가 안내 문구를 보여줍니다.
		String planIdParam = request.getParameter("planId");
		if (planIdParam != null && !planIdParam.isEmpty()) {
			PlanDTO plan = dao.getPlan(Integer.parseInt(planIdParam));
			request.setAttribute("plan", plan);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/Plan/plan.jsp");
		rd.forward(request, response);
	}

}
