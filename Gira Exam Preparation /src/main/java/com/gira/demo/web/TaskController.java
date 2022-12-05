package com.gira.demo.web;

import com.gira.demo.models.binding.TaskAddBindingModel;
import com.gira.demo.models.services.TaskServiceModel;
import com.gira.demo.services.ClassificationService;
import com.gira.demo.services.TaskService;
import com.gira.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller()
@RequestMapping("/tasks")
public class TaskController {

    private final ModelMapper modelMapper;
    private final ClassificationService classificationService;
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(ModelMapper modelMapper, ClassificationService
            classificationService, TaskService taskService, UserService userService) {
        this.modelMapper = modelMapper;
        this.classificationService = classificationService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/add")
    private String addTask (Model model) {
        if (!model.containsAttribute("taskAddBindingModel")) {
            model.addAttribute("taskAddBindingModel", new TaskAddBindingModel());

        }
        model.addAttribute("classifications", classificationService.getAllClassifications());
        return "add-task";
    }

    @PostMapping("/add")
    private String addTaskConf(@Valid @ModelAttribute("taskAddBindingModel")
                               TaskAddBindingModel taskAddBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, HttpSession httpSession) {
                if (bindingResult.hasErrors()) {
                    redirectAttributes.addFlashAttribute("taskAddBindingModel", taskAddBindingModel);
                    redirectAttributes.addFlashAttribute(
                            "org.springframework.validation.BindingResult.taskAddBindingModel", bindingResult);
                    return "redirect:add";
                }

                try {
                    TaskServiceModel taskServiceModel = modelMapper.map(taskAddBindingModel, TaskServiceModel.class);
                    taskService.addTask(taskServiceModel,
                            this.userService.findUserById(httpSession.getAttribute("id").toString()));
                    return "redirect:/";
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    redirectAttributes.addFlashAttribute("taskAddBindingModel", taskAddBindingModel);
                    redirectAttributes.addFlashAttribute(
                            "org.springframework.validation.BindingResult.taskAddBindingModel", bindingResult);
                    return "redirect:add";
                }
    }
    @PostMapping("/changeProgress/")
    private String changeProgress(@RequestParam(name = "id") String id) {
        taskService.changeProgress(id);
        return "redirect:/";
    }
}
