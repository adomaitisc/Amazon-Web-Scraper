package amazon_monitor;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	private String url;
	private String id;
	private String name;
	private String price;
	
//	private String imageURL;
	
	private Document HTML;

	public Scraper(String productURL, boolean run) {
		this.url = productURL;
		
		try {
			this.HTML = Jsoup.connect(productURL)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64)")
				.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(run) this.execute();
	}
	
	private void idFromURL() {
		String[] ids = this.url.split("/", 0);
		this.id = ids[5];
	}
	
	private void scrapeName() {
		this.name = HTML.select("span.a-size-large").first().text();
	}
	
	private void scrapePrice() {
		this.price = HTML.select("span.a-offscreen").first().text();
	}
	
//	private void scrapeImage() {
//		this.imageURL = 
//	}
	
	public String getURL() {
		return this.url;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public String getPrice() {
		return this.price;
	}
	
//	public String getImage() {
//		return this.imageURL;
//	}
	
	public void execute() {
		idFromURL();
		scrapeName();
		scrapePrice();
	}
}
