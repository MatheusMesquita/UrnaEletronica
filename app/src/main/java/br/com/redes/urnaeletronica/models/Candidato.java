package br.com.redes.urnaeletronica.models;

/**
 * Created by Matheus Mesquita on 18-Jun-16.
 */
public class Candidato {

    private int cod_vote;
    private int votes;
    private String name;
    private String party;

    public Candidato() {

    }

    public int getCod_vote() {
        return cod_vote;
    }

    public void setCod_vote(int cod_vote) {
        this.cod_vote = cod_vote;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
