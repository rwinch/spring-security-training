package sample.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheController {

	@GetMapping("/**")
	public String permitAll() {
		return "all";
	}

	@GetMapping("/secret")
	public String secret() {
		return "secret";
	}
}
