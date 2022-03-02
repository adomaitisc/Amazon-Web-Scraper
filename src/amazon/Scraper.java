package amazon;

public class Scraper {
	
	private boolean AVAILABLE;
	private String URL;
	private String ID;
	private String NAME;
	private String PRICE;

	public Scraper(String productURL) {
		this.URL = productURL;
		
		//connect to document
		//assign this.doc to html document
	}
	
	private boolean isAvailable() {
		//see if price is on document
		return true;
	}
	
	private String getProductId() {
		//get an id for the product
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
