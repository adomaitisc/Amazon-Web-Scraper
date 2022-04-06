package amazon_monitor;

public class Main {

	public static void main(String[] args) {		
		testScraper();
	}
	
	public static void testScraper() {
		
		String productURL = "https://www.amazon.com/LEGO-Ultimate-Millennium-Falcon-Building/dp/B075SDMMMV";
		
		Scraper product = new Scraper(productURL, true);
		
		String webhookURL = "https://discord.com/api/webhooks/943528557353762846/piYYPcg2SP1qJajPp8TAjnMaBW6Bkq_u2MwPelAi6ADeM9F9PqMcXbfZbg6GB-P-gUUp";
		
		String name_description = product.getName();
		
		String url_link = product.getURL();
		
		String[] prices_fields = {product.getPrice(), "--"};
		
		Webhook message = new Webhook(webhookURL, name_description, url_link, prices_fields, true);
		
	}
}
