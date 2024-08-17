package controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.security.CitizenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "register";  // This corresponds to register.html
    }

    @PostMapping("/register")
    public String registerCitizen(Citizen citizen) {
        return "redirect:/login";  // Redirect to login page after registration
    }
}
