package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.SearchCriteria;

public class BoardDao {
	//conn 객체 정보를 쓴다.
	Connection conn;
	PreparedStatement pstmt;
	
	public BoardDao() {
		
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	public int insertBoard(String subject, String content, String writer, String ip, int midx ,String fileName) {
		int value = 0;
		ResultSet rs = null;
		int maxbidx = 0;
		String sql1 = "INSERT INTO a_board(originbidx,depth,level_,subject,content,writer,ip,midx,filename)"
				 		+ "VALUES (0,0,0,?,?,?,?,?,?)";
		
		String sql2 = "select max(bidx) as maxbidx from a_board";
				
		String sql3 = "update a_board set originbidx = ? where bidx=?";
		
		try {
		pstmt = conn.prepareStatement(sql1);
		pstmt.setString(1,subject);
		pstmt.setString(2,content);
		pstmt.setString(3,writer);
		pstmt.setString(4,ip);
		pstmt.setInt(5,midx);
		pstmt.setString(6,fileName);
		value = pstmt.executeUpdate();
		
		pstmt = conn.prepareStatement(sql2);
		rs = pstmt.executeQuery();
		
		if (rs.next()) {
		maxbidx = rs.getInt("maxbidx");
		}
		
		pstmt = conn.prepareStatement(sql3);
		pstmt.setInt(1,maxbidx);
		pstmt.setInt(2,maxbidx);
		value = pstmt.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			pstmt.close();
			conn.close();
			rs.close();
			} catch(SQLException e) {
			
				e.printStackTrace();	
			}
		}
		
		return value;
	}
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
		//close() 메서드를 사용하기 위해 ResultSet rs를 try-catch 구문에서 뺀다음 선언해준다.
		ResultSet rs = null;

		String str = "";
		if (scri.getSearchType().equals("subject")) {
			str = "and subject like ?";
		} else {
			str = "and writer like ?";
		}
		
		String sql = "select * from a_board where delyn = 'N' " + str + "order by originbidx desc, depth asc limit ?,?";
		try {
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			pstmt.setInt(2, (scri.getPage()-1)*15+1);
			pstmt.setInt(3, (scri.getPage()*15));
			rs = pstmt.executeQuery();
			//반복문을 사용하여 출력
			//다음 값이 존재하면 true 이고 그 행으로 커서가 이동한다. 
			while (rs.next()) {
				BoardVo bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx")); //rs에 복사된 bidx를 bv에 옮겨 담는다.
				bv.setSubject(rs.getString("subject"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				//답변표시 하기 위한 level_
				bv.setLevel_(rs.getInt("level_"));
				
				alist.add(bv); //각각의 bv객체를 alist에 추가한다.
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			try {	
				rs.close();
				pstmt.close();
				conn.close();	
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}

		}
		return alist;
	}
		
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = null;
		ResultSet rs = null;
		String sql = "select * from a_board where bidx = ?";
		try {
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			//executeQuery는 select 쿼리를 실행 시킬 때 쓰는 메서드
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			 	bv = new BoardVo();
			 	bv.setBidx(rs.getInt("bidx"));
			 	bv.setOriginbidx(rs.getInt("originbidx"));
			 	bv.setDepth(rs.getInt("depth"));
			 	bv.setLevel_(rs.getInt("level_"));
			 	//하나의 값들만 나오면 돼서 array에 담지 않는다.
			 	bv.setSubject(rs.getString("subject"));
			 	bv.setContent(rs.getString("content"));
			 	bv.setWriter(rs.getString("writer"));
			 	bv.setWriteday(rs.getString("writeday"));
			 	bv.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return bv;
	}
	
	public int modifyBoard(String subject, String content, String writer, int bidx) {
		int value = 0;
		
		String sql = "update a_board set subject = ?, content = ?, writer = ? , writeday = now() where bidx = ?";
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,subject);
		pstmt.setString(2,content);
		pstmt.setString(3,writer);
		pstmt.setInt(4,bidx);
		//executeUpdate 메서드는 select 쿼리를 제외한 나머지 쿼리를 사용할 때 쓴다. 
		value = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			pstmt.close();
			conn.close();
			} catch(SQLException e) {
			
				e.printStackTrace();	
			}
		}
		
		return value;
	}

	public int deleteBoard (int bidx) {
		int value = 0;
		
		String sql = "update a_board set delyn = 'Y', writeday = now() where bidx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return value;
	}
	
	public int replyBoard(BoardVo bv) {
		int value = 0;
		//1.update 처리를 한 후 
		String sql1 = "update a_board set depth = depth + 1 where originbidx = ? and depth > ?";
		//2.insert를 해준다.
		String sql2 = "INSERT INTO a_board(originbidx,depth,level_,subject,content,writer,ip,midx)"
				 		+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
		/*
		*트랜젝션 두 개 이상의 작업단위를 하나의 작업단위로 만드는것
		*모든 작업이 완료가 되면 commit
		*한 개의 작업이라도 오류가 발생하면 rollback 
		*/
			//오토커밋을 fales로 하여 오토커밋을 끈다.
		conn.setAutoCommit(false);
			//1번 처리
		pstmt = conn.prepareStatement(sql1);
		pstmt.setInt(1,bv.getOriginbidx());
		pstmt.setInt(2,bv.getDepth());
		value = pstmt.executeUpdate();
			//2번 처리
		pstmt = conn.prepareStatement(sql2);
		pstmt.setInt(1, bv.getOriginbidx());
		pstmt.setInt(2, bv.getDepth()+1);
		pstmt.setInt(3, bv.getLevel_()+1);
		pstmt.setString(4, bv.getSubject());
		pstmt.setString(5, bv.getContent());
		pstmt.setString(6, bv.getWriter());
		pstmt.setString(7, bv.getIp());
		pstmt.setInt(8, bv.getMidx());
		value = pstmt.executeUpdate();
		
		conn.commit();
		
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
			pstmt.close();
			conn.close();
			} catch(SQLException e) {
			
				e.printStackTrace();	
			}
		}
		
		return value;
	}
	
	public int boardTotall(SearchCriteria scri) { //젼체 게시물 갯수 리턴해주는 메서드
		int cnt = 0;
		ResultSet rs = null;
		
		String str = "";
		if (scri.getSearchType().equals("subject")) {
			str = "and subject like ?";
		} else {
			str = "and writer like ?";
		}
		
		 String sql = "select count(*) as cnt from  a_board where delyn = 'N' "+str+"";
		 try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				//conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return cnt;
	}
}