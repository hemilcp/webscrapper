package util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebScrapperUtil {

	public static WebDriver driver;
	private static int i = 0;
	private List<String> path = new ArrayList<String>();

	/**
	 * WebScrapperUtil class serves to the controller.
	 * The constructor initialises the selenium driver using the configuration
	 * from properties file.
	 * 
	 * @throws IOException
	 */
	public WebScrapperUtil() throws IOException{

		Properties prop = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("properties.properties").getFile());
		InputStream input = new FileInputStream(file);	
		prop.load(input);


		/* set the driver path */
		
		System.setProperty("webdriver.gecko.driver", "D:\\Softwares\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();

		input.close();
	}

	/**
	 * Open the test website.
	 */
	public void openTestSite(String uri) {

		driver.navigate().to(uri);
		path.add(driver.findElement(By.tagName("H1")).getText());
	}

	/**
	 * Takes the HTML text from driver and parses until the Philosophy page is found.
	 * Max number of hops allowed = 30.
	 * As the median hops required are 19-23.
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<String> getText() throws IOException {

		int i = 0;
		// Click the first link that is not in parenthesis, until we're at the Philosophy page
		while ( !driver.getTitle().equals("Philosophy - Wikipedia") && i < 30) {

			// Print tracer for current location
			System.out.println(i+" ***"+driver.getTitle()+"*********");


			// Get the first paragraph
			WebElement firstParagraph = driver.findElement(By.tagName("p"));

			// Clicks the first link not inside text of firstParagraph
			clickFirstViableLink(firstParagraph, path);
			i++;


		}

		/**
		 * If the hops count > 30, then we have run into a dead loop. 
		 * We break the loop and declare the dead end.
		 */
		if(i >= 30){
			path.clear();
			path.add("Sorry! We couldn`t reach to Philisophy. Our search resulted in a dead loop!");
		}

		closeBrowser();
		return path;
	}

	/**
	 * Close the driver.
	 */
	public void closeBrowser() {
		driver.close();
	}

	/**
	 *  Clicks the first link the list of links after insuring that 
	 *  the link text is not inside parenthesis
	 * @param firstP
	 * @param path
	 */
	private static void clickFirstViableLink(WebElement firstP, List<String> path) {

		// Produce the list of links we can click in the first paragraph
		List<WebElement> links = firstP.findElements(By.xpath("a"));

		// Get a string for the text without what is inside the parenthensis
		String trimmedParagrpah = firstP.getText().replaceAll("\\(.+?\\)", "");

		// Once a suitable link is clicked, we return
		for ( WebElement link :  links ) {      

			if (  trimmedParagrpah.contains( link.getText() )  ) {  
				path.add(link.getText().toString());
				link.click();
				return;
			}       
		}

	}


}
