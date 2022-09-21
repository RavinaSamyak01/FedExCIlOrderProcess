package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import fdxCIL_Stages.CSR;
import fdxCIL_Stages.ConfirmPuLOC;
import fdxCIL_Stages.Pickup;
import fdxCIL_Stages.ReadyForDispatch;
import fdxCIL_Stages.TCAcknowledge;

public class LOC extends FedExCILOrderCreation {

	@Test
	public static void FedExLOC() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// get LOC jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();
		OC.searchFedExCILJob(4);

		// CSR Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CSR.FedExCSRAcknowledge();

		// TC Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// Confirm PU alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPuLOC.FedExConfirmPuAlertLOC();

		// PICKEDUP
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();
	}

}
