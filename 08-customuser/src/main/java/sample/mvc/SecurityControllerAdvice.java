package sample.mvc;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import sample.data.MyUser;
import sample.security.CurrentUser;

@ControllerAdvice
public class SecurityControllerAdvice {

	@ModelAttribute("currentUser")
	MyUser currentUser(@CurrentUser MyUser currentUser) {
		return currentUser;
	}
}
