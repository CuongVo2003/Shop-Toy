package com.poly.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.poly.been.Account;
import com.poly.been.Authority;
import com.poly.been.Role;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	AuthorityService authorityService; 

	// get admin
	@GetMapping()
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}

	// GET /rest/account/
	@GetMapping("/")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.findAll();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	// GET /rest/account/{username}
	@GetMapping("/{username}")
	public ResponseEntity<Account> getAccountByUsername(@PathVariable String username) {
		Account account = accountService.findById(username);
		if (account != null) {
			return new ResponseEntity<>(account, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// POST /rest/account/
	@PostMapping("/")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account savedAccount = accountService.saveProduct(account);
		if (savedAccount != null) {
			return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DELETE /rest/account/{username}
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteAccount(@PathVariable String username) {
		accountService.deleteAccount(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


	
	@PutMapping("{username}")
	public Account update(@PathVariable("username") String username, @RequestBody Account account) {
		return accountService.updateProduct(account);
	}

// // lấy username đã đăng nhập 
	@GetMapping("/current")
	public Account getCurrentAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		return accountService.findById(currentUsername);
		
	}
	




}
