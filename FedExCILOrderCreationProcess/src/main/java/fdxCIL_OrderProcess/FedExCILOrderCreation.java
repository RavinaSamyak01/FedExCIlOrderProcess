package fdxCIL_OrderProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import fdxCIL_BasePackage.BaseInit;

public class FedExCILOrderCreation extends BaseInit {
	static double OrderCreationTime;
	static StringBuilder msg = new StringBuilder();
	static String jobid;

	@Test
	public static void fedEXCILOrder() throws Exception {

		long start, end;
		WebDriverWait wait = new WebDriverWait(driver, 5);
		// JavascriptExecutor jse = (JavascriptExecutor) driver;

		// Read data from Excel
		File src = new File(".\\TestFiles\\FedExCILTestResult.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");

		for (int i = 1; i < 5; i++) {
			DataFormatter formatter = new DataFormatter();
			String file = formatter.formatCellValue(sh1.getRow(i).getCell(0));
			// String TFolder=".//TestFiles//";
			driver.findElement(By.id("MainContent_ctrlfileupload"))
					.sendKeys("C:\\Users\\tgandhi\\FedExCIL\\FedExCIL\\FedExCIL\\TestFiles\\" + file + ".txt");
			Thread.sleep(1000);
			driver.findElement(By.id("MainContent_btnProcess")).click();
			// --start time
			start = System.nanoTime();
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblresult")));
			String Job = driver.findElement(By.id("MainContent_lblresult")).getText();
			end = System.nanoTime();
			OrderCreationTime = (end - start) * 1.0e-9;
			System.out.println("Order Creation Time (in Seconds) = " + OrderCreationTime);
			msg.append("Order Creation Time (in Seconds) = " + OrderCreationTime + "\n");

			try {
				if (Job.contains("<LoadTenderResult>")) {

					// System.out.println(Job);

					Pattern pattern = Pattern.compile("\\w+([0-9]+)\\w+([0-9]+)");
					Matcher matcher = pattern.matcher(Job);
					matcher.find();
					jobid = matcher.group();
					System.out.println("JOB# " + jobid);

					File src1 = new File(".\\TestFiles\\FedExCILTestResult.xlsx");
					FileOutputStream fis1 = new FileOutputStream(src1);
					Sheet sh2 = workbook.getSheet("Sheet1");
					sh2.getRow(i).createCell(1).setCellValue(jobid);
					workbook.write(fis1);
					fis1.close();
					msg.append("JOB # " + jobid + "\n");
					getScreenshot(driver, "FedExCILResponse");

				} else {
					msg.append("Response== " + Job + "\n");
					msg.append("Order not created==FAIL" + "\n");
					getScreenshot(driver, "FedExCILResponse");

				}
			} catch (Exception e) {
				msg.append("Order not created==FAIL" + "\n");
				msg.append("Response== " + Job + "\n");
				getScreenshot(driver, "FedExCILResponse");

			}

		}
		Thread.sleep(5000);

	}

	public String getServiceID() {
		// --Get ServiceID

		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		logs.info("Service is==" + svc);

		return svc;
	}

	public void getStageName() {
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time

		// --Get the Stage Name
		WebElement Stage = isElementPresent("EOStageName_id");
		wait.until(ExpectedConditions.visibilityOf(Stage));
		String StageName = Stage.getText();
		System.out.println(StageName);
		logs.info("Stage=" + StageName);
		msg.append("Stage=" + StageName + "\n");

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

	public void unknowShipper(int i) throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);

		// --Unknown Shipper click
		WebElement UnShipper = isElementPresent("TLUnShipp_id");
		wait.until(ExpectedConditions.visibilityOf(UnShipper));
		wait.until(ExpectedConditions.elementToBeClickable(UnShipper));
		act.moveToElement(UnShipper).build().perform();
		js.executeScript("arguments[0].click();", UnShipper);
		logs.info("Clicked on Unknown Shipper");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Wait for pop up of Unknown Shipper
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

		// --Click on Confirm Button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnConfirmExtrernal")));
		WebElement UnShCOnfirm = isElementPresent("TLUnShConfrm_id");
		wait.until(ExpectedConditions.elementToBeClickable(UnShCOnfirm));
		UnShCOnfirm.click();
		logs.info("Clicked on Confirm button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Scroll to get Rate
		js.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("Sheet1", i, 31, cost);
		logs.info("Scroll down to Get the Rate");

		// --Click on Save Changes
		isElementPresent("TLSaveChanges_id").click();
		logs.info("Clicked on Save Changes button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
	}

	public void selectFlight() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Move to Job Status Tab
		WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.visibilityOf(JoStatusTab));
		wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
		act.moveToElement(JoStatusTab).click().build().perform();
		logs.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Select Flight
		WebElement SelectFlight = isElementPresent("TLSelFlight_id");
		wait.until(ExpectedConditions.elementToBeClickable(SelectFlight));
		SelectFlight.click();
		logs.info("Clicked on Select Flight button");

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception ExtraWait) {
			WebDriverWait wait1 = new WebDriverWait(driver, 50);// wait time
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"ItineraryForm\"]")));
		Thread.sleep(2000);

		// --CLick on Select Flight
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkSel_0")));
		WebElement Select1stFlight = isElementPresent("TLSelect1stFlgt_id");
		wait.until(ExpectedConditions.elementToBeClickable(Select1stFlight));
		Select1stFlight.click();
		logs.info("Selected 1st flight");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Assign button
		WebElement AssignFlight = isElementPresent("TLAssignFlght_xpath");
		wait.until(ExpectedConditions.elementToBeClickable(AssignFlight));
		AssignFlight.click();
		logs.info("Clicked on Assign button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
	}

	public void reCalc(String svc) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
			String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
			logs.info("Validation message is displayed=" + Valmsg);
			if (Valmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
				// Recalculate the charges
				// --Go to Edit Job tab
				WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
				act.moveToElement(EditOrTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
				act.moveToElement(EditOrTab).click().perform();
				logs.info("Click on Edit Order Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -Recalculate button
				WebElement ReCalc = isElementPresent("EORecal_id");
				act.moveToElement(ReCalc).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
				act.moveToElement(ReCalc).click().perform();
				logs.info("Click on Recalculate button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click on Save Changes button
				WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
				act.moveToElement(SaveChanges).build().perform();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
				wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
				act.moveToElement(SaveChanges).click().perform();
				logs.info("Click on Save Changes button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Go to job Status Tab
				WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
				act.moveToElement(JobOverTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
				act.moveToElement(JobOverTab).click().perform();
				logs.info("Click on Job Overview Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(5000);

				// --Enter SIgnature
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtDeliverySignature")));
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logs.info("Enter Signature");

				// --Enter Actual DL time
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualDeliveryTime")));
				isElementPresent("TLDActDLTime_id").clear();
				String DLTimeZone = isElementPresent("TLDACDTimeZone_id").getText();
				logs.info("Actual DL TimeZone==" + DLTimeZone);
				String DLTime = getTimeAsTZone(DLTimeZone);
				logs.info("Actual DL Time==" + DLTime);
				isElementPresent("TLDActDLTime_id").sendKeys(DLTime);
				logs.info("Enter Actual DL Time");

				if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")) {
					// --Click on Confirm DL button
					WebElement ConfDL = isElementPresent("TLDConfDL_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfDL));
					Thread.sleep(2000);
					act.moveToElement(ConfDL).click().build().perform();
					logs.info("Click on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				} else if (svc.equals("SD") || svc.equals("PA") || svc.equals("FRA")) {
					// --Click on Confirm DL
					WebElement ConDL = isElementPresent("TLDAOConfDrop_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConDL));
					js.executeScript("arguments[0].click();", ConDL);
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} else if (svc.equals("AIR")) {
					// --Click on Confirm DL
					WebElement ConDL = isElementPresent("TLDAIRDLStage_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConDL));
					js.executeScript("arguments[0].click();", ConDL);
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		} catch (Exception PModify) {
			logs.info("Validation message is not displayed for Recalculate the charges");

		}
	}

}
