package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.security.CitizenDetails;
import ba.sum.fsre.hepoc.service.CitizenDetailsService;
import ba.sum.fsre.hepoc.service.CitizenService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class CitizenController {

    private final CitizenService citizenService;
    private final CitizenDetailsService citizenDetailsService;

    public CitizenController(CitizenService citizenService, CitizenDetailsService citizenDetailsService) {
        this.citizenService = citizenService;
        this.citizenDetailsService = citizenDetailsService;
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

    @GetMapping("/index")
    public String showIndexPage(Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);
        return "index";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Citizen citizen) {
        citizenService.save(citizen);
        return "redirect:/login";
    }
}
