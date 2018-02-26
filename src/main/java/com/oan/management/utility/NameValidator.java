package com.oan.management.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oan on 26/02/2018.
 * Snippet source:
 * https://stackoverflow.com/questions/44421036/check-if-name-is-valid-with-proper-case-and-max-one-space
 */
public class NameValidator {
    public static boolean check(String namVar) {
        String namRegExpVar = "^[A-Z][a-z]{2,}(?: [A-Z][a-z]*)*$";
        Pattern pVar = Pattern.compile(namRegExpVar);
        Matcher mVar = pVar.matcher(namVar);
        return mVar.matches();
    }
}
