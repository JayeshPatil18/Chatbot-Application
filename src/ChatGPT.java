import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class ChatGPT {
    public String takeInput() {        
        Scanner scanner = new Scanner(System.in);
        String input = null;
        try {
            	System.out.print("Enter Prompt: ");
                input = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error while reading input: " + e.getMessage());
        }
        return input;
    }

    public String takeResponse(String input) {

        try {
            // Create a URL object for the API endpoint
            URL url = new URL("http://127.0.0.1:8000/input/" + input.replace(" ", "%"));
      
            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      
            // Set the request method to GET
            connection.setRequestMethod("GET");
      
            // Get the response code
            int responseCode = connection.getResponseCode();
      
            // If the response code indicates success
            if (responseCode == HttpURLConnection.HTTP_OK) {
              // Create a BufferedReader to read the response
              BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      
              // Read the response into a StringBuilder
              StringBuilder response = new StringBuilder();
              String line;
              while ((line = reader.readLine()) != null) {
                response.append(line);
              }
              reader.close();
              
               JSONObject json = new JSONObject(response.toString());
               
               System.out.print("Result: ");
      
              // Print the JSON response
              return json.getString("message");
            } else {
              System.out.println("API request failed with response code " + responseCode);
            }
          } catch (Exception e) {
            System.out.println("API request failed: " + e.getMessage());
          }
        return null;
    }

    public static void main(String[] args) {
        ChatGPT chatGPT = new ChatGPT();
        
        while(true) {
        	String input = chatGPT.takeInput();
        	System.out.println("Loading...");
        	System.out.println(chatGPT.takeResponse(input));
        	System.out.println();
        }
    }
}