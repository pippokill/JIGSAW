/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author pierpaolo
 */
public class SimpleTextProcessing {

    private SnowballProgram stemmer;
    private Tokenizer tokenizer;
    private POSTaggerME tagger;
    private Set<String> stopWordSet = new HashSet<String>();

    public boolean isStopWord(String word) {
        return stopWordSet.contains(word.toLowerCase());
    }

    public String normalize(String doc) {
        if (doc == null) {
            return null;
        } else {
            return doc.replaceAll("[^a-zA-Z0-9]+", " ");
        }
    }

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

    public String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    public String stem(String word) {
        stemmer.setCurrent(word);
        return stemmer.getCurrent().toLowerCase();
    }

    public String[] posTagging(String[] tokens) {
        return tagger.tag(tokens);
    }
}
