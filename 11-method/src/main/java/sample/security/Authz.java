package sample.security;

public class Authz {

	public boolean isOdd(String value) {
		return value != null && value.length() % 2 == 1;
	}
}
