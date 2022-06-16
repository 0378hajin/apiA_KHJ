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
		//�ּ��� Ǯ ��θ� �����ϴ� �޼���
		String uri = request.getRequestURI();
		//������Ʈ �̸��� ����
		String pj = request.getContextPath();
		//������Ʈ �̸��� �� ������ ���� ��θ� ����
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
					out.println("<script>alert('ȸ������ ����'); location.href = '"+request.getContextPath()+"/index.jsp'</script>");
				} else {
					//response.sendRedirect(request.getContextPath() + "/member/memberJoin.do");
					out.println("<script>alert('�ٽ� �ۼ����ּ���.'); location.href = '"+request.getContextPath()+"/index.jsp'</script>");
				}
		} else if (command.equals("/member/memberJoin.do")) {
				//ȸ������ ���������� ������ ó���ϴ� ����
			
				//�������� �̵���Ű�� ���
				RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
				//forward ���
				rd.forward(request,response);
			
		} else if (command.equals("/member/memberList.do")) {
			
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			request.setAttribute("alist", alist);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request,response);
			
		} else if (command.equals("/member/memberLogin.do")) {
			//�������� �̵���Ű�� ���
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
			//forward ���
			rd.forward(request,response);
	
		} else if (command.equals("/member/memberLoginAction.do")) { 
			//1.�Ѿ�� ���̵�� ��й�ȣ ���� �޴´�.
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			//2.ó���ϱ�
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			
			//���� - �� ���� ���� �� �����̳ʿ� ���¸� �����ϱ� ���� ������ �����ϴ°�
			//�������� ���� �ʴ� �̻� ���� ��� �����´�. �Ǵ� �α׾ƿ��� �Ѵ�.
			HttpSession session = request.getSession();
			//3.�̵�
			if (mv != null) {
				session.setAttribute("midx", mv.getMidx());
				session.setAttribute("memberId", mv.getMemberid());
				session.setAttribute("memberName", mv.getMembername());
				
				//�Խ��� �۾��⸦ ������ �α����� �ȵ��� �� �α��� �� �� ���� ������ ���� ������.
				if(session.getAttribute("saveUrl") != null) {//���� �ִٸ�
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
//forward : �������� �̵� ��Ű�� �� 
//Redirect : ó���� ������ �̵� ��Ű�� ��
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}