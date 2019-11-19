package com.sjh.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionForword execute(HttpServletRequest request, HttpServletResponse response,ActionForm param)throws ServletException, IOException;
}
