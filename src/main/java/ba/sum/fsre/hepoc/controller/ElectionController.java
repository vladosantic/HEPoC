package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.ElectionService;
import ba.sum.fsre.hepoc.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ElectionController {

    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CitizenService citizenService;

    @Autowired
    private final ResultService resultService;

    public ElectionController(ElectionService electionService, CitizenService citizenService, ResultService resultService) {
        this.electionService = electionService;
        this.citizenService = citizenService;
        this.resultService = resultService;
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
        model.addAttribute("citizen", citizen);

        return "elections";
    }

    @GetMapping("/election/results/{id}")
    public String viewResults(@PathVariable Integer id, Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);

        Map<Candidate, Long> results = resultService.getElectionResults(id);
        model.addAttribute("results", results);
        model.addAttribute("electionId", id);

        return "results";
    }
}
