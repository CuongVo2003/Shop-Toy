package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.been.Category;
import com.poly.been.Voucher;
import com.poly.service.VoucherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vouchers")
public class VoucherRestController {

    @Autowired
    private VoucherService voucherService;
    
//    @GetMapping("/vouchers")
//    public List<Voucher> getAllVouchers() {
//        return voucherService.findAll();
//    }
//    
//    @GetMapping("/discount/{vouId}")
//    public ResponseEntity<Double> getDiscountPercentage(@PathVariable String vouId) {
//        Voucher voucher = voucherService.findById(vouId);
//        if (voucher != null) {
//            return ResponseEntity.ok(voucher.getDiscountPercentage());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    
    @GetMapping()
	public List<Voucher> getAll() {
		return voucherService.findAll();
	}

	// lấy id
	@GetMapping("{id}")
	public Voucher getOne(@PathVariable("id") String id) {
		return voucherService.findById(id);
	}

	@PostMapping()
	public Voucher create(@RequestBody Voucher voucher) {
		return voucherService.saveVoucher(voucher);
	}

	@PutMapping("{id}")
	public Voucher update(@PathVariable("id") String id, @RequestBody Voucher voucher) {
		return voucherService.saveVoucher(voucher);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {

		voucherService.deleteVoucher(id);
	}
    
    
    

}
