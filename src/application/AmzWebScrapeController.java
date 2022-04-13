package application;

import amazon_monitor.Scraper;
import amazon_monitor.Webhook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class AmzWebScrapeController {

	//initialize JavaFX variable in the gui (all from the auto generated controller class)
	@FXML
    private Button btnAdd;

    @FXML
    private Button btnCopyURL;

    @FXML
    private Button btnRemove;

    @FXML
    private ImageView imgviewLogo;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;
    
    @FXML
    private Label lblWebhook;

    @FXML
    private TableColumn<Scraper, String> nameCol;

    @FXML
    private TableColumn<Scraper, String> priceCol;

    @FXML
    private TableView<Scraper> tbleItems;

    @FXML
    private TextField txtProdLink;
    
    //initialize other varaiables (variables manually created)
    private static ObservableList<Scraper> listItems = FXCollections.observableArrayList();
    private final static String webhookURL = "https://discord.com/api/webhooks/943528557353762846/piYYPcg2SP1qJajPp8TAjnMaBW6Bkq_u2MwPelAi6ADeM9F9PqMcXbfZbg6GB-P-gUUp";
    
    //initialize time variables for how often the thread checks prices
    final static int ONE_SECOND = 1000;
	final static int TEN_SCEONDS = 10000;
	final static int ONE_MINUTE = 60000;
	final static int ONE_HOUR = 60*ONE_MINUTE;

    /**
     * initializes the app on startup
     */
    public void initialize()
    {
    	/*
    	 * Sets imageView in the top left to the amazon logo and sets lblwebhook to the webhook url
    	 */
    	Image amzLogo = new Image(new File("data/amazonLogo.png").toURI().toString());
    	imgviewLogo.setImage(amzLogo);
    	lblWebhook.setText(webhookURL);
    	/*
    	 * end of setting imageView and lbl
    	 */
    	
    	
    	/*
    	 * Sets up the columns in the table view to take the specific info from the scrapers
    	 */
    	nameCol.setCellValueFactory(new PropertyValueFactory<Scraper, String>("name"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<Scraper, String>("price"));
    	
    	tbleItems.setItems((ObservableList<Scraper>) listItems);
    	/*
    	 * end of setting up columns
    	 */
    	
    	/*
    	 * creating a new thread which checks for price changes
    	 */
    	CheckPrices thread = new CheckPrices();
        thread.setDaemon(true);
        thread.start();
        /*
         * end of thread for price changes
         */
    	
    }
    
    /**
     *Class which acts as the thread that runs to check if the prices have gone down on each item
     */
    public static class CheckPrices extends Thread 
    {

        @Override
        public void run() 
        {
        	int testTrackRuns = 0;
            while (true) 
            {
            	testTrackRuns += 1;
            	
                System.out.println("\nChecking prices...");
                if(!listItems.isEmpty())
                {
                	/*
                	 * loop through each scraper
                	 * check their prices and then scrape the urls again to see if there has been a change in the prices
                	 */
                	for(int i = 0; i < listItems.size();i++)
                	{
                		String itemName = listItems.get(i).getName();
                		String oldPrice = listItems.get(i).getPrice();
                		listItems.get(i).execute();
                		String newPrice = listItems.get(i).getPrice();
                		
                		Double oldP = Double.parseDouble(oldPrice.replace("$", ""));
                		Double newP = Double.parseDouble(newPrice.replace("$", ""));
                		
                		System.out.println(itemName);
                		System.out.println("Old Price: $" + oldP + "\nNew Price: $" + newP);
                		
                		//for testing/demo purposes send notification on 6th run since a price change can't be forced by us
                		if(newP < oldP || testTrackRuns == 6)
                		{
                			System.out.println("Sending Webhook notification");
                			
                			/*
                        	 * Take the webhook URL
                        	 * Create a webhook message that gives some info on the item and declares that this message is just for a new item being added to the monitor
                        	 */
                    		Webhook message = new Webhook();
                			message.setURL(webhookURL);
                			message.setDescription(listItems.get(i).getDescription());
                			message.setLink(listItems.get(i).getURL());
                			message.setTitle("PRICE DROP: " + listItems.get(i).getName());
                			message.setPrices(new String[]{oldPrice, newPrice});
                			message.execute();
                    		/*
                    		 * end of webhook
                    		 */
                		}
                	}
                }
                else
                	System.out.println("NO ITEMS TO CHECK");
                try {
                    Thread.sleep(ONE_HOUR);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    
    /**
     * On btnAdd mouse click:
     * adds a new Scraper to listItems based on the link in the text box
     * then updates the list view to have the appropriate list of items
     * the list view column info is pulled from corresponding SimpleStringProperty value in that scraper
     * @param event
     */
    @FXML
    void addItem(MouseEvent event) 
    {
    	Scraper newItem = new Scraper(txtProdLink.getText());
    	listItems.add(newItem);
    	tbleItems.setItems((ObservableList<Scraper>) listItems);
    	
    	if(webhookURL.length() > 4)
    	{
    		/*
        	 * Take the webhook URL
        	 * Create a webhook message that gives some info on the item and declares that this message is just for a new item being added to the monitor
        	 */
    		Webhook message = new Webhook();
			message.setURL(webhookURL);
			message.setDescription(newItem.getDescription());
			message.setLink(newItem.getURL());
			message.setTitle("ITEM ADDED TO MONITOR: " + newItem.getName());
			message.setPrices(new String[]{newItem.getPrice(), "--"});
			message.execute();
    		/*
    		 * end of webhook
    		 */
    	}
    	

    }
    
    
    @FXML
    void btnCopyClicked(MouseEvent event) 
    {
    	/*
    	 * Create a scraper that will act as the selected item from the table view\
    	 * then copy that item's url to the user's clipboard
    	 */
    	Scraper sel = tbleItems.getSelectionModel().getSelectedItem();
    	StringSelection urlFromScraper = new StringSelection(sel.getURL());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(urlFromScraper, null);
		/*
		 * end of copying url to clipboard
		 */
		
		/*
		 * Create an alert menu and display it to the user
		 * this menu confirms that the user has successfully coppied the url
		 */
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Copied");
		alert.setHeaderText("You have successfully copied the URL");
		alert.setContentText("The URL you have copied is " + sel.getURL());
		alert.showAndWait();
		/*
		 * end of alert menu
		 */
    } 
    
    
    /**
     * On btnRemove mouse click:
     * removes/deletes the selected scraper from the list
     * then updates the list view to have the appropriate list of items
     * also updates the next on the right/selected item to be blank since nothing will be selected
     * @param event
     */
    @FXML
    void removeItem(MouseEvent event) 
    {
    	Scraper sel = tbleItems.getSelectionModel().getSelectedItem();
		int indexOf = tbleItems.getItems().indexOf(sel);
		if (indexOf >= 0) 
		{
			listItems.remove(indexOf);
			lblName.setText("");
		    lblPrice.setText("");
			lblDescription.setText("");
		}

    }
    
    /**
     * On tbleItems mouse click:
     * fills the right side/slected item texts in with the appropriate data for that scraper
     * fills in the name, price, and description
     * @param event
     */
    @FXML
    void selectTableItem(MouseEvent event) 
    {
    	/*
    	 * Create a scraper that will act as the selected item from the table view
    	 * then if it was a primary/left click on item
    	 * 		set the labels on the right to there appropriate info
    	 * if it was a secondary/right click on an item
    	 * 		copy the item's url to the users clipboard
    	 */
    	Scraper sel = tbleItems.getSelectionModel().getSelectedItem();
    	if(event.getButton() == MouseButton.PRIMARY)
    	{
        	lblName.setText(sel.getName());
        	lblPrice.setText(sel.getPrice());
    		lblDescription.setText(sel.getDescription());
    	}
    	else if(event.getButton() == MouseButton.SECONDARY)
    	{
    		/*
    		 * copies the url to the users clipboard
    		 */
    		StringSelection urlFromScraper = new StringSelection(sel.getURL());
    		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(urlFromScraper, null);
    		/*
    		 * end of copying url to clipboard
    		 */
    		
    		
    		/*
    		 * Create an alert menu and display it to the user
    		 * this menu confirms that the user has successfully coppied the url
    		 */
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    		alert.setTitle("Copied");
    		alert.setHeaderText("You have successfully copied the URL");
    		alert.setContentText("The URL you have copied is " + sel.getURL());
    		alert.showAndWait();
    		/*
    		 * end of alert menu
    		 */
    	}
    	
    }

}
