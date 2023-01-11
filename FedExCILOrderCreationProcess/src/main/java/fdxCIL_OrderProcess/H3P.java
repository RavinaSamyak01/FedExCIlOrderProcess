package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_Stages.ConfirmPUAlert;
import fdxCIL_Stages.ConfirmPULL;
import fdxCIL_Stages.TenderTo3P;

public class H3P extends BaseInit {

	@Test
	public static void FedExH3P() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// get H3P jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();

		// --Search FedExCILJob
		OC.searchFedExCILJob(2);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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

		// Tender to 3P
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TenderTo3P tenderTo3P = new TenderTo3P();
		tenderTo3P.tndrTo3P();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();
		
		
		msg.append("\n\n\n");

	}

}
