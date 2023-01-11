package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.Board;
import fdxCIL_Stages.CSRAcknowledge;
import fdxCIL_Stages.ConfirmPULL;
import fdxCIL_Stages.ConfirmPullAlert;
import fdxCIL_Stages.Deliver;
import fdxCIL_Stages.Drop;
import fdxCIL_Stages.Pickup;
import fdxCIL_Stages.ReadyForDispatch;
import fdxCIL_Stages.Recover;
import fdxCIL_Stages.SendDelAlert;
import fdxCIL_Stages.SendPull;
import fdxCIL_Stages.TCAcknowledge;
import fdxCIL_Stages.WaitForArrival;
import fdxCIL_Stages.WaitForDeptarture;

public class SD extends BaseInit {

	@Test
	public static void FedExSD() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// get SD jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// --Search FedExCILJob
		OC.searchFedExCILJob(3);

		// get ServiceID
		String ServiceID = OC.getServiceID();
		msg.append("Service=" + ServiceID + "\n");

		// --Unknown Shipper
		OC.unknowShipper(3);

		// --Select Flight
		OC.selectFlight();

		// CSR Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CSRAcknowledge CSR = new CSRAcknowledge();
		CSR.csrAcknowledge();

		// TC Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Send Pull
		SendPull SP = new SendPull();
		SP.sendPull();

		// Pickup Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// Confirm PULL Alert
		ConfirmPullAlert CP = new ConfirmPullAlert();
		CP.ConfirmPullAlertstage();

		// Confirm PULL
		ConfirmPULL pull = new ConfirmPULL();
		pull.ConfirmPull();

		// PICKEDUP
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// DROP
		Drop Drp = new Drop();
		Drp.dropAtOrigin();

		// Send Del Alert
		SendDelAlert SDA = new SendDelAlert();
		SDA.delAlert();

		// Wait for Departure
		WaitForDeptarture WFD = new WaitForDeptarture();
		WFD.waitForDept();

		// OnBorad
		Board Brd = new Board();
		Brd.onBoard();

		// Wait For Arrival
		WaitForArrival WFA = new WaitForArrival();
		WFA.waitForArr();

		// Recover
		Recover RCV = new Recover();
		RCV.recoverAtDestination();

		// DELIVERED
		Deliver Del = new Deliver();
		Del.confirmDelivery();

		// --Refresh App
		OC.refreshApp();
		
		
		msg.append("\n\n\n");

	}
}
