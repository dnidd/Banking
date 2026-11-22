/**
* Class saving and reading account transactions to text file.
*/

package bankApp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class SaveLoadAccountStatement {
	private static final Locale SV_SE = Locale.of("sv", "SE");
	private NumberFormat numberFormat;
	DateTimeFormatter dateformatter;
	DateTimeFormatter dateTimeFormatter;
	
	public SaveLoadAccountStatement() {
		this.numberFormat = NumberFormat.getCurrencyInstance(SV_SE);
		// statement date
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// file name date
		this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
	}

	/**
	 * Save statement/receipt.
	 * @param customerAccountHandler = CustomerAccountHandler.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @throws FileNotFoundException.
	 * @throws IOException.
	 */
	public void saveStatement(CustomerAccountHandler customerAccountHandler, String pNo, int accountId) throws FileNotFoundException, IOException {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime dateTime = LocalDateTime.now();
		// print content date.
		String strDate = date.format(dateformatter);
		// filename date & time.
		String strDateTime = dateTime.format(dateTimeFormatter);
		
		// get balance.
		BigDecimal balance = customerAccountHandler.getBalance(pNo, accountId);
		
		// get transactions.
		ArrayList<String> customerTransactions = customerAccountHandler.getTransactions(pNo, accountId);
		
		// get customer name + surname.
		Customer customer = customerAccountHandler.findCustomer(pNo);
		String name = customer.getName();
		String surName = customer.getSurname();
		
		// path, file name & file type.
		String filePath = "src/bankApp/files/";
		String fileName = accountId + "_" + strDateTime + ".txt";
		
		// try-with-resouce - automatic stream close.
		try (PrintWriter printWriter = new PrintWriter (new BufferedWriter (new FileWriter(filePath + fileName)))) {
			printWriter.println("Kontoutdrag " + strDate);
			printWriter.println("----------------------------------");
			printWriter.println("Kontoinnehavare " + name + " " + surName);
			printWriter.println();
			printWriter.println("Transaktioner konto " + accountId);
			for(String trans : customerTransactions) {
				printWriter.println(trans);
			}
			printWriter.println("----------------------------------");
			printWriter.println("Saldo: " + numberFormat.format(balance));
		}
	}
	
	
	/**
	 * Load/read statement/receipt from file.
	 * @param path = file path.
	 * @return text built with StringBuilder.
	 * @throws FileNotFoundException.
	 * @throws IOException.
	 */
	public String loadStatement(String path) throws FileNotFoundException, IOException {
		StringBuilder stringBuilder = new StringBuilder();
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String row;
			while ((row = bufferedReader.readLine()) != null) {
				stringBuilder.append(row).append("\n");
			}
		return stringBuilder.toString();
		}
	}
}
