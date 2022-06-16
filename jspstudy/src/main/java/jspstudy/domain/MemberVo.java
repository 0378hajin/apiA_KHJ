package jspstudy.domain;

//클래스 용도는 DB에서 member 컬럼 값을 담는 역할(bean)
public class MemberVo {
//외부의 접근을 막기위해 private를 사용
	private int midx;
	private String memberid;
	private String memberpwd;
	private String membername;
	private String membermail;
	private String membergender;
	private String memberaddr;
	private String memberphone;
	private String memberjumin;
	private String memberhobby;
	private String writeday;
	private String delyn;
	private String memberip;
	
	public int getMidx() {
		return midx;
	}
	public void setMidx(int midx) {
		this.midx = midx;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMemberpwd() {
		return memberpwd;
	}
	public void setMemberpwd(String memberpwd) {
		this.memberpwd = memberpwd;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMembermail() {
		return membermail;
	}
	public void setMembermail(String membermail) {
		this.membermail = membermail;
	}
	public String getMembergender() {
		return membergender;
	}
	public void setMembergender(String membergender) {
		this.membergender = membergender;
	}
	public String getMemberaddr() {
		return memberaddr;
	}
	public void setMemberaddr(String memberaddr) {
		this.memberaddr = memberaddr;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getMemberjumin() {
		return memberjumin;
	}
	public void setMemberjumin(String memberjumin) {
		this.memberjumin = memberjumin;
	}
	public String getMemberhobby() {
		return memberhobby;
	}
	public void setMemberhobby(String memberhobby) {
		this.memberhobby = memberhobby;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getMemberip() {
		return memberip;
	}
	public void setMemberip(String memberip) {
		this.memberip = memberip;
	}
	
	
}
