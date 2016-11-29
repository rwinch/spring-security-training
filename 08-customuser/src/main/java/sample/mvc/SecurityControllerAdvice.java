package sample.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

	@ModelAttribute("currentUser")
	Object currentUser(Authentication currentUser) {
		return currentUser == null ? null : currentUser.getPrincipal();
	}
}
