package src;
import java.util.*;

/**
 * This file tests seat allocation when there is a tie for MPO
 * @author Crystal Wen
 */
public class TestMPOAllocateSeatsCoinToss {
    public static void main(String[] args) {
        ArrayList<String[]> ballots = new ArrayList<>();
    	ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();

        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
        
        candidates.add(new Candidate("Pike", "D", 3));
        candidates.add(new Candidate("Foster", "D", 2));
        candidates.add(new Candidate("Deutsch", "R", 0));
        candidates.add(new Candidate("Borg", "R", 2));
        candidates.add(new Candidate("Jones", "R", 1));
        candidates.add(new Candidate("Smith", "I", 1));

        demCandidates.add(new Candidate("Pike", "D", 3));
        demCandidates.add(new Candidate("Foster", "D", 2));

        repCandidates.add(new Candidate("Deutsch", "R", 0));
        repCandidates.add(new Candidate("Borg", "R", 2));
        repCandidates.add(new Candidate("Jones", "R", 1));
        
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

        parties.add(new Party("D", 5, demCandidates));
        parties.add(new Party("R", 3, repCandidates));
        parties.add(new Party("I", 1, indepCandidates));

        int totalVotes = 9;
        int totalSeats = 2;

        MPO mpo = new MPO(totalVotes, totalSeats, parties, ballots, candidates);
        mpo.allocateSeats();

        //Prints out candidate name and number of seats gained
        for(Candidate c : candidates) {
            System.out.println(c.getName() + " " + c.getNumSeats());
        }
    }
}
