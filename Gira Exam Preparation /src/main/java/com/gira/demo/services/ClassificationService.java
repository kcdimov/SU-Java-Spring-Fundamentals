package com.gira.demo.services;

import com.gira.demo.models.entities.Classification;
import com.gira.demo.models.services.ClassificationServiceModel;

import java.util.List;

public interface ClassificationService {

    List<ClassificationServiceModel> getAllClassifications();
    Classification getClassificationById(String classificationId);
}
