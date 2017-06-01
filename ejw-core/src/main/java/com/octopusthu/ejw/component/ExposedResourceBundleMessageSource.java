package com.octopusthu.ejw.component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ExposedResourceBundleMessageSource extends ResourceBundleMessageSource {

	public Map<String, String> getAllMessages(Locale locale) {
		Map<String, String> ret = new HashMap<String, String>();
		super.getBasenameSet().forEach(basename -> {
			ret.putAll(this.getAllMessages(basename, locale));
		});
		return ret;
	}

	public Map<String, String> getAllMessages(String basename, Locale locale) {
		Map<String, String> ret = new HashMap<String, String>();
		ResourceBundle resourceBundle = super.getResourceBundle(basename, locale);
		Enumeration<String> e = resourceBundle.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			ret.put(key, resourceBundle.getString(key));
		}
		return ret;
	}

}
