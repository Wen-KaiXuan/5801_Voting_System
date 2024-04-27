package src;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * This file tests Party class methods.
 * @author Fumisato Teranishi
 * @author Shunichi Sawamura
 */
class TestParty {
	
	@Test
	void testParty() {
		String partyName = "Democratic";
		int firstNumVotes = 0;
        ArrayList<Candidate> candidates = new ArrayList<>();

        candidates.add(new Candidate("Joe", partyName, 0));
        candidates.add(new Candidate("Sally", partyName, 0));
        candidates.add(new Candidate("Ahmed", partyName, 0));

		Party party = new Party(partyName, firstNumVotes, candidates);
		
		assertEquals(partyName, party.getName(), "Party name should be initialized correctly.");
		assertEquals(firstNumVotes, party.getNumVotes(), "Party numVote should be initialized correctly.");
		assertEquals(candidates, party.getCandidates(), "Party candidates should be initialized correctly.");
		assertEquals(0, party.getNumAllocatedSeats(), "Default party numAllocatedSeats is 0.");
	}


	@Test
	void testSetNumVotes() {
		String partyName = "Democratic";
		int firstNumVotes = 0;
        ArrayList<Candidate> candidates = new ArrayList<>();

        candidates.add(new Candidate("Joe", partyName, 0));
        candidates.add(new Candidate("Sally", partyName, 0));
        candidates.add(new Candidate("Ahmed", partyName, 0));

		Party party = new Party(partyName, firstNumVotes, candidates);
		
		int newNumVotes = 100;
		party.setNumVotes(newNumVotes);
		assertEquals(newNumVotes, party.getNumVotes(), "setNumVotes should update party numVotes.");
	}

	@Test
	void testSetCandidates() {
		String partyName = "Democratic";
		int firstNumVotes = 0;
        ArrayList<Candidate> candidates = new ArrayList<>();

		Party party = new Party(partyName, firstNumVotes, candidates);
		
        ArrayList<Candidate> newCandidates = new ArrayList<>();
        newCandidates.add(new Candidate("Joe", partyName, 0));
        newCandidates.add(new Candidate("Sally", partyName, 0));
        newCandidates.add(new Candidate("Ahmed", partyName, 0));
        party.setCandidates(newCandidates);
        
        for (int i=0; i<party.getCandidates().size(); i++) {
        	assertEquals(newCandidates.get(i), party.getCandidates().get(i), "setCandidates should update candidates list in party.");
        }
	}

	@Test
	void testSetNumAllocatedSeats() {
		String partyName = "Democratic";
		int firstNumVotes = 0;
        ArrayList<Candidate> candidates = new ArrayList<>();

		Party party = new Party(partyName, firstNumVotes, candidates);
		
		int numAllocatedSeats = 2;
		party.setNumAllocatedSeats(numAllocatedSeats);
		assertEquals(numAllocatedSeats, party.getNumAllocatedSeats(), "setNumAllocatedSeats should update the num of party allocated seats.");
	}

}
