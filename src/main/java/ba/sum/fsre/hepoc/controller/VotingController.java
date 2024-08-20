package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.service.CandidateService;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class VotingController {
    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CandidateService candidateService;

    @Autowired
    private final CitizenService citizenService;

    public VotingController(ElectionService electionService, CandidateService candidateService, CitizenService citizenService) {
        this.electionService = electionService;
        this.candidateService = candidateService;
        this.citizenService = citizenService;
    }

    @GetMapping("/voting/{id}")
    public String showVotingPage(@PathVariable("id") Integer electionId, Model model, Principal principal) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        model.addAttribute("citizen", citizen);
        Election election = electionService.getElectionById(electionId);
        List<Candidate> candidates = candidateService.findAllFromElection(election);

        model.addAttribute("citizen", citizen);
        model.addAttribute("election", election);
        model.addAttribute("candidates", candidates);

        return "vote";
    }
}
