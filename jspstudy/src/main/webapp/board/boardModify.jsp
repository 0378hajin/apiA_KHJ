<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "jspstudy.domain.BoardVo" %>
<%

BoardVo bv = (BoardVo)request.getAttribute("bv"); 

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시물 수정</title>
<script>
function check() {

	var fm = document.frm;

	if (fm.subject.value == ""){
		alert("제목을 입력해주세요.");
		fm.subject.focus();
		return;
	} else if (fm.content.value == ""){
		alert("내용을 입력해주세요");
		fm.content.focus();
		return;
	} else if (fm.writer.value == ""){
		alert("작성자를 입력해주세요");
		fm.writer.focus();
		return;
	} 
	
	fm.action = "<%=request.getContextPath()%>/board/boardModifyAction.do";
	fm.method = "post";
	fm.submit();
	
	return;
}
</script>
<style>
	table,td,tr,td {
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
<h1>게시판 게시물 수정</h1>
<hr>
<form name = "frm">
<input type = "hidden" name = "bidx" value = "<%=bv.getBidx() %>">
<table width="600" height="100">

<tr>
<th id = cen width = "100">제목</th>
<td><input type = "text" name = "subject" value = "<%=bv.getSubject() %>" size = "50"></td>
</tr>

<tr>
<th id = cen  width = "100">내용</th>
<td height = "400"><textarea name = "content" rows = "26" cols = "67"><%=bv.getContent() %></textarea></td>
</tr>

<tr>
<th id = cen  width = "100">작성자</th>
<td><input type = "text" name = "writer" value = "<%=bv.getWriter() %>" size = "20"></td>
</tr>

<tr>
<td colspan = "2">
<div>
 <input type = "button" name = "btn" value = "확인" onclick = "check();">
 </div>
 </td>
</tr>

</table>
</form>
</body>
</html>