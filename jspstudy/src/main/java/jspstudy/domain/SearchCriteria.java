package jspstudy.domain;

public class SearchCriteria extends Criteria { //Criteria의 상속을 받아 Criteria의 기능을 다 사용할 수 있게한다.

	
	private String searchType;
	private String keyword;
	
	public SearchCriteria() {
		
		this.searchType = "";
		this.keyword = "";
		
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
