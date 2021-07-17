package me.too.scaffold.core.model;



public class SysRoleFunIJFun extends SysRoleFun {
    private String pageName;
    private String actionType;
    private String actionNo;
    private String actionName;
    
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
    







}
