<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
	<html>
	  <head>
	    <meta charset="utf-8">
	    <title>Input</title>
	    <script>
	    function check() {	
	    	
	    	//Id에 값이 있는지 없는지 확인하는 구문.
	    	var fm = document.frm
	    	
	    	if(fm.memberName.value == ""){
	    		alert("이름을 입력해주세요.");
	    		fm.memberName.focus();
	    	} else if (fm.memberJumin.value == ""){
	    		alert("주민번호를 입력해주세요.");
	    		rm.memberJumin.focus();
	    	} else if (fm.memberId.value == ""){
	    		alert("아이디를 입력해주세요.");
	    		fm.memberId.focus();
	    	} else if (fm.memberPwd.value == ""){
	    		alert("비밀번호를 입력해주세요.");
	    		fm.memberPwd.focus();
	    	} else if (fm.memberMail.value == ""){
	    		alert("이메일을 입력해주세요.");
	    		fm.memberMail.focus();
	    	} else if (fm.memberPhone.value == ""){
	    		alert("전화번호를 입력해주세요.");
	    		fm.memberPhone.focus();
	    	} else if (fm.memberAddr.value == ""){
	    		alert("주소를 입력해주세요.");
	    		fm.memberAddr.focus();s
	    		return;
	    	} else {
	    		var chkYn = false; //기본값은 false
	    		for (var i = 0; i <fm.memberHobby.length; i++){
	    			//체크가 하나라도 되어있다면
	    			if (fm.memberHobby[i].checked == true){
	    				chkYn = true;
	    				break;
	    			}
	    		}
	    		if (chkYn == false) {
	    			alert("취미를 한개이상 선택해주세요.");
	    			return;
	    		}
	    	}
	    	//가상경로를 사용해서 페이지 이동시킨다.			
	    	//frm.action = "./memberJoinOk.jsp";
	    	fm.action = "<%=request.getContextPath()%>/member/memberJoinAction.do";
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
		</style>
	  </head>
	  <body>
	    <h1>회원가입 페이지</h1>
	    <hr>
	    <form name = "frm" action="memberJoinOk.jsp" method="post">
	      <!--
	      get 방식과 post 방식의 차이점은 정보를 넘길 때
	      주소창에 정보가 보이느냐,
	      안보이느냐 차이이다.
	      get은 보이고,
	      post는 안보인다.
	      -->
	      <fieldset>
	        <legend>회원가입 양식</legend>
	        <table>
	    <tr>
	    <td id = cen>
	    이름  
	    </td>
	    <td>
	    <input type="text" name="memberName" size="10" value="">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    주민번호  
	    </td>
	    <td>
	    <input type="text" name="memberJumin" size="10" value = "" placeholder = "xxxxxx-xxxxxxx">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    아이디  
	    </td>
	    <td>
	    <input type="text" name="memberId" size="10" value="">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    비밀번호  
	    </td>
	    <td>
	    <input type="password" name="memberPwd" size="10" value="">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    성별   
	    </td>
	    <td>
	    <input type="radio" name="memberGender" value="M" checked>남성
	    <input type="radio" name="memberGender" value="F">여성 
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    이메일 
	    </td>
	    <td> 
	    <input type="email" name="memberMail" size="20" placeholder = "ex)aaa@naver.com">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    전화번호 
	    </td>
	    <td> 
	    <input type="tel" name="memberPhone" size = "10" value = "" placeholder ="010-xxxx-xxxx">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    주소 
	    </td>
	    <td> 
	    <input type="text" name="memberAddr" size="20" value="">
	    </td>
	    </tr>
	    
	    <tr>
	    <td id = cen>
	    취미 
	    </td>
	    <td> 
	    <input type ="checkbox" name = "memberHobby" value ="야구">야구
	    <input type ="checkbox" name = "memberHobby" value ="축구">축구
	    <input type ="checkbox" name = "memberHobby" value ="농구">농구
	    </td>
	   	</tr>
	    </table>
	    <br>
	      <input type="button" name="btn" onclick = "check();"value="확인">
	    </fieldset>
	  </form>
	  </body>
	</html>
	