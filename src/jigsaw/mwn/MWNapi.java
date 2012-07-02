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
package jigsaw.mwn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jigsaw.utils.DBAccess;

/**
 * MWN API
 *
 * @author Pierpaolo
 */
public class MWNapi {

    private DBAccess dbAccess;
    private Map<String, MWNSynset> synset_map_it;
    private Map<String, MWNSynset> synset_map_en;
    private Map<String, MWNLemma> lemma_map_it;
    private Map<String, MWNLemma> lemma_map_en;
    private Map<String, MWNDomain> domains;

    public MWNapi(DBAccess dbAccess) {
        this.dbAccess = dbAccess;
    }

    public void init() throws Exception {
        try {
            dbAccess.connect();
            Connection connection = dbAccess.getConnection();
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Loading MultiWordNet...");
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building IT synsets...");

            Statement statement = connection.createStatement();

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building EN synsets...");
            synset_map_en = new HashMap<String, MWNSynset>();
            ResultSet rs = statement.executeQuery("select * from english_synset");
            while (rs.next()) {
                MWNSynset s = new MWNSynset();
                s.setId(rs.getString("id"));
                String temp = rs.getString("word");
                if (temp != null) {
                    s.setWord(temp.split("[ ]+"));
                }
                temp = rs.getString("phrase");
                if (temp != null) {
                    s.setPhrase(temp.split("[ ]+"));
                }
                s.setGloss(rs.getString("gloss"));
                synset_map_en.put(s.getId(), s);
            }
            rs.close();
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "EN synsets={0}", synset_map_en.size());

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building EN lemmas index...");
            lemma_map_en = new HashMap<String, MWNLemma>();
            rs = statement.executeQuery("select * from english_index");
            while (rs.next()) {
                MWNLemma l = new MWNLemma();
                l.setLemma(rs.getString("lemma"));
                String temp = rs.getString("id_n");
                if (temp != null) {
                    l.setNoun(temp.split("[ ]+"));
                }
                temp = rs.getString("id_v");
                if (temp != null) {
                    l.setVerb(temp.split("[ ]+"));
                }
                temp = rs.getString("id_a");
                if (temp != null) {
                    l.setAdj(temp.split("[ ]+"));
                }
                temp = rs.getString("id_r");
                if (temp != null) {
                    l.setAdv(temp.split("[ ]+"));
                }
                lemma_map_en.put(l.getLemma(), l);
            }
            rs.close();
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "EN lemmas={0}", lemma_map_en.size());

            synset_map_it = new HashMap<String, MWNSynset>();
            rs = statement.executeQuery("select * from italian_synset");
            while (rs.next()) {
                MWNSynset s = new MWNSynset();
                s.setId(rs.getString("id"));
                String temp = rs.getString("word");
                if (temp != null) {
                    s.setWord(temp.split("[ ]+"));
                }
                temp = rs.getString("phrase");
                if (temp != null) {
                    s.setPhrase(temp.split("[ ]+"));
                }
                s.setGloss(rs.getString("gloss"));
                synset_map_it.put(s.getId(), s);
            }
            rs.close();
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "IT synsets={0}", synset_map_it.size());

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building IT lemmas index...");
            lemma_map_it = new HashMap<String, MWNLemma>();
            rs = statement.executeQuery("select * from italian_index");
            while (rs.next()) {
                MWNLemma l = new MWNLemma();
                l.setLemma(rs.getString("lemma"));
                String temp = rs.getString("id_n");
                if (temp != null) {
                    l.setNoun(temp.split("[ ]+"));
                }
                temp = rs.getString("id_v");
                if (temp != null) {
                    l.setVerb(temp.split("[ ]+"));
                }
                temp = rs.getString("id_a");
                if (temp != null) {
                    l.setAdj(temp.split("[ ]+"));
                }
                temp = rs.getString("id_r");
                if (temp != null) {
                    l.setAdv(temp.split("[ ]+"));
                }
                lemma_map_it.put(l.getLemma(), l);
            }
            rs.close();
            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "IT lemmas={0}", lemma_map_it.size());

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building common relations...");
            rs = statement.executeQuery("select * from common_relation");
            while (rs.next()) {
                MWNPointer p = new MWNPointer();
                p.setType(MWNType.translateCode(rs.getString("type")));
                String id_source = rs.getString("id_source");
                MWNSynset se = synset_map_en.get(id_source);
                MWNSynset si = synset_map_it.get(id_source);
                if (se != null) {
                    String id_target = rs.getString("id_target");
                    p.setTarget(id_target);
                    se.addPointer(p);
                    //build reverse relation
                    if (p.getType() == MWNType.HYPERNYM) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HYPONYM);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_en.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.MEMBER_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_MEMBER);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_en.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.SUBSTANCE_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_SUBSTANCE);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_en.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.PART_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_PART);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_en.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    }
                } else if (si != null) {
                    String id_target = rs.getString("id_target");
                    p.setTarget(id_target);
                    si.addPointer(p);
                    //build reverse relation
                    if (p.getType() == MWNType.HYPERNYM) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HYPONYM);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_it.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.MEMBER_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_MEMBER);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_it.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.SUBSTANCE_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_SUBSTANCE);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_it.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    } else if (p.getType() == MWNType.PART_OF) {
                        MWNPointer reverse_p = new MWNPointer();
                        reverse_p.setType(MWNType.HAS_PART);
                        reverse_p.setTarget(id_source);
                        MWNSynset reverse_s = synset_map_it.get(id_target);
                        if (reverse_s != null) {
                            reverse_s.addPointer(reverse_p);
                        }
                    }
                } else {
                    Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Source synset not found (common relations): {0}", id_source);
                }
            }
            rs.close();

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building italian relations...");
            rs = statement.executeQuery("select * from italian_relation");
            while (rs.next()) {
                MWNPointer p = new MWNPointer();
                p.setType(MWNType.translateCode(rs.getString("type")));
                if (MWNType.isRelationSemantic(p.getType())) {
                    String id_source = rs.getString("id_source");
                    MWNSynset s = synset_map_it.get(id_source);
                    if (s == null) {
                        s = synset_map_en.get(id_source);
                    }
                    if (s != null) {
                        String id_target = rs.getString("id_target");
                        p.setTarget(id_target);
                        s.addPointer(p);
                        //build reverse relation
                        if (p.getType() == MWNType.HYPERNYM) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HYPONYM);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_it.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.MEMBER_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_MEMBER);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_it.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.SUBSTANCE_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_SUBSTANCE);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_it.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.PART_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_PART);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_it.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        }
                    } else {
                        Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Source synset (italian semantic relation) not found: {0}", id_source);
                    }
                } else {
                    String w_source = rs.getString("w_source");
                    MWNLemma l = lemma_map_it.get(w_source);
                    if (l == null) {
                        lemma_map_en.get(w_source);
                    }
                    if (l != null) {
                        String w_target = rs.getString("w_target");
                        p.setTarget(w_target);
                        l.addPointer(p);
                        if (p.getType() == MWNType.COMPOSED_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.COMPOSES);
                            reverse_p.setTarget(w_source);
                            MWNLemma reverse_l = lemma_map_it.get(w_source);
                            if (l != null) {
                                reverse_l.addPointer(reverse_p);
                            }
                        }
                    } else {
                        Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Source lemma (italian lexical relation) not found: {0}", w_source);
                    }
                }
            }
            rs.close();

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building english relations...");
            rs = statement.executeQuery("select * from english_relation");
            while (rs.next()) {
                MWNPointer p = new MWNPointer();
                p.setType(MWNType.translateCode(rs.getString("type")));
                if (MWNType.isRelationSemantic(p.getType())) {
                    String id_source = rs.getString("id_source");
                    MWNSynset s = synset_map_en.get(id_source);
                    if (s != null) {
                        String id_target = rs.getString("id_target");
                        p.setTarget(id_target);
                        s.addPointer(p);
                        //build reverse relation
                        if (p.getType() == MWNType.HYPERNYM) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HYPONYM);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_en.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.MEMBER_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_MEMBER);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_en.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.SUBSTANCE_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_SUBSTANCE);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_en.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        } else if (p.getType() == MWNType.PART_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.HAS_PART);
                            reverse_p.setTarget(id_source);
                            MWNSynset reverse_s = synset_map_en.get(id_target);
                            if (reverse_s != null) {
                                reverse_s.addPointer(reverse_p);
                            }
                        }
                    } else {
                        Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Source synset (english semantic relation) not found: {0}", id_source);
                    }
                } else {
                    String w_source = rs.getString("w_source");
                    MWNLemma l = lemma_map_en.get(w_source);
                    if (l != null) {
                        String w_target = rs.getString("w_target");
                        p.setTarget(w_target);
                        l.addPointer(p);
                        if (p.getType() == MWNType.COMPOSED_OF) {
                            MWNPointer reverse_p = new MWNPointer();
                            reverse_p.setType(MWNType.COMPOSES);
                            reverse_p.setTarget(w_source);
                            MWNLemma reverse_l = lemma_map_en.get(w_source);
                            if (l != null) {
                                reverse_l.addPointer(reverse_p);
                            }
                        }
                    } else {
                        Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Source lemma (english lexical relation) not found: {0}", w_source);
                    }
                }
            }
            rs.close();


            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building MultiWordNet domains...");
            domains = new HashMap<String, MWNDomain>();
            rs = statement.executeQuery("select * from semfield_hierarchy");
            while (rs.next()) {
                MWNDomain d = new MWNDomain();
                d.setDomain(rs.getString("english"));
                domains.put(d.getDomain(), d);
            }
            rs.close();
            rs = statement.executeQuery("select * from semfield");
            while (rs.next()) {
                String synset = rs.getString("synset");
                MWNSynset si = synset_map_it.get(synset);
                MWNSynset se = synset_map_en.get(synset);
                if (si != null) {
                    String[] ds = rs.getString("english").split("[ ]+");
                    for (int i = 0; i < ds.length; i++) {
                        if (ds[i].length() > 0) {
                            MWNDomain d = domains.get(ds[i]);
                            if (d != null) {
                                si.addDomain(d);
                            } else {
                                Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Domain not found: {0}", ds[i]);
                            }
                        }
                    }
                }
                if (se != null) {
                    String[] ds = rs.getString("english").split("[ ]");
                    for (int i = 0; i < ds.length; i++) {
                        if (ds[i].length() > 0) {
                            MWNDomain d = domains.get(ds[i]);
                            if (d != null) {
                                se.addDomain(d);
                            } else {
                                Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Domain not found: {0}", ds[i]);
                            }
                        }
                    }
                }
                if (si == null && se == null) {
                    Logger.getLogger(MWNapi.class.getName()).log(Level.WARNING, "Domain not found: {0} {1}", new Object[]{si.getId(), se.getId()});
                }
            }
            rs.close();

            Logger.getLogger(MWNapi.class.getName()).log(Level.INFO, "Building domain hierarchy...");
            rs = statement.executeQuery("select * from semfield_hierarchy");
            while (rs.next()) {
                String domain = rs.getString("english");
                MWNDomain d = domains.get(domain);
                if (d != null) {
                    String[] hypons = rs.getString("hypons").split("[ ]");
                    String[] hypers = rs.getString("hypers").split("[ ]");
                    for (int i = 0; i < hypons.length; i++) {
                        MWNDomain d_h = domains.get(hypons[i]);
                        if (d_h != null) {
                            d.addHypo(d_h);
                        }
                    }
                    for (int i = 0; i < hypers.length; i++) {
                        MWNDomain d_h = domains.get(hypers[i]);
                        if (d_h != null) {
                            d.addHype(d_h);
                        }
                    }
                }
            }
            connection.close();
        } catch (Exception ex) {
            Logger.getLogger(MWNapi.class.getName()).log(Level.SEVERE, "Error to load MultiWordNet", ex);
        }
    }

    public void close() {
        synset_map_en.clear();
        synset_map_it.clear();
        lemma_map_en.clear();
        lemma_map_it.clear();
        domains.clear();
        synset_map_en = null;
        synset_map_it = null;
        lemma_map_en = null;
        lemma_map_it = null;
        domains = null;
        System.gc();
    }

    public MWNSynset getItalianSynset(String offset) {
        return synset_map_it.get(offset);
    }

    public MWNSynset getEnglishSynset(String offset) {
        return synset_map_en.get(offset);
    }

    public MWNSynset[] lookupItalianSynset(String word, int pos) {
        MWNLemma l = lemma_map_it.get(word);
        if (l == null) {
            return new MWNSynset[0];
        }
        String[] offsets = null;
        if (pos == MWNType.NOUN) {
            offsets = l.getNoun();
        } else if (pos == MWNType.VERB) {
            offsets = l.getVerb();
        } else if (pos == MWNType.ADJ) {
            offsets = l.getAdj();
        } else if (pos == MWNType.ADV) {
            offsets = l.getAdv();
        }
        if (offsets == null) {
            return null;
        }
        MWNSynset[] synsets = new MWNSynset[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            MWNSynset s = this.getItalianSynset(offsets[i]);
            if (s == null) {
                s = this.getEnglishSynset(offsets[i]);
            }
            synsets[i] = s;
        }
        return synsets;
    }

    public MWNSynset[] lookupEnglishSynset(String word, int pos) {
        MWNLemma l = lemma_map_en.get(word);
        if (l == null) {
            return null;
        }
        String[] offsets = null;
        if (pos == MWNType.NOUN) {
            offsets = l.getNoun();
        } else if (pos == MWNType.VERB) {
            offsets = l.getVerb();
        } else if (pos == MWNType.ADJ) {
            offsets = l.getAdj();
        } else if (pos == MWNType.ADV) {
            offsets = l.getAdv();
        }
        if (offsets == null) {
            return null;
        }
        MWNSynset[] synsets = new MWNSynset[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            synsets[i] = this.getEnglishSynset(offsets[i]);
        }
        return synsets;
    }

    public Iterator<MWNDomain> getDomainsIterator() {
        return domains.values().iterator();
    }
}
