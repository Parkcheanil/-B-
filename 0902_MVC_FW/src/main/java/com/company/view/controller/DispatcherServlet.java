package com.company.view.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "action", urlPatterns = { "*.do" })
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private HandlerMapping handlerMapping;
	private ViewResolver viewResolver;
   
	@Override
	public void init() throws ServletException {
		handlerMapping = new HandlerMapping();
		viewResolver = new ViewResolver();
		viewResolver.setPrefix("./");
		viewResolver.setSuffix(".jsp");
	}
	
    public DispatcherServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	//사용자 정의 메소드
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1. 클라이언트의 요청 path 정보 추출
		String uri = request.getRequestURI();
		System.out.println(uri);
		System.out.println();
		
		int lastposition = uri.lastIndexOf("/");
		String path = uri.substring(lastposition);
		
		//2. HandlerMapping을 통해서 path에 해당하는 Controller를 검색한다.
		Controller ctrl = handlerMapping.getController(path);
		
		//3. 검색된 Controller를 실행한다.
		String viewName = ctrl.handleRequest(request, response);
		
		//4. ViewResolver를 통해서 viewName에 해당하는 페이지(포워딩)을 검색한다.
		String view = null;
		
		if(viewName.contains(".do")) {
			view = viewName;
		}else {
			view = viewResolver.getView(viewName);
		}
		//5. 검색된 화면(페이지)으로 이동한다.
		response.sendRedirect(view);
	}
}
