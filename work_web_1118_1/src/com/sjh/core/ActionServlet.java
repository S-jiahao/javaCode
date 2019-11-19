package com.sjh.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ActionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Action> actionMap = (Map<String, Action>) request.getServletContext().getAttribute("actionMap");
		Properties p = (Properties)request.getServletContext().getAttribute("config");
		String uri = request.getRequestURI();
		String actionName = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf("."));
//		System.out.println(actionName);
		String classPath = null;
		String actionFormPath = null;
		if(actionName!=null){
			classPath = (String) p.get(actionName);
			actionFormPath = (String) p.get(actionName+"Form");
//			System.out.println(classPath);
		}
		try {
			//获取参数并封装
			Class c = Class.forName(actionFormPath);
			Object obj = Class.forName(actionFormPath).newInstance();
			Map<String, String[]> map = request.getParameterMap();
			Set<Entry<String, String[]>> set = map.entrySet();
			for (Entry<String, String[]> entry : set) {
				String paramName = entry.getKey();
				Method m = c.getDeclaredMethod("set"+paramName.substring(0, 1).toUpperCase()+paramName.substring(1), String.class);
//				System.out.println(m.getName());
				m.invoke(obj, entry.getValue()[0]);
			}
			ActionForm aFrom = (ActionForm) obj;
			//找对应的操作
			Action a = null;
			if(actionMap.get(classPath)==null){
				a = (Action) Class.forName(classPath).newInstance();
				actionMap.put(classPath, a);
				request.getServletContext().setAttribute("actionMap", actionMap);
			}else{
				a = (Action) actionMap.get(classPath);
			}
			ActionForword af = a.execute(request, response,aFrom);
			af.forword(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//存action
		Map<String,Action> map = new HashMap<String, Action>();
		//获取初始化信息
		String path = config.getInitParameter("configLocation");
		path = config.getServletContext().getRealPath(path);
//		System.out.println(path);
		Properties p = new Properties();
		try {
			//加载配置文件
			p.load(new FileInputStream(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.getServletContext().setAttribute("actionMap", map);
		config.getServletContext().setAttribute("config", p);
	}

}
