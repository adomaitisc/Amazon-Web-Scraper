import java.util.Scanner;

public class Main {

	public static void main(String[] args) {		
		Scraper product = new Scraper("https://www.amazon.com/at-at-Ultimate-Collector-Building-Pieces/dp/B09MV3H7WX/ref=pb_allspark_dp_sims_pao_desktop_session_based_5/140-3123829-3494248?pd_rd_w=VswQ5&pf_rd_p=e896123b-6614-49c5-873e-d532e726c2f0&pf_rd_r=44T0AXNWQRQDKB76PMJB&pd_rd_r=375faa50-99aa-4270-9c3f-18fa5174f2af&pd_rd_wg=ZlTvZ&pd_rd_i=B09MV3H7WX&psc=1");
		System.out.println(product.getTitle());
	}

}
