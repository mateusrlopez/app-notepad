package model.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public interface Constants {
	ArrayList<Integer> SIZES_VALUES = new ArrayList<Integer>(
			Arrays.asList(8,9,10,11,12,14,16,18,20,22,24,26,28,36,42,48,72));
	String INITIAL_DIRECTORY = String.format("C:\\Users\\%s\\Documents", System.getProperty("user.name"));
	Function<String,String> BACKSP = p -> new StringBuilder(p).deleteCharAt(p.length()-1).toString();
	SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
}
