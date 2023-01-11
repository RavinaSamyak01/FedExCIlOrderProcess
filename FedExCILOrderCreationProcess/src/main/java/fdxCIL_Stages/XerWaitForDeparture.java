package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class XerWaitForDeparture extends FedExCILOrderCreation {
	@Test
	public void xerWaitForDept() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'XER Wait For Dept@')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			String stg = driver.findElement(By.id("lblStages")).getText();

			if (stg.contains("XER WAIT FOR DEPT")) {// String tzone =
				// driver.findElement(By.id("lblEditArrivalTimeSZone")).getText();
				// String xerarrtime = getTime(tzone);

				WebElement ZoneID = isElementPresent("TLXWFDTimeZone_id");
				String ZOneID = ZoneID.getText();
				System.out.println("ZoneID of is==" + ZOneID);

				// --get Time
				String ZnTime = getTimeAsTZone(ZOneID);

				WebElement Time = isElementPresent("TLXWFDTime_id");
				Time.clear();
				Time.sendKeys(ZnTime);
				Time.sendKeys(Keys.TAB);
				logs.info("Enter Depature Time");

				// --Click on Depart Button
				WebElement Depart = isElementPresent("TLXWFDButton_id");
				wait.until(ExpectedConditions.elementToBeClickable(Depart));
				act.moveToElement(Depart).build().perform();
				js.executeScript("arguments[0].click();", Depart);
				logs.info("Click on Arrival button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "XERWaitforDep" + svc);
			System.out.println("XER Wait for Departure Not Exist in Flow!!");
			logs.info("XER Wait for Departure Not Exist in Flow!!");

		}
	}

}
