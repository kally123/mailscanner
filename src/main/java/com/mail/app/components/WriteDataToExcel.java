package com.mail.app.components;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mail.app.model.Customer;
import com.mail.app.model.OrderDetails;

public class WriteDataToExcel {

	private static WriteDataToExcel dataToExcel;

	private WriteDataToExcel() {
	}

	public static WriteDataToExcel getInstance() {
		if (dataToExcel == null) {
			dataToExcel = new WriteDataToExcel();
		}
		return dataToExcel;
	}

	public int copyDataToExcel(List<Customer> customerList, XSSFWorkbook workbook, XSSFSheet spreadsheet,
			FileOutputStream out) throws Exception {
		System.out.println(".............Copying Data Started for above list.............");
		// Create row object
		XSSFRow row;
		int rowid = 1;
		for (Customer customer : customerList) {
			// Print Customer Data
			Object[] data = new Object[] { customer.getId(), customer.getName(), customer.getMobNumber(),
					customer.getAddress() };
			row = spreadsheet.createRow(rowid++);
			populateRowData(data, row);
			List<OrderDetails> orderDetails = customer.getOrderDetails();
			int orderRowCounter = 1;
			for (OrderDetails details : orderDetails) {
				Object[] orderData = new Object[] { details.getOrderId(), details.getOrderSummary(),
						details.getPackagingCharge(), details.getPaymentMode(), details.getBill(),
						details.getRestaurantPromo(), details.getZomatoPromo(), details.getOrderDate() };

				if (orderRowCounter == 1) {
					populateRowData(orderData, row);
				} else {
					row = spreadsheet.createRow(rowid++);
					populateRowData(new Object[] { "", "", "", "" }, row);
					populateRowData(orderData, row);
				}

				orderRowCounter++;
			}
		}
		System.out.println("Data written to Sheet up to row :" + rowid);

		return rowid;
	}

	private void populateRowData(Object[] data, XSSFRow row) {
		int cellid = 0;
		// Iterate over data and write to sheet
		for (Object value : data) {
			Cell cell = row.createCell(cellid++);
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else {
				cell.setCellValue(String.valueOf(value));
			}
		}
	}

}