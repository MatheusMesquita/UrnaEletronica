package models;

import java.util.ArrayList;
import java.util.List;

public class Data {
	
	private static List<Candidato> mCandidates;
	
	public Data(){
		
		mCandidates = new ArrayList<>();
		Candidato c1 = new Candidato();
		c1.setCodigo_votacao(0);
		c1.setNome_candidato("Andrea Matarazzo");
		c1.setPartido("PSD");
		c1.setNum_votos(0);
		
		Candidato c2 = new Candidato();
		c2.setCodigo_votacao(1);
		c2.setNome_candidato("Celso Russomanno");
		c2.setPartido("PRB");
		c2.setNum_votos(0);
		
		Candidato c3 = new Candidato();
		c3.setCodigo_votacao(2);
		c3.setNome_candidato("Fernando Haddad");
		c3.setPartido("PT");
		c3.setNum_votos(0);
		
		Candidato c4 = new Candidato();
		c4.setCodigo_votacao(3);
		c4.setNome_candidato("João Doria Júnior");
		c4.setPartido("PSDB");
		c4.setNum_votos(0);
		
		Candidato c5 = new Candidato();
		c5.setCodigo_votacao(4);
		c5.setNome_candidato("Levy Fidelix");
		c5.setPartido("PRTB");
		c5.setNum_votos(0);

		Candidato c6 = new Candidato();
		c6.setCodigo_votacao(5);
		c6.setNome_candidato("Luiza Erundina");
		c6.setPartido("PSOL");
		c6.setNum_votos(0);

		Candidato c7 = new Candidato();
		c7.setCodigo_votacao(6);
		c7.setNome_candidato("Marco Feliciano");
		c7.setPartido("PSC");
		c7.setNum_votos(0);

		Candidato c8 = new Candidato();
		c8.setCodigo_votacao(7);
		c8.setNome_candidato("Marlene Campos Machado");
		c8.setPartido("PTB");
		c8.setNum_votos(0);

		Candidato c9 = new Candidato();
		c9.setCodigo_votacao(8);
		c9.setNome_candidato("Marta Suplicy");
		c9.setPartido("PMDB");
		c9.setNum_votos(0);

		Candidato c10 = new Candidato();
		c10.setCodigo_votacao(9);
		c10.setNome_candidato("Paulo Maluf");
		c10.setPartido("PP");
		c10.setNum_votos(0);
		
		mCandidates.add(c1);
		mCandidates.add(c2);
		mCandidates.add(c3);
		mCandidates.add(c4);
		mCandidates.add(c5);
		mCandidates.add(c6);
		mCandidates.add(c7);
		mCandidates.add(c8);
		mCandidates.add(c9);
		mCandidates.add(c10);

	}
	
	public List<Candidato> getCandidates(){
		return mCandidates; 
	}
	
	
}