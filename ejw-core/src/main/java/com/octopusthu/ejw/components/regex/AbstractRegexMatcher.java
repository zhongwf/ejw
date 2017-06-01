package com.octopusthu.ejw.components.regex;

import java.util.regex.Pattern;

public abstract class AbstractRegexMatcher {
	protected boolean matches(String s, Pattern p) {
		return (s == null || p == null) ? false : p.matcher(s).matches();
	}
}
