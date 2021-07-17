package me.too.scaffold.core.model;

public class SysFun {
    
    private Long id;

    private String funNo;
    
    private String pageName;

    
    private String actionType;

    
    private String actionNo;

    
    private String actionName;

    
    private String memo;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getFunNo() {
        return funNo;
    }

    
    public void setFunNo(String funNo) {
        this.funNo = funNo == null ? null : funNo.trim();
    }

    
    public String getPageName() {
        return pageName;
    }

    
    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    
    public String getActionType() {
        return actionType;
    }

    
    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    
    public String getActionNo() {
        return actionNo;
    }

    
    public void setActionNo(String actionNo) {
        this.actionNo = actionNo == null ? null : actionNo.trim();
    }

    
    public String getActionName() {
        return actionName;
    }

    
    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}
