package fdxCIL_OrderProcess;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.ConfirmPu;
import fdxCIL_Stages.ConfirmPull;
import fdxCIL_Stages.CustomerDel;

public class CPU extends BaseInit{

	public static void FedExCpu() throws Exception {
		
		//Confirm PU alert
		Thread.sleep(8000);
		ConfirmPu.FedExConfirmPuAlert();
		
		//confirm Pull
		Thread.sleep(8000);
		ConfirmPull.FedExConfirmPull();
		
		//Customer Del in progress
		Thread.sleep(8000);
		CustomerDel.CustomerDelivery();
	}
}
