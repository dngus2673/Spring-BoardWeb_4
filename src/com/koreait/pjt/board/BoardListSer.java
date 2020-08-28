package com.koreait.pjt.board;

import java.io.IOException;
import java.util.List;

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
import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;

@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(MyUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		String searchText = request.getParameter("searchText");
		searchText = (searchText == null ? "" : searchText);
		System.out.println("searchText : " + searchText);
		int page = MyUtils.getIntParameter(request, "page");
		page = (page == 0 ? 1 : page);


		

		BoardDomain param = new BoardDomain();
		
		int recordCnt = MyUtils.getIntParameter(request, "record_cnt");
		recordCnt = (recordCnt == 0 ? 10 : recordCnt);
		
		System.out.println("recordCnt : " + recordCnt);
		param.setRecord_cnt(recordCnt);
	
		int eIdx = page * recordCnt;
		int sIdx = eIdx - recordCnt;

		param.setEIdx(eIdx);
		param.setSIdx(sIdx);
		param.setSearchText("%"+ searchText +"%");		
		
		int pagingCnt = BoardDAO.selPagingCnt(param);
		
		//이전 레코드수 값이 있고, 이전 레코드수 보디 변경한 레코드수가 더 크다면 마지막 페이지수로 변경
		if(page > pagingCnt) {
			page = pagingCnt;
		}
		
		request.setAttribute("page", page);
		request.setAttribute("list", BoardDAO.selBoardList(param));
		request.setAttribute("pagingCnt", BoardDAO.selPagingCnt(param));
		
		
		ViewResolver.forward("board/list", request, response);
	}

}