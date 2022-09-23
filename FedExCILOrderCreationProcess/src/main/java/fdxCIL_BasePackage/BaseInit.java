package fdxCIL_BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseInit {
	public static WebDriver driver;
	public static Logger logger;
	public static Logger logs;
	public static ExtentReports report;
	public static ExtentTest test;

	public static StringBuilder msg = new StringBuilder();
	public static Properties storage = new Properties();

	String BaseURL;

	@BeforeSuite
	public void beforeMethod() throws InterruptedException, IOException {
		if (driver == null) {
			String logFilename = this.getClass().getSimpleName();
			logs = Logger.getLogger(logFilename);
			startTest();
			storage = new Properties();
			FileInputStream fi = new FileInputStream(".\\src\\main\\resources\\config.properties");
			storage.load(fi);
			logs.info("initialization of the properties file is done");

			// --Opening Chrome Browser
			DesiredCapabilities capabilities = new DesiredCapabilities();
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("--headless", "--window-size=1382, 744");
			options.addArguments("--incognito");
			options.addArguments("--test-type");
			options.addArguments("--no-proxy-server");
			options.addArguments("--proxy-bypass-list=*");
			options.addArguments("--disable-extensions");
			options.addArguments("--no-sandbox");
			options.addArguments("enable-automation");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--disable-gpu");
			// options.addArguments("--start-maximized");
			// options.addArguments("window-size=1920,1200");
			String downloadFilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\Downloads";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("safebrowsing.enabled", "false");
			chromePrefs.put("download.default_directory", downloadFilepath);
			options.setExperimentalOption("prefs", chromePrefs);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setPlatform(Platform.ANY);
			driver = new ChromeDriver(options);

			// Default size
			driver.manage().window().maximize();

			/*
			 * Dimension currentDimension = driver.manage().window().getSize(); int height =
			 * currentDimension.getHeight(); int width = currentDimension.getWidth();
			 * System.out.println("Current height: " + height);
			 * System.out.println("Current width: " + width);
			 * System.out.println("window size==" + driver.manage().window().getSize());
			 */

			/*
			 * Dimension newDimension = new Dimension(1038, 776);
			 * driver.manage().window().setSize(newDimension);
			 */

			String Env = storage.getProperty("Env");
			System.out.println("Env " + Env);
			String baseUrl = null;
			if (Env.equalsIgnoreCase("Pre-Prod")) {
				baseUrl = storage.getProperty("PREPRODURL");
			} else if (Env.equalsIgnoreCase("STG")) {
				baseUrl = storage.getProperty("STGURL");
			} else if (Env.equalsIgnoreCase("DEV")) {
				baseUrl = storage.getProperty("DEVURL");
			}

			driver.get(baseUrl);
			Thread.sleep(2000);

		}

	}

	@BeforeMethod
	public void testMethodName(Method method) {

		String testName = method.getName();
		test = report.startTest(testName);

	}

	public static void startTest() {
		// You could find the xml file below. Create xml file in your project and copy
		// past the code mentioned below

		System.setProperty("extent.reporter.pdf.start", "true");
		System.setProperty("extent.reporter.pdf.out", "./Report/PDFExtentReport/ExtentPDF.pdf");

		// report.loadConfig(new File(System.getProperty("user.dir")
		// +"\\extent-config.xml"));
		report = new ExtentReports("./Report/ExtentReport/ExtentReportResults.html", true);
		// test = report.startTest();
	}

	public static void endTest() {
		report.endTest(test);
		report.flush();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/FedEXCIL_Screenshot/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static String getFailScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/FailedTestsScreenshots/" + screenshotName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static WebElement isElementPresent(String propkey) {

		try {

			if (propkey.contains("xpath")) {

				return driver.findElement(By.xpath(storage.getProperty(propkey)));

			} else if (propkey.contains("id")) {

				return driver.findElement(By.id(storage.getProperty(propkey)));

			} else if (propkey.contains("name")) {

				return driver.findElement(By.name(storage.getProperty(propkey)));

			} else if (propkey.contains("linkText")) {

				return driver.findElement(By.linkText(storage.getProperty(propkey)));

			} else if (propkey.contains("className")) {

				return driver.findElement(By.className(storage.getProperty(propkey)));

			} else if (propkey.contains("cssSelector")) {

				return driver.findElement(By.cssSelector(storage.getProperty(propkey)));

			} else {

				System.out.println("propkey is not defined");

				logs.info("prop key is not defined");
			}

		} catch (Exception e) {

		}
		return null;

	}

	public static void highLight(WebElement element, WebDriver driver) {
		// for (int i = 0; i < 2; i++) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 4px solid red;");
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			// test.log(LogStatus.FAIL, "Test Case Failed is " +
			// result.getThrowable().getMessage());
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent reports using
			// "logs.addScreenCapture" method.
			String screenshotPath = getFailScreenshot(driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Pass is " + result.getName());
			String screenshotPath = getScreenshot(driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.PASS, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
	}

	public void login() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		String Env = storage.getProperty("Env");
		String baseUrl = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			baseUrl = storage.getProperty("PREPRODConnectURL");
			driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("PREPRODConnectUserName");
				highLight(isElementPresent("UserName_id"), driver);
				isElementPresent("UserName_id").sendKeys(UserName);
				logs.info("Entered UserName");
				String Password = storage.getProperty("PREPRODConnectPassword");
				highLight(isElementPresent("Password_id"), driver);
				isElementPresent("Password_id").sendKeys(Password);
				logs.info("Entered Password");

			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(driver, "LoginIssue");
				driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\FDXCIL_ScreenShot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + " FedEXCIL Order Creation&Processing";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					Email.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logs.error(ex);
				}

			}

		} else if (Env.equalsIgnoreCase("STG")) {
			baseUrl = storage.getProperty("STGConnectURL");
			driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("STGConnectUserName");
				highLight(isElementPresent("UserName_id"), driver);
				isElementPresent("UserName_id").sendKeys(UserName);
				logs.info("Entered UserName");
				String Password = storage.getProperty("STGConnectPassword");
				highLight(isElementPresent("Password_id"), driver);
				isElementPresent("Password_id").sendKeys(Password);
				logs.info("Entered Password");
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(driver, "LoginIssue");
				driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\FDXCIL_ScreenShot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + " FedEXCIL Order Creation&Processing";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					Email.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logs.error(ex);
				}

			}

		} else if (Env.equalsIgnoreCase("DEV")) {
			baseUrl = storage.getProperty("DEVURL");
			driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("DEVUserName");
				highLight(isElementPresent("UserName_id"), driver);
				isElementPresent("UserName_id").sendKeys(UserName);
				logs.info("Entered UserName");
				String Password = storage.getProperty("DEVPassword");
				highLight(isElementPresent("Password_id"), driver);
				isElementPresent("Password_id").sendKeys(Password);
				logs.info("Entered Password");
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(driver, "LoginIssue");
				driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\FDXCIL_ScreenShot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + " FedEXCIL Order Creation&Processing";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					Email.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logs.error(ex);
				}

			}
		}

		BaseURL = baseUrl;
		highLight(isElementPresent("Login_id"), driver);
		isElementPresent("Login_id").click();
		logs.info("Login done");
		getScreenshot(driver, "ConnectLogin");
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Logging In...')]")));
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception ee) {
			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}

	public void logOut() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement LogOut = isElementPresent("LogOut_linkText");
		act.moveToElement(LogOut).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(LogOut));
		highLight(LogOut, driver);
		js.executeScript("arguments[0].click();", LogOut);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-bind=\"LogoutMessage\"]")));
		String LogOutMsg = isElementPresent("LogOutMsg_xpath").getText();
		logs.info("Logout Message is displayed==" + LogOutMsg);
		logs.info("Logout done");
		getScreenshot(driver, "ConnectLogOut");

	}

	public void Complete() throws Exception {
		driver.close();
		driver.quit();

	}

	public String CuDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current Date :- " + date1);
		return date1;
	}

	public static String getDate(Calendar cal) {
		return "" + cal.get(Calendar.MONTH) + "/" + (cal.get(Calendar.DATE) + 1) + "/" + cal.get(Calendar.YEAR);
	}

	public static Date addDays(Date d, int days) {
		d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		return d;
	}

	public void scrollToElement(WebElement element, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void pagination() {
		Actions act = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Check paging
		List<WebElement> pagination = driver
				.findElements(By.xpath("//*[@class=\"dx-pages\"]//div[contains(@aria-label,'Page')]"));
		System.out.println("size of pagination is==" + pagination.size());

		if (pagination.size() > 0) {
			WebElement pageinfo = driver.findElement(By.xpath("//*[@class=\"dx-info\"]"));
			System.out.println("page info is==" + pageinfo.getText());
			WebElement pagerdiv = driver.findElement(By.className("dx-pages"));
			WebElement secndpage = driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
			WebElement prevpage = driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
			WebElement nextpage = driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
			WebElement firstpage = driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
			// Scroll
			js.executeScript("arguments[0].scrollIntoView();", pagerdiv);

			if (pagination.size() > 1) {
				// click on page 2
				secndpage = driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
				act.moveToElement(secndpage).click().perform();
				System.out.println("Clicked on page 2");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on previous button
				prevpage = driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				prevpage = driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				act.moveToElement(prevpage).click().perform();
				System.out.println("clicked on previous page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on next button
				nextpage = driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				nextpage = driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				act.moveToElement(nextpage).click().perform();
				System.out.println("clicked on next page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				firstpage = driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
				act.moveToElement(firstpage).click().perform();
				System.out.println("Clicked on page 1");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} else {
				System.out.println("Only 1 page is exist");
			}

		} else {
			System.out.println("pagination is not exist");
		}
	}

	public static String getData(String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));
		FIS.close();
		return Cell;
	}

	public static void setData(String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		sh.getRow(row).createCell(col).setCellValue(value);
		workbook.write(fos1);
		fos1.close();
		fis.close();
	}

	public static int getTotalRow(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		int rowNum = sh1.getLastRowNum() + 1;
		FIS.close();
		return rowNum;

	}

	public static int getTotalCol(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		Row row = sh1.getRow(0);
		int colNum = row.getLastCellNum();
		FIS.close();
		return colNum;

	}

	@AfterSuite
	public void SendEmail() throws Exception {
		try {
			logOut();
		} catch (Exception e) {
			System.out.println("Logout is not there for FedExCIL");
			logs.info("Logout is not there for FedExCIL");
		}
		report.flush();
		// --Close browser
		Complete();
		System.out.println("====Sending Email=====");
		logs.info("====Sending Email=====");
		// Send Details email

		msg.append("\n" + "*** This is automated generated email and send through automation script ***" + "\n");
		msg.append("Process URL : " + BaseURL + "\n");
		msg.append("Please find attached file of Report and Log");

		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script:" + Env + " FedExCIL Order Creation&Processing";
		String File = ".\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\ConnectOrderProcess.html";

		try {
//			/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

			/*
			 * Email.sendMail(
			 * "ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com, saurabh.jain@samyak.com, himanshu.dholakia@samyak.com"
			 * , subject, msg.toString(), File);
			 */
			Email.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			logs.error(ex);
		}
	}

	public void isFileDownloaded(String fileName) {
		String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources";
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				logs.info("File is exist with FileName==" + fileName);
				// File has been found, it can now be deleted:
				dirContents[i].delete();
				logs.info(fileName + " File is Deleted");

			} else {
				logs.info("File is not exist with Filename==" + fileName);
			}
		}

	}

	public static void waitUntilFileToDownload(String Name) throws InterruptedException {
		String folderLocation = System.getProperty("user.dir") + "\\src\\main\\resources";
		File directory = new File(folderLocation);
		boolean downloadinFilePresence = false;
		File[] filesList = null;
		LOOP: while (true) {
			filesList = directory.listFiles();
			for (File file : filesList) {
				downloadinFilePresence = file.getName().contains(Name);
			}
			if (downloadinFilePresence) {
				for (; downloadinFilePresence;) {
					Thread.sleep(5000);
					continue LOOP;
				}
			} else {
				logs.info("File is Downloaded successfully:Verified");
				break;
			}
		}
	}

	public String getTimeAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logs.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		} else if (timeZone.equalsIgnoreCase("MDT")) {
			timeZone = "America/Denver";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logs.info(dateFormat.format(date));
		String Time = dateFormat.format(date);
		System.out.println("Time==" + Time);
		return Time;

	}

	public String getDateAsTZone(String timeZone) {

		System.out.println("ZoneID is==" + timeZone);
		logs.info("ZoneID is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logs.info(dateFormat.format(date));
		String Date = dateFormat.format(date);
		System.out.println("Date==" + Date);
		return Date;

	}

	public void ActivateAccount()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		// --Go to Customer
		WebElement Customers = isElementPresent("Customers_id");
		wait.until(ExpectedConditions.visibilityOf(Customers));
		wait.until(ExpectedConditions.elementToBeClickable(Customers));
		act.moveToElement(Customers).click().build().perform();
		logs.info("Click on Customers menu");

		// --Go to Customer
		WebElement Customer = isElementPresent("Customer_id");
		wait.until(ExpectedConditions.visibilityOf(Customer));
		wait.until(ExpectedConditions.elementToBeClickable(Customer));
		act.moveToElement(Customer).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logs.info("Click on Customer");

		// --Get Account No
		String Env = storage.getProperty("Env");
		String FedExAC = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FedExAC = getData("Sheet1", 5, 2);
		} else if (Env.equalsIgnoreCase("STG")) {
			FedExAC = getData("Sheet1", 5, 2);
		} else if (Env.equalsIgnoreCase("DEV")) {
			FedExAC = getData("Sheet1", 5, 2);
		}

		// --Enter Account No
		WebElement CustomerInput = isElementPresent("CustACID_id");
		wait.until(ExpectedConditions.visibilityOf(CustomerInput));
		wait.until(ExpectedConditions.elementToBeClickable(CustomerInput));
		CustomerInput.clear();
		CustomerInput.sendKeys(FedExAC);
		logs.info("Enter Account Number");

		// --Click on Search button
		WebElement SearchButton = isElementPresent("CustSearchBTN_id");
		wait.until(ExpectedConditions.visibilityOf(SearchButton));
		wait.until(ExpectedConditions.elementToBeClickable(SearchButton));
		act.moveToElement(SearchButton).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logs.info("Click on Search button");

		// --Waiting for Customer Editor
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("customerscreens")));

		// --Activate the status of Customer
		Select CustSTatus = new Select(isElementPresent("CustEdStatusDrop_id"));
		CustSTatus.selectByVisibleText("Active Account");
		Thread.sleep(2000);
		logs.info("Activate the Account");

		// --Click on Save button
		WebElement SaveBTN = isElementPresent("CustEdSaveBtn_xpath");
		wait.until(ExpectedConditions.visibilityOf(SaveBTN));
		wait.until(ExpectedConditions.elementToBeClickable(SaveBTN));
		act.moveToElement(SaveBTN).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logs.info("Click on Save button");

		// --Wait for Success Msg
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RecordSave")));
		String SucMsg = isElementPresent("CustEdSuccMsg_id").getText();
		logs.info("SuccessMessage=" + SucMsg);
		logs.info("Account Activated successfully");

		// --Refresh App
		refreshApp();

	}

	public void refreshApp() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);

		try {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		} catch (Exception refresh) {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			act.moveToElement(NGLLOgo).click().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		}

	}
}
