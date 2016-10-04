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
      wordScanner.useDelimiter("[^가-하]+"); // 긍정단어 들어갈 자리
      //여기에 포함되는 어미가 count 된다.
      //여기에 모든 긍정 단어 넣기.
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
      wordScanner.useDelimiter("[^가-하]+"); //부정 단어 들어갈 자리
      // 여기들어간 단어를 제외하고 count된다.
      //여기다가 부정어를 싹다 집어 넣으면 결과값이 긍정어 갯수가 count 된다.
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