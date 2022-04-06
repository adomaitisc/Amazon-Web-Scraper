package amazon_monitor;

public class Main {

	public static void main(String[] args) {		
		testScraper();
	}
	
	public static void testScraper() {
		
		String productURL = "https://www.amazon.com/LEGO-Ultimate-Millennium-Falcon-Building/dp/B075SDMMMV";
		
		Scraper product = new Scraper(productURL, true);
		
		String webhookURL = "https://discord.com/api/webhooks/956575740395139152/v0gtahUBqd8rv4dCBLQfrrJ_mvku7Dazo0wDcm0MwluakgiOKZnMpaBbAq-c5oPs38rT";
		
		String name_description = product.getName();
		
		String url_link = product.getURL();
		
		String[] prices_fields = {product.getPrice(), "--"};
		
		Webhook message = new Webhook(webhookURL, name_description, url_link, prices_fields, true);
		
	}
}
