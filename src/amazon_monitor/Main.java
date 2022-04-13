package amazon_monitor;

public class Main {

	public static void main(String[] args) {		
		testScraper();
	}
	
	public static void testScraper() {
		
		String webhookURL = "https://discord.com/api/webhooks/943528557353762846/piYYPcg2SP1qJajPp8TAjnMaBW6Bkq_u2MwPelAi6ADeM9F9PqMcXbfZbg6GB-P-gUUp";
		
		String[] PRODUCTS = new String[] {
				"List of URLs"
		};
		
		for( String url : PRODUCTS) {
			try {
				Scraper product = new Scraper(url);
				
				Webhook message = new Webhook();
				message.setURL(webhookURL);
				message.setDescription(product.getDescription());
				message.setLink(product.getURL());
				message.setTitle(product.getName());
				message.setPrices(new String[]{product.getPrice(), "--"});
				message.execute();
				
			} catch (Exception e) {

				Webhook def = new Webhook();
				def.setURL(webhookURL);
				def.setDescription("Unable to srcape this url.");
				def.setLink("http://localhost:3000");
				def.setTitle("Unable to scrape this url.");
				def.setPrices(new String[]{"--", "--"});
				def.execute();
			}
		}
	}
}
