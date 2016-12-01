package sample.mvc;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import sample.data.MyUser;
import sample.security.CurrentUser;

@ControllerAdvice
public class SecurityControllerAdvice {

	// FIXME return the custom user the attribute currentUser
	@ModelAttribute("username")
	String currentUser(Principal principal) {
		return principal == null ? null : principal.getName();
	}
}
