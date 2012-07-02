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
import java.util.HashMap;

/**
 * 
 * This class contains information about MWN types
 * @author Pierpaolo
 */

public class MWNType {
    
    private final static HashMap<String,Integer> map;
    
    public final static int ANTONYM=0;
    
    public final static int HYPERNYM=1;
    
    public final static int HYPONYM=2;
    
    public final static int MEMBER_OF=3;
    
    public final static int SUBSTANCE_OF=4;
    
    public final static int PART_OF=5;
    
    public final static int HAS_MEMBER=6;
    
    public final static int HAS_SUBSTANCE=7;
    
    public final static int HAS_PART=8;
    
    public final static int ATTRIBUTE=9;
    
    public final static int NEAREST=10;
    
    public final static int COMPOSED_OF=11;
    
    public final static int COMPOSES=12;
    
    public final static int ENTAILMENT=13;
    
    public final static int CAUSES=14;
    
    public final static int ALSO_SEE=15;
    
    public final static int VERB_GROUP=16;
    
    public final static int SIMILAR_TO=17;
    
    public final static int PARTICIPLE=18;
    
    public final static int PERTAINS_TO=19;
    
    //public static int IS_VALUE_OF=20;
    
    public final static int DERIVED_FROM=21;
    
    public final static int NOUN=1001;
    
    public final static int VERB=1002;
    
    public final static int ADJ=1003;
    
    public final static int ADV=1004;
    
    /** Init map */
    static {
        map=new HashMap<String, Integer>();
        map.put("!",new Integer(MWNType.ANTONYM));
        map.put("@",new Integer(MWNType.HYPERNYM));
        map.put("~",new Integer(MWNType.HYPONYM));
        map.put("#m",new Integer(MWNType.MEMBER_OF));
        map.put("#s",new Integer(MWNType.SUBSTANCE_OF));
        map.put("#p",new Integer(MWNType.PART_OF));
        map.put("%m",new Integer(MWNType.HAS_MEMBER));
        map.put("%s",new Integer(MWNType.HAS_SUBSTANCE));
        map.put("%p",new Integer(MWNType.HAS_PART));
        map.put("=",new Integer(MWNType.ATTRIBUTE));
        map.put("|",new Integer(MWNType.NEAREST));
        map.put("+c",new Integer(MWNType.COMPOSED_OF));
        map.put("-c",new Integer(MWNType.COMPOSES));
        map.put("*",new Integer(MWNType.ENTAILMENT));
        map.put(">",new Integer(MWNType.CAUSES));
        map.put("^",new Integer(MWNType.ALSO_SEE));
        map.put("$",new Integer(MWNType.VERB_GROUP));
        map.put("&",new Integer(MWNType.SIMILAR_TO));
        map.put("<",new Integer(MWNType.PARTICIPLE));
        map.put("\\",new Integer(MWNType.DERIVED_FROM));
    }
    
    
    /**
     * Convert string type to int type
     * @param code String type
     * @return int type
     */
    public static int translateCode(String code) {
        Integer value=map.get(code);
        if (value==null)
            return -1;
        else
            return value.intValue();
    }
    
    /**
     * 
     * Check semantic relations
     * @param code int type
     * @return true if type represents a semantic relation then false
     */
    public static boolean isRelationSemantic(int code) {
        if (code==MWNType.ANTONYM && code==MWNType.COMPOSED_OF && code==MWNType.COMPOSES)
            return false;
        else
            return true;
    }
    
    /**
     * 
     * Return all type
     * @return array of type
     */
    public static int[] getAllType() {
        int[] types=new int[21];
        int j=0;
        for (int i=0;i<22;i++) {
            if (i!=20) {
                types[j]=i;
                j++;
            }
        }
        return types;
    }
    
}
