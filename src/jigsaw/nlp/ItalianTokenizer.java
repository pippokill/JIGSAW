/**
 * Copyright (c) 2012, the JIGSAW AUTHORS.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the University of Bari nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package jigsaw.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pierpaolo
 */
public class ItalianTokenizer {
    
    private final Pattern tokenRegex = Pattern.compile("[^A-Za-z0-9\\uc3a0\\uc3a1\\uc3a8\\uc3a9\\uc3ac\\uc3ad\\uc3b2\\uc3b3\\uc3b9\\uc3ba\\u00c0-\\u00dd\\u00e0-\\u00ff']+");
    private final String apxRegex = "[A-Za-z]+[\\u0027][0-9A-Za-z\\uc3a0\\uc3a1\\uc3a8\\uc3a9\\uc3ac\\uc3ad\\uc3b2\\uc3b3\\uc3b9\\uc3ba]+";
    
    public ItalianTokenizer() {
    }
    
    public String[] tokenize(String text) {
        List<String> tokens = new ArrayList<String>();
        Matcher matcher = tokenRegex.matcher(text);
        int offset = 0;
        while (matcher.find()) {
            String token = text.substring(offset, matcher.start()).replaceAll("[\\s]+", "");
            String noToken = text.substring(matcher.start(), matcher.end()).replaceAll("[\\s]+", "");
            if (token.length() > 0) {
                if (token.matches(apxRegex)) {
                    int index = token.indexOf("'");
                    if (index > 0) {
                        tokens.add(token.substring(0, index + 1));
                        tokens.add(token.substring(index + 1));
                    } else {
                        tokens.add(token);
                    }
                } else {
                    tokens.add(token);
                }
            }
            if (noToken.length() > 0) {
                tokens.add(noToken);
            }
            offset = matcher.end();
        }
        if (text.length() > 0 && tokens.isEmpty()) {
            tokens.add(text);
        }
        if (offset<text.length()) {
            tokens.add(text.substring(offset).replaceAll("[\\s]+", ""));
        }
        return tokens.toArray(new String[tokens.size()]);
    }
}
