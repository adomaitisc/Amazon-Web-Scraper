package amazon;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String productURL = input.nextLine();
		input.close();
		
		Scraper product = new Scraper(productURL);
	}

}
