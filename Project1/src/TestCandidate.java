package src;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This file tests Candidate class methods.
 * @author Fumisato Teranishi
 * @author Shunichi Sawamura
 */
class TestCandidate {
	
	@Test
	public void testCandidateInitialization() {
	   Candidate candidate = new Candidate("John Doe", "Independent", 1000);
	   assertEquals("John Doe", candidate.getName(), "Candidate name should be initialized correctly.");
	   assertEquals("Independent", candidate.getParty(), "Candidate party should be initialized correctly.");
	   assertEquals(1000, candidate.getNumVotes(), "Candidate votes should be initialized correctly.");
	}

	@Test
	public void testSetNumVotes() {
		Candidate candidate = new Candidate("John Doe", "Independent", 0);
		int numVotes = 1500;
		candidate.setNumVotes(numVotes);
		assertEquals(numVotes, candidate.getNumVotes(), "setNumVotes should update he candidate's vote count.");
	}

}
