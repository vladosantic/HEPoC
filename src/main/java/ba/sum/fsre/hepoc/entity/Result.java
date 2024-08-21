package ba.sum.fsre.hepoc.entity;

import jakarta.persistence.*;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "candidate_id", nullable = false)
    private Integer candidateId;

    @Column(name = "election_id", nullable = false)
    private Integer electionId;

    @Column(name = "encrypted_tally", columnDefinition = "TEXT",nullable = false)
    private String encryptedTally;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public String getEncryptedTally() {
        return encryptedTally;
    }

    public void setEncryptedTally(String encryptedTally) {
        this.encryptedTally = encryptedTally;
    }
}
