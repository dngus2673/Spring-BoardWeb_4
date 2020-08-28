<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 보기</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<style>
	.like { cursor: pointer; }
	.marginTop30 { margin-top: 35px; }
	#cmt { width: 580px; height: 40px; }
</style>
</head>
<body>
<!-- 목록, 수정, 삭제 button -->
	<h1>글 보기</h1>
	<div><a href="/board/list?page=${param.page}&record_cnt=${param.record_cnt}">목록</a></div>
	<div>
		<c:if test="${loginUser.i_user == data.i_user}">
			<a href="regmod?i_board=${data.i_board}">수정</a>
			<form id="delFrm" action="/board/del" method="post">
				<input type="hidden" name="i_board" value="${data.i_board}">
				<a href="#" onclick="submitDel()">삭제</a>
			</form>
			
		</c:if>
	</div>
<!-- detail 내용들, 좋아요  -->
	<div>제목 : ${data.title}</div>
	<div>작성일시 : ${data.r_dt}</div>
	<div>작성자 : ${data.nm}</div>
	<div> 조회수 : ${data.hits}</div>
	<div class="like" onclick="toggleLike(${data.yn_like})">
		<c:if test="${data.yn_like == 0}">
		<span class="material-icons"> favorite_border </span>
		</c:if>
		<c:if test="${data.yn_like == 1 }">
		<span class="material-icons" style="color: red;"> favorite </span>
		</c:if>
 	</div>
 	
	<br>
	<div> ${data.ctnt}</div>
	<br>
<!-- 댓글 목록들 -->
	<div class="marginTop30">
		<form id="cmtFrm" action="/board/cmt" method="post">
			<input type="hidden" name="i_cmt" value="0">
			<input type="hidden" name="i_board" value="${data.i_board}">
			<div>
				<input type="text" id="cmt"  name="cmt" value="${item.cmt}" placeholder="댓글내용">
				<input type="submit" id="cmtSubmit" value="등록">
				<input type="button" value="취소" onclick="clkCmtCancel()">
			</div>
		</form>
		<div class="marginTop30">댓글 목록</div>
		<div>
			<table>
				<tr>
					<th>내용</th>
					<th>글쓴이</th>
					<th>등록일</th>
					<th>비고</th>
				</tr>
				<c:forEach items="${cmtList}" var="item">
				<tr>
					<td>${item.cmt}</td>
					<td>${item.nm}</td>
					<td>${item.m_dt}</td>
					<td>
						<c:if test="${loginUser.i_user == cmtitem.i_user}">
							<button onclick="clkCmtMod(${item.i_cmt}, '${item.cmt}')">수정</button>
							<button onclick="clkCmtDel(${item.i_cmt})">삭제</button>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<script>
		function submitDel() {
			delFrm.submit()
		}
		function toggleLike(yn_like) {
			location.href = "/board/toggleLike?i_board=${data.i_board}&yn_like="+yn_like
			// 쿼리String => key = value값 같이 사용
		}
		//댓글 수정 취소
		function clkCmtMod(i_cmt, cmt) {
			cmtFrm.i_cmt.value = i_cmt
			cmtFrm.cmt.value = cmt
			
			cmtSubmit.value='수정'
		}
		//댓글 수정
		function clkCmtCancel() {
			cmtFrm.i_cmt.value = 0
			cmtFrm.cmt.value = ''
			cmtSubmit.value = '등록'
		}
		//댓글 삭제
		function clkCmtDel(i_cmt) {
			if(confirm('삭제 하시겠습니까?')){
				location.href = '/board/cmt?i_board=${data.i_board}&i_cmt=' + i_cmt
			}
		}
	</script>
</body>
</html>