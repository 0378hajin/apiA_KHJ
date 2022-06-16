/*����ѷ��� ����
 * 1. �Ѿ�� ��
 * 2. ó��
 * 3. �̵�(���� �̵��ؾ��ϱ� ������ send - redirect�� �̵��Ѵ�)
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
		//������ ����
		//�ּ��� Ǯ ��θ� �����ϴ� �޼���
		String uri = request.getRequestURI();
		//������Ʈ �̸��� ����
		String pj = request.getContextPath();
		//������Ʈ �̸��� �� ������ ���� ��θ� ����
		String command = uri.substring(pj.length());
		System.out.println("command : " + command);
		
		String uploadPath = "D:\\openAPI(A)\\dev\\jspstudy\\src\\main\\webapp\\";
		// uploadPath�� �̹��� ������
		String saveFolder = "images";
		String saveFullPath = uploadPath+saveFolder;
		//if ���� �ȿ� �־����� ���������� ����ϱ� ���� ������
		if(command.equals("/board/boardWrite.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response);
			
		} else if (command.equals("/board/boardWriteAction.do")){

			
			int sizeLimit = 1024*1024*15;
			//���� ����� 15�ް�����Ʈ�� ����
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, saveFullPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
			//DefaultfileRenamePolicy�� �̸��� �ߺ� ���� �� ����ϴ� ��å
			
			String subject = multi.getParameter("subject");
			System.out.println("subject" + subject);
			String content = multi.getParameter("content");
			String writer = multi.getParameter("writer");
			Enumeration files = multi.getFileNames();
			//Enumeration �����θ� �̷�����ִ� Ŭ����, ������
			//�����ڿ� ����� ������ ��� ��ü�� �����Ѵ�. 
			String file = (String)files.nextElement();
			//��� ������ ��ü�� ���� �̸��� ��´�. 
			String fileName = multi.getFilesystemName(file);
			//�����̸��� �̾Ƴ� �� �ִ� �޼���
			String originFileName = multi.getOriginalFileName(file);
			// ������ ���� �̸��� �˾Ƴ� �� �ִ� �޼���
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
			System.out.println("����Ʈ�� ����");
			//�������� ������ �ٳ����
			String page = request.getParameter("page");
			if(page == null) {
				page = "1";
			}
			int pagex = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");
			if (keyword == null) keyword = "";
			String searchType = request.getParameter("searchType");
			if (searchType == null) searchType = "";
			
			
			//������ �ϴܿ� ��ȣ�� ����� ���� ��ü����
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			scri.setSearchType(searchType);	
			
			//ó���ϴ� �κ�
			BoardDao bd = new BoardDao();
			int cnt = bd.boardTotall(scri);
			
			PageMaker pm = new PageMaker();
			pm.setSCri(scri);
			pm.setTotalCount(cnt);
			request.setAttribute("pm", pm);
			
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);
			request.setAttribute("alist", alist); //������(�ڿ�) ����
			
			
			//�̵��ϴ� �κ�(forward ������� �̵�)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
				
		} else if (command.equals("/board/boardContent.do")) {

			//1.�Ķ���Ͱ� �Ѿ��
			String bidx = request.getParameter("bidx");
			//String ������ �Ѿ�� ���� boardSelectOne�� int ���� �ޱ� ������ ����ȯ�� ���Ѿ��Ѵ�. 
			int bidx_ = Integer.parseInt(bidx);
			//2.ó����
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv); //���������� �ڿ�����
			
			//3.�̵���
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
			
		} else if (command.equals("/board/boardModify.do")) {

			//1.�Ķ���Ͱ� �Ѿ��
			String bidx = request.getParameter("bidx");
			//String ������ �Ѿ�� ���� boardSelectOne�� int ���� �ޱ� ������ ����ȯ�� ���Ѿ��Ѵ�. 
			int bidx_ = Integer.parseInt(bidx);
			//2.ó����
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv); //���������� �ڿ�����
			
			//3.�̵���
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
			System.out.println("�Խù� ���� ������");
			
			//1.�Ķ���Ͱ� �Ѿ��
			String bidx = request.getParameter("bidx");
			//String ������ �Ѿ�� ���� boardSelectOne�� int ���� �ޱ� ������ ����ȯ�� ���Ѿ��Ѵ�. 
			int bidx_ = Integer.parseInt(bidx);
			//2.ó����
			//bidx �� �ϳ��� �������� �ǹǷ� bidx�� bidx�� ��´�. 
			request.setAttribute("bidx", bidx); //���������� �ڿ�����
			//3.ó����
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
		
		
		}else if (command.equals("/board/boardDeleteAction.do")) {

			//1.�Ķ���� �ѱ�
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			//2.ó����
			BoardDao bd = new BoardDao();
			int value = bd.deleteBoard(bidx_);
			//3.�̵���
			request.getRequestDispatcher("/board/boardDelete.jsp");
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardList.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?bidx="+bidx);
			}
		} else if (command.equals("/board/boardReply.do")) {
			System.out.println("�亯�ϱ�");
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			
			BoardVo bv = new BoardVo();
			//bv �ȿ����� int ���ε� ���⼭ String ������ ���� �޾ұ⶧���� ����ȯ�� �����ش�.
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
			
			//���� ������ ��´�.
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
			//���� �̸��� �Ѱ� �޴´�.
			String filename = request.getParameter("filename");
			//File.separator ������, ������ ��ü ���
			String filePath = saveFullPath + File.separator + filename;
			
			//�ش���ġ�� �ִ� ������ �о���δ�. 
			FileInputStream fileInputStream = new FileInputStream(filePath);
			
			Path source = Paths.get(filePath);
			// mimeType�� ���� ����
			String mimeType = Files.probeContentType(source);
			//header������ ������ ���������� ��´�.
			response.setContentType(mimeType);
			
			String sEncoding = new String(filename.getBytes("utf-8"));
			//header������ ���� �̸��� ��´�.
			response.setHeader("Content-Disposition", "attachment;fileName="+sEncoding);
			//���� ����
			ServletOutputStream servletOutStream = response.getOutputStream();
			byte[] b = new byte[4096];
			//4����Ʈ�� �о�帲
			int read = 0;
			while ((read = fileInputStream.read(b, 0, b.length)) != -1) {
				servletOutStream.write(b, 0, read);
			}
			//flush�� ���
			servletOutStream.flush();
			servletOutStream.close();
			fileInputStream.close();
		}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
		
	}

}