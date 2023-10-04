package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

public class AuthInterceptor  implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String[] url = request.getRequestURI().split("/");

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null || !url[2].equals(authUser.getId())) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
//		else if(!url[2].equals(authUser.getId())) {
//			response.sendRedirect(request.getContextPath()+"/user/login");
//			return false;
//		}
		return true;
	}
	
}
