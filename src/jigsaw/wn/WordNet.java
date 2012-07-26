/**
   Copyright (c) 2012, the JIGSAW AUTHORS.

   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:

 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 * Neither the name of the University of Bari nor the names
   of its contributors may be used to endorse or promote products
   derived from this software without specific prior written
   permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/
package jigsaw.wn;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.extjwnl.data.*;
import net.sf.extjwnl.dictionary.Dictionary;

/**
 * This class implements methods to access the WordNet knowledge-base.
 *
 * @author Basile Pierpaolo and Grieco Franco
 */
public class WordNet {

    /**
     * Max depth of relations.
     */
    public static int MAX_DEPTH = 18;
    private Dictionary dictionary = null;

    /**
     * Creates a new instance 
     *
     */
    public WordNet() {
    }

    public String lemmatize(String word, String pos) throws Exception {
        IndexWord indexWord = null;
        if (pos.equals("n")) {
            indexWord = dictionary.getMorphologicalProcessor().lookupBaseForm(POS.NOUN, word);
        } else if (pos.equals("v")) {
            indexWord = dictionary.getMorphologicalProcessor().lookupBaseForm(POS.VERB, word);
        } else if (pos.equals("a")) {
            indexWord = dictionary.getMorphologicalProcessor().lookupBaseForm(POS.ADJECTIVE, word);
        } else if (pos.equals("r")) {
            indexWord = dictionary.getMorphologicalProcessor().lookupBaseForm(POS.ADVERB, word);
        }
        if (indexWord != null) {
            return indexWord.getLemma().toLowerCase();
        } else {
            return word.toLowerCase();
        }
    }

    /**
     * Compute the min-distance between two synsets
     *
     * @param s1 First synset offset
     * @param s2 Second synset offset
     * @param pos Pos tagger information
     * @param relation Relation type
     * @throws Exception Exception
     * @return Distance
     */
    public int getDepthByOffset(String s1, String s2, String pos, PointerType relation) {
        try {

            List<WnNode> result1 = getAllRelationNode(s2, pos, relation, MAX_DEPTH, MAX_DEPTH);
            List<WnNode> result2 = getAllRelationNode(s1, pos, relation, MAX_DEPTH, MAX_DEPTH);

            int min = 2 * MAX_DEPTH;
            for (int i = 0; i < result1.size(); i++) {
                int index = result2.indexOf(result1.get(i));
                if (index != -1) {
                    int depth = result2.get(index).getDepth() + result1.get(i).getDepth();
                    if (depth <= min) {
                        min = depth;
                    }
                }
            }

            return min;
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to compute depth: " + s1 + ", " + s2 + ", pos: " + pos, ex);
            return MAX_DEPTH + 1;
        }
    }

    /**
     * Compute the min-distance between two synsets, but limiting the search to maxDepth
     *
     * @param s1 First synset offset
     * @param s2 Second synset offset
     * @param pos Pos tagger information
     * @param relation Relation type
     * @param maxDepth Max depth
     * @throws Exception Exception
     * @return Distance
     */
    public int getDepthByOffset(String s1, String s2, String pos, PointerType relation, int maxDepth) {
        try {
            if (maxDepth < 0) {
                Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "Error to compute depth: depth < 0: {0}", maxDepth);
                return MAX_DEPTH + 1;
            }
            List<WnNode> result1 = getAllRelationNode(s2, pos, relation, maxDepth, maxDepth);
            List<WnNode> result2 = getAllRelationNode(s1, pos, relation, maxDepth, maxDepth);


            int min = 2 * MAX_DEPTH;
            for (int i = 0; i < result1.size(); i++) {
                int index = result2.indexOf(result1.get(i));
                if (index != -1) {
                    int depth = result2.get(index).getDepth() + result1.get(i).getDepth();
                    if (depth <= min) {
                        min = depth;
                    }
                }
            }

            return min;
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to compute depth (max depth): " + s1 + ", " + s2 + ", pos: " + pos, ex);
            return MAX_DEPTH + 1;
        }
    }

    private IndexWord returnIndexWord(String word, String pos, boolean lookup) {
        try {
            if (pos.equals("a")) {
                if (lookup) {
                    return dictionary.lookupIndexWord(POS.ADJECTIVE, word);
                } else {
                    return dictionary.getIndexWord(POS.ADJECTIVE, word);
                }
            } else if (pos.equals("r")) {
                if (lookup) {
                    return dictionary.lookupIndexWord(POS.ADVERB, word);
                } else {
                    return dictionary.getIndexWord(POS.ADVERB, word);
                }
            } else if (pos.equals("n")) {
                if (lookup) {
                    return dictionary.lookupIndexWord(POS.NOUN, word);
                } else {
                    return dictionary.getIndexWord(POS.NOUN, word);
                }
            } else if (pos.equals("v")) {
                if (lookup) {
                    return dictionary.lookupIndexWord(POS.VERB, word);
                } else {
                    return dictionary.getIndexWord(POS.VERB, word);
                }
            } else {
                //Logger.getLogger(DefaultWordNet.class.getName()).log(Level.WARNING,"Error to return index word (pos tag wrong: " + word + ", pos: " + pos);
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to return index word: " + word + ", pos: " + pos, ex);
            return null;
        }
    }

    private Synset returnSynset(String offset, String pos) {
        try {
            if (pos.equals("a")) {
                return dictionary.getSynsetAt(POS.ADJECTIVE, Long.parseLong(offset));
            } else if (pos.equals("r")) {
                return dictionary.getSynsetAt(POS.ADVERB, Long.parseLong(offset));
            } else if (pos.equals("n")) {
                return dictionary.getSynsetAt(POS.NOUN, Long.parseLong(offset));
            } else if (pos.equals("v")) {
                return dictionary.getSynsetAt(POS.VERB, Long.parseLong(offset));
            } else {
                //Logger.getLogger(DefaultWordNet.class.getName()).log(Level.WARNING,"Error to return synset (pos tag wrong: " + offset + ", pos: " + pos);
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to return synset: " + offset + ", pos: " + pos, ex);
            return null;
        }
    }

    /**
     * Return the pos-tag of the word
     *
     * @param word Word
     * @param loolup Set lookup mode true/false
     * @throws Exception Exception
     * @return pos-tag
     */
    public String hasAnySyns(String word, boolean lookup) throws Exception {
        try {
            IndexWord iw = returnIndexWord(word, "n", lookup);

            if (iw == null) {
                iw = returnIndexWord(word, "a", lookup);
            } else {
                return "n";
            }
            if (iw == null) {
                iw = returnIndexWord(word, "v", lookup);
            } else {
                return "a";
            }
            if (iw == null) {
                iw = returnIndexWord(word, "r", lookup);
            } else {
                return "v";
            }
            if (iw == null) {
                return null;
            } else {
                return "r";
            }
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error in hasAnySyns: " + word, ex);
            return null;
        }
    }

    /**
     * Return all synset offsets by word
     *
     * @return Array of strings, each string represents a synset offset
     * @param word Word
     * @param pos Pos-tagger information
     */
    public String[] getAllSynsetByWord(String word, String pos) {
        try {
            IndexWord indexWord = returnIndexWord(word, pos, true);
            if (indexWord == null) {
                return null;
            }
            long[] synsetOffsets = indexWord.getSynsetOffsets();
            String[] offsets = new String[synsetOffsets.length];
            for (int i = 0; i < offsets.length; i++) {
                offsets[i] = String.valueOf(synsetOffsets[i]);
            }
            return offsets;
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error in getAllSynsetByWord (general): " + word + ", pos: " + pos, ex);
            return null;
        }

    }

    /**
     * Init WordNet
     *
     * @throws Exception Exception
     * @return 
     */
    public boolean init(File configFile) throws Exception {
        try {
            dictionary = Dictionary.getInstance(new FileInputStream(configFile));
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to inizialize WordNet(JWNL), error...return false", ex);
            return false;
        }
        return true;
    }

    /**
     * Return a string which contains all lemmas assigned to a synset
     *
     * @param offset Sysnet offset
     * @param pos POS-tag
     * @return String
     */
    public String getAllWordsInSynset(String offset, String pos) {

        Synset s = this.returnSynset(offset, pos);
        if (s == null) {
            Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "Error in getAllWordsInSynset (synset null): {0}, pos: {1}", new Object[]{offset, pos});
            return "U";
        }
        List<Word> w = s.getWords();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < w.size(); i++) {
            buf.append(w.get(i).getLemma());
            if (i < w.size() - 1) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }

    public String convertPOS(POS pos) {
        if (pos.equals(POS.NOUN)) {
            return "n";
        } else if (pos.equals(POS.VERB)) {
            return "v";
        } else if (pos.equals(POS.ADJECTIVE)) {
            return "a";
        } else if (pos.equals(POS.ADVERB)) {
            return "r";
        } else {
            return "o";
        }
    }

    /**
     * Return a string which contains all lemmas assigned to synsets related to a specific synset
     *
     * @param offset Synset offset
     * @param pos POS-tag offset
     * @param relation Relation type
     * @param depth Max depth (max distance in WordNet)
     * @return String
     */
    public String getRelationElement(String offset, String pos, PointerType pointerType, int depth, boolean lemma) {
        StringBuilder buf = new StringBuilder();
        try {
            Synset s = this.returnSynset(offset, pos);
            if (s == null) {
                Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "Error in getRelationElement (synset null): {0}, pos: {1}", new Object[]{offset, pos});
                return buf.toString();
            }
            List<PointerTarget> pt = s.getTargets(pointerType);
            if (pt == null) {
                //no pointer
                return buf.toString();
            }
            for (int i = 0; i < pt.size(); i++) {
                Synset synset = pt.get(i).getSynset();
                if (synset != null) {
                    String so = String.valueOf(synset.getOffset());
                    //format sysnet
                    while (so.length() < 8) {
                        so = "0" + so;
                    }
                    buf.append(so);
                    buf.append(" ");
                    if (depth > 0) {
                        buf.append(getRelationElement(String.valueOf(synset.getOffset()), convertPOS(synset.getPOS()), pointerType, depth - 1, lemma));
                    }
                }
                List<Word> words = pt.get(i).getSynset().getWords();
                for (int k = 0; words != null && k < words.size(); k++) {
                    buf.append(words.get(k).getLemma().replaceAll("[_]", " ")).append(" ");
                }

            }
            return buf.toString();

        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Exception getRelationElement: " + offset + ", pos: " + pos + ", error:" + ex.toString(), ex);
            return buf.toString();
        }
    }

    /**
     * Return a List which contains WnNodes (WordNet node) which are in
     * relation with a sysnet. The search start from start_depth.
     *
     * @param offset Sysnet offset
     * @param pos POS-tag
     * @param relation Relation type
     * @param depth Current depth
     * @param start_depth Start depth
     * @return List of {@link nlp.wordNet.types.WnNode}
     */
    public List<WnNode> getAllRelationNode(String offset, String pos, PointerType pointerType, int depth, int start_depth) {
        List<WnNode> result = new ArrayList<WnNode>();
        try {
            Synset s = this.returnSynset(offset, pos);
            if (s == null) {
                Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "Error in getAllRelationNode (synset null): {0}, pos: {1}", new Object[]{offset, pos});
                return result;
            }
            List<PointerTarget> pt = s.getTargets(pointerType);
            if (pt.isEmpty()) {
                //pointer null skip
                return result;
            }
            for (int i = 0; i < pt.size(); i++) {
                WnNode node = new WnNode();
                node.setDepth(start_depth - depth + 1);
                node.setOffset(String.valueOf(pt.get(i).getSynset().getOffset()));
                result.add(node);
                if (depth > 0) {
                    result.addAll(getAllRelationNode(node.getOffset(), pos, pointerType, depth - 1, start_depth));
                }
            }

            return result;
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error in getAllRelationNode (synset null): " + offset + ", pos: " + pos + ", error: " + ex.toString(), ex);
            return result;
        }

    }

    /**
     * Return the common synsets in relation with o1 and o2 limiting the search to depth
     * @param o1 Synset offset
     * @param o2 Synset offset
     * @param pos POS-tag
     * @param relation Relation type
     * @param depth Max depth
     * @return The common synsets in relation with o1 and o2
     */
    public String[] getCommon(String o1, String o2, String pos, PointerType relation, int depth) {
        String[] result = new String[2];

        try {
            List<WnNode> list1 = this.getAllRelationNode(o1, pos, relation, depth, depth);
            List<WnNode> list2 = this.getAllRelationNode(o2, pos, relation, depth, depth);
            int minDepth = MAX_DEPTH + 1;
            String offset = "-1";
            int index1 = -1, index2 = -1;
            int ref1 = -1, ref2 = -1;
            for (int i = 0; i < list1.size(); i++) {
                WnNode n1 = (WnNode) list1.get(i);
                for (int j = 0; j < list2.size(); j++) {
                    WnNode n2 = list2.get(j);
                    if (n1.equals(n2)) {
                        if (n1.getDepth() < minDepth) {
                            minDepth = n1.getDepth();
                            index1 = i;
                            ref1 = j;
                        }
                    }
                }
            }
            minDepth = MAX_DEPTH + 1;
            for (int i = 0; i < list2.size(); i++) {
                WnNode n2 = list2.get(i);
                for (int j = 0; j < list1.size(); j++) {
                    WnNode n1 = list1.get(j);
                    if (n2.equals(n1)) {
                        if (n2.getDepth() < minDepth) {
                            minDepth = n2.getDepth();
                            index2 = i;
                            ref2 = j;
                        }
                    }
                }
            }
            minDepth = MAX_DEPTH + 1;
            if (index1 != -1 && index2 != -1) {
                int depth1 = list1.get(index1).getDepth();
                int depth2 = list2.get(index2).getDepth();
                if (depth1 < depth2) {
                    minDepth = depth2 + list1.get(ref2).getDepth();
                    offset = list2.get(index2).getOffset();
                } else {
                    minDepth = depth1 + list2.get(ref1).getDepth();
                    offset = list1.get(index1).getOffset();
                }
            }
            if (minDepth == MAX_DEPTH + 1) {
                result[0] = String.valueOf(MAX_DEPTH + 1);
                result[1] = "-1";
            } else {
                result[0] = String.valueOf(minDepth);
                result[1] = String.valueOf(offset);
            }



            return result;
        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error to common: " + o1 + ", " + o2 + ", pos: " + pos, ex);
            return result;
        }
    }

    /**
     * Return true if the start synset is an hypernym of the end sysnet, otherwise return false
     *
     * @param start Start synset offset
     * @param end End synset offset
     * @param pos POS-tag
     * @throws Exception Exception
     * @return true/false
     */
    public boolean isHypernym(String start, String end, String pos) throws Exception {
        try {
            List<WnNode> result = getAllRelationNode(end, pos, PointerType.HYPERNYM, MAX_DEPTH, MAX_DEPTH);

            WnNode n = new WnNode();
            n.setOffset(start);
            return result.contains(n);

        } catch (Exception ex) {
            Logger.getLogger(WordNet.class.getName()).log(Level.SEVERE, "Error in isHypernym: " + start + ", " + end + ", pos: " + pos + ", error: " + ex.toString(), ex);
            return false;
        }
    }

    /**
     * Return the synset decription
     *
     * @param offset Synset offset
     * @param pos POS tag
     * @return Synset description
     */
    public String getAllDescriptionByOffset(String offset, String pos) {
        Synset syn = returnSynset(offset, pos);
        if (syn != null) {
            return syn.toString();
        } else {
            Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "getAllDescriptionByOffset (syn null): {0}, pos: {1}", new Object[]{offset, pos});
            return "";
        }
    }

    /**
     * Return the synset gloss
     *
     * @param offset Synset offset
     * @param pos POS tag
     * @return Synset description
     */
    public String getAllGlossByOffset(String offset, String pos) {
        Synset syn = returnSynset(offset, pos);
        if (syn != null) {
            return syn.getGloss();
        } else {
            Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "getAllGlossByOffset (syn null): {0}, pos: {1}", new Object[]{offset, pos});
            return "";
        }
    }

    /**
     * Return the synset normalized decription
     *
     * @param offset Synset offset
     * @param pos POS tag
     * @return Synset description
     */
    public String getNormalizeDescriptionByOffset(String offset, String pos) {
        Synset syn = returnSynset(offset, pos);
        if (syn != null) {
            String description = syn.getGloss();
            if (description.indexOf("\"") >= 0) {
                description = description.substring(0, description.indexOf("\""));
            }
            return description;
        } else {
            Logger.getLogger(WordNet.class.getName()).log(Level.WARNING, "getAllGlossByOffset (syn null): {0}, pos: {1}", new Object[]{offset, pos});
            return "";
        }
    }

    public void destroy() {
        dictionary.close();
    }

    public int getMaxDepth() {
        return MAX_DEPTH;
    }
}
