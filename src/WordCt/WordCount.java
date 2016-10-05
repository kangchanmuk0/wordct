package WordCt;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class Alphabetize implements Comparator<Map.Entry<String, Int>> {
  public int compare(Map.Entry<String, Int> entry1
                   , Map.Entry<String, Int> entry2) {
      return entry1.getKey().compareTo(entry2.getKey());
  }
}

class CompareFrequency implements Comparator<Map.Entry<String, Int>> {
  public int compare(Map.Entry<String, Int> word1
                   , Map.Entry<String, Int> word2) {
      int result;
      int count1 = word1.getValue().value;
      int count2 = word2.getValue().value;
      if (count1 < count2) {
          result = -1;
          
      } else  if(count1 > count2) {
          result = 1;
          
      } else { 
          //If counts are equal, compare keys alphabetically.
          result = word1.getKey().compareTo(word2.getKey());
      }
      return result;
  }
}


public class WordCount {
  private static final Comparator<Map.Entry<String, Int>> SORT_BY_FREQUENCY = 
          new CompareFrequency();
  private static final Comparator<Map.Entry<String, Int>> SORT_ALPHABETICALLY = 
          new Alphabetize();
  public enum SortOrder {ALPHABETICALLY, BY_FREQUENCY}
  
  Map<String, Int> _wordFrequency; 
  int              _totalWords;
 
 //Constructor
  public WordCount() {
      _wordFrequency = new HashMap<String, Int>();
      _totalWords    = 0;
  }
  
  public void countWords(File sourceFile) throws IOException {
      Scanner wordScanner = new Scanner(sourceFile);
      wordScanner.useDelimiter("[^°¡-°¡,³ª-³ª,´Ù-´Ù,¶ó-¶ó,¸¶-¸¶,¹Ù-¹Ù,»ç-»ç,¾Æ-¾Æ,ÀÚ-ÀÚ,Â÷-Â÷,Ä«-Ä«,Å¸-Å¸,ÆÄ-ÆÄ,ÇÏ-ÇÏ,"
      		+ "±î-±î ±ú-±ú ²¥-²¥ ƒÆ-ƒÆ ²¨-²¨ ²²-²² ²¸-²¸ ²¾-²¾ ²¿-²¿ ²Ê-²Ê ²Ï-²Ï ²Ò-²Ò ²Ø-²Ø ²Ù-²Ù ²ã-²ã ²ç-²ç ²î-²î ²ó-²ó ²ô-²ô …Ê-…Ê ³¢-³¢,"
      		+ "µû-µû ¶§-¶§ ‹x-‹x ‹š-‹š ¶°-¶° ¶¼-¶¼ ¶Å-¶Å ‹ó-‹ó ¶Ç-¶Ç ¶Ì-¶Ì ¶Î-¶Î ¶Ï-¶Ï ŒÃ-ŒÃ ¶Ñ-¶Ñ Œô-Œô ¶Ø-¶Ø ¶Ù-¶Ù - ¶ß-¶ß ¶ç-¶ç ¶ì-¶ì,"
      		+ "ºü-ºü »©-»© »²-»² •û-•û »µ-»µ »¾-»¾ »À-»À –§-–§ »Ç-»Ç –Ø-–Ø –ô-–ô »Î-»Î »Ï-»Ï »Ñ-»Ñ —¨-—¨ —Ä-—Ä —à-—à »Ø-»Ø »Ú-»Ú ˜u-˜u »ß-»ß,"
      		+ "½Î-½Î ½Ø-½Ø ›X-›X ›y-›y ½á-½á ½ê-½ê ›Ç-›Ç ›ã-›ã ½î-½î ½÷-½÷ ½û-½û ½ı-½ı ¾¤-¾¤ ¾¥-¾¥ ¾¬-¾¬ ¾®-¾® ¾¯-¾¯ o-o ¾²-¾² ¾º-¾º ¾¾-¾¾,"
      		+ "Â¥-Â¥ Â°-Â° Â¹-Â¹ ¤Š-¤Š Â¼-Â¼ ÂÅ-ÂÅ ÂÇ-ÂÇ ¥™-¥™ ÂÉ-ÂÉ ÂÒ-ÂÒ ÂÖ-ÂÖ ÂØ-ÂØ §c-§c ÂŞ-ÂŞ Âå-Âå ¨R-¨R Âè-Âè Âé-Âé Âê-Âê ©n-©n Âî-Âî,"
      		+ "°¡-°¡ °³-°³ °¼-°¼ °Â-°Â °Å-°Å °Ô-°Ô °Ü-°Ü °è-°è °í-°í °ú-°ú ±¥-±¥ ±«-±« ±³-±³ ±¸-±¸ ±Å-±Å ±Ë-±Ë ±Í-±Í ±Ô-±Ô ±×-±× ±á-±á ±â-±â,"
      		+ "³ª-³ª ³»-³» ³Ä-³Ä †v-†v ³Ê-³Ê ³×-³× ³à-³à ³é-³é ³ë-³ë ³ö-³ö ‡R-‡R ³ú-³ú ´¢-´¢ ´©-´© ´²-´² ´´-´´ ´µ-´µ ´º-´º ´À-´À ´Ì-´Ì ´Ï-´Ï,"
      		+ "´Ù-´Ù ´ë-´ë ´ô-´ô ˆÛ-ˆÛ ´õ-´õ µ¥-µ¥ µ®-µ® µ³-µ³ µµ-µµ µÂ-µÂ µÅ-µÅ µÇ-µÇ µÍ-µÍ µÎ-µÎ µÖ-µÖ µØ-µØ µÚ-µÚ µà-µà µå-µå µï-µï µğ-µğ,"
      		+ "¶ó-¶ó ·¡-·¡ ·ª-·ª m-m ·¯-·¯ ·¹-·¹ ·Á-·Á ·Ê-·Ê ·Î-·Î ·Ö-·Ö O-O ·Ú-·Ú ·á-·á ·ç-·ç ·ï-·ï ·ñ-·ñ ·ò-·ò ·ù-·ù ¸£-¸£ l-l ¸®-¸®,"
      		+ "¸¶-¸¶ ¸Å-¸Å ¸Ï-¸Ï Ù-Ù ¸Ó-¸Ó ¸Ş-¸Ş ¸ç-¸ç ¸ï-¸ï ¸ğ-¸ğ ¸ú-¸ú ‘À-‘À ¸ş-¸ş ¹¦-¹¦ ¹«-¹« ¹¹-¹¹ ¹¾-¹¾ ¹¿-¹¿ ¹Â-¹Â ¹Ç-¹Ç ’Ş-’Ş ¹Ì-¹Ì,"
      		+ "¹Ù-¹Ù ¹è-¹è ¹ò-¹ò “-“ ¹ö-¹ö º£-º£ º­-º­ º¶-º¶ º¸-º¸ ºÁ-ºÁ ºÄ-ºÄ ºÆ-ºÆ ºÌ-ºÌ ºÎ-ºÎ ºÛ-ºÛ ºŞ-ºŞ ºß-ºß ºä-ºä ºê-ºê •‘-•‘ ºñ-ºñ,"
      		+ "»ç-»ç »õ-»õ »ş-»ş ¼¨-¼¨ ¼­-¼­ ¼¼-¼¼ ¼Å-¼Å ¼Î-¼Î ¼Ò-¼Ò ¼İ-¼İ ¼â-¼â ¼è-¼è ¼î-¼î ¼ö-¼ö ½¤-½¤ ½¦-½¦ ½¬-½¬ ½´-½´ ½º-½º šÃ-šÃ ½Ã-½Ã,"
      		+ "¾Æ-¾Æ ¾Ö-¾Ö ¾ß-¾ß ¾ê-¾ê ¾î-¾î ¿¡-¿¡ ¿©-¿© ¿¹-¿¹ ¿À-¿À ¿Í-¿Í ¿Ö-¿Ö ¿Ü-¿Ü ¿ä-¿ä ¿ì-¿ì ¿ö-¿ö ¿ş-¿ş À§-À§ À¯-À¯ À¸-À¸ ÀÇ-ÀÇ ÀÌ-ÀÌ,"
      		+ "ÀÚ-ÀÚ Àç-Àç Àğ-Àğ À÷-À÷ Àú-Àú Á¦-Á¦ Á®-Á® Áµ-Áµ Á¶-Á¶ ÁÂ-ÁÂ ÁÈ-ÁÈ ÁË-ÁË ÁÒ-ÁÒ ÁÖ-ÁÖ Áà-Áà Áâ-Áâ Áã-Áã Áê-Áê Áî-Áî £p-£p Áö-Áö,"
      		+ "Â÷-Â÷ Ã¤-Ã¤ Ã­-Ã­ ª‰-ª‰ Ã³-Ã³ Ã¼-Ã¼ ÃÄ-ÃÄ ÃÇ-ÃÇ ÃÊ-ÃÊ ÃÒ-ÃÒ ¬‚-¬‚ ÃÖ-ÃÖ Ãİ-Ãİ Ãß-Ãß Ãç-Ãç Ãé-Ãé Ãë-Ãë Ãò-Ãò Ã÷-Ã÷ ¯M-¯M Ä¡-Ä¡,"
      		+ "Ä«-Ä« Ä³-Ä³ Ä¼-Ä¼ °m-°m Ä¿-Ä¿ ÄÉ-ÄÉ ÄÑ-ÄÑ ÄÙ-ÄÙ ÄÚ-ÄÚ Äâ-Äâ Äè-Äè Äê-Äê Äì-Äì Äí-Äí Äõ-Äõ Äù-Äù Äû-Äû Å¥-Å¥ Å©-Å© ´”-´” Å°-Å°,"
      		+ "Å¸-Å¸ ÅÂ-ÅÂ ÅË-ÅË ¶O-¶O ÅÍ-ÅÍ Å×-Å× Åß-Åß Åâ-Åâ Åä-Åä Åí-Åí Åï-Åï Åğ-Åğ Åô-Åô Åõ-Åõ Åı-Åı Æ¡-Æ¡ Æ¢-Æ¢ Æ©-Æ© Æ®-Æ® Æ·-Æ· Æ¼-Æ¼,"
      		+ "ÆÄ-ÆÄ ÆĞ-ÆĞ ÆÙ-ÆÙ »—-»— ÆÛ-ÆÛ Æä-Æä Æì-Æì Æó-Æó Æ÷-Æ÷ Ç¡-Ç¡ ½-½ Ç£-Ç£ Ç¥-Ç¥ Çª-Çª Ç´-Ç´ ¿R-¿R Ç¶-Ç¶ Ç»-Ç» ÇÁ-ÇÁ Àc-Àc ÇÇ-ÇÇ,"
      		+ "ÇÏ-ÇÏ ÇØ-ÇØ Çá-Çá Á…-Á… Çã-Çã Çì-Çì Çô-Çô Çı-Çı È£-È£ È­-È­ È³-È³ È¸-È¸ È¿-È¿ ÈÄ-ÈÄ ÈÌ-ÈÌ ÈÑ-ÈÑ ÈÖ-ÈÖ ÈŞ-ÈŞ Èå-Èå Èñ-Èñ È÷-È÷,]+"); 
      // ±àÁ¤´Ü¾î µé¾î°¥ ÀÚ¸®
      //¿©±â¿¡ Æ÷ÇÔµÇ´Â ¾î¹Ì°¡ count µÈ´Ù.
      //¿©±â¿¡ ¸ğµç ±àÁ¤ ´Ü¾î ³Ö±â.
      while (wordScanner.hasNext()) {
          String word = wordScanner.next();
          _totalWords++;
      
          //Add word if not already placed, else increment count
              Int count = _wordFrequency.get(word);
              if (count == null) {    // Create new entry
                  _wordFrequency.put(word, new Int(1));
              } else {               
                  count.value++;
              }
    
      }
      wordScanner.close(); 
  }
  
  
 //Record frequency of a word in a String
  public void countWords(String source) {
      Scanner wordScanner = new Scanner(source);
      wordScanner.useDelimiter("[^¾Ê-¾Ê-¾Ê,¸ø-¸ø,½È-]+"); //ºÎÁ¤ ´Ü¾î µé¾î°¥ ÀÚ¸®
      // ¿©±âµé¾î°£ ´Ü¾î¸¦ Á¦¿ÜÇÏ°í countµÈ´Ù.
      //¿©±â´Ù°¡ ºÎÁ¤¾î¸¦ ½Ï´Ù Áı¾î ³ÖÀ¸¸é °á°ú°ªÀÌ ±àÁ¤¾î °¹¼ö°¡ count µÈ´Ù.
      while (wordScanner.hasNext()) {
          String word = wordScanner.next();
          _totalWords++;
          
          //Add word if not already placed, else increment count
              Int count = _wordFrequency.get(word);
              if (count == null) {    // Create new entry.
                  _wordFrequency.put(word, new Int(1));
              } else {               
                  count.value++;
              }
      }
  }
  
 
  //Return the number of words in source
  public int getWordCount() {
      return _totalWords;
  }
  

  //Return Number of unique words.
  public int getEntryCount() {
      return _wordFrequency.size();
  }
  
  //Store the words and their frequency in array
  public void getWordFrequency(ArrayList<String> out_words,
          ArrayList<Integer> out_counts) {
      //Sort entries in Array by frequency
      ArrayList<Map.Entry<String, Int>> entries =
              new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());
      Collections.sort(entries, new CompareFrequency());
      
      for (Map.Entry<String, Int> ent : entries) {
          out_words.add(ent.getKey());
          out_counts.add(ent.getValue().value);
      }
  }
  
  //Return array of unique words in order
  public String[] getWords(SortOrder sortBy) {
      String[] result = new String[_wordFrequency.size()];
      ArrayList<Map.Entry<String, Int>> entries =
              new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());
      if (sortBy == SortOrder.ALPHABETICALLY) {
          Collections.sort(entries, SORT_ALPHABETICALLY);
      } else {
          Collections.sort(entries, SORT_BY_FREQUENCY);
      }
      
      int i = 0;
      for (Map.Entry<String, Int> ent : entries) {
          result[i++] = ent.getKey();
      }
      return result;
  }
  
 //Return frequencies in order
  public int[] getFrequencies(SortOrder sortBy) {
      int[] result = new int[_wordFrequency.size()];
      ArrayList<Map.Entry<String, Int>> entries =
              new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());
      if (sortBy == SortOrder.ALPHABETICALLY) {
          Collections.sort(entries, SORT_ALPHABETICALLY);
      } else {
          Collections.sort(entries, SORT_BY_FREQUENCY);
      }
      
      int i = 0;
      for (Map.Entry<String, Int> ent : entries) {
          result[i++] = ent.getValue().value;
      }
      return result;
  }
}