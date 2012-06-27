/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jigsaw.wn;

/**
 *
 * @author pierpaolo
 */
public class Tag2Wn {

    public static String getPos(String posTag) {
        if (posTag.startsWith("N")) {
            return "n";
        } else if (posTag.startsWith("V")) {
            return "v";
        } else if (posTag.startsWith("R")) {
            return "r";
        } else if (posTag.startsWith("J")) {
            return "a";
        } else {
            return "o";
        }
    }
}
