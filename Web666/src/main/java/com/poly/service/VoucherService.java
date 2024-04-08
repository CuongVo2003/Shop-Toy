package com.poly.service;

import java.util.List;

import com.poly.been.Voucher;

public interface VoucherService {
	List<Voucher> findAll();
	Voucher findById (String vouId);
	Voucher saveVoucher(Voucher voucher);
	void deleteVoucher (String vouId);
	Voucher getValidVoucher(String vouId);
	Boolean   applyVoucher (String vouId);
	Double getDiscountPercentage(String vouId);
	String getVoucherName(String vouId);
}
