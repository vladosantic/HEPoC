package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.security.CitizenDetails;
import ba.sum.fsre.hepoc.service.CitizenDetailsService;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.EmailService;
import ba.sum.fsre.hepoc.service.TwoFactorAuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;


@Controller
public class CitizenController {

    private final CitizenService citizenService;
    private final CitizenDetailsService citizenDetailsService;
    private final TwoFactorAuthService twoFactorAuthService;
    private final EmailService emailService;

    public CitizenController(CitizenService citizenService, CitizenDetailsService citizenDetailsService, TwoFactorAuthService twoFactorAuthService, EmailService emailService) {
        this.citizenService = citizenService;
        this.citizenDetailsService = citizenDetailsService;
        this.twoFactorAuthService = twoFactorAuthService;
        this.emailService = emailService;
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

    @GetMapping("/2fa")
    public String show2FAPage(@RequestParam String jmbg, Model model) {
        model.addAttribute("jmbg", jmbg);
        return "2fa";
    }

    @PostMapping("/2fa")
    public String verify2FA(@RequestParam String jmbg, @RequestParam String code) {
        if (twoFactorAuthService.verifyCode(jmbg, code)) {
            // 2FA successful, proceed to the secure area
            return "redirect:/index";
        }
        // On failure, redirect back to the 2FA page with the JMBG and error message
        return "redirect:/2fa?jmbg=" + jmbg + "&error";
    }

    @GetMapping("/index")
    public String showIndexPage(Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);
        return "index";
    }

    @GetMapping("/")
    public String redirectToIndexPage(Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);
        return "index";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Citizen citizen,
                                      Model model) {
        if (citizenService.citizenExistsByJmbg(citizen.getJmbg())) {
            model.addAttribute("errorMessage", "Citizen with inputted JMBG already exists");
            return "register";
        } else {
            citizenService.register(citizen);
            model.addAttribute("successMessage", "Citizen successfully registered");
            return "login";
        }
    }
}
