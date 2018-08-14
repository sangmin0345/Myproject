<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
		<h2><font color="white">게시글 보기</font></h2>
		<table width="800" border="1" bordercolor="white" bgcolor="black">
			<tr height="40">
				<td colspan="5" align="right">
					<a href="boardWrite.do" style="text-decoration: none;">
						<font size="3" color="white">질문 글쓰기</font>
					</a>
				</td>
			</tr>
			<tr height="40">
				<th width="50"><font size="2" color="white">번호</font></th>
				<th width="300"><font size="2" color="white">제목</font></th>
				<th width="100"><font size="2" color="white">작성자</font></th>
				<th width="150"><font size="2" color="white">작성일</font></th>
				<th width="100"><font size="2" color="white">조회수</font></th>
			</tr>
		</table>
	</center>




</body>
</html>