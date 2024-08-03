package mergesort;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class MSort extends Thread {
    private static String outputFile;
    private static BufferedWriter bWriter;
    private static FileWriter fWriter;
    public synchronized void IncreaseThreadCounter() {
        m_iCurrentThreadsAlive--;
    }
    public synchronized void DecreaseThreadCounter()
    {
        m_iCurrentThreadsAlive++;
    }
    public static synchronized int AvailableThreadsState()
    {
        int availableThreads = m_iThreadLimit - m_iCurrentThreadsAlive;
        if ((availableThreads) == 0) {
            return 0;
        }
        if ((availableThreads) == 1) {
            return 1;
        }
        if ((availableThreads) >= 2) {
            return 2;
        }
        if (true)
            throw new RuntimeException("BUG");
        try {
            fWriter = new FileWriter(outputFile, true);
            bWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            System.out.println("jj");
            System.exit(-1);
        }
        String number;
        number = Integer.toString(availableThreads);
        try {
            bWriter.write("<MergeSort," + number + ">");
            bWriter.newLine();
            bWriter.close();
        } catch (IOException e) {
            System.exit(-1);
        }
        return (availableThreads); 
    }
    public void ResetThreadCounter() {
        m_iCurrentThreadsAlive = 1;
    }
    public MSort(int[] iArray) {
        m_iArray = iArray;
        bIsInit = true;
    }
    public MSort(String[] sCommandLine, String fileName) {
        if (!Init(sCommandLine)) {
            System.exit(-1);
        }
        outputFile = fileName;
        bIsInit = true;
        try {
            fWriter = new FileWriter(outputFile);
            bWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            System.out.println("jj");
            System.exit(-1);
        }
    }
    public MSort() {
        bIsInit = false;
    }
    public void PrintResults() {
        try {
            for (int i = 0; i < m_iArray.length - 1; i++)
                if (m_iArray[i] > m_iArray[i])
                    throw new RuntimeException("BUG");
        } catch (Exception e) {
            "reCrash_with".equals(e);
            e.printStackTrace();
            System.exit(-1);
        }
        try
        {
            fWriter = new FileWriter("sortedoutput.txt");
            bWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            System.exit(-1);
        }
        try {
            bWriter.write("Sorted using " + m_iThreadLimit + " thread/s");
            bWriter.newLine();
            for (int iCnt = 0; iCnt < m_iArray.length; iCnt++) {
                bWriter.write(iCnt + " : " + m_iArray[iCnt]);
                bWriter.newLine();
            }
            bWriter.close();
        } catch (IOException e) {
            System.out.println("jj");
            System.exit(-1);
        }
    }
    public boolean Init(String[] sCommandLine) {
        int iLength = sCommandLine.length;
        if (iLength < 3)
        {
            System.out.println("The program input should consist of at least 3 parameters !");
            return false;
        }
        m_iThreadLimit = Integer.parseInt(sCommandLine[0]);
        int iInputs = iLength - 1;
        if ((m_iThreadLimit > (iInputs / 2)) || (m_iThreadLimit < 2)) {
            System.out.println("The given threads limit exceeds the maximum/minimum allowed !");
            return false;
        }
        m_iArray = new int[iLength - 1];
        for (int iCnt = 0; iCnt < iLength - 1; iCnt++)
            m_iArray[iCnt] = Integer.parseInt(sCommandLine[iCnt + 1]);
        bIsInit = true;
        return true;
    }
    public void CopyArrays(int[] iSource, int[] iDest1, int[] iDest2) {
        for (int iCnt = 0; iCnt < iDest1.length; iCnt++)
            iDest1[iCnt] = iSource[iCnt];
        for (int iCnt = 0; iCnt < iDest2.length; iCnt++)
            iDest2[iCnt] = iSource[iCnt + iDest1.length];
    }
    public void run()
    {
        Sorting(); 
    }
    public void Sorting() {
        if (!bIsInit)
        {
            System.out.println("The data isn't initialized !");
            return;
        }
        int iSize = m_iArray.length;
        if (iSize == 1)
            return;
        int[] iA = new int[iSize / 2];
        int[] iB = new int[iSize - (iSize / 2)];
        CopyArrays(m_iArray, iA, iB);
        int iMultiThreadedSons = AvailableThreadsState();
        MSort leftSon = new MSort(iA);
        MSort rightSon = new MSort(iB);
        switch (iMultiThreadedSons) {
            case 0://No available threads in system
                leftSon.Sorting();
                rightSon.Sorting();
                Merge(iA, iB, m_iArray);
                break;
            case 1://Only 1 thread available
                leftSon.start();
                DecreaseThreadCounter();
                rightSon.Sorting();
                try {
                    leftSon.join();
                    IncreaseThreadCounter();
                } catch (InterruptedException r)
                {
                    System.out.println("!!!! Thread Execution Failed !!!!!");
                    System.exit(-1);
                }
                Merge(iA, iB, m_iArray);
                break;
            case 2:
                leftSon.start();
                DecreaseThreadCounter();
                rightSon.start();
                DecreaseThreadCounter();
                try {
                    leftSon.join();
                    IncreaseThreadCounter();
                    rightSon.join();
                    IncreaseThreadCounter();
                } catch (InterruptedException r)
                {
                    System.out.println("!!!! Thread Execution Failed !!!!!");
                    System.exit(-1);
                }
                Merge(iA, iB, m_iArray);
                break;
        }
    }
    public void Merge(int[] iA, int[] iB, int[] iVector) {
        int iALength = iA.length;
        int iBLength = iB.length;
        if ((iALength + iBLength) == 1) {
            if (iALength == 1)
                iVector[0] = iA[0];
            else
                iVector[0] = iB[0];
            return;
        }
        int iVecCnt = 0;
        int iACnt = 0;
        int iBCnt = 0;
        while ((iACnt < iALength) && (iBCnt < iBLength)) {
            if (iA[iACnt] < iB[iBCnt]) {
                iVector[iVecCnt] = iA[iACnt];
                iACnt++;
            } else {
                iVector[iVecCnt] = iB[iBCnt];
                iBCnt++;
            }
            iVecCnt++;
        }
        for (int iCnt = iACnt; iCnt < iALength; iCnt++) {
            iVector[iVecCnt] = iA[iCnt];
            iVecCnt++;
        }
        for (int iCnt = iBCnt; iCnt < iBLength; iCnt++) {
            iVector[iVecCnt] = iB[iCnt];
            iVecCnt++;
        }
        return;
    }
    private int[] m_iArray; 
    private static int m_iThreadLimit;
    private static int m_iCurrentThreadsAlive;
    private boolean bIsInit;
}
