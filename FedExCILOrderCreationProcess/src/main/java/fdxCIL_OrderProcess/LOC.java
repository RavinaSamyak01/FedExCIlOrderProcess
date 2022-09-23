package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.CSRAcknowledge;
import fdxCIL_Stages.Deliver;
import fdxCIL_Stages.Pickup;
import fdxCIL_Stages.ReadyForDispatch;
import fdxCIL_Stages.TCAcknowledge;

public class LOC extends BaseInit {

	@Test
	public void FedExLOC() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// get LOC jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// --Create LOC  Job
		OC.fedEXCILOrderCreate(4);

		// --Search FedExCILJob
		OC.searchFedExCILJob(4);

		// get ServiceID
		String ServiceID = OC.getServiceID();
		msg.append("Service=" + ServiceID + "\n");

		// CSR Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CSRAcknowledge CSR = new CSRAcknowledge();
		CSR.csrAcknowledge();

		// TC Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// PICKEDUP
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// DELIVERED
		Deliver Del = new Deliver();
		Del.confirmDelivery();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();
	}

}
