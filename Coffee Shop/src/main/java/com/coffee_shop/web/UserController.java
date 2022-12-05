package com.coffee_shop.web;

import com.coffee_shop.models.binding.UserLoginBM;
import com.coffee_shop.models.binding.UserRegisterBM;
import com.coffee_shop.models.services.UserServiceModel;
import com.coffee_shop.services.UserService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBM")) {
            model.addAttribute("userRegisterBMl", new UserRegisterBM());
            model.addAttribute("isExist", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBM userRegisterBM, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBM.getPassword().equals(userRegisterBM.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBMl", userRegisterBM);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBMl",
                    bindingResult);
            return "redirect: register";
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBM, UserServiceModel.class);

        //this.userService.registerUser(userServiceModel);

        boolean isSaved = userService.registerUser(modelMapper.map(userRegisterBM, UserServiceModel.class));

        if (!isSaved) {
            redirectAttributes.addFlashAttribute("userRegisterBM", userRegisterBM);
            redirectAttributes.addFlashAttribute("isExist", true);
            return "redirect:register";

        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login (Model model) {
        if (!model.containsAttribute("userLoginBM")) {
            model.addAttribute("userLoginBM", new UserLoginBM());
            model.addAttribute("notFound", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBM userLoginBM, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBM", userLoginBM);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBM",
                    bindingResult);

            return "redirect:login";
        }

        UserServiceModel userServiceModel = this.userService.findByUsernameAndPassword(userLoginBM.getUsername(),
                userLoginBM.getPassword());

        if (userServiceModel == null) {
            redirectAttributes.addFlashAttribute("userLoginBM", userLoginBM);
            redirectAttributes.addFlashAttribute("notFound", false);
            return "redirect:login";
        }
        this.userService.loginUser(userServiceModel.getId(), userServiceModel.getUsername());

        httpSession.setAttribute("user", userServiceModel);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();

        return "redirect:/";
    }


    @ModelAttribute
    public UserRegisterBM userRegisterBM() {
        return new UserRegisterBM();
        //v template register add: th:action, th:method
    }
    @ModelAttribute
    public UserLoginBM userLoginBM() {
        return new UserLoginBM();
        //v template register add: th:action, th:method
    }
}
