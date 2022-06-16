package jspstudy.domain;

public class Criteria {
//페이지 번호를 담기 위한 클래스
	
	private int page; //페이지
	private int perPageNum; //페이지를 몇개 보여줄 것인지에 대한 변수. 리스트 출력개수
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 15;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <= 1 ) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100 ) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	
}
