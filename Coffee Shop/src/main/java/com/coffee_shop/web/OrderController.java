package com.coffee_shop.web;

import com.coffee_shop.models.binding.OrderAddBM;
import com.coffee_shop.models.services.OrderServiceModel;
import com.coffee_shop.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add() {
        return "order-add";
    }

    @PostMapping("/add")
    public String addOrder (@Valid OrderAddBM orderAddBM, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddBM", orderAddBM);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddBM",
                    bindingResult);
            return "redirect:add";
        }

        OrderServiceModel orderServiceModel = this.modelMapper.map(orderAddBM, OrderServiceModel.class);

        this.orderService.addOrder(orderServiceModel);

        return "redirect:/";

    }

    @GetMapping("/ready/{id}")
    public String ready(@PathVariable Long id) {
        this.orderService.orderReady(id);
        return "redirect:/";
    }

    @ModelAttribute
    public OrderAddBM orderAddBM() {
        return new OrderAddBM();
        //v template register add: th:action, th:method
    }
}
