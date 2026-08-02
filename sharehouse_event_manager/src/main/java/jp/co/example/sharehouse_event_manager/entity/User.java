package jp.co.example.sharehouse_event_manager.entity;

public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String displayName;

    public User() {
    }

    public User(Integer userId, String userName, String password, String displayName) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", displayName="
                + displayName + "]";
    }

    

    
}
