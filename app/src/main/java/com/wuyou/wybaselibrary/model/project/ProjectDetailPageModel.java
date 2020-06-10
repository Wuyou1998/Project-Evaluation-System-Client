package com.wuyou.wybaselibrary.model.project;

import java.util.List;

public class ProjectDetailPageModel {
    private ProjectDetailModel detail;
    private List<ProjectCommentModel> comments;

    public ProjectDetailModel getDetail() {
        return detail;
    }

    public void setDetail(ProjectDetailModel detail) {
        this.detail = detail;
    }

    public List<ProjectCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<ProjectCommentModel> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProjectDetailPageModel{" +
                "detail=" + detail.toString() +
                ", comments=" + comments.toString() +
                '}';
    }
}
