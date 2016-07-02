package application;
/**
 * Diego Wilde Rodrigues 				7239200
 * Matheus Mesquita Nascimento dos Santos               8531459	
 * Sergio Andrade de Souza        			8531588
 */

import models.Data;
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
	
    private static Data mData;
    private static List<Candidato> mCandidates;
    private static List<Vote> mVotes;
    private static Candidato mNull;
    private static Candidato mWhite;
	
    public static void main(String[] args) throws IOException {
    	String option; //999 ou 888
    	
    	mData = new Data(); 
    	mCandidates = mData.getCandidates();
    	generateCandidates();
    	
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
    
    private static void generateCandidates() {
		        
        mWhite = new Candidato();
        mWhite.setCodigo_votacao(4);
        mWhite.setNome_candidato("Branco");
        mWhite.setPartido("Branco");
        mWhite.setNum_votos(0);

        mNull = new Candidato();
        mNull.setCodigo_votacao(5);
        mNull.setNome_candidato("Nulo");
        mNull.setPartido("Nulo");
        mNull.setNum_votos(0);

    }

    
	private static void sendCandidates(final Socket socket) throws IOException {
            Gson gson = new Gson();
            final JsonElement element = gson.toJsonTree(mCandidates, new TypeToken<List<Candidato>>() {}.getType()); //Transforma a lista de candidatos em Json
		
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8"); //Escrever no socket
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
                
                switch(vote.getVoteType()){
                	case "normal":
                		mCandidates.get(vote.getCandidato().getCodigo_votacao()).somaVoto();
                		break;
                	case "white":
                		mWhite.somaVoto();
                		break;
                	case "nullVote":
                		mNull.somaVoto();
                		break; 
                }
                socket.close();
        		
                return vote;
            }
    	});
        thread.run();
        
        try {
            mVotes.add(thread.get()); //Adicionar voto retornado na lista de votos
            
            showTotalVotes();
            
	} catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
	}
    }
    
    private static void showTotalVotes(){
        System.out.println("\n\n+----------------------------------------+\nLista de votos: \n");
        
        for(Candidato candidato: mCandidates){
            System.out.println(candidato.getNome_candidato()+": "+candidato.getNum_votos());
        }
        System.out.println("Total de votos: "+mVotes.size());
        System.out.println("+----------------------------------------+\n\n");
    }
    
    private static String getOption(final Socket socket) throws IOException {
    	FutureTask<String> thread = new FutureTask<String>(new Callable<String>() //Thread que vai me retornar uma string. Dentro da thread o Callable dÃ¡ o retorno
        {
            String option;
            BufferedReader mReader;
                @Override
                public String call() throws Exception {
                    mReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    System.out.println("Cliente enviou opçao");
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