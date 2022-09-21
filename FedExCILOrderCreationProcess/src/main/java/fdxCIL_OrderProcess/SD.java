package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_Stages.Board;
import fdxCIL_Stages.Board1;
import fdxCIL_Stages.CSR;
import fdxCIL_Stages.ConfirmDel;
import fdxCIL_Stages.ConfirmPuSD;
import fdxCIL_Stages.ConfirmPull;
import fdxCIL_Stages.Deliver;
import fdxCIL_Stages.Drop;
import fdxCIL_Stages.PULL;
import fdxCIL_Stages.Pickup;
import fdxCIL_Stages.ReadyForDispatch;
import fdxCIL_Stages.Recover;
import fdxCIL_Stages.SendDelAlert;
import fdxCIL_Stages.SendPull;
import fdxCIL_Stages.TCAcknowledge;
import fdxCIL_Stages.WaitForArrival;
import fdxCIL_Stages.WaitForDeptarture;
import fdxCIL_Stages.XerWaitForArrival;
import fdxCIL_Stages.XerWaitForDeparture;

public class SD extends FedExCILOrderCreation {

	@Test
	public static void FedExSD() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// get SD jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();
		OC.searchFedExCILJob(3);

		// CSR Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		CSR.FedExCSRAcknowledge();

		// TC Acknowledge
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Send Pull Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		SendPull.FedExSendPullAlert();

		// Pickup Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// Confirm PU alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPuSD.FedExConfirmPuAlertSD();

		// ---
		// confirm Pull 3455369
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPull.FedExConfirmPull();

		// again confirm Pull
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		PULL.FedExPull();

		// PICKEDUP
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// DROP
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Drop Drp = new Drop();
		Drp.dropAtOrigin();

		// Send Del Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		SendDelAlert SDA = new SendDelAlert();
		SDA.delAlert();

		// Confirm Del Alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmDel.FedExcomfirmDel();

		// Wait for Departure
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		WaitForDeptarture WFD = new WaitForDeptarture();
		WFD.waitForDept();

		// OnBorad
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Board Brd = new Board();
		Brd.onBoard();

		// XER wait for Arrival
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		XerWaitForArrival XWFA = new XerWaitForArrival();
		XWFA.xerWaitForArr();

		// XER Wait for Departure
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		XerWaitForDeparture XWFD = new XerWaitForDeparture();
		XWFD.xerWaitForDept();

		// board2
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Board1 Brd1 = new Board1();
		Brd1.onBoard1();

		// Wait for Arrival
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		WaitForArrival WFA = new WaitForArrival();
		WFA.waitForArr();

		// Recover
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Recover RCV = new Recover();
		RCV.recoverAtDestination();

		// DELIVERED
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Deliver Del = new Deliver();
		Del.confirmDelivery();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();
	}
}
