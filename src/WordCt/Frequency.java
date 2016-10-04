package WordCt;

import java.io.*;
import java.util.*;
import java.text.*;

class Counter {
  int count = 1;
}

@SuppressWarnings(value = "unchecked")

public class Frequency {

  public static Reader reader;

  public static void main(String[] args) {
      try {
       reader = new BufferedReader(new FileReader(args[0]));
 search();
       reader.close();
      } catch (IOException error) {
       System.err.println(error);
      }
  }

  public static void search() {
      HashMap map = new HashMap();
      
      //Takes an input stream and parses it into "tokens", allowing the tokens to be read one at a time. 
      //The parsing process is controlled by a table and a number of flags that can be set to various states.
      // The stream tokenizer can recognize identifiers, numbers, quoted strings, and various comment styles. 
      try {
          StreamTokenizer streamToken = new StreamTokenizer(reader);
          streamToken.lowerCaseMode(true);
          streamToken.whitespaceChars( 0, 64 );
          streamToken.wordChars(65, 90);
          streamToken.whitespaceChars( 91, 96 );
          streamToken.wordChars(97, 122);
          streamToken.whitespaceChars( 123, 255 );
          int tType = streamToken.nextToken();
          while (tType != StreamTokenizer.TT_EOF) {
              if (tType == StreamTokenizer.TT_WORD) {
                  if (map.containsKey(streamToken.sval)) {
                      ((Counter)map.get(streamToken.sval)).count++;
                  } else {
                      map.put(streamToken.sval, new Counter());
                  }
              }
              tType = streamToken.nextToken();
          }
      } catch (IOException error) {
          System.err.println(error);
          return;
      }

      Collection entries = map.entrySet();
// flatten the entries set into a vector for sorting
Vector rev_wf = new Vector (entries);

      // Sort the vector according to its value
      Collections.sort(rev_wf, new Comparator() {
 public int compare(Object object1, Object object2) {
     // First sort by frequency
     int c = ((Counter)((Map.Entry)object2).getValue()).count - ((Counter)((Map.Entry)object1).getValue()).count;
     if (c == 0) { // Second sort by lexicographical order
   c = ((String)((Map.Entry)object2).getKey()).compareTo((String)((Map.Entry)object1).getKey());
     }
     return c;
 }
   }
);

      @SuppressWarnings("rawtypes")
	Iterator it = rev_wf.iterator();
@SuppressWarnings("rawtypes")
Map.Entry entry;
@SuppressWarnings("unused")
String word;
int count;
      while(it.hasNext()) {
          entry = (Map.Entry)it.next();
   word = ((String)entry.getKey());
   count = ((Counter)entry.getValue()).count;
      }
  }
}

