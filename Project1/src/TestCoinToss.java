package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This file tests CoinToss method in CPL and OPL class.
 * @author Crystal Wen
 */
public class TestCoinToss {
    public static void testCoinTossCPL() throws FileNotFoundException{
        ArrayList<Party> parties = new ArrayList<>();
        int totalVotes = 10000;
        int totalSeats = 3;
        FileReader fileReader = new FileReader("testCPLVote.csv");
        BufferedReader br = new BufferedReader(fileReader);
        int c1 = 0, c2 = 0;

        ArrayList<Party> testWinner = new ArrayList<>();
        testWinner.add(new Party("Democratic", 5, new ArrayList<>()));
        testWinner.add(new Party("Republican", 5, new ArrayList<>()));

        CPL cpl = new CPL(totalVotes, totalSeats, parties, br);
        Party winner;

        for(int i = 0; i < 1000; i++) {
            winner = cpl.coinToss(testWinner);
            if(winner.getName().equals("Democratic")) c1++;
            else c2++;
        }
        System.out.println("Round 1");
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2 + "\n");

        c1 = 0;
        c2 = 0;
        int c3 = 0;
        testWinner.add(new Party("Reform", 5, new ArrayList<>()));
        
        for(int i = 0; i < 1000; i++) {
            winner = cpl.coinToss(testWinner);
            if(winner.getName().equals("Democratic")) c1++;
            else if (winner.getName().equals("Republican")) c2++;
            else c3++;
        }
        System.out.println("Round 2");
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println("c3: " + c3);
    }

    public static void testCoinTossOPL() throws FileNotFoundException {
        int totalVotes = 10000;
        int totalSeats = 3;
        ArrayList<Party> parties = new ArrayList<>();
        FileReader fileReader = new FileReader("testOPLVote.csv");
        BufferedReader br = new BufferedReader(fileReader);
        ArrayList<Candidate> candidates = new ArrayList<>();
        int c1 = 0, c2 = 0;

        ArrayList<Candidate> testWinner = new ArrayList<>();
        testWinner.add(new Candidate("Sara", "Democratic", 10));
        testWinner.add(new Candidate("Bob Mc'Bobson", "Republican", 10));

        OPL opl = new OPL(totalVotes, totalSeats, parties, br, candidates);
        Candidate winner;

        for(int i = 0; i < 1000; i++) {
            winner = opl.coinTossOPL(testWinner);
            if(winner.getName().equals("Sara")) c1++;
            else c2++;
        }
        System.out.println("Round 1");
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2 + "\n");

        c1 = 0; 
        c2 = 0;
        int c3 = 0, c4 = 0;
        testWinner.add(new Candidate("Steve Mc'Steveson", "Green", 10));
        testWinner.add(new Candidate("Renee", "Independent", 10));

        for(int i = 0; i < 1000; i++) {
            winner = opl.coinTossOPL(testWinner);
            if(winner.getName().equals("Sara")) c1++;
            else if(winner.getName().equals("Bob Mc'Bobson")) c2++;
            else if(winner.getName().equals("Steve Mc'Steveson")) c3++;
            else c4++;
        }
        System.out.println("Round 2");
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println("c3: " + c3);
        System.out.println("c4: " + c4);
    }

    public static void main (String[] args) throws FileNotFoundException{
        testCoinTossCPL();

        System.out.println("\n\nTesting coinTossOPL");
        testCoinTossOPL();
    }    
}
