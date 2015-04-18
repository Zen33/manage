package fitfame.common.page;

@SuppressWarnings("serial")
public class PageInfo extends PObject {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPageNo;
		result = prime * result + pageSize;
		result = prime * result + (totalCount==null?0:totalCount);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageInfo other = (PageInfo) obj;
		if (currentPageNo != other.currentPageNo)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalCount != other.totalCount)
			return false;
		return true;
	}

	public static final String CURRENT_PAGE_NAME = "_pn"; // 记录当前页的参数
	public static final int FIRST_PAGE_NO = 1;
	public static final int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int currentPageNo; // 当前是第几页

	private Integer totalCount = null; // 总记录数

	/**
	 * 构造方法，只构造空页.
	 */
	public PageInfo() {
		this(FIRST_PAGE_NO, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param start
	 *            本页数据在数据库中的起始位置
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public PageInfo(int start, int pageSize) {
		this.currentPageNo = start;
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取分页脚本
	 * @return
	 */
	public String makePage(String url,int CASE){
	StringBuffer str=new StringBuffer("");
	int tostart=currentPageNo-CASE;
	int toend=currentPageNo+CASE;
	if(currentPageNo-CASE<1){
		toend=(currentPageNo+CASE)-(currentPageNo-CASE-1);
	}
	if(currentPageNo+CASE>getTotalPage()){
		tostart=(currentPageNo-CASE)-(currentPageNo+CASE-getTotalPage());
	}
	if(toend>getTotalPage())toend=getTotalPage();
	if(tostart<1)tostart=1;
	
	for(int i=1;i<=getTotalPage();i++){
		if(i>=tostart&&i<=toend){
			if(i!=currentPageNo){
				str.append("<a href=\"");
				str.append(url);
				str.append(i);
				str.append("\">");
				str.append(i);
				str.append("</a>");
				str.append("&nbsp;&nbsp;");
			}else{
				str.append(i);
				str.append("&nbsp;&nbsp;");
			}
		}
	}
	return str.toString();
	}

	/**
	 * 取总记录数.
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取总页数.
	 */
	public int getTotalPage() {
		return pageSize > 0 ? (totalCount + pageSize - 1) / pageSize : 0;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public boolean isFirstPage() {
		return this.getCurrentPageNo() == FIRST_PAGE_NO ? true : false;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < getTotalPage() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	public boolean isLastPage() {
		return this.getCurrentPageNo() == getTotalPage() ? true : false;
	}
	
	/**
	 * 返回当前页开始的第一个序号
	 * @return
	 */
	public int getCurrentPageStart() {
		return getPageSize() * (getCurrentPageNo() - 1);
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param totalCount
	 *            The totalCount to set.
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNO) {
		this.currentPageNo = currentPageNO;
	}
}