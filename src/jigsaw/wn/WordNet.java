/*
 * DefaultWordNet.java
 *
 * Created on 29 aprile 2005, 11.17
 */
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
 * This class contains all methods need to access WordNet.
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
     * Creates a new instance of DefaultWordNet
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
     * Return depth by relation and offset
     *
     * @param s1 First offset
     * @param s2 Second offset
     * @param pos Pos tagger information
     * @param relation Relation type
     * @throws Exception Exception
     * @return Depth
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
     * Return depth by relation and offset, but don't get over maxDepth
     *
     * @param s1 First offset
     * @param s2 Second offset
     * @param pos Pos tagger information
     * @param relation Relation type
     * @param maxDepth Max depth
     * @throws Exception Exception
     * @return Depth
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
     * Return the wordnet tag from word. If word not have a sense, this method
     * return -1.
     *
     * @param word Word
     * @param loolup Set lookup mode true/false
     * @throws Exception Exception
     * @return Wordnet tag
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
     * Return all synsets by word
     *
     * @return Synsets. Array of strings, each string contains a synset unique
     * identify (synset offset).
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
     * Init wordnet
     *
     * @throws Exception Exception
     * @return true - init ok false - init problem
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
     * Return the lemma's words wich are contained in synset offset
     *
     * @param offset Sysnet offset
     * @param pos POS-tag
     * @return String of all lemma's words wich are contained in synset offset
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
     * Return a string wich contains all lemma's word that appertain to all
     * synsets in relation with specified synset.
     *
     * @param offset Synset offset
     * @param pos POS-tag offset
     * @param relation Relation type
     * @param depth Max depth
     * @return String wich contains all lemma's word
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
     * Return an ArrayList that contains WnNode objects wich are in
     * relation with offset. The search start to start_depth. When this method
     * are called start_depth must be equal to depth.
     *
     * @param offset Sysnet offset
     * @param pos POS-tag
     * @param relation Relation type
     * @param depth Recorsive depth
     * @param start_depth Start depth
     * @return ArrayList of {@link nlp.wordNet.types.WnNode}
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
     * Return the common element in relation with o1 and o2, but don't get over
     * depth.
     *
     * @param o1 Synset offset
     * @param o2 Synset offset
     * @param pos POS-tag
     * @param relation Relation type
     * @param depth Max depth
     * @return The common element in relation with o1 and o2
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
     * Return true if start word is a hypernym of end word else false
     *
     * @param start Start word
     * @param end End word
     * @param pos Wordnet tag
     * @throws Exception Exception
     * @return true if start word is a hypernym of end word else false
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
     * Return the synset decription by offset
     *
     * @param offset Synset offset
     * @param pos Wordnet tag
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
     * Return the synset gloss by offset
     *
     * @param offset Synset offset
     * @param pos Wordnet tag
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
     * Return the synset normalize decription by offset. The normalize
     * description is the synset gloss.
     *
     * @param offset Synset offset
     * @param pos Wordnet tag
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

    /**
     * Return average polysemy for this wordnet tag
     *
     * @param posTag Wordnet tag
     * @return Average polysemy for this wordnet tag
     */
    public double getAveragePolisemy(String posTag) {
        if (posTag.equals("n")) {
            return 2.79;
        }
        if (posTag.equals("v")) {
            return 3.66;
        }
        if (posTag.equals("a")) {
            return 2.80;
        }
        if (posTag.equals("r")) {
            return 2.49;
        }
        return 0;
    }

    public void destroy() {
        dictionary.close();
    }

    public int getMaxDepth() {
        return MAX_DEPTH;
    }
}
