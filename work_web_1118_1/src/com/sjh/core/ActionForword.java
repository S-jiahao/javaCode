package com.sjh.core;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionForword {
	private String path;
	private boolean redirect = false;
	
	public ActionForword(String path) {
		this(path, false);
	}
	
	public ActionForword(String path,boolean redirect) {
		this.path = path;
		this.redirect = redirect;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return redirect;
	}
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
	public void forword(HttpServletRequest request, HttpServletResponse response){
		Properties p = (Properties) request.getServletContext().getAttribute("config");
		if(redirect){
			try {
				response.sendRedirect(p.getProperty(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				request.getRequestDispatcher(p.getProperty(path)).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
