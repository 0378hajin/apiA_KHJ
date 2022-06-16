<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "jspstudy.domain.*" %>
    <%@ page import = "java.util.*" %>
<%

//select 쿼리를 사용하기 위해서 function에서 메서드를 만든다.
//memberSelectAll 메서드를 호출한다.
	//MemberDao md = new MemberDao();
	//ArrayList<MemberVo> alist = md.memberSelectAll();
	// out.println(alist.get(0).getMembername() + "<br>"); 확인용 메서드
	
	ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table, td, tr, th {
	border : 1px solid gray;
	border-collapse : collapse;
}
</style>
</head>
<body>

<%

if (session.getAttribute("midx") != null) {
	out.println("환영합니다!" + "<br>");
	out.println("회원 아이디 : " + session.getAttribute("memberId") + "<br>");
	out.println("회원 이름 : " + session.getAttribute("memberName") + "<br>");
	
	out.println("<a href = '"+request.getContextPath()+"/member/memberLogout.do'> 로그아웃</a>" + "<br>");
}
out.println("<a href = '"+request.getContextPath()+"/board/boardWrite.do'> 글쓰기</a>");
%>

<h1>회원 목록 리스트</h1>
<hr>
<table style = "width : 600px">


<tr>
<th>번호</th>
<th>회원 이름</th>
<th>회원 연락처</th>
<th>작성일</th>
</tr>

<%for(MemberVo mv : alist) {%>
<tr>
<td><%=mv.getMidx() %></td>
<td><%=mv.getMembername() %></td>
<td><%=mv.getMemberphone() %></td>
<td><%=mv.getWriteday() %></td>
</tr>
<% } %>
</table>
</body>
</html>