package com.company.view.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.model2board.board.BoardDAO;
import com.company.model2board.board.BoardDO;
import com.company.model2board.user.UserDAO;
import com.company.model2board.user.UserDO;

@WebServlet(name = "action", urlPatterns = { "*.do" })
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DispatcherServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	// 사용자 정의 메소드
	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//1. 클라이언트의 요청 path 정보를 추출한다.
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
	
		//2. 클라이언트의 요청 path에 따라 적절히 분기처리한다.
		if(path.equals("/login.do")) {
			//1. 사용자 입력 정보 추출(얻어옴)
		 	String id = request.getParameter("id");
		 	String password = request.getParameter("password");
		 
		 	//2. UserDO 클래스 객체 생성 후 setter 메소드 호출하여 '중간 저장소'에 값 초기화 작업
		 	UserDO userDO = new UserDO();
		 	userDO.setId(id);
		 	userDO.setPassword(password);

		 	//3. UserDAO 클래스 객체 생성 후 getUser() 메소드 호출
		 	UserDAO userDAO = new UserDAO();
		 	UserDO user = userDAO.getUser(userDO);
		 	
		 	//4. 화면 네비게이션 => 포워딩 => 응답(response)
		 	if(user != null){
		 		HttpSession session = request.getSession();
		 		session.setAttribute("IdKey", id);
		 		
		 		response.sendRedirect("getBoardList.do");
		 		System.out.println("로그인 성공!");
		 	}else{
		 		response.sendRedirect("login.jsp");
		 		System.out.println("로그인 실패!");
		 	}
		}else if(path.equals("/getBoardList.do")) {
			String searchField = "";
			String searchText = "";
			
			if(request.getParameter("searchCondition")!= "" 
							&& request.getParameter("searchKeyword") != "")	{
				searchField = request.getParameter("searchCondition");
				searchText = request.getParameter("searchKeyword");		
			}
			//BoardDAO 클래스 객체 생성
			BoardDAO boardDAO = new BoardDAO();
			List<BoardDO> boardList = boardDAO.getBoardList(searchField, searchText);

			HttpSession session = request.getSession();
			session.setAttribute("boardList", boardList);

			response.sendRedirect("getBoardList.jsp");
		}else if(path.equals("/getBoard.do")) {
			System.out.println("게시글 상세보기 처리함.");
			
			//1. 검색할 게시글 번호 추출
			String seq = request.getParameter("seq");
			
			//2. BoardDO 객체 생성 후 setSeq()메소드 호출하여 게시글번호 값을 초기화
			BoardDO boardDO = new BoardDO();
			boardDO.setSeq(Integer.parseInt(seq));

			//3. BoardDAO 객체 생성
			BoardDAO boardDAO = new BoardDAO();
			BoardDO board = boardDAO.getBoard(boardDO);

			//4. 등록
			HttpSession session = request.getSession();
			session.setAttribute("board", board);
			
			response.sendRedirect("getBoard.jsp");
		}else if(path.equals("/updateBoard.do")) {
			System.out.println("게시글 업데이트 처리함.");
			
			//1. 사용자 입력정보 추출
			String seq = request.getParameter("seq");
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			//2. BoardDO 객체 생성하여 setter 메소드 호출
			BoardDO boardDO = new BoardDO();
			boardDO.setSeq(Integer.parseInt(seq));
			boardDO.setTitle(title);
			boardDO.setContent(content);

			//3. BoardDAO 객체 생성 후 updateBoard() 메소드 호출
			BoardDAO boardDAO = new BoardDAO();
			boardDAO.updateBoard(boardDO);

			//4. 포워딩 => 응답
			response.sendRedirect("getBoardList.do");
		}else if(path.equals("/insertBoard.do")) {
			System.out.println("게시글 입력 처리함.");
			
			String title = request.getParameter("title");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");

			BoardDO boardDO = new BoardDO();
			boardDO.setTitle(title);
			boardDO.setWriter(writer);
			boardDO.setContent(content);

			BoardDAO boardDAO = new BoardDAO();
			boardDAO.insertBoard(boardDO);

			response.sendRedirect("getBoardList.do");
		}else if(path.equals("/deleteBoard.do")) {
			System.out.println("게시글 삭제 처리함.");
			
			String seq = request.getParameter("seq");

			BoardDO boardDO = new BoardDO();
			boardDO.setSeq(Integer.parseInt(seq));

			BoardDAO boardDAO = new BoardDAO();
			boardDAO.delectBoard(boardDO);

			response.sendRedirect("getBoardList.do");
		}else if(path.equals("/logout.do")) {
			HttpSession session = request.getSession();
			session.invalidate();

			response.sendRedirect("login.jsp");
		}
	}
}
