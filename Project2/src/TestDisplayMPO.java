package src;

import java.util.*;

/** 
 * This file tests if the display results are correctly 
 * displaying the votes, statistics, and who won. 
 * @author Crystal Wen
 */
public class TestDisplayMPO {
    public static void main(String[] args) {
        ArrayList<String[]> ballots = new ArrayList<>();
    	ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();

        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
        
        candidates.add(new Candidate("Pike", "D", 3));
        candidates.add(new Candidate("Foster", "D", 3));
        candidates.add(new Candidate("Deutsch", "R", 0));
        candidates.add(new Candidate("Borg", "R", 2));
        candidates.add(new Candidate("Jones", "R", 1));
        candidates.add(new Candidate("Smith", "I", 1));

        demCandidates.add(new Candidate("Pike", "D", 3));
        demCandidates.add(new Candidate("Foster", "D", 3));

        repCandidates.add(new Candidate("Deutsch", "R", 0));
        repCandidates.add(new Candidate("Borg", "R", 2));
        repCandidates.add(new Candidate("Jones", "R", 1));
        
        indepCandidates.add(new Candidate("Smith", "I", 1));

        parties.add(new Party("D", 6, demCandidates));
        parties.add(new Party("R", 3, repCandidates));
        parties.add(new Party("I", 1, indepCandidates));

        int totalVotes = 10;
        int totalSeats = 2;

        candidates.get(0).setNumSeats(1);
        candidates.get(1).setNumSeats(1);
        parties.get(0).setNumAllocatedSeats(2);

        parties.get(0).getCandidates().get(0).setNumSeats(1);
        parties.get(0).getCandidates().get(1).setNumSeats(1);

        /*The expected results:
         * Winners: Pike and Foster
         * Candidates and their number of seats won, % of votes, and number of votes
         * Pike: 1 seat, 30%, 3 votes
         * Foster: 1 seat, 30%, 3 votes
         * Deutsch: 0 seats, 0%, 0 votes
         * Borg: 0 seats, 20%, 2 votes
         * Jones: 0 seats, 10%, 1 vote
         * Smith: 0 seats, 10%, 1 votes
         */
        MPO mpo = new MPO(totalVotes, totalSeats, parties, ballots, candidates);
        mpo.findWinners();
        mpo.displayResults("MPO");

    }
}
