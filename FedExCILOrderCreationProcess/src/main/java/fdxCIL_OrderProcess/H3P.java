package fdxCIL_OrderProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_Stages.ConfirmPu;
import fdxCIL_Stages.ConfirmPull;
import fdxCIL_Stages.TenderTo3P;

public class H3P extends FedExCILOrderCreation {
	
	@Test
	public static void FedExH3P() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// get H3P jobID
		FedExCILOrderCreation OC = new FedExCILOrderCreation();
		OC.searchFedExCILJob(2);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// Confirm PU alert
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPu.FedExConfirmPuAlert();

		// confirm Pull
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		ConfirmPull.FedExConfirmPull();

		// Tender to 3P
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		TenderTo3P tenderTo3P = new TenderTo3P();
		tenderTo3P.tndrTo3P();
	}

}
