<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.ArrayList" %>
    <%@ page import = "jspstudy.domain.*" %>
<% 
ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
PageMaker pm = (PageMaker)request.getAttribute("pm");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#tab {
	border : 1px solid gray;
	border-collapse : collapse;
}
</style>
</head>
<body>
<h1>게시글 목록 리스트</h1>

<form name = "frm" action = "<%=request.getContextPath() %>/board/boardList.do" method = "post">

<table border = 0 style = "width:600px; text-align:center;">
<tr>

<td style = "width:400px; text-align:right;">
<select name = "searchType">
<option value ="subject">제목</option>
<option value ="writer">작성자</option>
</select>
</td>

<td><input type = "text" name = "keyword" size = "15"></td>
<td><input type = "submit" name = "submit" value = "검색"></td>

</tr>
</table>

</form>

<hr>

<table id = tab style = "width : 600px">

<tr id = tab>
<th id = tab>bidx 번호</th>
<th id = tab>제목</th>
<th id = tab>작성자</th>
<th id = tab>작성일</th>
</tr>

<%for(BoardVo bv : alist) {%>
<tr id = tab>
<td id = tab><%=bv.getBidx() %></td>
<td id = tab>
<%
for(int i = 1; i <= bv.getLevel_(); i++){
	out.print("&nbsp;&nbsp;");
	if (i == bv.getLevel_()){
		out.println("└");
	}
}
//level 0 이면 답변이 아니다. 
//level 1 이면 &nbsp을 활용해서 한칸 옆으로 옮기고 'ㄴ'자 표시
///board/boardContent.do 이 가상경로에 bidx가 bv.getBidx()인 값을 넘긴다.
%>
<a href="<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx() %> "><%=bv.getSubject() %></a>
</td>
<td id = tab><%=bv.getWriter() %></td>
<td id = tab><%=bv.getWriteday() %></td>
</tr>
<% } %>

</table>

<table style = "width : 600px; text-align : center;">
<tr>
<td style = "width : 200px; text-align : right;" >
<%
if (pm.isPrev() == true) {
	out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+(pm.getStartPage()-1)+"&keyword="+pm.encoding(pm.getSCri().getKeyword())+"&searchType="+pm.getSCri().getSearchType()+"'>◀</a>");
	}
%>
</td>
<td> 
<%
for(int i = pm.getStartPage(); i<= pm.getEndPage(); i++){
	out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+i+"&keyword="+pm.encoding(pm.getSCri().getKeyword())+"&searchType="+pm.getSCri().getSearchType()+"'>"+i+"</a>");
	}
%>
<%=pm.getEndPage() %>
</td>
<td style = "width : 200px; text-align : left;" >
<%
if (pm.isNext() && pm.getEndPage() > 0) {
	out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+(pm.getEndPage()+1)+"&keyword="+pm.encoding(pm.getSCri().getKeyword())+"&searchTyp="+pm.getSCri().getSearchType()+"'>▶</a>");
	}
%>
</td>
</tr>
</table>

</body>
</html>