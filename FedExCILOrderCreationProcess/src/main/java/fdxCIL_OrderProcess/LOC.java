package fdxCIL_OrderProcess;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.CSR;
import fdxCIL_Stages.ConfirmPuLOC;
import fdxCIL_Stages.Pickup;
import fdxCIL_Stages.ReadyForDispatch;
import fdxCIL_Stages.TCAcknowledge;

public class LOC extends BaseInit {

	public static void FedExLOC() throws Exception {

		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// CSR Acknowledge
		Thread.sleep(7000);
		CSR.FedExCSRAcknowledge();

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// Confirm PU alert
		Thread.sleep(8000);
		ConfirmPuLOC.FedExConfirmPuAlertLOC();

		// PICKEDUP
		Pickup PU = new Pickup();
		PU.confirmPickup();

		// --Refresh App
		OC.refreshApp();
	}

}
