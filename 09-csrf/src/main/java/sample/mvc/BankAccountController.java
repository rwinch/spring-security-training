package sample.mvc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/account")
public class BankAccountController {
	private double balance = 1000;

	@GetMapping("/")
	public String transferForm(Map<String,Object> model) {
		model.put("balance", balance);
		return "account";
	}

	@PostMapping("/transfer")
	public String transfer(@RequestParam double ammount) {
		balance += ammount;
		return "redirect:/account/";
	}

}
