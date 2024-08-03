package pingpong;
import java.io.*;
public class PingPong {
    private BuggedProgram bug;
    private DataOutputStream out;
    private int threadsNumber;
    public PingPong(DataOutputStream output, int threadsNumber) {
        this.out = output;
        this.threadsNumber = threadsNumber;
        this.bug = new BuggedProgram(output, threadsNumber);
    }
    public void doWork() {
        String newLine = System.getProperty("line.separator");
        try {
            out.writeBytes("Number Of Threads: " + this.threadsNumber + " Number Of Bugs: ");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        this.bug.doWork();
    }
    public static void main(String[] args) {
        File output = new File("output.txt");
        DataOutputStream out = null;
        try {
            FileOutputStream os = new FileOutputStream(output);
            out = new DataOutputStream(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        try {
            String newLine = System.getProperty("line.separator");
            out.writeBytes("In this file you will find the number of the bug appearances " +
                    "accordingly to the number of threads that the " +
                    "bugged program utilized with:" + newLine + newLine);
            out.writeBytes("Few Threads: " + newLine + newLine);
            PingPong fewThreads = new PingPong(out, 4);
            fewThreads.doWork();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
