package sample.prepost;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {

	@Override
	public void denyAll() {
	}

	@Override
	public void permitAll() {
	}

	@Override
	public void isUser() {
	}

	@Override
	public void isAdmin() {
	}

	@Override
	public void isAdminOrDba() {
	}

	@Override
	public void isValueOdd(String value) {
	}

	@Override
	public void isValueEven(String value) {
	}

	@Override
	public String messageForCurrentUser(String username) {
		return "Hello " + username;
	}

	@Override
	public List<Integer> onlyAllowedEvens(List<Integer> numbers) {
		return new ArrayList<>(numbers);
	}
}
