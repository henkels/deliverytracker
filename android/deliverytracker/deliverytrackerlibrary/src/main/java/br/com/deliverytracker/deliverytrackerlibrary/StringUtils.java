package br.com.deliverytracker.deliverytrackerlibrary;

/**
 * Created by tarcisio on 26/11/16.
 */

public class StringUtils {

    static public String emailToName(String email) {
        boolean capitalize = true;
        boolean inLowercase = false;
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < email.length(); i++) {
            char ch = email.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                if (capitalize) {
                    ch = Character.toUpperCase(ch);
                }
                ret.append(ch);
                inLowercase = true;
                capitalize = false;
                continue;
            }
            boolean wasInLowerCase = inLowercase;
            inLowercase = false;
            if (ch >= 'A' && ch <= 'Z') {
                if (wasInLowerCase){
                    ret.append(' ');
                }
                ret.append(ch);
                capitalize = false;
                continue;
            }
            if (ch == '@') {
                break;
            }
            capitalize = true;
            ret.append(' ');
        }
        return ret.toString().trim();
    }
}
