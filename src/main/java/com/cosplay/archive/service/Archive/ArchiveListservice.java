package com.cosplay.archive.service.Archive;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.model.ArchiveYearGroupDTO;
import com.cosplay.archive.model.PictureDAO;
import com.cosplay.archive.model.PictureDTO;
import com.cosplay.archive.service.Action;

public class ArchiveListservice implements Action {

	private static final int PAGE_SIZE = 10;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("activeMenu", "home");

		PictureDAO dao = PictureDAO.getinstance();

		// pictureId가 있을 때만 picture를 채웁니다 — 안 채우면 rightbar.jsp가
		// "게시물을 클릭하세요" 안내를 보여줍니다. (없는 상태에서 그냥 빈 PictureDTO를
		// 넣으면 필드가 전부 기본값(0/null)일 뿐 EL의 empty 판정으로는 "비어있지 않음"이라
		// rightbar가 잘못 렌더링돼서, pictureId가 있을 때만 조회/설정합니다.)
		String pictureIdParam = request.getParameter("pictureId");
		if (pictureIdParam != null && !pictureIdParam.isEmpty()) {
			PictureDTO picture = dao.getPicture(Integer.parseInt(pictureIdParam));
			request.setAttribute("picture", picture);
		}

		// 아코디언을 펼쳐서 다음 페이지를 눌렀을 때만 year/page가 같이 넘어옵니다 —
		// 그 연도 그룹만 해당 페이지를, 나머지 연도는 전부 1페이지를 보여줍니다.
		String yearParam = request.getParameter("year");
		String pageParam = request.getParameter("page");
		int targetYear = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : 0;
		int targetPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

		List<ArchiveYearGroupDTO> archiveGroups = dao.getYearGroups();
		for (ArchiveYearGroupDTO group : archiveGroups) {
			int page = (group.getYear() == targetYear) ? targetPage : 1;
			int totalPages = (int) Math.ceil(group.getCount() / (double) PAGE_SIZE);
			group.setPage(page);
			group.setTotalPages(totalPages);
			group.setPictures(dao.getPictureListByYear(group.getYear(), page, PAGE_SIZE));
		}
		request.setAttribute("archiveGroups", archiveGroups);

		RequestDispatcher rd = request.getRequestDispatcher("/Archive/archive.jsp");
		rd.forward(request, response);
	}

}
