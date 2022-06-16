package jspstudy.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {
//����Ʈ ������ �ϴܿ� ��ȣ�� ������ִ� Ŭ����
	private int totalCount;
	private int startPage; 
	private int endPage;
	private boolean prev;
	private boolean next;
	private int displayPageNum = 10; //< 1 2 3 4 5 6 7 8 9 10 > �̷� ������ ����¡ ���� ��Ÿ���ִ� ����
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
		//Math.ceil �ø�ó���ϴ� Math �޼���
		endPage = (int)(Math.ceil(scri.getPage() / (double)displayPageNum) * displayPageNum); // �Խñ��� � �����־ ������ ó���� �ϰڴٶ�� ��
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double)scri.getPerPageNum()));	
		System.out.println(totalCount);
		System.out.println(scri.getPerPageNum());
		if (endPage > tempEndPage ) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true; //false�� �ȳ�Ÿ���ְ� true�� ��Ÿ���ش�.
		next = endPage * scri.getPerPageNum() >= totalCount ? false : true; //false�� �ȳ�Ÿ���ְ� true�� ��Ÿ���ش�.
		
	}
	
	
	public String encoding(String keyword) {
		String str = null;
		
			
			try {
				if (keyword != null) {
					str = URLEncoder.encode(keyword, "UTF-8");
				} // null�̸� ������ �ѱ��. 
			} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
			}
		
		
		return str;
	}
	
}
// �ǹ������� �Ź� �� Ŭ������ �������� �ʰ� ȸ�翡�� ����� ���� ���� ���⵵ �Ѵ�. 