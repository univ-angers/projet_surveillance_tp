//import model.*;


import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 */
public class JSONServer {


    private ServerSocket serverSocket;
    private int port;
    public static int clients = 0;


    public void establish(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        System.out.println("JSONServer has been established on port " + port);

    }


    public void accept() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new MyThreadHandler(socket);
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static class MyThreadHandler implements Runnable {
        private Socket socket;

        MyThreadHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            clients++;
            System.out.println(clients + " JSONClient(s) connected on port: " + socket.getPort());

            try {
                // For JSON Protocol
            	
            	
                JSONObject jsonObject = receiveJSON();
                sendJSON(jsonObject);
                closeSocket();

            } catch (IOException e) {

            } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
                try {
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSocket() throws IOException {
            socket.close();
        }


        /**
         * use the JSON Protocol to receive a json object as
         * String from the client and reconstructs that object
         * @return JSONObejct with the same state (data) as
         * the JSONObject the client sent as a String msg.
         * @throws IOException
         * @throws JSONException 
         * @throws ParseException 
         */
        public JSONObject receiveJSON() throws IOException, JSONException, ParseException {
        
        	BufferedReader input = null;
        	
       	 try {
   	            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   	           
   	        } catch(IOException e) {
   	            System.err.println("Association des flux impossible : " + e);
   	            System.exit(-1);
   	        }
       	 
       	 String line="";
       	 try {
   	            line = input.readLine();
   	        } catch(IOException e) {
   	            System.err.println("Erreur lors de la lecture : " + e);
   	            System.exit(-1);
   	        }
   	        System.out.println("Client sends: " + line);	
   	     JSONParser parser = new JSONParser();
   	  JSONObject obj = (JSONObject) parser.parse(line);
   	        
   	 
   	        return obj;
        	
        }


        public void sendJSON(JSONObject jsonObject) throws IOException, JSONException {
        	PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        	
        	
        	output.println(jsonObject.toJSONString());

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(out);
    }
    }

    public void start(int port) throws IOException{
        establish(port);
        accept();
    }

    public static void main(String[] args) {
    	
        JSONServer server = new JSONServer();

        try {
            server.start(7777);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e);
        }
    }
}