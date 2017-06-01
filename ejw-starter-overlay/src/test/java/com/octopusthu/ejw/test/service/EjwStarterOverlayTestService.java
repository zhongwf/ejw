package com.octopusthu.ejw.test.service;

import java.util.Map;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.octopusthu.ejw.component.ExposedResourceBundleMessageSource;

import lombok.Getter;

@Service
public class EjwStarterOverlayTestService {

	@Getter
	@Value("${test:default}")
	private String test;

	@Inject
	MessageSource messages;

	public Map<String, String> getAllMessages() {
		return ((ExposedResourceBundleMessageSource) messages).getAllMessages(LocaleContextHolder.getLocale());
	}
}
