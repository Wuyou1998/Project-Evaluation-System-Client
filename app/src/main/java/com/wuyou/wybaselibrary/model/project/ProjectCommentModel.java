package com.wuyou.wybaselibrary.model.project;

public class ProjectCommentModel {
//    {
//        "id": 1,
//            "projectName": "wuyou",
//            "person": "wuyou",
//            "comment": null,
//            "avatar": "https://review-system-1301777194.cos.ap-beijing.myqcloud.com/avatar/202004/df45b5658bc182b241c36744e38684fc.jpg"
//    }

    private int id;
    private String projectName;
    private String person;
    private String comment;
    private String avatar;

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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ProjectCommentModel{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", person='" + person + '\'' +
                ", comment='" + comment + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
