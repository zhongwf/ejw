package com.octopusthu.ejw.components.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;

public class MobileRegexMatcher extends AbstractRegexMatcher {

	@Value("${ejw.components.regex.mobile.zh_CN}")
	@NotEmpty
	private String zh_CN;

	private final Map<String, Pattern> cache = new HashMap<String, Pattern>();

	@PostConstruct
	private void init() {
		cache.put("zh_CN", Pattern.compile(zh_CN));
	}

	public boolean matches(String s, String type) {
		Pattern p = cache.get(type);
		return p != null ? super.matches(s, p) : false;
	}
}
