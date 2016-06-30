/**
 * Diego Wilde Rodrigues 					7239200
 * Matheus Mesquita Nascimento dos Santos 	8531459	
 * Sergio Andrade de Souza        			8531588
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Server {
	
    public static void main(String[] args) throws IOException {
    	
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("192.168.0.28", 40011));
        System.out.println(serverSocket.getLocalSocketAddress());
        
        try{
            while (true) {
                Socket socket = serverSocket.accept();
                startHandler(socket);
            }
        } finally {
            serverSocket.close();
        }
        
    }
    public void DatagramSocket(int port, InetAddress laddr) throws SocketException {
    	//InetAddress localAddress = DatagramSocket.getLocalAddress();
    }
    
    private static void startHandler(final Socket socket) throws IOException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    System.out.println("Client connected");
                    
                    //String line = reader.readLine();
                    JSONObject json1 = new JSONObject();
                    json1.put("codigo_votacao", 0);
                    json1.put("nome_candidato", "Dilma");
                    json1.put("partido", "PT");
                    json1.put("num_votos", 3);
                    
                    JSONObject json2 = new JSONObject();
                    json2.put("codigo_votacao", 1);
                    json2.put("nome_candidato", "Aecio");
                    json2.put("partido", "PSDB");
                    json2.put("num_votos", 2);
                    
                    JSONObject json3 = new JSONObject();
                    json3.put("codigo_votacao", 2);
                    json3.put("nome_candidato", "Marina");
                    json3.put("partido", "PV");
                    json3.put("num_votos", 1);
                    
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(json1);
                    jsonArray.put(json2);
                    jsonArray.put(json3);
                    
                    System.out.println(jsonArray.toString() + "\n");
                    writer.write(jsonArray.toString() + "\n");
                    writer.flush();
                    
                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    closeSocket();
                }
            }
            
            private void closeSocket(){
                try {
                    socket.close();
                } catch (IOException ex) {
                }
              }
            };
        thread.start();
    }
}