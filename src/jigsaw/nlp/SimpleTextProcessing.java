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
import java.util.HashSet;
import java.util.Set;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * This class implements the following NLP steps: tokenization, pos-tagging,
 * stemming and lemamtization
 *
 * @author pierpaolo
 */
public class SimpleTextProcessing {

    private SnowballStemmer stemmer;
    private Tokenizer tokenizer;
    private POSTaggerME tagger;
    private Set<String> stopWordSet = new HashSet<String>();

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
            return doc.replaceAll("[^a-zA-Z0-9]+", " ");
        }
    }

    /**
     *
     * @param tokenModel
     * @param posTagModel
     * @param stopWordFile
     * @throws Exception
     */
    public SimpleTextProcessing(File tokenModel, File posTagModel, File stopWordFile) throws Exception {
        stemmer = new englishStemmer();
        InputStream modelIn = new FileInputStream(tokenModel);
        TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
        tokenizer = new TokenizerME(tokenizerModel);
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
        in.close();
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
