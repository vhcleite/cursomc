package com.vleite.cursomc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlUtils {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTD-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static List<Integer> parseIntegerList(String integerList) {
		return Arrays.asList(integerList.split(",")).stream().map(i -> Integer.parseInt(i))
				.collect(Collectors.toList());
	}
}
