package amazon_monitor;

import java.io.OutputStream; 
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Webhook {
	
	private String url;
	private String title = "Amazon Price Drop";
	private String description;
	private String link;
	private String[] fields;
	
	public Webhook() {
		
	}
    
    public Webhook(String webhookURL, String productName, String productURL, String[] productPrices) {
    	this.url = webhookURL;
    	this.description = productName;
    	this.link = productURL;
    	this.fields = productPrices;
    }
    
    public void setURL(String newURL) {
    	this.url = newURL;
    }
    
    public void setTitle(String newTitle) {
    	this.title = newTitle;
    }
    
	public void setDescription(String newDescription) {
	    this.description = newDescription;
	}
	
	public void setLink(String newProductURL) {
		this.link = newProductURL;
	}
	
	public void setPrices(String[] newProductPrices) {
		this.fields = newProductPrices;
	}
	
	public void execute() {
        String jsonBrut = "";
        jsonBrut += "{\"username\": \"Amazon Price Monitor\","
        		+ "\"embeds\": [{"
                + "\"title\": \""+ this.title +"\","
                + "\"description\": \""+ this.description +"\","
                + "\"url\": \""+ this.link +"\","
                + "\"color\": 15258703,"
                + "\"fields\": [{ \"name\": \"PRICE DROP\","
                + "\"value\": \"BEFORE\","
                + "\"inline\": true },{"
                + "\"name\": \"" + this.fields[0] +"\","
                + "\"value\": \""+ this.fields[1] + "\","
                + "\"inline\": true }]"
                + "}]}";
        try {
            URL url = new URL(this.url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            connection.getInputStream().close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}