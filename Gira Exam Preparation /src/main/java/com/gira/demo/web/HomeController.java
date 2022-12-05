package com.gira.demo.web;

import com.gira.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final TaskService taskService;

    @Autowired
    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("user") != null) {

            model.addAttribute("tasks", taskService.getAllTasks());
            return "home";
        }

        return "index";

    }
}
