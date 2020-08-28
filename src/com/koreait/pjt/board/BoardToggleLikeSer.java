package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/board/toggleLike")
public class BoardToggleLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board"); // key값
		int i_board = MyUtils.parseStrToInt(strI_board);
		String strYn_like = request.getParameter("yn_like"); // 선언 parameter 호출은 agument
		int yn_like = MyUtils.parseStrToInt(strYn_like, 3);
		System.out.println(yn_like);
		// 인터페이스는 유연하게 교체해줄 수 있도록 해줌 
		
//		HttpSession hs = request.getSession();
//		UserVO loginUser = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		UserVO loginUser = MyUtils.getLoginUser(request);
		
		BoardVO param = new BoardVO();
		param.setI_user(loginUser.getI_user());
		param.setI_board(i_board);
		
		
		if(yn_like == 0) {
			BoardDAO.insBoardLike(param);
		}else if(yn_like == 1) {
			BoardDAO.delBoardLike(param);
		}
		response.sendRedirect("/board/detail?i_board="+ strI_board);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
	}
}
