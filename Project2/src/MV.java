package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The MV class inherits the Election class to run a MPO type election.
 * 
 * @author Lysong
 */
public class MV extends Election {
    ArrayList<Candidate> candidates;

    /**
     * The constructor for MPO. It initializes all variables when a user calls it.
     * 
     * @param totalVotes total number of votes in election
     * @param totalSeats total number of allocated seaats in election
     * @param parties    a list of parties in election
     * @param ballots    array of array list of strings storing ballots info
     * @param candidates a list of candidates in election
     */
    public MV(int totalVotes, int totalSeats, ArrayList<Party> parties,
            ArrayList<String[]> ballots, ArrayList<Candidate> candidates) {
        super(totalVotes, totalSeats, parties, ballots);
        this.candidates = candidates;
    }

    /**
     * Counts the votes each candidate and their respective party has.
     */
    @Override
    public void voteCounting() {
        // Iterate though all saved ballots
        for (String[] ballot : ballots) {
            // Check who got a ballot
            for (int voteIndex = 0; voteIndex < ballot.length; voteIndex++) {
                if (ballot[voteIndex].equals("1")) {
                    // Increment the corresponding candidate's vote count
                    candidates.get(voteIndex).setNumVotes(candidates.get(voteIndex).getNumVotes() + 1);
                }
            }
        }
        /*
         * Finds the party of each respective candidate and sums up the votes of the
         * candiates
         * in their respective party to determine the number of votes that their party
         * gets.
         */
        for (Candidate candidate : candidates) {
            int candidateVotes = candidate.getNumVotes();
            int partyIndex = -5;
            // Finds the party index in party arrays for each candidate.
            for (int i = 0; i < parties.size(); i++) {
                if (parties.get(i).getName().equals(candidate.getParty())) {
                    partyIndex = i;
                }
            }
            int canIndex = -5;
            // Finds the candidate index in candidate arrays in Party object for each
            // candidate.
            for (int i = 0; i < parties.get(partyIndex).getCandidates().size(); i++) {
                if (parties.get(partyIndex).getCandidates().get(i).getName().equals(candidate.getName())) {
                    canIndex = i;
                }
            }
            parties.get(partyIndex).getCandidates().get(canIndex).setNumVotes(candidateVotes);
            parties.get(partyIndex).setNumVotes(parties.get(partyIndex).getNumVotes() + candidateVotes);
        }

    }

    /**
     * Allocate seats to each candidate.
     */
    @Override
    public void allocateSeats() {
        int currIndex = 0;
        int seatLeft = totalSeats;
        Collections.sort(candidates, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate c1, Candidate c2) {
                return c2.getNumVotes() - c1.getNumVotes();
            }
        });

        // Allocates seats in order starting from the candidate with the most votes.
        while (seatLeft > 0) {
            int currVoteNum = candidates.get(currIndex).getNumVotes();
            List<Candidate> tied = candidates.stream().filter(c -> c.getNumVotes() == currVoteNum)
                    .collect(Collectors.toList());

            // Checks if there are more than one Candidate that tied.
            if (seatLeft >= tied.size()) {
                // There are an equal amount or more seats than the amount of candidates tied.
                for (Candidate chosen : tied) {
                    int i = candidates.indexOf(chosen);
                    candidates.get(i).setNumSeats(1);
                    seatLeft--;
                }
            } else {
                // There are less seats than the amount of candidates tied, thus needing a coin
                // toss.
                while (seatLeft > 0) {
                    int index = coinToss(tied.size());
                    Candidate chosen = tied.get(index);
                    int i = candidates.indexOf(chosen);
                    candidates.get(i).setNumSeats(1);
                    tied.remove(chosen);
                    seatLeft--;
                }
            }

            currIndex += tied.size();
        }

        // Loops through each party to update the number of seats won by each candidate.
        for (Party party : parties) {
            // Loops through each party's candidates to update the number of seats won by
            // each of them.
            for (Candidate candidate : party.getCandidates()) {
                for (Candidate c : candidates) {
                    // If the canadidate info is matched, update
                    if (c.getName().equals(candidate.getName())) {
                        int seats = c.getNumSeats();
                        candidate.setNumSeats(seats);
                        party.setNumAllocatedSeats(party.getNumAllocatedSeats() + seats);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Finds the candidates that won seats.
     */
    @Override
    public void findWinners() {
        // Cycle through all of the candidates to see who is a winner.
        for (Candidate c : candidates) {
            // If a candidate won a seat, the candidate is added to the winner list.
            if (c.getNumSeats() == 1) {
                this.winnerList.add(c);
            }
        }
    }

}
