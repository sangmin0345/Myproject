<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- ShoppingController.java에서.. joinproc메서드에서 리턴받아오 result값이 1일때 -->
<c:if test="${result == 1}">
	<script type="text/javascript">
		alert("해당아이디는 존재한다 이새끼야. 다른거해라"); 
	</script>
</c:if>

<c:if test="${result == 2}">
	<script type="text/javascript">
		alert("패스워드좀 확인해라 병신아");
	</script>
</c:if>




	<center>
		<h2><font color="white">회원가입</font></h2>
		<form action="joinproc.do" method="post">
			<table width="400" cellpadding="4" cellspacing="1">
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">아이디</font></td>
					<td width="220"><input type="text" name="memid" size="20"></td>
				</tr>
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">패스워드</font></td>
					<td width="220"><input type="text" name="mempasswd1" size="20"></td>
				</tr>
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">패스워드 확인</font></td>
					<td width="220"><input type="text" name="mempasswd2" size="20"></td>
				</tr>
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">이름</font></td>
					<td width="220"><input type="text" name="memname" size="20"></td>
				</tr>
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">전화번호</font></td>
					<td width="220"><input type="text" name="memphone" size="20"></td>
				</tr>
				<tr height="40">
					<td align="right" width="180"><font size="3" color="white">기념일</font></td>
					<td width="220"><input type="date" name="memdate" size="20"></td>
				</tr>
				<tr height="40">
					<td align="center" colspan="2">
						<input type="submit" value="회원가입">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="다시작성">
					</td>
				</tr>
			</table>
		</form>
	</center>

</body>
</html>