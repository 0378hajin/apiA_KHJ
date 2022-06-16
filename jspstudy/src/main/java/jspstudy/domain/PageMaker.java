package jspstudy.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {
//리스트 페이지 하단에 번호를 계산해주는 클래스
	private int totalCount;
	private int startPage; 
	private int endPage;
	private boolean prev;
	private boolean next;
	private int displayPageNum = 10; //< 1 2 3 4 5 6 7 8 9 10 > 이런 식으로 페이징 수를 나타내주는 변수
	private SearchCriteria scri;

	public SearchCriteria getSCri() {
		return scri;
	}

	public void setSCri(SearchCriteria scri) {
		this.scri = scri;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcDate();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	public void calcDate() {
		//Math.ceil 올림처리하는 Math 메서드
		endPage = (int)(Math.ceil(scri.getPage() / (double)displayPageNum) * displayPageNum); // 게시글이 몇개 남아있어도 페이지 처리를 하겠다라는 뜻
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double)scri.getPerPageNum()));	
		System.out.println(totalCount);
		System.out.println(scri.getPerPageNum());
		if (endPage > tempEndPage ) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true; //false는 안나타내주고 true는 나타내준다.
		next = endPage * scri.getPerPageNum() >= totalCount ? false : true; //false는 안나타내주고 true는 나타내준다.
		
	}
	
	
	public String encoding(String keyword) {
		String str = null;
		
			
			try {
				if (keyword != null) {
					str = URLEncoder.encode(keyword, "UTF-8");
				} // null이면 빈값으로 넘긴다. 
			} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
			}
		
		
		return str;
	}
	
}
// 실무에서는 매번 이 클래스를 만들지는 않고 회사에서 만들어 놓은 것을 쓰기도 한다. 