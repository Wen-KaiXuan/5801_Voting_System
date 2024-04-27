package src;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.ArrayList;

/**
 * This file tests Election and CPL class methods.
 * @author Crystal Wen
 * @author Shunichi Sawamura
 */
public class TestCPL {
    @Test
    public void testCalculateQuota() throws IOException {
        ArrayList<Party> parties = new ArrayList<>();
        int totalVotes = 10000;
        int totalSeats = 3;
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        
        CPL cpl = new CPL(totalVotes, totalSeats, parties, ballots);
        int actual = cpl.calculateQuota();
        assertEquals(3333, actual);

        totalVotes = 10;
        totalSeats = 5;

        cpl = new CPL(totalVotes, totalSeats, parties, ballots);
        actual = cpl.calculateQuota();
        assertEquals(2, actual);
    }

    @Test
    public void testVoteCounting() {
        ArrayList<Party> expected = new ArrayList<>();
        ArrayList<Party> actual = new ArrayList<>();

        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> waveCandidates = new ArrayList<>();
        ArrayList<Candidate> reformCandidates = new ArrayList<>();
        ArrayList<Candidate> greenCandidates =  new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();

        demCandidates.add(new Candidate("Joe", "Democratic", 0));
        demCandidates.add(new Candidate("Sally", "Democratic", 0));
        demCandidates.add(new Candidate("Ahmed", "Democratic", 0));

        repCandidates.add(new Candidate("Allen", "Republican", 0));
        repCandidates.add(new Candidate("Nikki", "Republican", 0));
        repCandidates.add(new Candidate("Taihui", "Republican", 0));

        waveCandidates.add(new Candidate("Sarah", "New Wave", 0));

        reformCandidates.add(new Candidate("Xinyue", "Reform", 0));
        reformCandidates.add(new Candidate("Nikita", "Reform", 0));

        greenCandidates.add(new Candidate("Bethany", "Green", 0));
        
        indepCandidates.add(new Candidate("Mike", "Independent", 0));

        expected.add(new Party("Democratic", 3, demCandidates));
        expected.add(new Party("Republican", 2, repCandidates));
        expected.add(new Party("New Wave", 0, waveCandidates));
        expected.add(new Party("Reform", 2, reformCandidates));
        expected.add(new Party("Green", 1, greenCandidates));
        expected.add(new Party("Independent", 1, indepCandidates));

        actual.add(new Party("Democratic", 0, demCandidates));
        actual.add(new Party("Republican", 0, repCandidates));
        actual.add(new Party("New Wave", 0, waveCandidates));
        actual.add(new Party("Reform", 0, reformCandidates));
        actual.add(new Party("Green", 0, greenCandidates));
        actual.add(new Party("Independent", 0, indepCandidates));

        int totalVotes = 9;
        int totalSeats = 3;

        ArrayList<String[]> ballots = new ArrayList<String[]>();
        
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

        CPL cpl = new CPL(totalVotes, totalSeats, actual, ballots);
        cpl.voteCounting();

        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getNumVotes(), cpl.parties.get(i).getNumVotes());
        }
        
    }

    @Test
    public void testAllocateSeats() {
       	//Assume the voteCounting is already done
    	ArrayList<Party> parties = new ArrayList<>();
    	
    	ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
    	
        demCandidates.add(new Candidate("Pike", "Democratic", 2));
        demCandidates.add(new Candidate("Lucy", "Democratic", 1));
        demCandidates.add(new Candidate("Beiye", "Democratic", 0));

        repCandidates.add(new Candidate("Etta", "Republican", 1));
        repCandidates.add(new Candidate("Alawa", "Republican", 2));
        
        indepCandidates.add(new Candidate("Sasha", "Independent1", 2));
        
        Party dem = new Party("Democratic", 3, demCandidates);
        Party rep = new Party("Republican", 3, repCandidates);
        Party ind = new Party("Independent1", 2, indepCandidates);
        parties.add(dem);
        parties.add(rep);
        parties.add(ind);
        
        int _totalVotes = 8;
        int _totalSeats = 2;
        
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        
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

        CPL cpl = new CPL(_totalVotes, _totalSeats, parties, ballots);
        cpl.calculateQuota();
        cpl.allocateSeats();
        //Check democratic gets 1 seat
        assertEquals(1, cpl.parties.get(0).getNumAllocatedSeats());
        //Check republican gets 1 seat
        assertEquals(1, cpl.parties.get(1).getNumAllocatedSeats());
        //Check independent1 gets no seat
        assertEquals(0, cpl.parties.get(2).getNumAllocatedSeats());

    }

    @Test
    public void testAllocateSeatsForTie() {
    	//if there is a seat left, but two parties have same remaining votes
    	//Assume the voteCounting is already done
    	ArrayList<Party> parties = new ArrayList<>();
    	
    	ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
    	
        demCandidates.add(new Candidate("Pike", "Democratic", 2));
        demCandidates.add(new Candidate("Lucy", "Democratic", 1));
        demCandidates.add(new Candidate("Beiye", "Democratic", 0));

        repCandidates.add(new Candidate("Etta", "Republican", 1));
        repCandidates.add(new Candidate("Alawa", "Republican", 2));
        
        indepCandidates.add(new Candidate("Sasha", "Independent1", 0));
        
        Party dem = new Party("Democratic", 3, demCandidates);
        Party rep = new Party("Republican", 3, repCandidates);
        Party ind = new Party("Independent1", 0, indepCandidates);
        parties.add(dem);
        parties.add(rep);
        parties.add(ind);
        
        int _totalVotes = 6;
        int _totalSeats = 3;
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        
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

        CPL cpl = new CPL(_totalVotes, _totalSeats, parties, ballots);
        cpl.calculateQuota();
        cpl.allocateSeats();
        
        int totalNumAllocatedSeats = 0;
        for (Party party: cpl.parties) { 
            totalNumAllocatedSeats += party.getNumAllocatedSeats();
        }
        //Check total allocated seats are 3
        assertEquals("Total allocated seats are same value as total seats in election",
                _totalSeats, totalNumAllocatedSeats);
        //Either Democratic or Republican gets 2 seats or 1 seat
        int democraticNumSeat = cpl.parties.get(0).getNumAllocatedSeats();
        assertTrue(democraticNumSeat == 1 || democraticNumSeat == 2);
        int republicanNumSeat = cpl.parties.get(0).getNumAllocatedSeats();
        assertTrue(republicanNumSeat == 1 || republicanNumSeat == 2);
    }
    
    
    @Test 
    public void testFindWinners(){
    	
	    //Assume vote counting is already done
    	ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
    	
        Candidate winner1 = new Candidate("Pike", "Democratic", 2);
        Candidate winner2 = new Candidate("Etta", "Republican", 2);
        Candidate winner3 = new Candidate("Alawa", "Republican", 2);
        
        demCandidates.add(winner1);
        demCandidates.add(new Candidate("Lucy", "Democratic", 1));
        demCandidates.add(new Candidate("Beiye", "Democratic", 0));

        repCandidates.add(winner2);
        repCandidates.add(winner3);
        
        indepCandidates.add(new Candidate("Sasha", "Independent1", 0));
        
        Party dem = new Party("Democratic", 3, demCandidates);
        Party rep = new Party("Republican", 4, repCandidates);
        Party ind = new Party("Independent1", 0, indepCandidates);
        parties.add(dem);
        parties.add(rep);
        parties.add(ind);
        
        ArrayList<Candidate> winners = new ArrayList<>();
        winners.add(winner1);
        winners.add(winner2);
        winners.add(winner3);
        
        int _totalVotes = 7;
        int _totalSeats = 3;

        ArrayList<String[]> ballots = new ArrayList<String[]>();
        
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
        
        CPL cpl = new CPL(_totalVotes, _totalSeats, parties, ballots);
        cpl.calculateQuota();
        cpl.allocateSeats();
        cpl.findWinners();
        
        assertEquals("Number of allocated seat is equal to the size of winner list",
                _totalSeats, cpl.winnerList.size());
        assertEquals("Winners are saved successfully", winners, cpl.winnerList);
    }
}
