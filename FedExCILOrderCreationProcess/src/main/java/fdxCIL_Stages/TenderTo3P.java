package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class TenderTo3P extends FedExCILOrderCreation {

	@Test
	public static void tndrTo3P() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Tender to 3P')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Ship Label
			WebElement ShipLabel = isElementPresent("TT3ShipLabel_id");
			wait.until(ExpectedConditions.elementToBeClickable(ShipLabel));
			ShipLabel.click();
			logs.info("Clicked on Ship Label Services");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

			// --Select 3p Account
			Select p3acc = new Select(isElementPresent("TT3ACDrop_id"));
			p3acc.selectByIndex(1);
			logs.info("Selected 3p Account");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Select Service
			Select Contacttype = new Select(isElementPresent("TT3Servicedrp_id"));
			Contacttype.selectByVisibleText("FEDEX_GROUND");
			logs.info("Selected Service");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Submit
			WebElement Submit = isElementPresent("TT3ShiLSubmit_id");
			wait.until(ExpectedConditions.elementToBeClickable(Submit));
			Submit.click();
			logs.info("Clicked on Submit button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Close
			WebElement Close = isElementPresent("TT3AhipLClose_id");
			wait.until(ExpectedConditions.elementToBeClickable(Close));
			Close.click();
			logs.info("Clicked on Close button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txt3pTime")));

			// --Enter Drop Time
			WebElement DropTime = isElementPresent("TT3droptime_id");
			DropTime.clear();
			DropTime.sendKeys(rdytime);
			DropTime.sendKeys(Keys.TAB);
			logs.info("Entered Drop time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Tender To 3P
			WebElement Ten3P = isElementPresent("TT3Button_id");
			wait.until(ExpectedConditions.elementToBeClickable(Ten3P));
			Ten3P.click();
			logs.info("Clicked on Tender To 3P button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "Tenderto3P" + svc);
			System.out.println("Tender to 3P Not Exist in Flow!!");
			logs.info("Tender to 3P Not Exist in Flow!!");

		}

	}

}
//--510087763
