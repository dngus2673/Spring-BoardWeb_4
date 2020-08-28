<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
	<style>
		.itemRow:hover { background-color: #ecf0f1; 
								  cursor: pointer; }
		.Pcnt { text-align: center; }
		a { text-decoration: none; color: ; }
		.mButton {
				box-shadow: 0px 0px 0px 2px #9fb4f2;
				background:linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
				background-color:#7892c2;
				border-radius:10px;
				border:1px solid #4e6096;
				display:inline-block;
				cursor:pointer;
				color:#ffffff;
				font-family:Arial;
				font-size:19px;
				padding:12px 37px;
				text-decoration:none;
				text-shadow:0px 1px 0px #283966; }
		.mButton:hover {
					background:linear-gradient(to bottom, #476e9e 5%, #7892c2 100%);
					background-color:#476e9e; }
		.mButton:active {
						position:relative;
						top:1px; }
	</style>
</head>
<body>
	<h2>&nbsp;<span style="color: #0066cc;">${loginUser.nm}</span>&nbsp;
	님 환영합니다.</h2>
	<div><a href="/logout">LogOut</a></div>
	<div>
		<!--  ${param.page == null ? 1 : param.page}
		${param.record_cnt == item || (param.record_cnt == null && item == 10)}-->
		<form id="selFrm" action="/board/list" method="get">
			<input type="hidden" name="page" value="${param.page == null ? 1 : param.page }">
			레코드 수 :
			<select name="record_cnt" onchange="changeRecord()">
			
			<c:forEach begin="10" end="30" step="10" var="item">
				<c:choose>
					<c:when test="${ param.record_cnt == item }">
						<option value="${item}" selected> ${item} 개</option>	
					</c:when>
					<c:otherwise>
						<option value="${item}"> ${item} 개</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</select>
		</form>
	</div>
	<div>
		<a href="regmod">글 작성</a>
	</div>
	<h1>List</h1>
	<table>
		<tr>
			<th>NO</th>
			<th>제목</th>
			<th>조회수</th>
			<th>작성자</th>
			<th>등록일</th>
		</tr>
		<c:forEach items="${list}" var="item">
		<tr  class="itemRow" onclick="moveToDetail(${item.i_board})">
			<td>${item.i_board}</td>
			<td>${item.title}</td>
			<td>${item.hits}</td>
			<td>${item.nm}</td>
			<td>${item.r_dt == item.m_dt? item.r_dt:item.m_dt}</td>
		</tr>
		</c:forEach>
	</table>
	<div>
		<form action="/board/list">
			<input type="search" value="${param.searchText}" name="searchText">
			<input type="hidden" name="record_cnt" value="${param.record_cnt}">
			<input type="submit" value="검색">
		</form>
	</div>
	<div class="Pcnt">
		<c:forEach var="item" begin="1" end="${pagingCnt}">
			<c:choose>
				<c:when test="${param.page == item} || ${param.page == null && item == 1 }">
					<span>${item}</span>
				</c:when>
				<c:otherwise>
					<span>
						<a class="mButton" href="/board/list?page=${item}&record_cnt=${param.record_cnt}&searchText=${param.searchText}">${item}</a>
					</span>
				</c:otherwise>
			</c:choose>	
		</c:forEach>
	</div>
	<script>
		function moveToDetail(i_board) {
			location.href = '/board/detail?page=${page}&record_cnt=${param.record_cnt}&searchText=${param.searchText}&i_board='+ i_board
		}
		function changeRecord() {
			selFrm.submit()
		}
	</script>
</body>
</html>