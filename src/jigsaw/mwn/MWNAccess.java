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

 * Neither the name of the University of Pittsburgh nor the names
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
package jigsaw.mwn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Pierpaolo Basile
 */
public class MWNAccess {

    private IndexSearcher searcher_word;
    private IndexSearcher searcher_synset;

    public MWNAccess(String synsetIndexPath, String wordIndexPath) throws Exception {
        RAMDirectory ramDirSynset = new RAMDirectory(FSDirectory.open(new File(synsetIndexPath)));
        RAMDirectory ramDirWord = new RAMDirectory(FSDirectory.open(new File(wordIndexPath)));
        searcher_synset = new IndexSearcher(IndexReader.open(ramDirSynset));
        searcher_word = new IndexSearcher(IndexReader.open(ramDirWord));
    }

    public String POS_WN2MWN(POS posTag) {
        if (posTag.equals(POS.NOUN)) {
            return "n";
        } else if (posTag.equals(POS.VERB)) {
            return "v";
        } else if (posTag.equals(POS.ADJECTIVE)) {
            return "a";
        } else if (posTag.equals(POS.ADVERB)) {
            return "r";
        } else {
            return "U";
        }
    }

    public String formatOffset(long offset) {
        String s = String.valueOf(offset);
        while (s.length() < 8) {
            s = "0" + s;
        }
        return s;
    }

    public String getMWNformSynset(Synset synset) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.POS_WN2MWN(synset.getPOS()));
        sb.append(this.formatOffset(synset.getOffset()));
        return sb.toString();
    }

    public String getGloss(String id) throws Exception {
        TopDocs topDocs = searcher_synset.search(new TermQuery(new Term("id", id)), 1);
        if (topDocs.scoreDocs.length > 0) {
            return searcher_synset.doc(topDocs.scoreDocs[0].doc).get("gloss");
        } else {
            return "U";
        }
    }

    public String[] getWords(String id) throws Exception {
        TopDocs topDocs = searcher_synset.search(new TermQuery(new Term("id", id)), 1);
        if (topDocs.scoreDocs.length > 0) {
            return searcher_synset.doc(topDocs.scoreDocs[0].doc).get("word").split(" ");
        } else {
            return new String[0];
        }
    }

    public MWNSynset getSynset(String id) throws Exception {
        MWNSynset synset = new MWNSynset(id, this.getGloss(id));
        synset.setWords(this.getWords(id));
        return synset;
    }

    public MWNWord retrieveWord(String word) throws Exception {
        List<MWNSynset> concepts = new ArrayList<MWNSynset>();
        TopDocs topDocs = searcher_word.search(new TermQuery(new Term("lemma", word)), 1000);
        for (int k = 0; k < topDocs.scoreDocs.length; k++) {
            Document document = searcher_word.doc(topDocs.scoreDocs[k].doc);
            String[] tksn = document.get("id_n").split(" ");
            for (int i = 0; i < tksn.length; i++) {
                if (tksn[i].length() > 0) {
                    MWNSynset concept = new MWNSynset(tksn[i], this.getGloss(tksn[i]));
                    concept.setWords(this.getWords(tksn[i]));
                    concepts.add(concept);
                }
            }
            String[] tksv = document.get("id_v").split(" ");
            for (int i = 0; i < tksv.length; i++) {
                if (tksv[i].length() > 0) {
                    MWNSynset concept = new MWNSynset(tksv[i], this.getGloss(tksv[i]));
                    concept.setWords(this.getWords(tksv[i]));
                    concepts.add(concept);
                }
            }

            String[] tksa = document.get("id_a").split(" ");
            for (int i = 0; i < tksa.length; i++) {
                if (tksa[i].length() > 0) {
                    MWNSynset concept = new MWNSynset(tksa[i], this.getGloss(tksa[i]));
                    concept.setWords(this.getWords(tksa[i]));
                    concepts.add(concept);
                }
            }

            String[] tksr = document.get("id_r").split(" ");
            for (int i = 0; i < tksr.length; i++) {
                if (tksr[i].length() > 0) {
                    MWNSynset concept = new MWNSynset(tksr[i], this.getGloss(tksr[i]));
                    concept.setWords(this.getWords(tksr[i]));
                    concepts.add(concept);
                }
            }
        }
        return new MWNWord(word, concepts);
    }

    public void shutdown() throws Exception {
        searcher_synset.close();
        searcher_word.close();
    }

    public void dbToIndex(String outputIndexSynset, String outputIndexWord, String address, String port, String schema, String user, String password) {
        try {
            String driver = "com.mysql.jdbc.Driver";
            //Load JDBC/MySQL driver
            Class.forName(driver).newInstance();
            String url = "jdbc:mysql://" + address + ":" + port + "/" + schema;
            Properties connProp = new Properties();
            connProp.setProperty("user", user);
            connProp.setProperty("password", password);
            connProp.setProperty("connectionString", url);
            Connection connection = DriverManager.getConnection(url, connProp);
            IndexWriterConfig idxConfigSynset = new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36));
            idxConfigSynset.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writerSynset = new IndexWriter(FSDirectory.open(new File(outputIndexSynset)), idxConfigSynset);
            Statement st1 = connection.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT id,word,gloss FROM italian_synset");
            while (rs1.next()) {
                Document document = new Document();
                String id = rs1.getString("id");
                if (id != null) {
                    document.add(new Field("id", id.replace("#", ""), Field.Store.YES, Field.Index.NOT_ANALYZED));
                } else {
                    document.add(new Field("id", "", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                String word = rs1.getString("word");
                if (word != null) {
                    document.add(new Field("word", word.toLowerCase(), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    document.add(new Field("word", "", Field.Store.YES, Field.Index.ANALYZED));
                }

                String gloss = rs1.getString("gloss");
                if (gloss != null) {
                    document.add(new Field("gloss", gloss.toLowerCase(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                } else {
                    document.add(new Field("gloss", "", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                writerSynset.addDocument(document);
            }
            st1.close();
            writerSynset.close();

            IndexWriterConfig idxConfigWord = new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36));
            idxConfigWord.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writerWord = new IndexWriter(FSDirectory.open(new File(outputIndexWord)), idxConfigWord);
            Statement st2 = connection.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT lemma,id_n,id_v,id_a,id_r FROM italian_index");
            while (rs2.next()) {
                Document document = new Document();
                String lemma = rs2.getString("lemma");
                if (lemma != null) {
                    document.add(new Field("lemma", lemma.toLowerCase(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                } else {
                    document.add(new Field("lemma", "", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                String id_n = rs2.getString("id_n");
                if (id_n != null) {
                    document.add(new Field("id_n", id_n.replace("#", ""), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    document.add(new Field("id_n", "", Field.Store.YES, Field.Index.ANALYZED));
                }

                String id_v = rs2.getString("id_v");
                if (id_v != null) {
                    document.add(new Field("id_v", id_v.replace("#", ""), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    document.add(new Field("id_v", "", Field.Store.YES, Field.Index.ANALYZED));
                }

                String id_a = rs2.getString("id_a");
                if (id_a != null) {
                    document.add(new Field("id_a", id_a.replace("#", ""), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    document.add(new Field("id_a", "", Field.Store.YES, Field.Index.ANALYZED));
                }

                String id_r = rs2.getString("id_r");
                if (id_r != null) {
                    document.add(new Field("id_r", id_r.replace("#", ""), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    document.add(new Field("id_r", "", Field.Store.YES, Field.Index.ANALYZED));
                }
                writerWord.addDocument(document);
            }
            st2.close();
            writerWord.close();

            connection.close();
        } catch (Exception ex) {
            Logger.getLogger(MWNAccess.class.getName()).log(Level.SEVERE, "Error to index MWN db", ex);
        }
    }
}
