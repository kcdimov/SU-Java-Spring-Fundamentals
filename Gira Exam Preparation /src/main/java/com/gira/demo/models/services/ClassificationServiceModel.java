package com.gira.demo.models.services;

public class ClassificationServiceModel extends BaseServiceModel {
    private String classificationName;
    private String description;

    public ClassificationServiceModel() {
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
