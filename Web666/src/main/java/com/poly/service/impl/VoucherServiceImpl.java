package com.poly.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Voucher;
import com.poly.dao.VouchersDao;
import com.poly.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	VouchersDao vDao;

	@Override
	public List<Voucher> findAll() {
		return vDao.findAll();
	}

	@Override
	public Voucher findById(String vouId) {
		return vDao.findById(vouId).orElse(null);
	}

	@Override
	public Voucher saveVoucher(Voucher voucher) {
		return vDao.save(voucher);
	}

	@Override
	public void deleteVoucher(String vouId) {
		vDao.deleteById(vouId);

	}

	@Override
	public Voucher getValidVoucher(String vouId) {
		Voucher voucher = vDao.findById(vouId).orElse(null);
		if (voucher != null && voucher.getExpiryDate().isAfter(LocalDate.now()) && voucher.getQuantity() > 0) {
			System.out.println("tc12");
			return voucher;

		}
		System.out.println("tb12");
		return null;
	}

	@Override
	public Boolean  applyVoucher(String vouId) {
		Voucher voucher = getValidVoucher(vouId);
		// Áp dụng voucher vào đơn hàng và giảm số lượng trong cơ sở dữ liệu
		if (voucher != null) {
			if (voucher.getQuantity() > 0) {
				voucher.setQuantity(voucher.getQuantity() - 1);
				vDao.save(voucher);
				System.out.println("tc1");
				return true; // Áp dụng voucher thành công
			} else {
				System.out.println("tcb");
				return false; // Số lượng voucher đã hết
			}
		}
		System.out.println("tcba");
		return false; // Voucher không hợp lệ
		
		
//		Voucher voucher = vDao.findById(vouId).orElse(null);
//	    if (voucher != null && voucher.getQuantity() > 0 && voucher.getExpiryDate() != null && voucher.getExpiryDate().after(new Date())) {
//	        voucher.setQuantity(voucher.getQuantity() - 1);
//	        vDao.save(voucher);
//	        return voucher.getDiscountPercentage();
//	    } else {
//	        return -1.0;
//	    }
	}

	@Override
	public Double getDiscountPercentage(String vouId) {
		Voucher voucher = vDao.findById(vouId).orElse(null);
		if (voucher != null) {
			return voucher.getDiscountPercentage();
		}
		return 0.0;
	}
	@Override
	 public String getVoucherName(String vouId) {
	        Voucher voucher = vDao.findById(vouId).orElse(null);
	        if (voucher != null) {
	            return voucher.getVouId(); 
	        } else {
	            return "Unknown Voucher";
	        }
	    }

}
