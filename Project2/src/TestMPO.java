package src;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

/**
 * This file tests the MPO class methods.
 * @author Crystal Wen
 */
public class TestMPO {

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
    private MPO mpo;
    
    public void setDefault() {
        ballots = new ArrayList<>();
    	expected = new ArrayList<>();
        actual = new ArrayList<>();
        candidatesActual = new ArrayList<>();
        candidatesExpected = new ArrayList<>();

        demCandidates = new ArrayList<>();
        repCandidates = new ArrayList<>();
        indepCandidates = new ArrayList<>();
        
        candidatesExpected.add(new Candidate("Pike", "D", 3));
        candidatesExpected.add(new Candidate("Foster", "D", 3));
        candidatesExpected.add(new Candidate("Deutsch", "R", 0));
        candidatesExpected.add(new Candidate("Borg", "R", 2));
        candidatesExpected.add(new Candidate("Jones", "R", 1));
        candidatesExpected.add(new Candidate("Smith", "I", 1));

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
        
        String[] b1 = {"1"};
        String[] b2 = {"", "1"};
        String[] b4 = {"", "", "", "1"};
        String[] b5 = {"", "", "", "", "1"};
        String[] b6 = {"","", "", "", "", "1"};
    
        ballots.add(b1);
        ballots.add(b1);
        ballots.add(b2);
        ballots.add(b5);
        ballots.add(b6);
        ballots.add(b4);
        ballots.add(b4);
        ballots.add(b1);
        ballots.add(b2);
        ballots.add(b2);

        expected.add(new Party("D", 6, demCandidates));
        expected.add(new Party("R", 3, repCandidates));
        expected.add(new Party("I", 1, indepCandidates));

        actual.add(new Party("D", 0, demCandidates));
        actual.add(new Party("R", 0, repCandidates));
        actual.add(new Party("I", 0, indepCandidates));

        candidatesExpected.get(0).setNumSeats(1);
        candidatesExpected.get(1).setNumSeats(1);
        expected.get(0).setNumAllocatedSeats(2);
    }

    @Test
    public void testVoteCountingMPO() {    	
    	setDefault();
    	totalVotes = 10;
        totalSeats = 2;
        
        mpo = new MPO(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mpo.voteCounting();
    	
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
        totalVotes = 10;
        totalSeats = 2;

        mpo = new MPO(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mpo.voteCounting();
        mpo.allocateSeats();

        assertEquals(candidatesExpected.get(0).getNumSeats(), candidatesActual.get(0).getNumSeats());
        assertEquals(candidatesExpected.get(1).getNumSeats(), candidatesActual.get(1).getNumSeats());
        assertEquals(expected.get(0).getNumAllocatedSeats(), actual.get(0).getNumAllocatedSeats());
    }

    @Test
    public void findWinners() {
        setDefault();
        totalVotes = 10;
        totalSeats = 2;

        mpo = new MPO(totalVotes, totalSeats, actual, ballots, candidatesActual);
        mpo.voteCounting();
        mpo.allocateSeats();
        mpo.findWinners();

        assertEquals(2, mpo.winnerList.size());
        assertEquals("Pike", mpo.winnerList.get(0).getName());
        assertEquals("Foster", mpo.winnerList.get(1).getName());
    }
}
