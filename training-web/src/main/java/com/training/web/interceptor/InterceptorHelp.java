package com.training.web.interceptor;

import javax.servlet.http.HttpServletRequest;

public class InterceptorHelp {

	public final static String NOT_LOGIN = "-1";
    
	/*
	 * 是否是Ajax请求
	 */
	public static boolean isAjax(HttpServletRequest request) {
    	String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

	public static String loginType(HttpServletRequest request){
		//String requestURI = request.getRequestURI();

		return "/passport/login"; //PC端
	}
	
}
