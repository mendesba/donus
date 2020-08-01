package com.ztech.donus.util;

import java.text.SimpleDateFormat;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Constantes {
	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static final String REGEX_NUMBER_ORDER = "^([\\[])([0-9]){1,2}([\\]])";
}