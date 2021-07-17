package me.too.scaffold.core.model;

public class SysPage {
    
    private Long id;

    
    private String pageName;

    
    private String pageTitle;

    
    private String memo;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getPageName() {
        return pageName;
    }

    
    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    
    public String getPageTitle() {
        return pageTitle;
    }

    
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle == null ? null : pageTitle.trim();
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}
