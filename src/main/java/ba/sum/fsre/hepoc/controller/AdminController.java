package ba.sum.fsre.hepoc.controller;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.service.CandidateService;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class AdminController {
    @Autowired
    private final ElectionService electionService;

    @Autowired
    private final CandidateService candidateService;

    @Autowired
    private final CitizenService citizenService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AdminController(ElectionService electionService, CandidateService candidateService, CitizenService citizenService, PasswordEncoder passwordEncoder) {
        this.electionService = electionService;
        this.candidateService = candidateService;
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAdminPage (Model model) {
        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "admin";
    }

    @PostMapping("/admin/edit/candidate")
    public String editCandidate(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("party") String party,
            @RequestParam("electionId") Integer electionId,
            Model model) {

        Candidate candidate = candidateService.findCandidateById(id);

        candidate.setName(name);
        candidate.setLastName(lastName);
        candidate.setParty(party);

        Election election = electionService.getElectionById(electionId);

        candidate.setElection(election);

        candidateService.save(candidate);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }

    @PostMapping("/admin/edit/election")
    public String editElection(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Model model) {

        Election election = electionService.getElectionById(id);

        election.setName(name);
        election.setStartDate(startDate);
        election.setEndDate(endDate);

        electionService.save(election);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }

    @PostMapping("/admin/edit/citizen")
    public String editCitizen(
            @RequestParam("id") Integer id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("jmbg") String jmbg,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            Model model) {

        Citizen citizen = citizenService.findById(id);

        citizen.setFirstName(firstName);
        citizen.setLastName(lastName);
        citizen.setJmbg(jmbg);
        citizen.setPhoneNumber(phoneNumber);
        citizen.setEmail(email);
        citizen.setAddress(address);
        citizen.setCity(city);

        citizenService.save(citizen);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/candidate/{id}")
    public String deleteCandidate(@PathVariable("id") Integer id,
                                  Model model) {
        try {
            candidateService.deleteById(id);
        } catch (Exception ex){
            model.addAttribute("errorMessage", "Something went wrong when deleting candidate.");
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/election/{id}")
    public String deleteElection(@PathVariable("id") Integer id,
                                 Model model) {
        try {
            electionService.deleteById(id);
        } catch (Exception ex){
            model.addAttribute("errorMessage", "Something went wrong when deleting election.");
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/citizen/{id}")
    public String deleteCitizen(@PathVariable("id") Integer id,
                                Model model) {
        try {
            citizenService.deleteById(id);
        } catch (Exception ex){
            model.addAttribute("errorMessage", "Something went wrong when deleting citizen.");
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/add/candidate")
    public String addCandidate(@RequestParam("name") String name,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("party") String party,
                               @RequestParam("email") String email,
                               @RequestParam("electionId") Integer electionId,
                               Model model) {
        Candidate newCandidate = new Candidate();
        newCandidate.setName(name);
        newCandidate.setLastName(lastName);
        newCandidate.setParty(party);
        newCandidate.setEmail(email);
        Election election = electionService.getElectionById(electionId);
        newCandidate.setElection(election);

        candidateService.save(newCandidate);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }

    @PostMapping("/admin/add/election")
    public String addElection(@RequestParam("name") String name,
                               @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                               Model model) {

        Election newElection = new Election();
        newElection.setName(name);
        newElection.setStartDate(startDate);
        newElection.setEndDate(endDate);

        electionService.save(newElection);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }

    @PostMapping("/admin/add/citizen")
    public String addCitizen(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("jmbg") String jmbg,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("address") String address,
                             @RequestParam("city") String city,
                             @RequestParam("dateOfBirth") String dateOfBirth,
                             Model model) {

        Citizen newCitizen = new Citizen();
        newCitizen.setFirstName(firstName);
        newCitizen.setLastName(lastName);
        newCitizen.setJmbg(jmbg);
        newCitizen.setPassword(passwordEncoder.encode(password));
        newCitizen.setEmail(email);
        newCitizen.setPhoneNumber(phoneNumber);
        newCitizen.setAddress(address);
        newCitizen.setCity(city);
        newCitizen.setDateOfBirth(dateOfBirth);

        citizenService.save(newCitizen);

        model.addAttribute("citizens", citizenService.findAll());
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("elections", electionService.getAllElections());

        return "redirect:/admin";
    }
}
