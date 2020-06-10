package com.wuyou.wybaselibrary.model.page;

import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;

import java.util.List;

public class MainPageModel {
    private List<ProjectDetailModel> penddingProjects;
    private List<ProjectDetailModel> passedProjects;
    private List<ProjectDetailModel> rejectedProjects;

    public List<ProjectDetailModel> getPenddingProjects() {
        return penddingProjects;
    }

    public void setPenddingProjects(List<ProjectDetailModel> penddingProjects) {
        this.penddingProjects = penddingProjects;
    }

    public List<ProjectDetailModel> getPassedProjects() {
        return passedProjects;
    }

    public void setPassedProjects(List<ProjectDetailModel> passedProjects) {
        this.passedProjects = passedProjects;
    }

    public List<ProjectDetailModel> getRejectedProjects() {
        return rejectedProjects;
    }

    public void setRejectedProjects(List<ProjectDetailModel> rejectedProjects) {
        this.rejectedProjects = rejectedProjects;
    }
}
