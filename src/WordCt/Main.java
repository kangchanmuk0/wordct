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
          outputFileWriter.write("\r\n�����ܾ� ����: " + counter.getWordCount());
          outputFileWriter.write("\r\n�� ��� �ܾ� ��: " + n);
          outputFileWriter.close();     
         }
          System.out.println("���� �ܾ� ��: " + counter.getWordCount()); //�� �ܾ� ��
          System.out.println("�� ��� �ܾ� ��: " + n); // �ؽ�Ʈ���Ͽ��� ���� �ܾ��
          } catch (IOException error) {
            System.out.println(error);
      }
  }
}