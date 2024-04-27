package src;
import java.util.*;

/**
 * This file tests seat allocation when there is a tie for MV
 * @author Crystal Wen
 */
public class TestMVAllocateSeatsCoinToss {

    public static void main (String[] args) {
        ArrayList<String[]> ballots = new ArrayList<>();
    	ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();

        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();
        
        candidates.add(new Candidate("Pike", "D", 4));
        candidates.add(new Candidate("Foster", "D", 3));
        candidates.add(new Candidate("Deutsch", "R", 4));
        candidates.add(new Candidate("Borg", "R", 4));
        candidates.add(new Candidate("Jones", "R", 4));
        candidates.add(new Candidate("Smith", "I", 3));

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
        String[] b5 = {"","", "", "", "", "1"};
        String[] b6 = {"1", "", "", "1", "1"};
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

        parties.add(new Party("D", 6, demCandidates));
        parties.add(new Party("R", 3, repCandidates));
        parties.add(new Party("I", 1, indepCandidates));

        int totalVotes = 9;
        int totalSeats = 3;

        MV mv = new MV(totalVotes, totalSeats, parties, ballots, candidates);
        mv.allocateSeats();

        //Prints out candidate name and number of seats gained
        for(Candidate c : candidates) {
            System.out.println(c.getName() + " " + c.getNumSeats());
        }
    }
    
}
