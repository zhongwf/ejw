package com.octopusthu.ejw.util;

import java.lang.reflect.Field;

import org.apache.commons.lang.reflect.FieldUtils;

public class ReflectionUtils {
	public static void setAllFields(Object target, Object value) throws IllegalAccessException {
		Class<?> clazz = target.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			FieldUtils.writeDeclaredField(target, f.getName(), value, true);
		}
	}
}
