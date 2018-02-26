package com.cinker.util;

import java.text.DecimalFormat;

@SuppressWarnings("rawtypes")
public class CommonsUtil {
		
	public static boolean isClazz(Class clazz){
		boolean result = false;
		if(clazz == null) return result;
		String className=clazz.getName().toLowerCase();
		if(className.startsWith("java.")) result = true;
		return result;
	}
	
	public static String roundByScale(double v, int scale) {          
        if(scale == 0){  
            return new DecimalFormat("0").format(v);  
        }  
        String formatStr = "0.";  
        for(int i=0;i<scale;i++){  
            formatStr = formatStr + "0";  
        }  
        return new DecimalFormat(formatStr).format(v);  
    }
}
