/**
* Class saving object to file using ObjectOutputStream.
* Loading object from file using ObjectInputStream.
*/


package bankApp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.NoSuchFileException;

public class SaveLoadBank {
	
	/**
	 * Save object of Bank & current account number to byte file.
	 * @param customerAccountHandler = CustomerAccountHandler.
	 * @param path = file path.
	 */
	public void saveBank(CustomerAccountHandler customerAccountHandler, String path)
		throws NoSuchFileException, NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException {
		// create object to structure customers & account number.
		BankData bankData = new BankData(customerAccountHandler.getCustomers(), Account.getAccountNumberCounter());
		// converts into a sequence (stream) of bytes.
		// path = path to file in file save directory.
		// try-with-resouces - automatic stream close.
		try (ObjectOutputStream saveOutput = new ObjectOutputStream(new FileOutputStream(path))) {
			saveOutput.writeObject(bankData);
		}
	}
	
	/**
	 * Load Bank and latest account number.
	 * Set customers from file to Bank.
	 * Set account number from file to Bank.
	 * If file opened while Bank has customers: clear bank.
	 * @param customerAccountHandler= CustomerAccountHandler.
	 * @param path = file path.
	 */
	public void loadBank(CustomerAccountHandler customerAccountHandler, String path)
		throws NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException {
		// try-with-resouce - automatic stream close.
		// path = path to chosen file in file chooser directory.
		try (ObjectInputStream loadInput = new ObjectInputStream(new FileInputStream(path))) {
			// read stream of bytes, convert it back into a Java object in the  of BankData.
			// Cast to BankData.
			// Store in bankData.
			BankData bankData = (BankData) loadInput.readObject();

			// clear customers.
			customerAccountHandler.clearCustomers();
			
			// populate HashMap customers in customerAccountHandler.
			customerAccountHandler.setLoadedCustomers(bankData.getCustomers());
			// populate accountNumberCounter in Account.
			Account.setAccountNumberCounter(bankData.getAccountNumber());
		}
	}
	
}




























