package com.gira.demo.web;


import com.gira.demo.models.binding.UserLoginBindingModel;
import com.gira.demo.models.binding.UserRegisterBindingModel;
import com.gira.demo.models.services.UserServiceModel;
import com.gira.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String register(Model model) {

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String confirmRegister(@Valid @ModelAttribute("userRegisterBindingModel")
                                  UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.rejectValue("password",
                    "error.userRegisterBindingModel", "Passwords Doesn't match!");
        }
        if (userService.existBuUsername(userRegisterBindingModel.getUsername())) {
            bindingResult.rejectValue("username",
                    "error.userRegisterBindingModel", "Username exist!");
        }
        if (userService.existByEmail(userRegisterBindingModel.getEmail())) {
            bindingResult.rejectValue("email",
                    "error.userRegisterBindingModel", "Email exist!");

        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/register";
        }

        try {
            this.userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/register";
        }

        return "redirect:login";

    }

    @GetMapping("/login")
    private String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    private String confirmLogin(@Valid UserLoginBindingModel userLoginBindingModel,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult,
                                HttpSession httpSession) {

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                redirectAttributes.addFlashAttribute(
                        "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

                return "redirect:login";
            } else {
                try {
                    UserServiceModel userServiceModel = this.userService
                            .findUserByEmail(userLoginBindingModel.getEmail());

                    if (!userServiceModel.getPassword().equals(userLoginBindingModel.getPassword())) {
                        redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel"
                                , bindingResult);
                        redirectAttributes.addFlashAttribute("loginErr", true);
                        return "redirect:/login";
                    }

                    httpSession.setAttribute("user", userServiceModel);
                    httpSession.setAttribute("id", userServiceModel.getId());
                    return "redirect:/";
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    redirectAttributes.addFlashAttribute("loginErr", true);
                    redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                    redirectAttributes.addFlashAttribute(
                            "org.springframework.validation.BindingResult.userLoginBindingModel"
                            , bindingResult);
                    return "redirect:/login";
                }
            }
    }

    @GetMapping("/logout")
    private String logout( HttpSession httpSession) {
       httpSession.invalidate();
       return "redirect:/";
    }
}
