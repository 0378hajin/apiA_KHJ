<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("midx") == null){
	session.setAttribute("saveUrl", request.getRequestURI());
	out.println("<script> alert('로그인을 해주세요.'); location.href = '"+request.getContextPath()+"/member/memberLogin.do'</script>");
	
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
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
	
	fm.action = "<%=request.getContextPath()%>/board/boardWriteAction.do";
	fm.method = "post";
	fm.enctype = "multipart/form-data";//enctype으로 파일을 넘길수 있다.
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
<h1>게시판 글쓰기</h1>
<hr>
<form name="frm">
<table style ="width : 600px; height : 100px;">

<tr>
<td id = cen bgcolor = "#D9E5FF">제목</td>
<td><input type = "text" name="subject" value = "" size = 40 id = color ></td>
</tr>

<tr>
<td id = cen bgcolor = "#D9E5FF">내용</td>
<td><textarea name="content" cols="70" rows="15" placeholder = "내용을 입력해주세요."></textarea></td>
</tr>

<tr>
<td id = cen bgcolor = "#D9E5FF">작성자</td>
<td><input type = "text" name = "writer" size = 20 id = color value = "<%=session.getAttribute("memberName")%>" readonly></td>
</tr>

<tr>
<td id = cen bgcolor = "#D9E5FF">파일</td>
<td><input type = "file" name = "filename"></td>
</tr>

<tr>
<td colspan = "2"><div><input type="button" name="btn" onclick = "check();" value="확인" id = btu> <input type="reset" value = "리셋" id = btu2></div></td>
</tr>

</table>
</form>
</body>
</html>