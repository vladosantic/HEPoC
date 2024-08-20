package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.service.CandidateService;
import ba.sum.fsre.hepoc.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class VotingController {
    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CandidateService candidateService;

    public VotingController(ElectionService electionService, CandidateService candidateService) {
        this.electionService = electionService;
        this.candidateService = candidateService;
    }

    @GetMapping("/voting/{id}")
    public String showVotingPage(@PathVariable("id") Integer electionId, Model model) {
        Election election = electionService.getElectionById(electionId);
        List<Candidate> candidates = candidateService.findAllFromElection(election);

        model.addAttribute("election", election);
        model.addAttribute("candidates", candidates);

        return "vote";
    }
}
