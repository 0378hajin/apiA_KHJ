package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jspstudy.domain.MemberVo;
import jspstudy.service.MemberDao;

@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = UTF-8");
		//주소의 풀 경로를 추출하는 메서드
		String uri = request.getRequestURI();
		//프로젝트 이름을 추출
		String pj = request.getContextPath();
		//프로젝트 이름을 뺀 나머지 가상 경로를 추출
		String command = uri.substring(pj.length());
		System.out.println("command : " + command);
		
		if (command.equals("/member/memberJoinAction.do")) {

				String memberName = request.getParameter("memberName");
				String memberJumin = request.getParameter("memberJumin");
				String memberId = request.getParameter("memberId");
				String memberPwd = request.getParameter("memberPwd");
				String memberGender = request.getParameter("memberGender");
				String memberMail = request.getParameter("memberMail");
				String memberPhone = request.getParameter("memberPhone");
				String memberAddr = request.getParameter("memberAddr");
				String[] memberHobby = request.getParameterValues("memberHobby");
				
				String hobby = "";
				for(int i = 0; i <memberHobby.length; i++){
					hobby = hobby + "," + memberHobby[i];
					//out.println(memberHobby[i] + "<br>");
				}
				hobby = hobby.substring(1);
				String ip = InetAddress.getLocalHost().getHostAddress();
				
				MemberDao md = new MemberDao();
				int value = md.insertMember(memberId,memberPwd,memberName,memberMail,memberGender,memberAddr,memberPhone,memberJumin,hobby,ip);
				PrintWriter out = response.getWriter();
				
				if (value == 1) {
					//response.sendRedirect(request.getContextPath() + "/member/memberList.do");
					out.println("<script>alert('회원가입 성공'); location.href = '"+request.getContextPath()+"/index.jsp'</script>");
				} else {
					//response.sendRedirect(request.getContextPath() + "/member/memberJoin.do");
					out.println("<script>alert('다시 작성해주세요.'); location.href = '"+request.getContextPath()+"/index.jsp'</script>");
				}
		} else if (command.equals("/member/memberJoin.do")) {
				//회원가입 페이지에서 들어오면 처리하는 구문
			
				//페이지를 이동시키는 방법
				RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
				//forward 방식
				rd.forward(request,response);
			
		} else if (command.equals("/member/memberList.do")) {
			
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			request.setAttribute("alist", alist);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request,response);
			
		} else if (command.equals("/member/memberLogin.do")) {
			//페이지를 이동시키는 방법
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
			//forward 방식
			rd.forward(request,response);
	
		} else if (command.equals("/member/memberLoginAction.do")) { 
			//1.넘어온 아이디와 비밀번호 값을 받는다.
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			//2.처리하기
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			
			//세션 - 웹 서버 쪽의 웹 컨테이너에 상태를 유지하기 위한 정보를 저장하는것
			//브라우저을 끄지 않는 이상 값을 계속 가져온다. 또는 로그아웃을 한다.
			HttpSession session = request.getSession();
			//3.이동
			if (mv != null) {
				session.setAttribute("midx", mv.getMidx());
				session.setAttribute("memberId", mv.getMemberid());
				session.setAttribute("memberName", mv.getMembername());
				
				//게시판 글쓰기를 누르고 로그인이 안됐을 때 로그인 후 그 세션 값으로 돌려 보낸다.
				if(session.getAttribute("saveUrl") != null) {//값이 있다면
				response.sendRedirect((String) session.getAttribute("saveUrl"));
				} else {
				response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
			 }	
		}  
	} else if (command.equals("/member/memberLogout.do")) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
	}
		
}
//forward : 페이지를 이동 시키는 것 
//Redirect : 처리가 끝나면 이동 시키는 것
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}