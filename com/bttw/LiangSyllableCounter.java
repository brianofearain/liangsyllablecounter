package com.readability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


 /**
 *
 * @author Brian O Fearain
 *
 */

public class Liang {

    
        
    private static TreeMap<String, String> convertPatterns() {
        Patterns ret = new Patterns();
        HashMap map = new HashMap();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        int myInt = 3;
        int start;
        int end;
        String pat = "";
        String key = "";

        int j = 3;
        for (int i = 0; myInt < 9; i++) {
            myInt = j + i;
            start = 0;
            end = myInt;
            while (ret.patterns.get(i).substring(start, end) != null) {
                pat = ret.patterns.get(i).substring(start, end);
                String temp = pat;
                key = temp.replaceAll("(\\d+)", "");
                map.put(key, pat);
                start = end;
                end += myInt;
                if (end >= ret.patterns.get(i).length()) {
                    break;
                }
            }
        }

        treeMap.putAll(map);

        return treeMap;
    }

    public static  double hyphenateWord(ArrayList<String> words) {

        TreeMap map = convertPatterns();

        int count = 0;
        for (String word : words) {

            String w = '_' + word + '_';
            int wordLength = w.length();
            if (wordLength > 7){
            char[] s = w.toCharArray();
            w = w.toLowerCase();
            ArrayList<String> hyphenPoints = new ArrayList<String>();
            for (int i = 0; i < 26; i++) {
                hyphenPoints.add("");

            }

            int n = wordLength - 2;
            String c = "";

            for (int p = 0; p <= n; p++) {
                int maxwins = Math.min(wordLength - p, 8);
                for (int win = 2; win <= maxwins; win++) {
                    String patternKey = w.substring(p, p + win);
                    String pattern = (String) map.get(patternKey);

                    if (pattern != null && !pattern.isEmpty()) {
                        int t = 0;
                        String val = "";
                        for (int i = 0; i < pattern.length(); i++) {
                            c = pattern.substring(i, i + 1);
                            if (isNumeric(c)) {
                                int iMinusT = i - t;
                                val = val + iMinusT;
                                val = val + c;
                                t++;
                            }
                        }
                        map.put(patternKey, val);
                        pattern = val;

                        for (int i = 0; i < pattern.length(); i++) {
                            int aux = p - 1;
                            int aux2 = Integer.parseInt(pattern.substring(i, i + 1));
                            int z = aux + aux2;
                            if(z >= 0){
                            if ( hyphenPoints.get(z).length() > 0) {
                                if (hyphenPoints.get(z).charAt(0) < (pattern.substring(i + 1, i + 2)).charAt(0)) {
                                    setCharInList(z, pattern.substring(i + 1, i + 2), hyphenPoints);
//                                                              hypos.set(z, pat.substring(i+1, i+2));
                                }
                            } else {
                                setCharInList(z, pattern.substring(i + 1, i + 2), hyphenPoints);
//                                                      hypos.set(z, pat.substring(i+1, i+2));
                            }
                            }
                            i++;
                        }
                    }
                }
            }


            for (int i = 2; i <= (word.length() - 2); i++) {
                if (!hyphenPoints.get(i).isEmpty()
                        && (Integer.parseInt(hyphenPoints.get(i)) % 2 == 1)) {
                    count++;
                }
            }
        }
            else{
    
    count++;
            }
        }
        

        System.out.println(count);
        return ((double) count);
    }

    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void setCharInList(int z, String string, ArrayList<String> hyphenPoints) {
        if (hyphenPoints != null) {
            if (hyphenPoints.size() < z) {
                for (int i = hyphenPoints.size() - 1; i < z; i++) {
                    hyphenPoints.add("");
                }
                hyphenPoints.add(string);
            } else {
                hyphenPoints.set(z, string);
            }
        }
    }
}