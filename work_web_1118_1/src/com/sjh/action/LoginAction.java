package com.sjh.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sjh.core.Action;
import com.sjh.core.ActionForm;
import com.sjh.core.ActionForword;

public class LoginAction implements Action{
	
	public LoginAction() {
		System.out.println("≥ı ºªØ");
	}
	@Override
	public ActionForword execute(HttpServletRequest request, HttpServletResponse response,ActionForm param) throws ServletException, IOException {
		System.out.println(param);
		
		return new ActionForword("success");
	}
}
