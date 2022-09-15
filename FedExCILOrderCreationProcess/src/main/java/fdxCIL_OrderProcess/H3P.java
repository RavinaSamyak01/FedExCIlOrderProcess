package fdxCIL_OrderProcess;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.ConfirmPu;
import fdxCIL_Stages.ConfirmPull;
import fdxCIL_Stages.TenderTo3P;

public class H3P extends BaseInit{
	
	public static void FedExH3P()  throws Exception{
		
		
		//Confirm PU alert
		Thread.sleep(8000);
		ConfirmPu.FedExConfirmPuAlert();
		
		//confirm Pull
		Thread.sleep(8000);
		ConfirmPull.FedExConfirmPull();
		
		//Tender to 3P
		Thread.sleep(8000);
		TenderTo3P.tndrTo3P();
	}

}
