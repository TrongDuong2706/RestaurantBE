package com.cyphersoft.osahaneat.payload;

public class LoginResponse {

    private int status = 200;
    private boolean isSuccess = true;
    private String desc;
    private Object data;

    private Integer userId;
    private Integer roleId;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public LoginResponse() {
    }

    public LoginResponse(int status, boolean isSuccess, String desc, Object data, int userId) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.desc = desc;
        this.data = data;
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
