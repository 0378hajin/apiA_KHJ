/*컨드롤러의 역할
 * 1. 넘어온 값
 * 2. 처리
 * 3. 이동(새로 이동해야하기 때문에 send - redirect로 이동한다)
*/
package jspstudy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.PageMaker;
import jspstudy.domain.SearchCriteria;
import jspstudy.service.BoardDao;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = UTF-8");
		//가상경로 추출
		//주소의 풀 경로를 추출하는 메서드
		String uri = request.getRequestURI();
		//프로젝트 이름을 추출
		String pj = request.getContextPath();
		//프로젝트 이름을 뺀 나머지 가상 경로를 추출
		String command = uri.substring(pj.length());
		System.out.println("command : " + command);
		
		String uploadPath = "D:\\openAPI(A)\\dev\\jspstudy\\src\\main\\webapp\\";
		// uploadPath는 이미지 저장경로
		String saveFolder = "images";
		String saveFullPath = uploadPath+saveFolder;
		//if 구문 안에 있었지만 전역변수로 사용하기 위해 빼놓음
		if(command.equals("/board/boardWrite.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response);
			
		} else if (command.equals("/board/boardWriteAction.do")){

			
			int sizeLimit = 1024*1024*15;
			//파일 사이즈를 15메가바이트로 리밋
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, saveFullPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
			//DefaultfileRenamePolicy은 이름이 중복 됐을 때 사용하는 정책
			
			String subject = multi.getParameter("subject");
			System.out.println("subject" + subject);
			String content = multi.getParameter("content");
			String writer = multi.getParameter("writer");
			Enumeration files = multi.getFileNames();
			//Enumeration 변수로만 이루어져있는 클래스, 열거자
			//열거자에 저장된 파일을 담는 객체를 생성한다. 
			String file = (String)files.nextElement();
			//담긴 파일의 객체의 파일 이름을 얻는다. 
			String fileName = multi.getFilesystemName(file);
			//파일이름을 뽑아낼 수 있는 메서드
			String originFileName = multi.getOriginalFileName(file);
			// 파일의 원래 이름을 알아낼 수 있는 메서드
			String ip = InetAddress.getLocalHost().getHostAddress();
			HttpSession session = request.getSession();
			int midx = (int) session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.insertBoard(subject, content, writer, ip, midx, fileName);

		if (value == 1) {
			response.sendRedirect(request.getContextPath() + "/board/boardList.do");
		} else {
				response.sendRedirect(request.getContextPath() + "/board/boardWrite.do");
			}
		
		} else if (command.equals("/board/boardList.do")) {
			System.out.println("리스트에 들어옴");
			//페이지를 가지고 다녀야함
			String page = request.getParameter("page");
			if(page == null) {
				page = "1";
			}
			int pagex = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");
			if (keyword == null) keyword = "";
			String searchType = request.getParameter("searchType");
			if (searchType == null) searchType = "";
			
			
			//페이지 하단에 번호를 만들기 위한 객체생성
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			scri.setSearchType(searchType);	
			
			//처리하는 부분
			BoardDao bd = new BoardDao();
			int cnt = bd.boardTotall(scri);
			
			PageMaker pm = new PageMaker();
			pm.setSCri(scri);
			pm.setTotalCount(cnt);
			request.setAttribute("pm", pm);
			
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);
			request.setAttribute("alist", alist); //데이터(자원) 공유
			
			
			//이동하는 부분(forward 방식으로 이동)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
				
		} else if (command.equals("/board/boardContent.do")) {

			//1.파라미터가 넘어옴
			String bidx = request.getParameter("bidx");
			//String 형으로 넘어온 값이 boardSelectOne는 int 형을 받기 때문에 형변환을 시켜야한다. 
			int bidx_ = Integer.parseInt(bidx);
			//2.처리함
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv); //내부적으로 자원공유
			
			//3.이동함
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
			
		} else if (command.equals("/board/boardModify.do")) {

			//1.파라미터가 넘어옴
			String bidx = request.getParameter("bidx");
			//String 형으로 넘어온 값이 boardSelectOne는 int 형을 받기 때문에 형변환을 시켜야한다. 
			int bidx_ = Integer.parseInt(bidx);
			//2.처리함
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv); //내부적으로 자원공유
			
			//3.이동함
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");
			rd.forward(request, response);
			
		} else if (command.equals("/board/boardModifyAction.do")) {
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			
			BoardDao bd = new BoardDao();
			int value = bd.modifyBoard(subject, content, writer, bidx_);
			System.out.println(value);
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?bidx="+bidx);
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardModify.do?bidx="+bidx);
			}
		} else if (command.equals("/board/boardDelete.do")) {
			System.out.println("게시물 삭제 페이지");
			
			//1.파라미터가 넘어옴
			String bidx = request.getParameter("bidx");
			//String 형으로 넘어온 값이 boardSelectOne는 int 형을 받기 때문에 형변환을 시켜야한다. 
			int bidx_ = Integer.parseInt(bidx);
			//2.처리함
			//bidx 값 하나만 가져오면 되므로 bidx에 bidx를 담는다. 
			request.setAttribute("bidx", bidx); //내부적으로 자원공유
			//3.처리함
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
		
		
		}else if (command.equals("/board/boardDeleteAction.do")) {

			//1.파라미터 넘김
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			//2.처리함
			BoardDao bd = new BoardDao();
			int value = bd.deleteBoard(bidx_);
			//3.이동함
			request.getRequestDispatcher("/board/boardDelete.jsp");
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardList.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?bidx="+bidx);
			}
		} else if (command.equals("/board/boardReply.do")) {
			System.out.println("답변하기");
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			
			BoardVo bv = new BoardVo();
			//bv 안에서는 int 형인데 여기서 String 형으로 값을 받았기때문에 형변환을 시켜준다.
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth(Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));
			
			request.setAttribute("bv", bv);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");
			rd.forward(request, response);
		} else if (command.equals("/board/boardReplyAction.do")) {
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			HttpSession session = request.getSession();
			int midx = (int) session.getAttribute("midx");
			
			//위에 값들을 담는다.
			BoardVo bv = new BoardVo();
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth(Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));
			bv.setSubject(subject);
			bv.setContent(content);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);
		
			BoardDao db = new BoardDao();
			int value = db.replyBoard(bv);
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardList.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?bidx="+bidx);
			}
			
		} else if (command.equals("/board/fileDownload.do")) {
			//파일 이름을 넘겨 받는다.
			String filename = request.getParameter("filename");
			//File.separator 구분자, 파일의 전체 경로
			String filePath = saveFullPath + File.separator + filename;
			
			//해당위치에 있는 파일을 읽어들인다. 
			FileInputStream fileInputStream = new FileInputStream(filePath);
			
			Path source = Paths.get(filePath);
			// mimeType은 파일 형식
			String mimeType = Files.probeContentType(source);
			//header정보에 추출한 파일형식을 담는다.
			response.setContentType(mimeType);
			
			String sEncoding = new String(filename.getBytes("utf-8"));
			//header정보에 파일 이름을 담는다.
			response.setHeader("Content-Disposition", "attachment;fileName="+sEncoding);
			//파일 쓰기
			ServletOutputStream servletOutStream = response.getOutputStream();
			byte[] b = new byte[4096];
			//4바이트씩 읽어드림
			int read = 0;
			while ((read = fileInputStream.read(b, 0, b.length)) != -1) {
				servletOutStream.write(b, 0, read);
			}
			//flush는 출력
			servletOutStream.flush();
			servletOutStream.close();
			fileInputStream.close();
		}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
		
	}

}