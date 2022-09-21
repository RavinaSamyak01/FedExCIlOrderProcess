package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_Stages.ConfirmPu;
import fdxCIL_Stages.ConfirmPull;
import fdxCIL_Stages.CustomerDel;

public class CPU extends FedExCILOrderCreation {

	@Test
	public static void FedExCpu() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//get CPU jobID
		FedExCILOrderCreation OC=new FedExCILOrderCreation();
		OC.searchFedExCILJob(1);

		// Confirm PU alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPu.FedExConfirmPuAlert();

		// confirm Pull
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPull.FedExConfirmPull();

		// Customer Del in progress
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CustomerDel.CustomerDelivery();
	}
}
