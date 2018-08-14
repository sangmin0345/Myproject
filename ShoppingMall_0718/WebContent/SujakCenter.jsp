	<%@page import="model.SuBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 
<%
	List<SuBean> list = (List<SuBean>)request.getAttribute("list");

%>

	<table width="800" border="0" height="470">
		<%
		for(int i = 0; i < list.size(); i++){
			if(i%4 == 0){
				%><tr align="center"><%
			}
			%>
			<td width="200" height="300" valign="top">
					<a href="suinfo.do?suno=<%=list.get(i).getSuno() %>"> 
						<img alt="" src="img/<%=list.get(i).getSuimg() %>" width="190" height="180">
					</a>
					<font color="white" size="2">
						공구명 : <%=list.get(i).getSuname() %>  <br>
						판매금액 : <%=list.get(i).getSuprice() %>  원					
					</font>
				</td>
			<%
		}
		
		%>
			<tr/>
	</table>
 --%>
	<table width="800" border="0" height="470">
		<c:set var="j" value="0"/>
		<c:forEach var="list" items="${list }">
			<c:if test="${j%4 == 0 }">
				<tr align="center">
			</c:if>
				<td width="200" height="300" valign="top">
					<a href="suinfo.do?suno=${list.suno }"> 
						<img alt="" src="img/${list.suimg}" width="190" height="180">
					</a>
					<font color="white" size="2">
						공구명 : ${list.suname } <br>
						판매금액 : ${list.suprice } 원					
					</font>
				</td>
				<c:set var="j" value="${j+1 }"/>
		</c:forEach>
			<tr/>
	</table>

</body>
</html>