package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.service.CitizenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "register";
    }

    @GetMapping(value = "/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping(value = "/index")
    public String showIndexPage() {
        return "index";
    }

    // Handle the registration process
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Citizen citizen) {
        citizenService.save(citizen);
        return "redirect:/login";
    }
}
