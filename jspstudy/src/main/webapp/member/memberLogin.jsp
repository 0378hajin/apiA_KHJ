<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인 하기</title>
<script>
function check() {	
	    	
var fm = document.frm
	    	
	    	if (fm.memberId.value == ""){
	    		alert("아이디를 입력해주세요.");
	    		fm.memberId.focus();
	    		return;
	    	} else if (fm.memberPwd.value == ""){
	    		alert("비밀번호를 입력해주세요.");
	    		fm.memberPwd.focus();
	    		return;
	    	}
	
	    	fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.do";
	    	fm.method = "post";
	    	fm.submit();
	    	
	    	return;
	    }
</script>
<style>
	table,td,tr {
	    border-collapse : collapse;
	    border : none;  
	}
	#cen{
		text-align : center;
		height : 15px;
	}
		input[type = text]{
	border :1px solid gray;
	border-radius : 5px;
	}
	input[type = text]:focus{
	background-color : aliceblue;
	}
	input[type = password]{
	border :1px solid gray;
	border-radius : 5px;
	}
	input[type = password]:focus{
	background-color : aliceblue;
	}
	
</style>
</head>
<body>
<h1>로그인 하기</h1>
<hr>
<form name = "frm" action="memberJoinOk.jsp" method="post">

<table style = "margin : auto; width : 500px; height : 200px;">

<tr>
<td id = cen>
I D  
</td>
<td>
<input type="text" name="memberId" size="10" value="" id = color>
</td>
</tr>
	    
<tr>
<td id = cen>
비밀번호
</td>
 <td>
<input type="password" name="memberPwd" size="10" value="" id = color>
 </td>
</tr>

<tr style = "text-align : center;">
<td colspan = 2 ><input type="button" name="btn" onclick = "check();"value="확인"></td>
</tr>

</table>


 </form>
</body>
</html>
	