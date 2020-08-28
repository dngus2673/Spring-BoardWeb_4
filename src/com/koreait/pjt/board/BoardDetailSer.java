package com.koreait.pjt.board;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/board/detail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(MyUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStrToInt(strI_board);
		//단 한개만 
		ServletContext application = getServletContext(); //어플리케이션 내장객체 얻어오기 위한 것
		Integer readI_user = (Integer)application.getAttribute("read_" + strI_board);
		// integer면 null 받을 수 있기 때문??
		// != 지금은 값 비교로 사용
		if(readI_user == null || readI_user != loginUser.getI_user()) {
			//조회수 올리기 - 혼자서 조회수 올리기 가능
			BoardDAO.addHits(i_board);
			application.setAttribute("read_" + strI_board, loginUser.getI_user());
		}
		//단독으로 조회수 올리기 방지용!
		//좋아요
		BoardVO param = new BoardVO();
		param.setI_user(loginUser.getI_user());
		param.setI_board(i_board);		
		request.setAttribute("data", BoardDAO.selBoard(param));
		
		request.setAttribute("cmtList", BoardCmtDAO.selCmtList(i_board));
		ViewResolver.forward("board/detail", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
