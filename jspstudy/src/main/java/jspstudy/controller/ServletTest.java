package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ServletTest")
// HttpServlet를 상속하고 있기 때문에 페이지를 띄울수 있다.
public class ServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//response.getWriter().append("Served at: ").append(request.getContextPath()).append("first servlet");
		
		//자바로 html 만들기
		//글자깨짐을 방지해주는 메서드
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = UTF-8");
		//자바 안에서 html 코드를 사용할 수 있도록 해줌
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ "<head>"
				+ "<title>서블릿</title>"
				+ "</head>"
				+ "<body>"
				+ "<h1>안녕하세요</h1>"
				+ "</body>"
				+ "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
