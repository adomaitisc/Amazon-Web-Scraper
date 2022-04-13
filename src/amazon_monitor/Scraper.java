package amazon_monitor;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.beans.property.SimpleStringProperty;

public class Scraper {
	
	SimpleStringProperty url;
	SimpleStringProperty id;
	SimpleStringProperty name;
	SimpleStringProperty description;
	SimpleStringProperty price;
	
	private Document HTML;

	public Scraper(String productURL) {
		this.url = new SimpleStringProperty(productURL);
		
		try {
			this.HTML = Jsoup.connect(productURL)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64)")
				.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.execute();
	}
	
	private void idFromURL() {
		String[] ids = this.url.get().split("/", 0);
		this.id = new SimpleStringProperty(ids[5]);
	}
	
	private void scrapeName() {
		try {
			this.name = new SimpleStringProperty(HTML.select("span#productTitle").first().text());
		} catch(Exception e) {
			this.name = new SimpleStringProperty("Name is not available for this item.");
		}
	}
	
	private void scrapePrice() {
		try {
			this.price = new SimpleStringProperty(HTML.select("span.a-offscreen").first().text());
		} catch(Exception e) {
			this.price = new SimpleStringProperty("Price is not available for this item.");
		}
	}
	
	private void scrapeDescription() {
		try {
			this.description = new SimpleStringProperty(HTML.select("div#productDescription").first().text());
		} catch(Exception e) {
			this.description = new SimpleStringProperty("Description is not available for this item.");
		}
	}
	
	public String getURL() {
		return this.url.get();
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public String getDescription() {
		return this.description.get();
	}

	public String getPrice() {
		return this.price.get();
	}
	
	public void execute() {
		idFromURL();
		scrapeName();
		scrapePrice();
		scrapeDescription();
	}
}
