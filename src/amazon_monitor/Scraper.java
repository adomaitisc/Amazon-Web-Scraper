package amazon_monitor;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	private String url;
	private String id;
	private String name;
	private String description;
	private String price;
	
	private Document HTML;

	public Scraper(String productURL) {
		this.url = productURL;
		
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
		String[] ids = this.url.split("/", 0);
		this.id = ids[5];
	}
	
	private void scrapeName() {
		try {
			this.name = HTML.select("span#productTitle").first().text();
		} catch(Exception e) {
			this.name = "Name is not available for this item.";
		}
	}
	
	private void scrapePrice() {
		try {
			this.price = HTML.select("span.a-offscreen").first().text();
		} catch(Exception e) {
			this.price = "Price is not available for this item.";
		}
	}
	
	private void scrapeDescription() {
		try {
			this.description = HTML.select("div#productDescription").first().text();
		} catch(Exception e) {
			this.description = "Description is not available for this item.";
		}
	}
	
	public String getURL() {
		return this.url;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public String getPrice() {
		return this.price;
	}
	
	public void execute() {
		idFromURL();
		scrapeName();
		scrapePrice();
		scrapeDescription();
	}
}
