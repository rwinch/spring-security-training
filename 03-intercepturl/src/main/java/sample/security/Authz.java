package sample.security;

import org.springframework.stereotype.Component;

@Component
public class Authz {

	public boolean isOdd(String value) {
		return value != null && value.length() % 2 == 1;
	}
}
