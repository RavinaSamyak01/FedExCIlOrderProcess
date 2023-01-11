package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.ConfirmPUAlert;
import fdxCIL_Stages.ConfirmPULL;
import fdxCIL_Stages.CustomerDelInProgress;

public class CPU extends BaseInit {

	@Test
	public static void FedExCpu() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// get CPU jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// --Search FedExCILJob
		OC.searchFedExCILJob(1);

		// get ServiceID
		String ServiceID = OC.getServiceID();
		msg.append("Service=" + ServiceID + "\n");

		// Confirm PU alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPUAlert CP = new ConfirmPUAlert();
		CP.confirmPUAlert();

		// confirm Pull
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPULL pull = new ConfirmPULL();
		pull.ConfirmPull();

		// Customer Del in progress
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CustomerDelInProgress CDIP = new CustomerDelInProgress();
		CDIP.customerDelInProgress();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();
		
		
		msg.append("\n\n\n");

	}
}
