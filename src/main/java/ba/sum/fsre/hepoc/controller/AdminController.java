package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.service.CandidateService;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CandidateService candidateService;

    @Autowired
    private final CitizenService citizenService;

    public AdminController(ElectionService electionService, CandidateService candidateService, CitizenService citizenService) {
        this.electionService = electionService;
        this.candidateService = candidateService;
        this.citizenService = citizenService;
    }

    @GetMapping("/admin")
    public String showAdminPage (Model model) {
        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "admin";
    }
}
