package sample.mvc;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

	@ModelAttribute("username")
	String username(Principal principal) {
		// FIXME return the principal.getName() to populate the username attribute
		return null;
	}
}
