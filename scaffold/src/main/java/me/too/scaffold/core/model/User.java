package me.too.scaffold.core.model;

public class User {
    
    private Long id;

    
    private String phone;

    
    private String password;

    
    private String status;

    
    private String roles;

    
    private String userName;

    
    private String avatar;

    
    private String gender;

    
    private String birthday;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    
    public String getRoles() {
        return roles;
    }

    
    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    
    public String getAvatar() {
        return avatar;
    }

    
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    
    public String getGender() {
        return gender;
    }

    
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    
    public String getBirthday() {
        return birthday;
    }

    
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }
}
