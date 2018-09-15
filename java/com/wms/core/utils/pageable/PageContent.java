package com.wms.core.utils.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

public class PageContent {  
    private String page;  
    private String rows;  
    private String sort;  
    private String order;  
  
    public String getPage() {  
        return page;  
    }  
    public void setPage(String page) {  
        this.page = page;  
    }  
    public String getRows() {  
        return rows;  
    }  
    public void setRows(String rows) {  
        this.rows = rows;  
    }  
    public String getSort() {  
        return sort;  
    }  
    public void setSort(String sort) {  
        this.sort = sort;  
    }  
    public String getOrder() {  
        return order;  
    }  
    public void setOrder(String order) {  
        this.order = order;  
    }  
    
    public Pageable makePageable(){
    	Sort sort = null;
    	if(StringUtils.isEmpty(this.getOrder())){
    		this.setOrder("asc");
    	}
    	if(StringUtils.isEmpty(this.getSort())){
    		this.setSort("createDate");
    	}
        if(this.getOrder().equals("asc")){  
             sort = new Sort(Direction.ASC, this.getSort());  
        }else if(this.getOrder().equals("desc")){  
             sort = new Sort(Direction.DESC, this.getSort());  
        }
        var pageNum = Integer.parseInt(this.getPage())-1;
        var rows = Integer.parseInt(this.getRows());
        Pageable pageable = PageRequest.of(pageNum,rows,sort);
        return pageable;
    }
      
}  