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

	public WebScrapperUtil() throws IOException{

		Properties prop = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("properties.properties").getFile());
		InputStream input = new FileInputStream(file);	
		prop.load(input);


		//		System.out.println("Driver Path: "+prop.getProperty("firefoxdriver"));

		System.setProperty("webdriver.gecko.driver", "D:\\Softwares\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();

		input.close();
	}

	/**
	 * Open the test website.
	 */
	public void openTestSite(String uri) {
		//		driver.navigate().to("http://en.wikipedia.org/wiki/Molecule");
		driver.navigate().to(uri);
		path.add(driver.findElement(By.tagName("H1")).getText());

		//		path = path.insert(0, driver.findElement(By.tagName("H1")).getText());
	}


	/**
	 * grabs the status text and saves that into status.txt file
	 * 
	 * @throws IOException
	 */


	public List<String> getText() throws IOException {

		int i = 0;
		// Click the first link that is not in parenthesis, until we're at the Philosophy page
		while ( !driver.getTitle().equals("Philosophy - Wikipedia") && i < 30) {

			System.out.println(i+" ***"+driver.getTitle()+"*********");
			// Print tracer for current location


			// Get the first paragraph
			WebElement firstParagraph = driver.findElement(By.tagName("p"));

			// Clicks the first link not inside text of firstParagraph
			clickFirstViableLink(firstParagraph, path);
			i++;


		}
		// We won! The current URL will be http://en.wikipedia.org/wiki/Philosophy

		//        System.out.println("We Send back: "+path);
		if(i >= 30){
			path.clear();
			path.add("Sorry! We couldn`t reach to Philisophy. Our search resulted in a dead loop!");
		}

		closeBrowser();
		return path;
	}


	public void closeBrowser() {
		driver.close();
	}

	// Clicks the first link the list of links after insuring that 
	// the link text is not inside parenthesis
	private static void clickFirstViableLink(WebElement firstP, List<String> path) {

		// Produce the list of links we can click in the first paragraph
		List<WebElement> links = firstP.findElements(By.xpath("a"));

		// Get a string for the text without what is inside the parenthensis
		String trimmedParagrpah = firstP.getText().replaceAll("\\(.+?\\)", "");

		// Once a suitable link is clicked, we're done here
		for ( WebElement link :  links ) {      

			if (  trimmedParagrpah.contains( link.getText() )  ) {  

				path.add(link.getText().toString());
				//            	path.insert(path.length()," -> "+link.getText().toString());
				//            	System.out.println("***"+link.getText().toString());
				link.click();
				return;
			}       
		}

	}


}
