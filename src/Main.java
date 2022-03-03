
public class Main {

	public static void main(String[] args) {		
		testScraper();
	}
	
	public static void testScraper() {
		String productURL = "https://www.amazon.com/LEGO-Ultimate-Millennium-Falcon-Building/dp/B075SDMMMV";
		
		Scraper Puzzle = new Scraper(productURL);
		
		System.out.println(Puzzle.NAME);
		System.out.println(Puzzle.ID);
		System.out.println(Puzzle.PRICE);
		System.out.println(Puzzle.AVAILABLE);
		
		System.out.println(Puzzle);
		
	}

}
