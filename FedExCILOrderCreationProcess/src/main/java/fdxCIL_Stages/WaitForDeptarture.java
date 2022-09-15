package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class WaitForDeptarture extends FedExCILOrderCreation {

	@Test
	public void waitForDept() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);//

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Wait for Departure')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Departure Time
			isElementPresent("TLWFDTime_id").clear();
			isElementPresent("TLWFDTime_id").sendKeys(rdytime);
			isElementPresent("TLWFDTime_id").sendKeys(Keys.TAB);
			logs.info("Enter Departure Time");

			// --Click on Depart button
			isElementPresent("TLWFDDepart_id").click();
			logs.info("Click on Depart button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "WaitforDep" + svc);
			System.out.println("Wait for Departure Not Exist in Flow!!");
			logs.info("Wait for Departure Not Exist in Flow!!");

		}

	}
}
