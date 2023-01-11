package fdxCIL_Stages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class CustomerDelInProgress extends BaseInit {

	public void customerDelInProgress() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		Actions act = new Actions(driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logs.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Customer Delivery in Progress')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Click on Confirm DEL button
			WebElement ConfrmDel = isElementPresent("CDINConfDel_id");
			act.moveToElement(ConfrmDel).build().perform();
			jse.executeScript("arguments[0].click();", ConfrmDel);
			logs.info("Clicked on Confirm DEL button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				WebElement Validation = isElementPresent("TLAlValidation_id");
				wait.until(ExpectedConditions.visibilityOf(Validation));

				String ValMsg = Validation.getText();
				System.out.println("Validation message is displayed:" + ValMsg);
				logs.info("Validation message is displayed:" + ValMsg);

				if (ValMsg.contains("Actual Deliver Time Required.")) {

					String tzone = isElementPresent("TLLOCDActTimZone_id").getText();
					String rectime = getTimeAsTZone(tzone);

					// --Enter Delivery Time
					isElementPresent("CDINPDelTime_id").clear();
					isElementPresent("CDINPDelTime_id").sendKeys(rectime);
					isElementPresent("CDINPDelTime_id").sendKeys(Keys.TAB);
					logs.info("Enter Actual Delivery Time");

					// --Click on Confirm DEL button
					ConfrmDel = isElementPresent("CDINConfDel_id");
					act.moveToElement(ConfrmDel).build().perform();
					jse.executeScript("arguments[0].click();", ConfrmDel);
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					try {
						Validation = isElementPresent("TLAlValidation_id");
						wait.until(ExpectedConditions.visibilityOf(Validation));

						ValMsg = Validation.getText();
						System.out.println("Validation message is displayed:" + ValMsg);
						logs.info("Validation message is displayed:" + ValMsg);

						if (ValMsg.contains("Signature Required.")) {

							tzone = isElementPresent("TLLOCDActTimZone_id").getText();
							rectime = getTimeAsTZone(tzone);

							// --Enter SIgnature
							isElementPresent("CDINPSign_id").clear();
							isElementPresent("CDINPSign_id").sendKeys("RVOza");
							logs.info("Enter Signature");

							// --Click on Confirm DEL button
							ConfrmDel = isElementPresent("CDINConfDel_id");
							act.moveToElement(ConfrmDel).build().perform();
							jse.executeScript("arguments[0].click();", ConfrmDel);
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content\"]")));

								getScreenshot(driver, "CustDelInProgValidationPopUp");

								String DialougeContent = isElementPresent("CDINPDialContent_xpath").getText();
								logs.info("Content==" + DialougeContent);
								System.out.println("Content==" + DialougeContent);

								// --Click on OK button
								WebElement OK = isElementPresent("CDINPDiaOK_id");
								act.moveToElement(OK).build().perform();
								jse.executeScript("arguments[0].click();", OK);
								logs.info("Clicked on OK button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception eDialogue) {
								logs.info("Confirmation Alert is not displayed");

							}
						}

					} catch (Exception eValidation) {
						logs.info(" Validation message for Signature is not displayed=FAIL");

					}
				}

			} catch (Exception eValidation) {
				logs.info(" Validation message for Delivery Time is not displayed=FAIL");

			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "CustomerDelInProgress" + svc);
			System.out.println("Customer Delivery In Progress Not Exist in Flow!!");
			logs.info("Customer Delivery In Progress Not Exist in Flow!!");
		}
	}

}
