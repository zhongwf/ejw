package com.octopusthu.ejw.components.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import lombok.Getter;

public class LocaleService {
	@Value("${ejw.components.i18n.availableLocales:en_US}")
	private String[] availableLocales;
	private Collection<AvailableLocale> list;

	@PostConstruct
	private void init() {
		if (ArrayUtils.isEmpty(availableLocales)) {
			list = Collections.emptyList();
		}
		list = new ArrayList<AvailableLocale>();
		Locale locale;
		for (String availableLocale : availableLocales) {
			locale = StringUtils.parseLocaleString(availableLocale);
			list.add(new AvailableLocale(locale.toString(), locale.getDisplayLanguage(locale)));
		}
	}

	public Collection<AvailableLocale> getAvailableLocales() {
		return list;
	}

	@Getter
	public static class AvailableLocale implements Serializable {
		private static final long serialVersionUID = 3976852851003200219L;

		AvailableLocale(String locale, String display) {
			this.locale = locale;
			this.display = display;
		}

		private String locale;
		private String display;
	}
}
