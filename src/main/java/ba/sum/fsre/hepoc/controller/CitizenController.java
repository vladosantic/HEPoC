package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CitizenController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "register";
    }

    @GetMapping(value = "/login")
    public String showLoginForm() {
        return "login";
    }
}
