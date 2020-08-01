package com.ztech.donus.util;

import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public final class Utils {
	public static String getMessage(String message, String... args) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		String erro = bundle.getString(message);
		MessageFormat errorFormatter = new MessageFormat(erro);
		message = errorFormatter.format(args);
		return message;
	}

	public static String getOrderRegex(String value) {
		Pattern pattern = Pattern.compile(Constantes.REGEX_NUMBER_ORDER);
		if (StringUtils.isBlank(value))
			return null;
		Matcher matcher = pattern.matcher(value);
		if (matcher.find())
			return matcher.group(0);
		return null;
	}

	public static String getMessageValidation(String value) {
		String message = getMessage(value.replace(getOrderRegex(value), ""));
		if (StringUtils.isBlank(message))
			return value;
		return message;
	}

	public static String getNumberAgency() {
		Random rnd = new Random();
		int number = rnd.nextInt(9999);
		return String.format("%04d", number);
	}

	public static String getNumberCc() {
		Random rnd = new Random();
		int number = rnd.nextInt(99999);
		return String.format("%05d", number);
	}

	public static String removeCaracter(String value) {
		if (StringUtils.isNotBlank(value))
			return value.replace(".", "").replace("-", "");
		return null;
	}
}