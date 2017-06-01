package com.octopusthu.ejw.components.i18n;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.octopusthu.ejw.component.ExposedResourceBundleMessageSource;
import com.octopusthu.ejw.components.i18n.LocaleService.AvailableLocale;
import com.octopusthu.ejw.util.JsonUtils;

public class ExposedResourceBundleMessageSourceService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private ExposedResourceBundleMessageSource messages;
	private LocaleService localeService;

	private Map<Locale, Map<String, String>> cache;
	private Map<Locale, String> jsonStringCache;

	public ExposedResourceBundleMessageSourceService(ExposedResourceBundleMessageSource messages,
			LocaleService localeService) {
		this.messages = messages;
		this.localeService = localeService;
	}

	@PostConstruct
	private void init() {
		Collection<AvailableLocale> availableLocales = localeService.getAvailableLocales();
		cache = new HashMap<Locale, Map<String, String>>(availableLocales.size());
		jsonStringCache = new HashMap<Locale, String>(availableLocales.size());
		availableLocales.forEach(availableLocale -> {
			Locale locale = StringUtils.parseLocaleString(availableLocale.getLocale());
			Map<String, String> allMessages = messages.getAllMessages(locale);
			cache.put(locale, allMessages);
			try {
				jsonStringCache.put(locale, JsonUtils.objectToJsonString(allMessages));
			} catch (JsonProcessingException e) {
				log.error("Error converting messages to json string!", e);
			}
		});
	}

	public String getAllMessagesJsonString() {
		return jsonStringCache.get(LocaleContextHolder.getLocale());
	}

	public Map<String, String> getAllMessages() {
		return cache.get(LocaleContextHolder.getLocale());
	}

}
