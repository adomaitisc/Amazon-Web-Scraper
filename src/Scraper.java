import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	
	private boolean AVAILABLE;
	private String URL;
	private String ID;
	private String NAME;
	private String PRICE;
	private Document DOC;

	public Scraper(String productURL) {
		this.URL = productURL;
		System.out.println("Scraper Running");
		
		try {
			this.DOC = Jsoup.connect(productURL)
//					.header("Host", "www.amazon.com")
					.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.3 Safari/605.1.15")
//				    .header("Accept-Language", "en-US,en;q=0.9")
//				    .header("Accept-Encoding", "gzip, deflate, br")
//				    .header("Connection", "keep-alive")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//See if price is on product page
	public boolean isAvailable() {
		return true;
	}
	
	//See if price is on product page
	public String getTitle() {
		return DOC.title();
	}
	
	//get an id for the product
	private String getProductId() {
		return "ID";
	}
	
	private String getProductName() {
		//get name from document
		return "NAME";
	}
	
	private String getProductPrice() {
		//get price from document
		return "PRICE";
	}
	
	private String getProductBuyingOptionURL() {
		//get buying option URL from document
		return "B_O_URL";
	}
	
	private String getProductBuyingOptionPrice() {
		//get buying option price from document
		return "B_O_PRICE";
	}
	
	
	private void initializeScraper() {
		this.AVAILABLE = isAvailable();
		this.ID = getProductId();
		this.NAME = getProductName();
		if(this.AVAILABLE) {
			this.PRICE = getProductPrice();
		}
		else {
			this.URL = getProductBuyingOptionURL();
			this.PRICE = getProductBuyingOptionPrice();
		}
	}
	
	public String getProductData() {
		initializeScraper();
		//build json object and return as string
		return "Product Data";
	}
}
