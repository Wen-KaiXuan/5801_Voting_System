package src;
import java.util.ArrayList;

public class TestDisplayMV {
    public static void main(String[] args) {
        ArrayList<String[]> ballots = new ArrayList<>();
    	ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();

        ArrayList<Candidate> demCandidates = new ArrayList<>();
        ArrayList<Candidate> repCandidates = new ArrayList<>();
        ArrayList<Candidate> indepCandidates = new ArrayList<>();

        int totalVotes = 9;
        int totalSeats = 3;

        candidates.add(new Candidate("Pike", "D", 4));
        candidates.add(new Candidate("Foster", "D", 3));
        candidates.add(new Candidate("Deutsch", "R", 4));
        candidates.add(new Candidate("Borg", "R", 4));
        candidates.add(new Candidate("Jones", "R", 3));
        candidates.add(new Candidate("Smith", "I", 3));

        demCandidates.add(candidates.get(0));
        demCandidates.add(candidates.get(1));

        repCandidates.add(candidates.get(2));
        repCandidates.add(candidates.get(3));
        repCandidates.add(candidates.get(4));
        
        indepCandidates.add(candidates.get(5));

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

        parties.add(new Party("D", 7, demCandidates));
        parties.add(new Party("R", 11, repCandidates));
        parties.add(new Party("I", 3, indepCandidates));

        candidates.get(0).setNumSeats(1);
        candidates.get(2).setNumSeats(1);
        candidates.get(3).setNumSeats(1);
        parties.get(0).setNumAllocatedSeats(1);
        parties.get(1).setNumAllocatedSeats(2);

        /*The expected results:
         * Winners: Pike, Deutsch, and Borg
         * Candidates and their number of seats won, % of votes, and number of votes
         * Pike: 1 seat, 18.2%, 4 votes
         * Foster: 0 seat, 13.6%, 3 votes
         * Deutsch: 1 seats, 18.2%, 4 votes
         * Borg: 1 seats, 18.2%, 4 votes
         * Jones: 0 seats, 13.6%, 3 vote
         * Smith: 0 seats, 13.6%, 3 votes
         */
        MV mv = new MV(totalVotes, totalSeats, parties, ballots, candidates);
        mv.findWinners();
        mv.displayResults("MV");
    }
}
