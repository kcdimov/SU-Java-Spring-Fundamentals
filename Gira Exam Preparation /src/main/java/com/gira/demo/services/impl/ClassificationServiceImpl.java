package com.gira.demo.services.impl;

import com.gira.demo.models.entities.Classification;
import com.gira.demo.models.entities.ClassificationName;
import com.gira.demo.models.services.ClassificationServiceModel;
import com.gira.demo.repositories.ClassificationRepository;
import com.gira.demo.services.ClassificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassificationServiceImpl implements ClassificationService {

    private final ClassificationRepository classificationRepository;
    private final ModelMapper modelMapper;

    public ClassificationServiceImpl(ClassificationRepository classificationRepository, ModelMapper modelMapper) {
        this.classificationRepository = classificationRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        if (classificationRepository.count() == 0) {

            classificationRepository.saveAndFlush(new Classification("BUG"));
            classificationRepository.saveAndFlush(new Classification("FEATURE"));
            classificationRepository.saveAndFlush(new Classification("SUPPORT"));
            classificationRepository.saveAndFlush(new Classification("OTHER"));
//            Arrays.stream(ClassificationName.values())
//                    .forEach(classificationName -> {
//                        Classification classification = new Classification(classificationName,
//                                "description for " + classificationName.name());
//                        classificationRepository.save(classification);
//                    });

        }
    }

    @Override
    public List<ClassificationServiceModel> getAllClassifications() {
        return this.classificationRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ClassificationServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Classification getClassificationById(String classificationId) {

        return this.classificationRepository.getById(classificationId);
    }
    //TODO
}
