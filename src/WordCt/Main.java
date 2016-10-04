package WordCt;
import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] unused) {
   
      
      try {
          //Get file to analyze   
          String fileName = "BraveNewWorld.txt";
          File inputFile = new File(fileName);
          System.out.println("File analyzed: " + fileName);
         
          //Supply file to WordCounter.
          WordCount counter = new WordCount();
          counter.countWords(inputFile);
          
          //Get the results.
          String[] words   = counter.getWords(WordCount.SortOrder.BY_FREQUENCY);
          int[] frequency = counter.getFrequencies(WordCount.SortOrder.BY_FREQUENCY);
          
          //Write the results to text file
          int n = counter.getEntryCount();{
          FileWriter outputFileWriter = new FileWriter("result.txt");
          for (int i=0; i<n; i++) {
          outputFileWriter.write(frequency[i] + " " + words[i]+"\r\n");
          }
          outputFileWriter.write("\r\nTotal number of words: " + counter.getWordCount());
          outputFileWriter.write("\r\nTotal number of unique words: " + n);
          outputFileWriter.close();     
         }
          System.out.println("긍정 단어 수: " + counter.getWordCount());
          System.out.println("부정 단어 수: " + n);
          } catch (IOException error) {
            System.out.println(error);
      }
  }
}