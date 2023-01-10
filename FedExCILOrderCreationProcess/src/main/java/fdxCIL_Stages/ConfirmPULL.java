package fdxCIL_Stages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class ConfirmPULL extends BaseInit {

	public void ConfirmPull() throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		Actions act = new Actions(driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// search the Job
		FedExCILOrderCreation OC = new FedExCILOrderCreation();
		OC.searchJob(3);

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logs.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Confirm Pull')]")));

			// --Get StageName
			OC.getStageName();

			// Click on Confirm Pull
			WebElement ConfmPull = isElementPresent("TLConfirmPull_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
			act.moveToElement(ConfmPull).build().perform();
			js.executeScript("arguments[0].click();", ConfmPull);
			logs.info("Click on Confirm Pull  button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Get the validation
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
				String Valmsg = isElementPresent("PartTimerequired_xpath").getText();
				logs.info("Validation message is displayed=" + Valmsg);
				if (Valmsg.contains("Part Pull time Required.")) {
					// --Get the timeZone
					String tzone = isElementPresent("TT3TimeZone_id").getText();
					String rectime = getTimeAsTZone(tzone);

					// --Enter Actual Pickup Time
					isElementPresent("PartPullTime_id").clear();
					isElementPresent("PartPullTime_id").sendKeys(rectime);
					isElementPresent("PartPullTime_id").sendKeys(Keys.TAB);
					logs.info("Enter Actual Part Pull time");

					// Click on Confirm Pull
					ConfmPull = isElementPresent("TLConfirmPull_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
					act.moveToElement(ConfmPull).build().perform();
					js.executeScript("arguments[0].click();", ConfmPull);
					logs.info("Click on Confirm Pull  button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
						String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
						logs.info("Validation message is displayed=" + Validmsg);
						if (Validmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
							// Recalculate the charges
							// --Go to Edit Job tab
							WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
							act.moveToElement(EditOrTab).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
							act.moveToElement(EditOrTab).click().perform();
							logs.info("Click on Edit Order Tab");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);

							// -Recalculate button
							WebElement ReCalc = isElementPresent("EORecal_id");
							act.moveToElement(ReCalc).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
							act.moveToElement(ReCalc).click().perform();
							logs.info("Click on Recalculate button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);

							// --Click on Save Changes button
							WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							js.executeScript("arguments[0].click();", SaveChanges);
							logs.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);

							// --Go to job Status Tab
							WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
							act.moveToElement(JobOverTab).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
							act.moveToElement(JobOverTab).click().perform();
							logs.info("Click on Job Overview Tab");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							tzone = isElementPresent("TT3TimeZone_id").getText();
							rectime = getTimeAsTZone(tzone);

							// --Enter Actual Pickup Time
							isElementPresent("PartPullTime_id").clear();
							isElementPresent("PartPullTime_id").sendKeys(rectime);
							isElementPresent("PartPullTime_id").sendKeys(Keys.TAB);
							logs.info("Enter Actual Part Pull time");

							// Click on Confirm Pull
							ConfmPull = isElementPresent("TLConfirmPull_id");
							wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
							act.moveToElement(ConfmPull).build().perform();
							js.executeScript("arguments[0].click();", ConfmPull);
							logs.info("Click on Confirm Pull  button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							js.executeScript("arguments[0].click();", ConfmPull);
							logs.info("Click on Confirm Pull  button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
								Validmsg = isElementPresent("OCValOnePack_xpath").getText();
								logs.info("Validation message is displayed=" + Validmsg);
								if (Validmsg.contains("Actual Datetime cannot be greater than Current Datetime.")) {
									// Recalculate the charges
									tzone = isElementPresent("TT3TimeZone_id").getText();
									rectime = getTimeAsTZone(tzone);

									// --Part Pull Date
									String PullDate=getDateAsTZone(tzone);
									System.out.println("Pull Date=="+PullDate);
									isElementPresent("TLPartPullDate_id").clear();
									isElementPresent("TLPartPullDate_id").sendKeys(PullDate);
									isElementPresent("TLPartPullDate_id").sendKeys(Keys.TAB);
									logs.info("Enter Actual Part Pull Date");

									/*
									 * WebElement PullDate = isElementPresent("TLPartPullDate_id");
									 * PullDate.clear(); Date date = new Date(); DateFormat dateFormat = new
									 * SimpleDateFormat("MM/dd/yyyy");
									 * dateFormat.setTimeZone(TimeZone.getTimeZone(tzone));
									 * logs.info(dateFormat.format(date));
									 * PullDate.sendKeys(dateFormat.format(date)); PullDate.sendKeys(Keys.TAB);
									 * logs.info("Entered Actual Pull Date");
									 */

									// --Enter Actual Pull Time
									isElementPresent("PartPullTime_id").clear();
									isElementPresent("PartPullTime_id").sendKeys(rectime);
									isElementPresent("PartPullTime_id").sendKeys(Keys.TAB);
									logs.info("Enter Actual Part Pull time");

									// Click on Confirm Pull
									ConfmPull = isElementPresent("TLConfirmPull_id");
									wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
									act.moveToElement(ConfmPull).build().perform();
									js.executeScript("arguments[0].click();", ConfmPull);
									logs.info("Click on Confirm Pull  button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									js.executeScript("arguments[0].click();", ConfmPull);
									logs.info("Click on Confirm Pull  button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								}

							} catch (Exception ee) {
								logs.info("Validation message is not displayed for Recalculate");

							}
						}

					} catch (Exception ee) {
						logs.info("Validation message is not displayed for Recalculate");

					}
				}

			} catch (Exception error) {
				logs.info("Validation message is not displayed for PartTime");

			}

		} catch (

		Exception e) {
			logs.error(e);
			getScreenshot(driver, "ConfirmPull" + svc);
			System.out.println("Confirm Pull Not Exist in Flow!!");
			logs.info("Confirm Pull Not Exist in Flow!!");
		}

	}
}
