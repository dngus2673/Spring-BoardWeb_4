package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Param;

import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

public class BoardDAO {
	
	public static int insBoard(BoardVO param) {

		String sql = " INSERT INTO t_board4 "
				+" (i_board, title, ctnt, i_user) "
				+" VALUES "
				+" (seq_board4.nextval, ?, ?, ?) ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setNString(2, param.getCtnt());
				ps.setInt(3, param.getI_user());
			}
		});
	}
	
	public static int delBoard(final BoardVO param){
		
		String sql = " DELETE FROM t_board4 "
				+ " WHERE i_board = ? AND i_user = ? ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getI_user());
			}
		});
	}
	
	public static List<BoardDomain> selBoardList(BoardDomain param){
		//final 주소값 변경 불가
		// 객체 추가 가능
		List<BoardDomain> list = new ArrayList();
		String sql = " SELECT A.* FROM ( "
				+ " SELECT ROWNUM as RNUM, A.* FROM ( "
					+ " SELECT A.i_board, A.title, A.hits, A.i_user, A.r_dt, A.m_dt, B.nm "
						+ " FROM t_board4 A"
						+ " INNER JOIN t_user B "
						+ " ON A.i_user = B.i_user "
						+ " WHERE A.title LIKE ? "
						+ " ORDER BY i_board DESC "
					+ " ) A "
					+ " WHERE ROWNUM <= ? "
				+ " ) A "
				+ " WHERE A.RNUM > ? ";

	 int result =	JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getSearchText());
				ps.setInt(2, param.getEIdx());
				ps.setInt(3, param.getSIdx());
			}
				
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					int i_board = rs.getInt("i_board");
					String title = rs.getNString("title");
					int hits = rs.getInt("hits");
					String nm = rs.getNString("nm");
					int i_user = rs.getInt("i_user");
					String r_dt = rs.getNString("r_dt");
					String m_dt = rs.getNString("m_dt");
					
					BoardDomain vo = new BoardDomain();
					
					vo.setI_board(i_board);
					vo.setTitle(title);
					vo.setHits(hits);
					vo.setNm(nm);
					vo.setI_user(i_user);
					vo.setR_dt(r_dt);
					vo.setM_dt(m_dt);
					
					list.add(vo);
				}
				return 1;
			}
		});
		return list;
	}
	
	public static BoardDomain selBoard(final BoardVO param) {
		final BoardDomain bdm = new BoardDomain();
		bdm.setI_board(param.getI_board());
		
		String sql = "SELECT A.i_user, B.nm "
				+ " , A.title, A.ctnt, A.hits, TO_CHAR(A.r_dt, 'YYYY/MM/DD HH24:MI') as r_dt "
				+ " , DECODE(C.i_user, null, 0, 1) as yn_like "
				+ " FROM t_board4 A "
				+ " INNER JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " LEFT JOIN t_board4_like C "
				+ " ON A.i_board = C.i_board "
				+ " AND C.i_user = ? "
				+ " WHERE A.i_board = ? ";
			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_user());
				ps.setInt(2, param.getI_board());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				
				if(rs.next()) {
					bdm.setI_user(rs.getInt("i_user")); // 작성자 i_user
					bdm.setNm(rs.getNString("nm"));
					bdm.setTitle(rs.getNString("title"));
					bdm.setCtnt(rs.getNString("ctnt"));
					bdm.setHits(rs.getInt("hits"));
					bdm.setR_dt(rs.getNString("r_dt"));
					bdm.setYn_like(rs.getInt("yn_like"));
				}
				return 1;
			}
		});
		
		 return bdm;
	}
	// page
	public static int selPagingCnt(final BoardDomain param) {
		String sql = " SELECT CEIL(COUNT(i_board) / ?) FROM t_board4 "
				+ " WHERE title LIKE ? ";
			//1행1열만 있는것 스칼라 값
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getRecord_cnt());
				ps.setNString(2, param.getSearchText());
				// 자동으로 ''를 넣어줌
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		});
	}

	public static int updBoard(final BoardVO param) {
		
		String sql = " UPDATE t_board4 "
				+ " SET m_dt = sysdate, title = ?, ctnt = ? "
				+ " WHERE i_board = ? AND i_user = ? ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setNString(2, param.getCtnt());
				ps.setInt(3, param.getI_board());
				ps.setInt(4, param.getI_user());
			}
		});
	}
	
	public static void addHits(final int i_board) {
		String sql = " UPDATE t_board4 "
				+ " SET hits = hits + 1 "
				+ " WHERE i_board = ? ";
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
			}

		});
	}
	
	// 좋아요 추가, 삭제
	public static int insBoardLike(BoardVO param) {
		String sql = " INSERT INTO t_board4_like "
				+ " (i_user, i_board) "
				+ " VALUES (?, ?) ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_user());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	public static int delBoardLike(BoardVO param) {
		String sql = " DELETE FROM t_board4_like "
				+ " WHERE i_user = ? AND i_board = ? ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_user());
				ps.setInt(2, param.getI_board());
				
			}
		});
	}
}