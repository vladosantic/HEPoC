package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ElectionController {

    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CitizenService citizenService;

    public ElectionController(ElectionService electionService, CitizenService citizenService) {
        this.electionService = electionService;
        this.citizenService = citizenService;
    }

    @GetMapping("/elections")
    public String showElectionPage(Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);

        List<Election> allElections = electionService.getAllElections();
        List<Election> currentElections = new ArrayList<>();
        List<Election> expiredElections = new ArrayList<>();

        for (Election election : allElections) {
            if (election.isCurrent()) {
                currentElections.add(election);
            } else {
                expiredElections.add(election);
            }
        }

        model.addAttribute("currentElections", currentElections);
        model.addAttribute("expiredElections", expiredElections);

        return "elections";
    }
}
