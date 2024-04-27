package src;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;

/**
 * This file tests OPL class methods.
 * @author Crystal Wen
 * @author Shunichi Sawamura
 */
public class TestOPL {
	
    private ArrayList<Party> expected;
    private ArrayList<Party> actual;
    private ArrayList<Candidate> candidatesActual;
    private ArrayList<Candidate> candidatesExpected;

    private ArrayList<Candidate> demCandidates;
    private ArrayList<Candidate> repCandidates;
    private ArrayList<Candidate> indepCandidates;
    
    private int totalVotes;
    private int totalSeats;
    private FileReader fileReader;
    private BufferedReader br;
    private OPL opl;

    public void setDefault() {
    	expected = new ArrayList<>();
        actual = new ArrayList<>();
        candidatesActual = new ArrayList<>();
        candidatesExpected = new ArrayList<>();

        demCandidates = new ArrayList<>();
        repCandidates = new ArrayList<>();
        indepCandidates = new ArrayList<>();
        
        candidatesExpected.add(new Candidate("Pike", "Democratic", 2));
        candidatesExpected.add(new Candidate("Lucy", "Democratic", 1));
        candidatesExpected.add(new Candidate("Beiye", "Democratic", 0));
        candidatesExpected.add(new Candidate("Etta", "Republican", 2));
        candidatesExpected.add(new Candidate("Alawa", "Republican", 2));
        candidatesExpected.add(new Candidate("Sasha", "Independent1", 2));

        candidatesActual.add(new Candidate("Pike", "Democratic", 0));
        candidatesActual.add(new Candidate("Lucy", "Democratic", 0));
        candidatesActual.add(new Candidate("Beiye", "Democratic", 0));
        candidatesActual.add(new Candidate("Etta", "Republican", 0));
        candidatesActual.add(new Candidate("Alawa", "Republican", 0));
        candidatesActual.add(new Candidate("Sasha", "Independent1", 0));

        demCandidates.add(new Candidate("Pike", "Democratic", 0));
        demCandidates.add(new Candidate("Lucy", "Democratic", 0));
        demCandidates.add(new Candidate("Beiye", "Democratic", 0));

        repCandidates.add(new Candidate("Etta", "Republican", 0));
        repCandidates.add(new Candidate("Alawa", "Republican", 0));
        
        indepCandidates.add(new Candidate("Sasha", "Independent1", 0));

        expected.add(new Party("Democratic", 3, demCandidates));
        expected.add(new Party("Republican", 4, repCandidates));
        expected.add(new Party("Independent1", 2, indepCandidates));

        actual.add(new Party("Democratic", 0, demCandidates));
        actual.add(new Party("Republican", 0, repCandidates));
        actual.add(new Party("Independent1", 0, indepCandidates));
        
    }
    
    @Test
    public void testVoteCountingOPL() {    	
    	setDefault();
    	totalVotes = 9;
        totalSeats = 2;
        
        try {
            fileReader = new FileReader("testOPLVote.csv");
            br = new BufferedReader(fileReader);
            opl = new OPL(totalVotes, totalSeats, actual, br, candidatesActual);
            opl.voteCounting();
        // Handle the FileNotFoundException
        } catch (FileNotFoundException e) {
            fail("File not found: " + e.getMessage());
    	// Get error when reading a ballot file
    	} catch (IOException e) {
            fail("Error: " + e.getMessage());
        }
    	
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getNumVotes(), opl.parties.get(i).getNumVotes());
        }

        for(int i = 0; i < candidatesExpected.size(); i++) {
            assertEquals(candidatesExpected.get(i).getNumVotes(), opl.candidates.get(i).getNumVotes());
        }
    }

    @Test
    public void testFindWinners() {
    	//Assume the voteCounting and allocateSeats is already done and allocated seats for each party is determined.
    	ArrayList<Party> parties = new ArrayList<>();
    	ArrayList<Candidate> candidates = new ArrayList<>();
    	
    	ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
    	
        Candidate largestInDem = new Candidate("Pike", "Democratic", 2);
        Candidate largestInRep = new Candidate("Alawa", "Republican", 2);
    	candidates.add(largestInDem);
        candidates.add(new Candidate("Lucy", "Democratic", 1));
        candidates.add(new Candidate("Beiye", "Democratic", 0));
        candidates.add(new Candidate("Etta", "Republican", 1));
        candidates.add(largestInRep);
        candidates.add(new Candidate("Sasha", "Independent1", 2));
        
        demCandidates.add(largestInDem);
        demCandidates.add(new Candidate("Lucy", "Democratic", 1));
        demCandidates.add(new Candidate("Beiye", "Democratic", 0));

        repCandidates.add(new Candidate("Etta", "Republican", 1));
        repCandidates.add(largestInRep);
        
        indepCandidates.add(new Candidate("Sasha", "Independent1", 2));
        
        Party dem = new Party("Democratic", 3, demCandidates);
        dem.setNumAllocatedSeats(1);
        Party rep = new Party("Republican", 3, repCandidates);
        rep.setNumAllocatedSeats(1);
        Party ind = new Party("Independent1", 2, indepCandidates);
        ind.setNumAllocatedSeats(0);
        parties.add(dem);
        parties.add(rep);
        parties.add(ind);
        
        int _totalVotes = 8;
        int _totalSeats = 2;
        
        try {
            fileReader = new FileReader("testOPLVote.csv");
            br = new BufferedReader(fileReader);
            opl = new OPL(_totalVotes, _totalSeats, parties, br, candidates);
            opl.voteCounting();
        // Handle the FileNotFoundException
        } catch (FileNotFoundException e) {
            fail("File not found: " + e.getMessage());
    	// Get error when reading a ballot file
    	} catch (IOException e) {
            fail("Error: " + e.getMessage());
        }
        
        opl.findWinners();
        //check the candidates who have the largest vote in seat allocated party win 
        assertTrue("List should contain element 'Pike'", opl.winnerList.contains(largestInDem));
        assertTrue("List should contain element 'Alawa'", opl.winnerList.contains(largestInRep));
    }
    
    @Test 
    public void testFindWinnersByCoinToss() {
    	
    	setDefault();
    	totalVotes = 9;
        totalSeats = 1;
        
        try {
            fileReader = new FileReader("testOPLVote.csv");
            br = new BufferedReader(fileReader);
            opl = new OPL(totalVotes, totalSeats, actual, br, candidatesActual);
            opl.calculateQuota();
            opl.voteCounting();
            opl.allocateSeats();
        // Handle the FileNotFoundException
        } catch (FileNotFoundException e) {
            fail("File not found: " + e.getMessage());
    	// Get error when reading a ballot file
    	} catch (IOException e) {
            fail("Error: " + e.getMessage());
        }
        
        //FindWinners are always implemented after voteCounting
    	opl.findWinners();
   
    	//Check winner list has a single candidate determined by coin toss
        boolean containsOneCandidate = false;
        System.out.println(opl.parties.get(2).getNumAllocatedSeats());
        for (Candidate winner: opl.winnerList) {
            if (winner.getName().equals("Etta") || winner.getName().equals("Alawa")) {
            	containsOneCandidate = true;
                break;
            }
        }

        // Check that the list contains either value A or value B
        assertTrue("Winner List should contain either Etta or Alawa in Republican", containsOneCandidate);
	// Check the size of winner list is equal to the total seat number.
	assertEquals("Number of allocated seat is equal to the size of winner list",
        	totalSeats, opl.winnerList.size());

    }
}

