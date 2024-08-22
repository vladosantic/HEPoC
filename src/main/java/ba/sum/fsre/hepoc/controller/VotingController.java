package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.entity.Vote;
import ba.sum.fsre.hepoc.repository.VotingRepository;
import ba.sum.fsre.hepoc.service.*;
import com.n1analytics.paillier.EncryptedNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private final EncryptionService encryptionService;

    @Autowired
    private final VotingService votingService;

    @Autowired
    private final ResultService resultService;

    public VotingController(ElectionService electionService, CandidateService candidateService, CitizenService citizenService, EncryptionService encryptionService, VotingService votingService, ResultService resultService) {
        this.electionService = electionService;
        this.candidateService = candidateService;
        this.citizenService = citizenService;
        this.encryptionService = encryptionService;
        this.votingService = votingService;
        this.resultService = resultService;
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

    @PostMapping("/submitVote")
    public String submitVote(@RequestParam("electionId") Integer electionId,
                             @RequestParam("candidateId") Integer candidateId,
                             Principal principal,
                             Model model) {
        String jmbg = principal.getName();
        Citizen citizen = citizenService.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));

        Vote vote = new Vote();
        vote.setElection(electionService.getElectionById(electionId));
        vote.setCitizen(citizen);
        // Encrypt the candidate id
        vote.setEncryptedVote(encryptionService.encryptNumberToString(candidateId));
        if (!citizen.isHasVoted()) {
            votingService.saveVote(vote);
            // Add the encrypted vote to the tally for the selected candidate and election
            resultService.addVote(candidateId, electionId, encryptionService.encryptNumberToString(1));

            citizen.setHasVoted(true);
            citizenService.save(citizen);

            // Redirect to a success page or show a success message
            model.addAttribute("message", "Vote submitted successfully!");
        } else {
            model.addAttribute("errorMessage", "You have already submitted your vote!");
        }

        model.addAttribute("citizen", citizen);

        return "index";
    }
}
