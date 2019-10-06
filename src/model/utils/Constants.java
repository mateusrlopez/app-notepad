package model.utils;

import java.util.function.Function;

public interface Constants {
	String INITIAL_DIRECTORY = String.format("C:\\Users\\%s\\Documents", System.getProperty("user.name"));
	Function<String,String> BACKSP = p -> new StringBuilder(p).deleteCharAt(p.length()-1).toString();
}
