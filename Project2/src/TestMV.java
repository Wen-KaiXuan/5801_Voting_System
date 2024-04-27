package src;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.*;

/**
 * This file tests the MV class methods.
 * @author Crystal Wen
 */
public class TestMV {
    private ArrayList<Party> expected;
    private ArrayList<Party> actual;
    private ArrayList<Candidate> candidatesActual;
    private ArrayList<Candidate> candidatesExpected;

    private ArrayList<Candidate> demCandidates;
    private ArrayList<Candidate> repCandidates;
    private ArrayList<Candidate> indepCandidates;
    
    private int totalVotes;
    private int totalSeats;
    private ArrayList<String[]> ballots;
    private MV mv;

    public void setDefault() {
        ballots = new ArrayList<>();
    	expected = new ArrayList<>();
        actual = new ArrayList<>();
        candidatesActual = new ArrayList<>();
        candidatesExpected = new ArrayList<>();

        demCandidates = new ArrayList<>();
        repCandidates = new ArrayList<>();
        indepCandidates = new ArrayList<>();
        
        candidatesExpected.add(new Candidate("Pike", "D", 4));
        candidatesExpected.add(new Candidate("Foster", "D", 3));
        candidatesExpected.add(new Candidate("Deutsch", "R", 4));
        candidatesExpected.add(new Candidate("Borg", "R", 4));
        candidatesExpected.add(new Candidate("Jones", "R", 3));
        candidatesExpected.add(new Candidate("Smith", "I", 3));

        candidatesActual.add(new Candidate("Pike", "D", 0));
        candidatesActual.add(new Candidate("Foster", "D", 0));
        candidatesActual.add(new Candidate("Deutsch", "R", 0));
        candidatesActual.add(new Candidate("Borg", "R", 0));
        candidatesActual.add(new Candidate("Jones", "R", 0));
        candidatesActual.add(new Candidate("Smith", "I", 0));

        demCandidates.add(new Candidate("Pike", "D", 0));
        demCandidates.add(new Candidate("Foster", "D", 0));

        repCandidates.add(new Candidate("Deutsch", "R", 0));
        repCandidates.add(new Candidate("Borg", "R", 0));
        repCandidates.add(new Candidate("Jones", "R", 0));
        
        indepCandidates.add(new Candidate("Smith", "I", 0));
        
        String[] b1 = {"1", "1", "1"};
        String[] b2 = {"1", "", "", "1", "", "1"};
        String[] b3 = {"", "1", "", "", "1"};
        String[] b4 = {"", "", "1", "", "1"};
        String[] b5 = {"", "", "", "", "", "1"};
        String[] b6 = {"1", "", "", "1"};
        String[] b7 = {"", "", "1", "1"};
        String[] b8 = {"1", "", "1", "1"};
        String[] b9 = {"", "1", "", "", "1", "1"};

        ballots.add(b1);
        ballots.add(b2);
        ballots.add(b3);
        ballots.add(b4);
        ballots.add(b5);
        ballots.add(b6);
        ballots.add(b7);
        ballots.add(b8);
        ballots.add(b9);

        expected.add(new Party("D", 7, demCandidates));
        expected.add(new Party("R", 11, repCandidates));
        expected.add(new Party("I", 3, indepCandidates));

        actual.add(new Party("D", 0, demCandidates));
        actual.add(new Party("R", 0, repCandidates));
        actual.add(new Party("I", 0, indepCandidates));

        candidatesExpected.get(0).setNumSeats(1);
        candidatesExpected.get(2).setNumSeats(1);
        candidatesExpected.get(3).setNumSeats(1);
        expected.get(0).setNumAllocatedSeats(1);
        expected.get(1).setNumAllocatedSeats(2);
    }

    @Test
    public void testVoteCountingMPO() {    	
    	setDefault();
    	totalVotes = 9;
        totalSeats = 3;
        
        mv = new MV(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mv.voteCounting();
    	
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getNumVotes(), actual.get(i).getNumVotes());
        }

        for(int i = 0; i < candidatesExpected.size(); i++) {
            assertEquals(candidatesExpected.get(i).getNumVotes(), candidatesActual.get(i).getNumVotes());
        }
    }
    
    @Test
    public void testAllocateSeats() {
        setDefault();
        totalVotes = 9;
        totalSeats = 3;

        mv = new MV(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mv.voteCounting();
        mv.allocateSeats();
        
        assertEquals(candidatesExpected.get(0).getNumSeats(), candidatesActual.get(0).getNumSeats());
        assertEquals(candidatesExpected.get(2).getNumSeats(), candidatesActual.get(1).getNumSeats());
        assertEquals(candidatesExpected.get(3).getNumSeats(), candidatesActual.get(2).getNumSeats());
        assertEquals(expected.get(0).getNumAllocatedSeats(), actual.get(0).getNumAllocatedSeats());
        assertEquals(expected.get(1).getNumAllocatedSeats(), actual.get(1).getNumAllocatedSeats());
    }

    @Test
    public void findWinners() {
        setDefault();
        totalVotes = 9;
        totalSeats = 3;

        mv = new MV(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mv.voteCounting();
        mv.allocateSeats();
        mv.findWinners();

        assertEquals(3, mv.winnerList.size());
        assertEquals("Pike", mv.winnerList.get(0).getName());
        assertEquals("Deutsch", mv.winnerList.get(1).getName());
        assertEquals("Borg", mv.winnerList.get(2).getName());
    }
}