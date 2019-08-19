<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8>
<title>picar index</title>
</head>
<body>
	
	<a href="question_req_list?reqPage=1">회원 게시판 이동</a><br/>
	<a href="question_req_admin_list?reqPage=1">관리자 게시판 이동</a>
	<ul>
		<li><a href="login_input">로그인</a></li> 		
		<li><a href="rentedList.do?reqPage=1">대여목록</a></li>
		<li><a href="myRentCar.do?membernum=${picarmember.memberNum }">내 차량</a></li>
	</ul>		
	<p>${picarmember.id}</p>
	<c:if test="${picarmember !=null}">
		<form action = "logout">
		<input type = "submit" value="로그 아웃" />
		<input type = "button" value="회원 정보 수정" />			
		</form>
	</c:if>	
	
</body>
</html>

