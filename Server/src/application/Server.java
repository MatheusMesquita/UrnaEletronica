package application;
/**
 * Diego Wilde Rodrigues 				7239200
 * Matheus Mesquita Nascimento dos Santos               8531459	
 * Sergio Andrade de Souza        			8531588
 */

//
import models.Candidato;
import models.Vote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class Server {
	
	private static List<Candidato> mCandidates;
	private static List<Vote> mVotes;
	
    public static void main(String[] args) throws IOException {
    	String option; //999 ou 888
    	
    	generateCandidatesList();
    	
    	mVotes = new ArrayList<>();
    	
    	ServerSocket mServerSocket = new ServerSocket(40011);
    	//192.168.0.28
        //mServerSocket.bind(new InetSocketAddress("192.168.0.100", 40011));
        System.out.println("Servidor iniciado");
    	
        try{
            while (true) {
            	Socket optionSocket = mServerSocket.accept(); 
            	option = getOption(optionSocket);
            	System.out.println(option);
            	switch (option) {
            		case("999"):
            			Socket sendingSocket = mServerSocket.accept();
                    	sendCandidates(sendingSocket);
            			break;
            		case("888"):
            			Socket receiveSocket = mServerSocket.accept();
            			receiveCandidate(receiveSocket);
            			break;
            	}
            }
        } finally {
            mServerSocket.close();
        }
        
    }
    
    private static void generateCandidatesList() {
		mCandidates = new ArrayList<>();
		
		Candidato c1 = new Candidato();
		c1.setCodigo_votacao(0);
		c1.setNome_candidato("Goku");
		c1.setPartido("DBZ");
		c1.setNum_votos(0);
		
		Candidato c2 = new Candidato();
		c2.setCodigo_votacao(1);
		c2.setNome_candidato("Fofao");
		c2.setPartido("Carreta Furacao");
		c2.setNum_votos(0);
		
		Candidato c3 = new Candidato();
		c3.setCodigo_votacao(2);
		c3.setNome_candidato("Ban Ban");
		c3.setPartido("13");
		c3.setNum_votos(0);
		
		Candidato c4 = new Candidato();
		c4.setCodigo_votacao(3);
		c4.setNome_candidato("Tiririca");
		c4.setPartido("PR");
		c4.setNum_votos(0);
                
                Candidato c5 = new Candidato();
                c5.setCodigo_votacao(4);
                c5.setNome_candidato("Branco");
                c5.setPartido("Branco");
                c5.setNum_votos(0);
		
		mCandidates.add(c1);
		mCandidates.add(c2);
		mCandidates.add(c3);
		mCandidates.add(c4);
                mCandidates.add(c5);
	}

    
	private static void sendCandidates(final Socket socket) throws IOException {
		Gson gson = new Gson();
        final JsonElement element = gson.toJsonTree(mCandidates, new TypeToken<List<Candidato>>() {}.getType()); //Transformo a lista de candidatos em Json
		
		Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8"); //escrever no socket
                    System.out.println("Servidor enviou lista de candidatos");
                                        
                    writer.write(element.toString()+"\n"); 
                    writer.flush();                    
                } catch (IOException e){
                    e.printStackTrace();
                }finally{
                    closeSocket();
                }
            }
            
            private void closeSocket(){
                try {
                    socket.close();
                } catch (IOException ex) {
                	ex.printStackTrace();
                }
            }
        };
        thread.start();
    }
    
    private static void receiveCandidate(final Socket socket) throws IOException {
    	FutureTask<Vote> thread = new FutureTask<Vote>( new Callable<Vote>() {
            @Override
            public Vote call() throws Exception {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                System.out.println("Cliente enviou candidato escolhido");   
              
                Gson gson = new Gson();
        	Vote vote = gson.fromJson(reader.readLine(), Vote.class);
                
        	socket.close();
        		
                return vote;
            }
    	});
        thread.run();
        
        try {
            mVotes.add(thread.get()); //adicionar voto retornado na lista de votos
            showTotalVotes();
            
	} catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
	}
    }
    
    private static void showTotalVotes(){
        for(Vote vote: mVotes){
            System.out.println(vote.getCandidato().getNome_candidato()+": "+vote.getCandidato().getNum_votos());
        }
        
    }
    
    private static String getOption(final Socket socket) throws IOException {
    	FutureTask<String> thread = new FutureTask<String>(new Callable<String>() //thread que vai me retornar uma string. Dentro da thread o Callable dá o retorno
        {
    		String option;
    		BufferedReader mReader;
			@Override
			public String call() throws Exception {
	            mReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	            System.out.println("Cliente enviou opção");
	            option = mReader.readLine();
	        
	        	socket.close();		           
		        
				return option;
			}
		});
    	thread.run();
    	
        try {
			return thread.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
    }
}