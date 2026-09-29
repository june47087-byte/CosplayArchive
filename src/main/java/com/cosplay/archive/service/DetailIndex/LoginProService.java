package com.cosplay.archive.service.DetailIndex;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosplay.archive.service.Action;

public class LoginProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = "june";
		String pw = "jmke";
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		boolean result = id.equals(loginId) && pw.equals(loginPw);

		if(result) {
			request.getSession().setAttribute("loginId", loginId);
		}

		response.setContentType("text/plain; charset=UTF-8");
		response.getWriter().print(result);
	}

}
