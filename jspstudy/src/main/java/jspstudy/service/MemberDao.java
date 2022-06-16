package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;


public class MemberDao {
	//연결 객체
	//전역 변수
	private Connection conn;	
	//구문객체
	private PreparedStatement pstmt;
	public MemberDao() {
		
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
		
	}
	
	
	public int insertMember(String memberId,String memberPwd,String memberName,String memberMail,String memberGender, String memberAddr,String memberPhone, String memberJumin,String hobby,String ip){
		int value = 0;
		
		
		String sql = "insert into a_member(MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERMAIL,MEMBERGENDER,MEMBERADDR,MEMBERPHONE,MEMBERJUMIN,MEMBERHOBBY,MEMBERIP)"
		+"values(?,?,?,?,?,?,?,?,?,?)";
		try{
		//	Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			//쿼리 구문상에 값을 넣는게 아니라 메서드를 통해 넣는다.
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			pstmt.setString(3, memberName);
			pstmt.setString(4, memberMail);
			pstmt.setString(5, memberGender);
			pstmt.setString(6, memberAddr);
			pstmt.setString(7, memberPhone);
			pstmt.setString(8, memberJumin);
			pstmt.setString(9, hobby);
			pstmt.setString(10, ip);
			value = pstmt.executeUpdate();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return value;
	}

	public ArrayList<MemberVo> memberSelectAll(){

		// 객체생성
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		ResultSet rs = null;
		String sql="select * from a_member where delyn = 'N' order by midx desc";
		try{
		// Connection 객체를 통해서 문자열을 쿼리화 시킨다.
		pstmt = conn.prepareStatement(sql);
		// conn.prepareStatement(sql)에서 담은 것을 prepare화 시켜서 구문을 실행 시킨다. 
		rs = pstmt.executeQuery();
		// executeQuery() : 내부에서 값을 받기위한 전용 클래스
		// ResultSet : 여러 데이터들을 그대로 복사해서 담는 클래스
		// 담겨있는 값을 꺼내기 위해 반복문 while을 사용
		// rs.next() : 다음 행에 값이 있는지, 값이 있으면 true를 반환 그리고 그 행으로 이동, 없으면 false를 반환,
		while(rs.next()) {
			
			//MemberVo를 객체 생성 ,ResultSet rs 값을 옮겨 담을려고 객체 생성을 한다.
			MemberVo mv = new MemberVo();
			//옮겨담고
			mv.setMidx(rs.getInt("midx"));
			mv.setMembername(rs.getString("MemberName"));
			mv.setMemberphone(rs.getString("Memberphone"));
			mv.setWriteday(rs.getString("writeday"));
			//담은 mv를 alist에 추가한다. 
			alist.add(mv);
			
		}
		
	} catch(Exception e) {
		
		e.printStackTrace();	

	} finally {
		
		try{
		rs.close();
		pstmt.close();
		conn.close();
		
		} catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
		
		return alist;
		
	}
	
	public MemberVo memberLogin(String memberid, String memberpwd) {
		
		ResultSet rs = null;
		MemberVo mv = null;
		//and를 잘 넣자
		String sql = "select * from a_member where delyn = 'N' and memberid = ? and memberpwd = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberid);
			pstmt.setString(2, memberpwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mv = new MemberVo();
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberId"));
				mv.setMembername(rs.getString("memberName"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return mv;
			
	}

}
