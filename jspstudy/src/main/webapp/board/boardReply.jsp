<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "jspstudy.domain.BoardVo" %>
<%

BoardVo bv = (BoardVo)request.getAttribute("bv"); 

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 답변하기</title>
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
	
	fm.action = "<%=request.getContextPath()%>/board/boardReplyAction.do";
	fm.method = "post";
	fm.submit();
	
	return;
}
</script>
<style>
	table,td,tr {
	    border-collapse : collapse;
	    border : 1px solid black; 
	    
	}
		#cen{
		text-align : center;
	}
		#cen2 {
		 display: block;
		 margin : auto;
		 height : 100%;
		}
		div{
			display : block;
			 text-align:center;
		}
		#color {
			background-color : Aliceblue;
		}
		#btu {
		background-color: white;
		border-color : white;
		width : 70pt;
		
		}
		#btu2 {
		background-color: Aliceblue;
		border-color : white;
		width : 70pt;
		}
		</style>
</head>
<body>
<h1>게시판 답변하기</h1>
<hr>
<form name="frm">
<input type = hidden name = "bidx" value = "<%=bv.getBidx()%>">
<input type = hidden name = "originbidx" value =  "<%=bv.getOriginbidx()%>">
<input type = hidden name = "depth" value = "<%=bv.getDepth()%>">
<input type = hidden name = "level_" value = "<%=bv.getLevel_()%>">
<table width="600" height="100">

<tr>
<td id = cen bgcolor = "#D9E5FF">제목</td>
<td><input type = "text" name="subject" value = "" size = 40 id = color></td>
</tr>

<tr>
<td id = cen bgcolor = "#D9E5FF">내용</td>
<td><textarea name="content" cols="70" rows="15" placeholder = "내용을 입력해주세요."></textarea></td>
</tr>

<tr>
<td id = cen bgcolor = "#D9E5FF">작성자</td>
<td><input type = "text" name = "writer" value = "" size = 20 id = color></td>
</tr>

<tr>
<td colspan = "2"><div><input type="button" name="btn" onclick = "check();" value="확인" id = btu> <input type="reset" value = "리셋" id = btu2></div></td>
</tr>

</table>
</form>
</body>
</html>