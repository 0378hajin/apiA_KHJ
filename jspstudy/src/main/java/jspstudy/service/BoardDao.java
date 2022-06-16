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
	//conn ��ü ������ ����.
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
		//close() �޼��带 ����ϱ� ���� ResultSet rs�� try-catch �������� ������ �������ش�.
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
			//�ݺ����� ����Ͽ� ���
			//���� ���� �����ϸ� true �̰� �� ������ Ŀ���� �̵��Ѵ�. 
			while (rs.next()) {
				BoardVo bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx")); //rs�� ����� bidx�� bv�� �Ű� ��´�.
				bv.setSubject(rs.getString("subject"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				//�亯ǥ�� �ϱ� ���� level_
				bv.setLevel_(rs.getInt("level_"));
				
				alist.add(bv); //������ bv��ü�� alist�� �߰��Ѵ�.
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
			//executeQuery�� select ������ ���� ��ų �� ���� �޼���
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			 	bv = new BoardVo();
			 	bv.setBidx(rs.getInt("bidx"));
			 	bv.setOriginbidx(rs.getInt("originbidx"));
			 	bv.setDepth(rs.getInt("depth"));
			 	bv.setLevel_(rs.getInt("level_"));
			 	//�ϳ��� ���鸸 ������ �ż� array�� ���� �ʴ´�.
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
		//executeUpdate �޼���� select ������ ������ ������ ������ ����� �� ����. 
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
		//1.update ó���� �� �� 
		String sql1 = "update a_board set depth = depth + 1 where originbidx = ? and depth > ?";
		//2.insert�� ���ش�.
		String sql2 = "INSERT INTO a_board(originbidx,depth,level_,subject,content,writer,ip,midx)"
				 		+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
		/*
		*Ʈ������ �� �� �̻��� �۾������� �ϳ��� �۾������� ����°�
		*��� �۾��� �Ϸᰡ �Ǹ� commit
		*�� ���� �۾��̶� ������ �߻��ϸ� rollback 
		*/
			//����Ŀ���� fales�� �Ͽ� ����Ŀ���� ����.
		conn.setAutoCommit(false);
			//1�� ó��
		pstmt = conn.prepareStatement(sql1);
		pstmt.setInt(1,bv.getOriginbidx());
		pstmt.setInt(2,bv.getDepth());
		value = pstmt.executeUpdate();
			//2�� ó��
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
	
	public int boardTotall(SearchCriteria scri) { //��ü �Խù� ���� �������ִ� �޼���
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