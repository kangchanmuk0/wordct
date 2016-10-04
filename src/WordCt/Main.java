package WordCt;
import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] unused) {
   
      
      try {
          //Get file to analyze   
          String fileName = "TextTest.txt";
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
          FileWriter outputFileWriter = new FileWriter("result1.txt");
          for (int i=0; i<n; i++) {
          outputFileWriter.write(frequency[i] + " " + words[i]+"\r\n");
          }
          outputFileWriter.write("\r\n긍정단어 개수: " + counter.getWordCount());
          outputFileWriter.write("\r\n총 사용 단어 수: " + n);
          outputFileWriter.close();     
         }
          System.out.println("긍정 단어 수: " + counter.getWordCount()); //총 단어 수
          System.out.println("총 사용 단어 수: " + n); // 텍스트파일에서 사용된 단어수
          } catch (IOException error) {
            System.out.println(error);
      }
  }
}