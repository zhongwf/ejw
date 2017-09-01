package com.octopusthu.ejw.component;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

public class ExposedResourceBundleMessageSource extends ResourceBundleMessageSource {

    public Map<String, String> getAllMessages(Locale locale) {
        Map<String, String> ret = new HashMap<String, String>();
        Set<String> basenameSet = super.getBasenameSet();
        if (CollectionUtils.isEmpty(basenameSet)) {
            return ret;
        }
        basenameSet.forEach(basename -> {
            ret.putAll(this.getAllMessages(basename, locale));
        });
        return ret;
    }

    public Map<String, String> getAllMessages(String basename, Locale locale) {
        Map<String, String> ret = new HashMap<String, String>();
        ResourceBundle resourceBundle = super.getResourceBundle(basename, locale);
        if (resourceBundle == null) {
            return ret;
        }
        Enumeration<String> e = resourceBundle.getKeys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            ret.put(key, resourceBundle.getString(key));
        }
        return ret;
    }

}
