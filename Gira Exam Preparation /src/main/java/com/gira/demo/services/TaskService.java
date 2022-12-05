package com.gira.demo.services;


import com.gira.demo.models.services.TaskServiceModel;
import com.gira.demo.models.services.UserServiceModel;
import com.gira.demo.models.views.TaskViewModel;

import java.util.List;


public interface TaskService {

    TaskServiceModel addTask(TaskServiceModel taskServiceModel, UserServiceModel userServiceModel);

    void changeProgress (String id);

    List<TaskViewModel> getAllTasks();
}
