<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "jspstudy.domain.BoardVo" %>
<%

	 String bidx = (String)request.getAttribute("bidx");
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 삭제</title>
<style>
	table {
	    border : 1px solid gray;
		border-radius : 10px;
		width : 500px; 
		height : 100px; 
		margin : 0 auto;
	}
	h1 {
	text-align : center;
	}
	div {

	border-radius : 50px;
	}
	
 hr{
  border: 0;
  height: 1px;
  background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0));
}
 
	
	.wrap{padding:50px;text-align:center;background:"white"}

/* TOOLTIP */
[data-tooltip]{position:relative;}
[data-tooltip]:before,
[data-tooltip]:after{visibility:hidden;opacity:0;position:absolute;left:50%;transform:translateX(-50%);white-space:nowrap;transition:all .2s ease;font-size:11px;font-family:dotum;letter-spacing:-1px;}
[data-tooltip]:before{content:attr(data-tooltip);height:13px;position:absolute;top:-20px;padding:5px 10px;border-radius:5px;color:#fff;background:#025272;box-shadow:0 3px 8px rgba(165, 165, 165, 0.5);}
[data-tooltip]:after{content: '';border-left:5px solid transparent;top:2px;border-right:5px solid transparent;border-top:5px solid #025272;}
[data-tooltip]:not([data-tooltip=""]):hover:before{visibility:visible;opacity:1;top:-30px}
[data-tooltip]:not([data-tooltip=""]):hover:after{visibility:visible;opacity:1;top:-8px}

</style>
<script>
function check(){

	var fm = document.del;
	
	fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do";
	fm.method = "post";
	fm.submit();
	
	return;

}
</script>
</head>
<body>
<h1>게시물 삭제</h1>
<hr>
<form name = "del">
<input type = "hidden" name = "bidx" value = "<%=bidx %>">
<div>
<table>
<tr>
<td colspan = 2 style = "text-align : center;"><div class = "wrap"><div><span data-tooltip="삭제">삭제하시겠습니까?</span></div></div></td>
</tr>

<tr style = "text-align : center;">
<td><input type = "button" name = "btn1" onclick = "check();" value = "확인"> </td>
<td><input type = "button" name = "btn" onclick = "location.href = '<%=request.getContextPath() %>/board/boardContent.do?bidx=<%=bidx %>'" value = "취소"></td>
</tr>

</table>
</div>
</form>

</body>
</html>