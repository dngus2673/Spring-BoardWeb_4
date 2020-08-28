package com.koreait.pjt.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;


@WebServlet("/join")
public class JoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       //화면에 보여주기 위해 사용
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.forward("user/join", request, response);
	}
	// 수정이나 값을 넣을 때 사용
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);
		String nm = request.getParameter("nm");
		String email = request.getParameter("email");
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(encrypt_pw);
		param.setNm(nm);
		param.setEmail(email);
		
		int result = UserDAO.insUser(param);
		System.out.println("result : " + result);
		
		if(result != 1) {
			request.setAttribute("msg", "에러 발생! 관리자에게 문의");
			request.setAttribute("data", param);
			doGet(request, response);
			return;
		}
		response.sendRedirect("/login");
	}

}
