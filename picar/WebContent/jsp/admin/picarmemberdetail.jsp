<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
   <title>PICAR - Pick your CAR</title>
   <meta charset="utf-8" />
   <meta name="viewport" content="width=device-width, initial-scale=1" />
   <link rel="stylesheet" href="assets/css/main.css" />
   <link rel="apple-touch-icon" sizes="57x57" href="favicon//apple-icon-57x57.png">
   <link rel="apple-touch-icon" sizes="60x60" href="favicon//apple-icon-60x60.png">
   <link rel="apple-touch-icon" sizes="72x72" href="favicon//apple-icon-72x72.png">
   <link rel="apple-touch-icon" sizes="76x76" href="favicon//apple-icon-76x76.png">
   <link rel="apple-touch-icon" sizes="114x114" href="favicon//apple-icon-114x114.png">
   <link rel="apple-touch-icon" sizes="120x120" href="favicon//apple-icon-120x120.png">
   <link rel="apple-touch-icon" sizes="144x144" href="favicon//apple-icon-144x144.png">
   <link rel="apple-touch-icon" sizes="152x152" href="favicon//apple-icon-152x152.png">
   <link rel="apple-touch-icon" sizes="180x180" href="favicon//apple-icon-180x180.png">
   <link rel="icon" type="image/png" sizes="192x192"  href="favicon/android-icon-192x192.png">
   <link rel="icon" type="image/png" sizes="32x32" href="favicon/favicon-32x32.png">
   <link rel="icon" type="image/png" sizes="96x96" href="favicon/favicon-96x96.png">
   <link rel="icon" type="image/png" sizes="16x16" href="favicon/favicon-16x16.png">
   <link rel="manifest" href="favicon/manifest.json">
   <meta name="msapplication-TileColor" content="#ffffff">
   <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
   <meta name="theme-color" content="#ffffff">
   <style>
   .asd{
		color: black;
	}
   .qwe{
      color: white;
      text-decoration: none;
   };
   .qwe:hover {
      color: black;
   };
   </style>
</head>
<body class="subpage">
   <!-- Header -->
      <header id="header" class="alt">
         <div class="logo"><a href="index.jsp">PICAR <span>Pick your CAR</span></a></div>
         <a href="#menu"><span>Menu</span></a>
      </header>

   <!-- Nav -->
      <nav id="menu">
			<ul class="links">
			   <li><a href="carlistloc">차량리스트</a></li>   
			   <li><a href="question_req_list?reqPage=1">회원 게시판</a></li>
			   			   
			   <c:if test="${picarmember.gradeNo==30}">  			  	         
			      <li><a href="question_req_admin_list.do?reqPage=1">관리자 게시판</a></li>
			      <li><a href="member_list?reqPage=1">회원 관리</a></li>			                       
			      <li><a href="rentedList.do?reqPage=1">대여목록</a></li>
			      <li><a href="insertcar">차량등록</a></li>
			      <li><a href="allRentCar.do?reqPage=1">관리자 차량목록</a></li>
			   </c:if>   
			      <li><a href="myRentCar.do?membernum=${picarmember.memberNum }">내 차량</a></li>   
			   <c:if test="${picarmember ==null}">
			      <table>
			      <form action="login" method="post">
					
					<input type="text" name="id" id="id" placeholder="Username" /> <br />
					<input type="password" name="password" id="password" placeholder="password" /> 
					
					<tr>
					<td><input type="submit" value="로그인"></td>		
				</form>
				<td><input type="button" value="회원가입" onclick="location.href='sign_up'" /></td> 
				</tr>
				<tr align="center">
					&nbsp<a href="id_find" class="qwe">아이디찾기</a>&nbsp&nbsp&nbsp
					<a href="password_find" class="qwe">비밀번호 찾기</a>
				</tr>					
				</table>
				<p>${message}</p>
			   </c:if>   
			      
			  <br>
			   <c:if test="${picarmember !=null}">
			      <form action = "logout">
			      <input type = "submit" value="로그 아웃" /> 		
			      <input type="button" value="내 정보" onclick="location.href='member_infor?membernum=${picarmember.memberNum}'" />	         
			      </form>	
			     
			   </c:if>   
			</ul>
		</nav>

      <section id="post" class="wrapper bg-img" data-bg="banner2.jpg">
         <div class="inner">
            <article class="box">
               <header>
                  <center><h2>detail</h2></center>
               </header>
               <form action="picarmember_update?membernum=${picarmember.memberNum}" method="post">
				회원번호<input type="text" name="memberNum" value="${picarmember.memberNum}" disabled="disabled" /> <br /> 
				아이디<input type="text" name="id" value="${picarmember.id}" /> <br />
				비밀번호 <input type="text" name="password" value="${picarmember.password}" /> <br />
				이름 <input type="text" name="name" value="${picarmember.name}" /> <br /> 
				전화번호 <input type="text" name="phone" value="${picarmember.phone}" /> <br />
				면허증번호 <input type="text" name="license" value="${picarmember.license}" /><br />
				면허증 유효기간 <input type="text" name="validate" value="${picarmember.validate}" /> <br />
		
				 회원등급 
			  	<select name="membergrade">
					<c:forEach var="member" items="${membergrades}">
					
						<c:if test="${picarmember.gradeNo == member.gradeNo}">
							<option value="${member.gradeNo}" selected="selected" class="asd">${member.memberGrade}</option>		
						</c:if>	
				
					<c:if test="${picarmember.gradeNo != member.gradeNo}">
						<option value="${member.gradeNo}" class="asd">${member.memberGrade}</option>
					</c:if>	
					
					</c:forEach>
				</select>   	
				<br />
				<div align="center"><input type="submit" class="btn btn-primary" value="수정" /></div> 
				<div align="right"><button><a href="picarmember_delete?membernum=${picarmember.memberNum}">삭제</a></button></div> 
	
				</form>
	            </article>
         </div>
      </section>
      <footer id="footer">
			<div class="inner" align="center">
				(주)피카 대표자:임동건
				<div class="copyright">
					&copy; Untitled. Design: <a href="https://templated.co">TEMPLATED</a>. Images: <a href="https://unsplash.com">Unsplash</a>.
				</div>

			</div>
		</footer>
	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.scrolly.min.js"></script>
	<script src="assets/js/jquery.scrollex.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
   </body>
</html>

