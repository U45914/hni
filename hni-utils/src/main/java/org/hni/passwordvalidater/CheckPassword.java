package org.hni.passwordvalidater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hni.user.om.User;

public class CheckPassword {

	public static boolean passwordCheck(User u) {
		boolean result;
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=]).{6,}");
		Matcher matcher = pattern.matcher(u.getPassword());
		result = matcher.matches();
		return result;
	}

	/*
	 * public static void main(String[] args) { Pattern pattern =
	 * Pattern.compile("(?=.*[0-9])(?=.*[@#$%^&+=]).{6,}"); Matcher matcher=
	 * pattern.matcher("AAAq12"); System.out.println(matcher.matches()); }
	 */
}
