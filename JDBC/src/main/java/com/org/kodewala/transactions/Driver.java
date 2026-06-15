package com.org.kodewala.transactions;

public class Driver {

	public static void main(String[] args) {
		FundTransfer ft = new FundTransfer();
		ft.doFundTransfer("faiz", "qrs", 100);

		System.out.println("Fund Transffered");
	}

}
