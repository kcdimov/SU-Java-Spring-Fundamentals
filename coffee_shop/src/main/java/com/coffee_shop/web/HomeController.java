package com.coffee_shop.web;

import com.coffee_shop.models.view.OrderViewModel;
import com.coffee_shop.models.view.UserViewModel;
import com.coffee_shop.security.CurrentUser;
import com.coffee_shop.services.OrderService;
import com.coffee_shop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final OrderService orderService;
    private final CurrentUser currentUser;

    public HomeController(UserService userService, OrderService orderService, CurrentUser currentUser) {
        this.userService = userService;
        this.orderService = orderService;
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String index (Model model) {
        if (currentUser.getId() == null) {
            return "index";
        }

        List<OrderViewModel> orders = this.orderService.findAllOrdersByPriceDecs();

        model.addAttribute("orders", orders);
        model.addAttribute("totalTime", orders.stream()
                .map(o -> o.getCategory().getNeededTime())
                .reduce(Integer::sum)
                .orElse(0));

        List<UserViewModel> users = this.userService.findAllUsersByCountDesc();

        model.addAttribute("users", users);

        return "home";
    }
}
