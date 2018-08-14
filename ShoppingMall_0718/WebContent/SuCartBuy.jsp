<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
		<h2><font color="yellow">결제 완료 페이지</font></h2>
		
		<table width="800">
			<tr height="40">
				<td align="center" width="200"><font size="2" color="white">상품 이미지</font></td>
				<td align="center" width="200"><font size="2" color="white">상품명</font></td>
				<td align="center" width="100"><font size="2" color="white">상품가격</font></td>
				<td align="center" width="100"><font size="2" color="white">상품수량</font></td>
				<td align="center" width="200"><font size="2" color="white">상품총금액</font></td>
			</tr>
			
<!-- 총결제 금액 저장 변수 -->
<c:set var="total" value="${0 }"/>

<!-- sucartBuy메서드에서 전달받은 cart객체가 존재하면 cart안에 있는 itemlist사이즈만큼 반복하여 수작업공구 출력, 총 결제 금액 출력 -->
<c:if test="${cart != null }">

		<c:forEach var="item" items="${cart.itemlist }">
			<tr height="80">
				<td align="center" width="200"><img alt="" src="img/${item.suimg }" width="180" height="70"></td>
				<td align="center" width="200"><font size="2" color="white">${item.suname }</font></td>
				<td align="center" width="100"><font size="2" color="white">${item.suprice }원</font></td>
				<td align="center" width="100"><font size="2" color="white">${item.suqty }개</font></td>
				<td align="center" width="200">
				<font size="2" color="white">${item.suprice * item.suqty }원</font>
				</td>
			</tr>
			
<!-- 기존 total변수에 누적시켜야함 -->
<c:set var="total" value="${total + (item.suprice * item.suqty)}"/>

		</c:forEach>


</c:if>			

			<tr height="70">
				<td align="center" colspan="5">
					<font color="red" size="5">총결제 금액 = ${total }원 입니다.</font>
				</td>
			</tr>
			<tr height="70">
				<td align="center" colspan="5">                  <!-- 계산완료하기 버튼 누르면  결제가 진행되어 카트에 있는 상품 모두 지우기 -->
					<input type="button" value="계산 완료하기" onclick="location.href='cartaldel.do'">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="취소하기" onclick="location.href='index.do'">
				</td>
			</tr>
			
		</table>
		
	</center>


</body>
</html>