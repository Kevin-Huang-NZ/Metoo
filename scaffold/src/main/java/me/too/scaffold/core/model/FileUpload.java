package me.too.scaffold.core.model;

public class FileUpload {
    
    public FileUpload(String tableName, Long rowId) {
    	this.tableName = tableName;
    	this.rowId = rowId;
    }

    private Long id;
    
    private String tableName;
    
    private Long rowId;

    private String fullPath;
    
    private String fileName;
    
    private String deleteStatus;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }
    
    public Long getRowId() {
        return rowId;
    }
    
    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }
    
    public String getFullPath() {
        return fullPath;
    }
    
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath == null ? null : fullPath.trim();
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    public String getDeleteStatus() {
        return deleteStatus;
    }
    
    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus == null ? null : deleteStatus.trim();
    }
}
