package com.gira.demo.services.impl;


import com.gira.demo.models.entities.Progress;
import com.gira.demo.models.entities.Task;
import com.gira.demo.models.services.TaskServiceModel;
import com.gira.demo.models.services.UserServiceModel;
import com.gira.demo.models.views.TaskViewModel;
import com.gira.demo.repositories.TaskRepository;
import com.gira.demo.services.ClassificationService;
import com.gira.demo.services.TaskService;
import com.gira.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ClassificationService classificationService;
    private final ModelMapper modelMapper;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ClassificationService classificationService, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.classificationService = classificationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskServiceModel addTask(TaskServiceModel taskServiceModel, UserServiceModel userServiceModel) {
//        Task task = this.modelMapper.map(taskServiceModel, Task.class);
//        task.setClassification(this.classificationService.getClassificationById(taskServiceModel.getClassification()));
//        task.setProgress(Progress.OPEN.name());
//        task.setUser(userServiceModel.getId());
//        Task task1 = this.taskRepository.save(task);
//
//        return this.modelMapper.map(task1, TaskServiceModel.class)


        Task task = this.modelMapper.map(taskServiceModel, Task.class);
        task.setClassification(this.classificationService.getClassificationById(taskServiceModel.getClassification()));
        task.setProgress(Progress.OPEN.name());
        task.setUser(userServiceModel.getId());
        Task task1 = taskRepository.save(task);

        return this.modelMapper.map(task1, TaskServiceModel.class);
    }

    @Override
    public void changeProgress(String id) {
        Task task = this.taskRepository.getById(id);
//        switch (task.getProgress()) {
//            case "OPEN":
//                task.setProgress(Progress.IN_PROGRESS.name());
//                this.taskRepository.saveAndFlush(task);
//                break;
//            case "IN_PROGRESS":
//                task.setProgress(Progress.COMPLETED.name());
//                this.taskRepository.saveAndFlush(task);
//                break;
//            case "COMPLETED":
//                this.taskRepository.deleteById(id);
//                break;
//        }
        switch (task.getProgress()) {
            case "OPEN":
                task.setProgress(Progress.IN_PROGRESS.name());
                this.taskRepository.saveAndFlush(task);
                break;
            case "IN_PROGRESS":
                task.setProgress(Progress.COMPLETED.name());
                taskRepository.saveAndFlush(task);
                break;
            case "COMPLETED":

                taskRepository.deleteById(id);
                break;
        }
    }

    @Override
    public List<TaskViewModel> getAllTasks() {
       return this.taskRepository.findAll()
                .stream().map(t->{
                    TaskViewModel taskViewModel = modelMapper.map(t, TaskViewModel.class);
                    taskViewModel.setUsername(this.userService.findUserById(t.getUser()).getUsername());

                    return taskViewModel;
        }).collect(Collectors.toList());
    }

}
