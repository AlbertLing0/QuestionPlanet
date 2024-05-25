package org.example.t1.utils;

/**
 * @author 郑悦
 * @Description: 处理前端的分页和排序请求，用于构建分页查询
 * @date 2024/5/25 02:15
 */
public class JQPageInfo {
    private Integer page;

    private Integer limit;

    private String sidx;

    private String order;

    private  Integer offset;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
