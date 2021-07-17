package me.too.scaffold.core.model;

public class SysRoleFun {
    
    private Long id;

    
    private String roleNo;

    private String funNo;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getRoleNo() {
        return roleNo;
    }

    
    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo == null ? null : roleNo.trim();
    }


    public String getFunNo() {
        return funNo;
    }

    
    public void setFunNo(String funNo) {
        this.funNo = funNo == null ? null : funNo.trim();
    }
}
