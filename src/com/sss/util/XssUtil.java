package com.sss.util;

public class XssUtil {
    // ¹ıÂËXSSÌØÊâ×Ö·û
    public static String escape(String str) {
        if(str == null) return "";
        return str.replace("<","&lt;")
                  .replace(">","&gt;")
                  .replace("\"","&quot;")
                  .replace("'","&#39;")
                  .replace("&","&amp;");
    }
}