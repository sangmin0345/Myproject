<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Jstl core태그를 사용을 위한 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body bgcolor="black">
	<center>
		<table width="1000" height="5">
			<tr height="50">
				<!-- 로고 이미지 -->
				<td align="left" width="500">
					<a href="ShoppingMain.jsp">
						<img alt="" src="img/logo.JPG" align="left">
					</a>
				</td>
				<!-- 회원 로그인 여부는? 세션값이 없으면? [로그인] [회원가입] 버튼 보이게 설정 -->
		<c:if test="${mbean==null}">
				<td align="right" width="300">
					<input type="button" value="로그인" onclick="location.href='login.do'">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="회원가입" onclick="location.href='joinform.do'">
				</td>
		</c:if>	
				<!-- 회원 로그인 여부는? 세션값이 있으면? ???님 환영 합니다. [로그아웃] 버튼이 보이게 설정 -->
		<c:if test="${mbean != null }">
				<td align="right" width="300">
					<font size="3" color="white">${mbean.memid}님 환영 합니다!!</font>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="로그아웃">
				</td>
		</c:if>					
				<!-- 검색 -->
				<form action="mainserch.do" method="post">
				<td align="right" width="200">
					<input type="text" name="msearch" size="10">
					<input type="submit" value="검색">
				</td>
				</form>
			</tr>
		</table>
		<!-- 메뉴들이 나타나는 공간  -->
		<table width="1000" height="5">
			<tr>
				<td align="center" bgcolor="black">
					<a href="sujak.do">
						<img alt="" src="img/sujak.JPG"> <!-- 수작업공구메뉴 -->
					</a>
				</td>
				<td align="center" bgcolor="black">
					<a href="jundong.do">
						<img alt="" src="img/jundong.JPG"> <!-- 전동공구메뉴 -->
					</a>
				</td>			
				<td align="center" bgcolor="black">
					<a href="stanlyinfo.do">
						<img alt="" src="img/stanlyinfo.JPG"> <!-- 스탠리 회사 소개 메뉴 -->
					</a>
				</td>
				<td align="center" bgcolor="black">
					<a href="tooluse.do">
						<img alt="" src="img/tooluse.JPG"> <!-- 공구 사용법 메뉴 -->
					</a>
				</td>
				<td align="center" bgcolor="black">
					<a href="download.do">
						<img alt="" src="img/download.JPG"> <!-- 다운로드 메뉴 -->
					</a>
				</td>
				<td align="center" bgcolor="black">
					<a href="board.do">
						<img alt="" src="img/gogak.JPG"> <!-- 고객센터 게시판 메뉴 -->
					</a>
				</td>															
			</tr>
		</table>
	</center>
</body>
</html>

