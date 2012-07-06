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
 * Neither the name of the University of Pittsburgh nor the names of its
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

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.italianStemmer;

/**
 * This class implements the following NLP steps: tokenization, pos-tagging,
 * stemming and lemmatizer
 *
 * @author pierpaolo
 */
public class SimpleItalianTextProcessing {

    private SnowballStemmer stemmer;
    private POSTaggerME tagger;
    private Set<String> stopWordSet = new HashSet<String>();
    private ItalianTokenizer tokenizer;
    private Map<String, String> lemmas;

    /**
     *
     * @param word
     * @return
     */
    public boolean isStopWord(String word) {
        return stopWordSet.contains(word.toLowerCase());
    }

    /**
     *
     * @param doc
     * @return
     */
    public String normalize(String doc) {
        if (doc == null) {
            return null;
        } else {
            return doc.replaceAll("[^a-zA-Z0-9\u00c0\u00c1\u00c8\u00c9\u00cc\u00cd\u00d2\u00d3\u00d9\u00da\u00e0\u00e1\u00e8\u00e9\u00ec\u00ed\u00f2\u00f3\u00f9\u00fa]+", " ");
        }
    }

    /**
     *
     * @param tokenModel
     * @param posTagModel
     * @param stopWordFile
     * @throws Exception
     */
    public SimpleItalianTextProcessing(File posTagModel, File stopWordFile, File morphItFile) throws Exception {
        stemmer = new italianStemmer();
        tokenizer = new ItalianTokenizer();
        InputStream modelIn2 = new FileInputStream(posTagModel);
        POSModel posModel = new POSModel(modelIn2);
        tagger = new POSTaggerME(posModel);
        BufferedReader in = new BufferedReader(new FileReader(stopWordFile));
        while (in.ready()) {
            String line = in.readLine().trim();
            if (line.length() > 0) {
                stopWordSet.add(line.toLowerCase());
            }
        }
        lemmas = new HashMap<String, String>();
        in = new BufferedReader(new FileReader(morphItFile));
        while (in.ready()) {
            String line = in.readLine().trim();
            String[] split = line.split("[ \t]+");
            if (split.length == 3) {
                if (split[2].startsWith("NOUN")) {
                    lemmas.put(split[0] + ".n", split[1]);
                } else if (split[2].startsWith("ADJ")) {
                    lemmas.put(split[0] + ".a", split[1]);
                } else if (split[2].startsWith("ADV")) {
                    lemmas.put(split[0] + ".r", split[1]);
                } else if (split[2].startsWith("VER")) {
                    lemmas.put(split[0] + ".v", split[1]);
                }
            }
        }
        in.close();
    }

    public String lemmatize(String token, String posTag) {
        String key = token + "." + posTag;
        String lemma = lemmas.get(key);
        if (lemma != null) {
            return lemma;
        } else {
            return token;
        }
    }

    /**
     *
     * @param text
     * @return
     */
    public String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    /**
     *
     * @param word
     * @return
     */
    public String stem(String word) {
        stemmer.setCurrent(word);
        if (stemmer.stem()) {
            return stemmer.getCurrent().toLowerCase();
        } else {
            return word.toLowerCase();
        }
    }

    /**
     *
     * @param tokens
     * @return
     */
    public String[] posTagging(String[] tokens) {
        return tagger.tag(tokens);
    }
}
