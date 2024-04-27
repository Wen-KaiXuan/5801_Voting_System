package src;

import java.util.ArrayList;

/**
 * The OPL class that inherits from the Election class and runs an OPL type election.
 * @author Crystal Wen 
 * @author Shunichi Sawamura
*/
public class OPL extends Election{
    public ArrayList<Candidate> candidates; 

    /**
     * The constructor for OPL. It initializes all variables when a user calls it.
     * @param totalVotes total number of votes in election
     * @param totalSeats total number of allocated seaats in election
     * @param parties    a list of parties in election
     * @param ballots    array of array list of strings storing ballots info
     * @param candidates a list of candidates in election
     */
    public OPL(int totalVotes, int totalSeats, ArrayList<Party> parties, ArrayList<String[]> ballots, ArrayList<Candidate> candidates){
        super(totalVotes, totalSeats, parties, ballots);
        winnerList = new ArrayList<>();
        this.candidates = candidates;
    }

    /**
     * Counts the votes each candidate and their respective party has.
     */
    @Override
    public void voteCounting() {
        //Sums up the number of votes for each candidate.
        for (int i = 0; i < totalVotes; i++) {
            int index = ballots.get(i).length - 1;
            candidates.get(index).setNumVotes(candidates.get(index).getNumVotes() + 1);
        }
    
    
        /* Finds the party of each respective candidate and sums up the votes of the candiates
        in their respective party to determine the number of votes that their party gets. */ 
        for (Candidate candidate:candidates) {
            int candidateVotes = candidate.getNumVotes();
            int partyIndex = -5;
            // Finds the party index in party arrays for each candidate. 
            for (int i=0; i<parties.size(); i++) {
                if (parties.get(i).getName().equals(candidate.getParty())) {
                    partyIndex = i;
                }
            }
            int canIndex = -5;
            // Finds the candidate index in candidate arrays in Party object for each candidate. 
            for (int i=0; i<parties.get(partyIndex).getCandidates().size(); i++) {
                if (parties.get(partyIndex).getCandidates().get(i).getName().equals(candidate.getName())) {
                    canIndex = i;
                }
            }

            parties.get(partyIndex).getCandidates().get(canIndex).setNumVotes(candidateVotes);
            parties.get(partyIndex).setNumVotes(parties.get(partyIndex).getNumVotes() + candidateVotes);
        }
    }

    /**
     * Finds the winner based on the candidate with the most votes.
     */
    @Override
    public void findWinners() {
        // For each party, check the obtained seats and allocate to candidates
        for (Party party: parties) {
            int thisPartySeats = party.getNumAllocatedSeats();
            ArrayList<Candidate> thisPartyCandidates = party.getCandidates();
            int maxAllocation = Math.min(thisPartySeats, thisPartyCandidates.size());

            // Goes through all of the party in the election.
            for (int i=0; i<maxAllocation; i++) {
                int largestVote = -1;
                ArrayList<Candidate> largestVoteCandidates = new ArrayList<Candidate>();
                // Iterate all candidates in this party
                for (Candidate candidate: thisPartyCandidates) {
                    int thisCandidateVote = candidate.getNumVotes();
                    
                    /* If the current candidate has more votes than the previous candidate, 
                    the candidate will replace the one in the list.
                    Otherwise, if the current candidate has the same amount of votes, 
                    it will be added to the list of winners. */  
                    if (largestVote < thisCandidateVote && !this.winnerList.contains(candidate)) {
                        largestVoteCandidates.clear();
                        largestVoteCandidates.add(candidate);
                        largestVote = thisCandidateVote;
                    } else if (largestVote == thisCandidateVote && !this.winnerList.contains(candidate)) {
                        largestVoteCandidates.add(candidate);
                    }
                }

                // Checks if there are candidates who tied.
                // If there are ties, it should find the winner of that tie breaker
                if (largestVoteCandidates.size() == 1) {
                    this.winnerList.add(largestVoteCandidates.get(0));
                } else if (largestVoteCandidates.size() > 1) {
                    int index = coinToss(largestVoteCandidates.size());
                    Candidate winner = largestVoteCandidates.get(index);
                    this.winnerList.add(winner);
                }
            }
        }
    }
}
