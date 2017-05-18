package com.netfinworks.optimus.request;

/**
 * 
 * <p>分页查询</p>
 * 
 */
public class PageQueryRequest  {
    
    /**
     * 页大小
     */
    private Integer pageSize;
    
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    
}
