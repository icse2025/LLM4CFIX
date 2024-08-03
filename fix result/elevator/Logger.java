package elevator;
import java.io.*;
import java.text.*;
import java.util.*;
public class Logger {
   private String preFix = "c:\\logs\\";
   private String lineSeparator;
   private String fileName;
   private BufferedWriter out;
   private File file;
   public Logger(String fileName){
      lineSeparator = System.getProperty("line.separator");
      this.fileName = preFix +  fileName + ".txt";
   }
   public  void write(String message){
   }
   public void close(){
   }
}
