package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class Drop extends BaseInit {

	@Test
	public void dropAtOrigin() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		Actions act = new Actions(driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Drop @ Origin')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Get the timeZone
			String tzone = isElementPresent("TLLOCDActTimZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter DropOff time
			WebElement dropoff = isElementPresent("TLDAODrpOfTime_id");
			dropoff.sendKeys(rectime);
			dropoff.sendKeys(Keys.TAB);
			logs.info("Enter Drop off time");

			// --Click on COnfirm Drop
			WebElement btng = isElementPresent("TLDAOConfDrop_id");
			jse.executeScript("arguments[0].click();", btng);
			logs.info("Click on Confirm Drop button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				String awb = isElementPresent("Error_id").getText();
				System.out.println(awb);
				logs.info("awb");
				if (awb.contains("Airbill is Required.")) {
					isElementPresent("TLDAOAirBill_id").click();
					logs.info("Click on Add AirBill");

					// --wait for editor
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SaveAirbill_0")));
					isElementPresent("TLDAOAirBillNo_id").clear();
					isElementPresent("TLDAOAirBillNo_id").sendKeys("2121-2170");
					logs.info("Click on Add AirBill");

					isElementPresent("TLDAOAirQty_id").clear();
					isElementPresent("TLDAOAirQty_id").sendKeys("1");
					logs.info("Enter Quantity");

					isElementPresent("TLDAOAirWeight_id").clear();
					isElementPresent("TLDAOAirWeight_id").sendKeys("10");
					logs.info("Enter Weight");

					WebElement saveairbill = isElementPresent("TLDAOSaveBill_id");
					jse.executeScript("arguments[0].click();", saveairbill);
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					logs.info("Click on Save Bill button");

					try {
						String DescVal = isElementPresent("Error_id").getText();
						System.out.println(DescVal);
						logs.info(DescVal);
						if (DescVal.contains("Description Required.")) {

							// --Enter Description
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtEditDescription_0")));
							isElementPresent("EOAirBDesc_id").clear();
							isElementPresent("EOAirBDesc_id").sendKeys("Test Description");
							logs.info("Enter Description");

							saveairbill = isElementPresent("TLDAOSaveBill_id");
							jse.executeScript("arguments[0].click();", saveairbill);
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logs.info("Click on Save Bill button");

							try {
								DescVal = isElementPresent("Error_id").getText();
								System.out.println(DescVal);
								logs.info(DescVal);
								if (DescVal.contains("Account Required.")) {

									// --Enter Description
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmbCarrier")));
									Select Account = new Select(isElementPresent("EOAccount_id"));
									Account.selectByIndex(1);
									Thread.sleep(2000);
									logs.info("Select Account");

									saveairbill = isElementPresent("TLDAOSaveBill_id");
									jse.executeScript("arguments[0].click();", saveairbill);
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									logs.info("Click on Save Bill button");

									try {
										DescVal = isElementPresent("Error_id").getText();
										System.out.println(DescVal);
										logs.info(DescVal);
										if (DescVal.contains("Service Level Required.")) {

											// --Enter Description
											wait.until(ExpectedConditions.visibilityOfElementLocated(
													By.xpath("//*[@id=\"cmbCarrier\"][@ng-model=\"AWB.CodeValue\"]")));
											Select ServLvlSelection = new Select(
													isElementPresent("EOServcLvlSelection_xpath"));
											ServLvlSelection.selectByIndex(1);
											Thread.sleep(2000);
											logs.info("Select Service Level");

											saveairbill = isElementPresent("TLDAOSaveBill_id");
											jse.executeScript("arguments[0].click();", saveairbill);
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));
											logs.info("Click on Save Bill button");

										}
									} catch (Exception eDesc) {
										logs.info("Validation for Service Level is not displayed");
									}

								}
							} catch (Exception eDesc) {
								logs.info("Validation for Account is not displayed");
							}
						}

					} catch (Exception eDesc) {
						logs.info("Validation for Air Bill Description is not displayed");
					}

					btng = isElementPresent("TLDAOConfDrop_id");
					wait.until(ExpectedConditions.elementToBeClickable(btng));
					jse.executeScript("arguments[0].click();", btng);
					logs.info("Click on Confirm Drop button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}
			} catch (Exception e) {
				System.out.println("Airbill already exist !!");
				logs.info("Airbill already exist !!");

			}

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

					// --Go to job Status Tab
					WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
					act.moveToElement(JobOverTab).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
					act.moveToElement(JobOverTab).click().perform();
					logs.info("Click on Job Overview Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Enter SIgnature
					isElementPresent("TLDSignature_id").clear();
					isElementPresent("TLDSignature_id").sendKeys("RVOza");
					logs.info("Enter Signature");

					// --Get the timeZone
					tzone = isElementPresent("TLLOCDActTimZone_id").getText();
					rectime = getTimeAsTZone(tzone);

					// --Enter Actual DL time
					isElementPresent("TLDActDLTime_id").clear();
					isElementPresent("TLDActDLTime_id").sendKeys(rectime);
					logs.info("Enter Actual DL Time");

					// --Click on COnfirm Drop
					btng = isElementPresent("TLDAOConfDrop_id");
					jse.executeScript("arguments[0].click();", btng);
					logs.info("Click on Confirm Drop button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception PModify) {
				logs.info("Validation message is not displayed for Recalculate the charges");

			}

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "DROP@ORIGIN" + svc);
			System.out.println("DROP @ ORIGIN Not Exist in Flow!!");
			logs.info("DROP @ ORIGIN Not Exist in Flow!!");

		}

	}
}
