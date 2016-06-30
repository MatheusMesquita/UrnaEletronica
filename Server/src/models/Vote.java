package models;

public class Vote {
	private Candidato mCandidato;
	private String mVoteType;
	
	public Vote(Candidato candidato, String voteType){
		mCandidato = candidato;
		mVoteType = voteType;
	}

	public Candidato getCandidato() {
		return mCandidato;
	}

	public String getVoteType() {
		return mVoteType;
	}
}
