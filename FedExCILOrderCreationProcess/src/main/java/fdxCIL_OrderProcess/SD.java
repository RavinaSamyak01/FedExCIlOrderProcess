package fdxCIL_OrderProcess;

import fdxCIL_BasePackage.BaseInit;
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

public class SD extends BaseInit {

	public static void FedExSD() throws Exception {

		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// CSR Acknowledge
		Thread.sleep(7000);
		CSR.FedExCSRAcknowledge();

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Send Pull Alert
		Thread.sleep(7000);
		SendPull.FedExSendPullAlert();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// Confirm PU alert
		Thread.sleep(8000);
		ConfirmPuSD.FedExConfirmPuAlertSD();

		// ---
		// confirm Pull 3455369
		Thread.sleep(8000);
		ConfirmPull.FedExConfirmPull();

		// again confirm Pull
		Thread.sleep(4000);
		PULL.FedExPull();

		// PICKEDUP
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// DROP
		Drop Drp = new Drop();
		Drp.dropAtOrigin();

		// Send Del Alert
		SendDelAlert SDA = new SendDelAlert();
		SDA.delAlert();

		// Confirm Del Alert
		Thread.sleep(7000);
		ConfirmDel.FedExcomfirmDel();

		// Wait for Departure
		WaitForDeptarture WFD = new WaitForDeptarture();
		WFD.waitForDept();

		// OnBorad
		Board Brd = new Board();
		Brd.onBoard();

		// XER wait for Arrival
		XerWaitForArrival XWFA = new XerWaitForArrival();
		XWFA.xerWaitForArr();

		// XER Wait for Departure
		XerWaitForDeparture XWFD = new XerWaitForDeparture();
		XWFD.xerWaitForDept();

		// board2
		Board1 Brd1 = new Board1();
		Brd1.onBoard1();

		// Wait for Arrival
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
	}
}
