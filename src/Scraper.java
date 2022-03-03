
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	private Document DOC;
	public boolean AVAILABLE;
	public String URL;
	public String ID;
	public String NAME;
	public String PRICE;

	public Scraper(String productURL) {
		this.URL = productURL;
		
		try {
			this.DOC = Jsoup.connect(productURL)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
				.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getAllData();
	}
	
	private void isAvailable() {
		String buy = DOC.select("div#buyNow").first().text();
		if(buy.length() > 3) {
			this.AVAILABLE = true;
		} else {
			this.AVAILABLE = false;
		}
	}
	
	private void getProductId() {
		String[] ids = this.URL.split("/", 0);
		this.ID = ids[5];
	}
	
	private void getProductName() {
		this.NAME = DOC.select("span.a-size-large").first().text();
	}
	
	private void getProductPrice() {
		this.PRICE = DOC.select("span.a-offscreen").first().text();
	}
	
	private void getAllData() {
		isAvailable();
		getProductId();
		getProductName();
		getProductPrice();
	}
	
	public String toString() {
		if(AVAILABLE) return "This item is available";
		return "This item is not available";
	}
}
