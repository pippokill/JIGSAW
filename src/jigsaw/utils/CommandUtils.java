/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jigsaw.utils;

import java.util.Properties;

/**
 *
 * @author pierpaolo
 */
public class CommandUtils {

    public static Properties cmd(String[] args) throws Exception {
        if (args.length == 0 || args.length % 2 != 0) {
            throw new Exception("Number of arguments is illegal.");
        } else {
            Properties prop = new Properties();
            int k = 0;
            while (k < args.length) {
                prop.put(args[k], args[k + 1]);
                k = k + 2;
            }
            return prop;
        }
    }
}
