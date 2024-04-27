package src;

import java.util.ArrayList;

/**
 * The CPL class inherits the Election class to run a CPL type election.
 * @author Crystal Wen
 */
public class CPL extends Election{
    public CPL(int totalVotes, int totalSeats, ArrayList<Party> parties, ArrayList<String[]> ballots){
        super(totalVotes, totalSeats, parties, ballots);
    }


    /**
     * Counts the votes for each party.
     * This will not work if the total number of votes is less than or equal to 0.
     */
    @Override
    public void voteCounting() {
        // Reads through each ballot and counts each parties ballots.
        for (int i = 0; i < totalVotes; i++) {
            int index = ballots.get(i).length - 1;
            parties.get(index).setNumVotes(parties.get(index).getNumVotes() + 1);
        }
    }


    /**
     * Finds the winner based on the party with the most seats.
     */
    @Override
    public void findWinners() {
        // Each party clarifies the number of allocated seats based on the voting results and candidate information.
        for (Party party: parties) {
            int thisPartySeats = party.getNumAllocatedSeats();
            ArrayList<Candidate> thisPartyCandidates = party.getCandidates();
            int maxAllocation = Math.min(thisPartySeats, thisPartyCandidates.size());

            // Give the seat to candidate from the
            for (int i=0; i<maxAllocation; i++) {
                this.winnerList.add(thisPartyCandidates.get(i));
            }
        }
    }


}
