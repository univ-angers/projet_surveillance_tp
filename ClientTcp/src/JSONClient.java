import com.google.gson.Gson;

import java.io.*;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**

 */
public class JSONClient {

    private String host;
    private int port;
    private Socket socket;
    private final String DEFAULT_HOST = "localhost";


    public void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        System.out.println("Client has been connected..");
    }


    /**
     * use the JSON Protocol to receive a json object as
     *  from the client and reconstructs that object
     *
     * @return JSONObejct with the same state (data) as
     * the JSONObject the client sent as a String msg.
     * @throws IOException
     * @throws ParseException 
     */
    public JSONObject receiveJSON() throws IOException, ParseException {
    	

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
	        System.out.println("Server sends: " + line);	
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


    public static void main(String[] args) throws JSONException, ParseException {
        JSONClient client = new JSONClient();
        try{

            client.connect("localhost", 7777);
            // For JSON call sendJSON(JSON json) & receiveJSON();
            JSONObject jsonObject2 = new JSONObject();
            
            User user= new User("guenane","riad",22);
            jsonObject2.put("firstname",user.prenom);
            jsonObject2.put("lastname",user.nom);
            jsonObject2.put("age",user.age);
            
           
            client.sendJSON(jsonObject2);
            
        /*    Pour recevoir des données  dans notre exemple on renvoie le meme objet Json 
        JSONObject o=client.receiveJSON();
           
           */
        }

        catch (ConnectException e) {
            System.err.println(client.host + " connect refused");
            return;
        }

        catch(UnknownHostException e){
            System.err.println(client.host + " Unknown host");
            client.host = client.DEFAULT_HOST;
            return;
        }

        catch (NoRouteToHostException e) {
            System.err.println(client.host + " Unreachable");
            return;

        }

        catch (IllegalArgumentException e){
            System.err.println(client.host + " wrong port");
            return;
        }

        catch(IOException e){
            System.err.println(client.host + ' ' + e.getMessage());
            System.err.println(e);
        }
        finally {
            try {
                client.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}