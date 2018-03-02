package com.training.core.common.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	
	public static String getEncing(String name){
		try {
			return new String(name.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}
	
}
