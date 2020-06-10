package com.wuyou.wybaselibrary.model.project;

public class ProjectDetailModel {
    private int id;
    private String projectName;
    private String author;
    private String content;
    private String administraor;
    private String image;
    private String fileUrl;
    private String userState;
    private int level;
    private int state;
    private String avatar;
    private String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdministraor() {
        return administraor;
    }

    public void setAdministraor(String administraor) {
        this.administraor = administraor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "ProjectDetailModel{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", administraor='" + administraor + '\'' +
                ", image='" + image + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", userState='" + userState + '\'' +
                ", level=" + level +
                ", state=" + state +
                ", avatar='" + avatar + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
