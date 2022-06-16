<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "jspstudy.domain.BoardVo" %>
<%
	BoardVo bv = (BoardVo)request.getAttribute("bv");
    System.out.println(bv);
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시물 보기</title> b
<style>
	table,td,tr {
	    border-collapse : collapse;
	    border : 1px solid black;
	}
	div {
		display : block;
		text-align : right;
	}
	#cen {
		text-align : center;
	}
</style>
</head>
<body>
<h1>게시판 게시물 보기</h1>
<hr>
<form>
<table width="600" height="100">

<tr>
<th id = cen width = "100">제목</th>
<td><%=bv.getSubject() %></td>
</tr>

<tr>
<th id = cen  width = "100">내용</th>
<td height = "200"><%=bv.getContent() %></td>
</tr>

<tr>
<th id = cen  width = "100">파일 다운로드</th>
<td height = "400">
<%if(bv.getFilename() != null){ %>
<img src = "<%=request.getContextPath() %>/images/<%=bv.getFilename() %>">

<a href = "<%=request.getContextPath()%>/board/fileDownload.do?filename=<%=bv.getFilename()%>"><%=bv.getFilename()%></a>


<%} %>
</td>
</tr>

<tr>
<th id = cen  width = "100">작성자</th>
<td><%=bv.getWriter() %></td>
</tr>

<tr>
<td colspan = "2">
<div>
 <input type = "button" name = "btn1" value = "수정" onclick = "location.href = '<%=request.getContextPath() %>/board/boardModify.do?bidx=<%=bv.getBidx()%>'">
 <input type = "button" name = "btn2" value = "삭제" onclick = "location.href = '<%=request.getContextPath() %>/board/boardDelete.do?bidx=<%=bv.getBidx()%>'"> 
 <input type = "button" name = "btn3" value = "답변" onclick = "location.href = '<%=request.getContextPath() %>/board/boardReply.do?bidx=<%=bv.getBidx()%>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth() %>&level_=<%=bv.getLevel_()%>'"> 
 <input type = "button" name = "btn4" value = "목록" onclick = "location.href = '<%=request.getContextPath() %>/board/boardList.do'">
 </div>
 </td>
</tr>

</table>
</form>
</body>
</html>