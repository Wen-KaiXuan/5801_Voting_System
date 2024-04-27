package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the parent class to both the CPL and OPL class. 
 * It has the methods required to run both types of elections.
 * @author Crystal Wen
 * @author Shunichi Sawamura
 */
public class Election{
    public int totalVotes;
    public int totalSeats;
    public ArrayList<Party> parties;
    public int quota;
    public ArrayList<Candidate> winnerList;
    public BufferedReader br;

    /**
     * Initializes the variables of the Election class.
     * @param totalVotes total number of votes in election
     * @param totalSeats total number of seats in election
     * @param parties    a list of parties in election
     * @param br         buffered reader to continue reading the given ballot file
     */
    public Election(int totalVotes, int totalSeats, ArrayList<Party> parties, BufferedReader br){
        this.totalVotes = totalVotes;
        this.totalSeats = totalSeats;
        this.parties = parties;
        this.br = br;
        winnerList = new ArrayList<>();
    }

    /**
     * Calculates the quota which is the floor of the 
     * total number of votes divided by the total number of seats.
     * @return quota return the quota value after calculate it
     */
    public int calculateQuota(){
        quota = (int) Math.floor(totalVotes/totalSeats);
        return quota;
    }

    /**
     * Counts the votes for each party.
     * This will not work if the total number of votes is less than or equal to 0.
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public void voteCounting() throws IOException {
        //Reads through each ballot and counts each parties ballots. 
        for (int i = 0; i < totalVotes; i++) {
            String ballot = br.readLine();
            String[] tokens = ballot.split(",");
            int index = tokens.length - 1;
            parties.get(index).setNumVotes(parties.get(index).getNumVotes() + 1);
        }
    }

    /**
     * Simulates a fair coin toss to break a tie between a list of parties.
     * @param winners a list of election winners
     * @return The randomly chosen index of winner 
     */
    public Party coinToss(ArrayList<Party> winners) {
        Random rand = new Random();
        
        /* The winning index is randomized 1000 times and the winner
        is chosen on the 1001th time to simulate a fair coin toss. */
        int index = rand.nextInt(winners.size());
        for(int i = 0; i < 1000; i++) {
            index = rand.nextInt(winners.size());
        }

        return winners.get(index);
    }

    /**
     * Allocate seats to each party.
     */
    public void allocateSeats() {
        int remainingSeats = this.totalSeats;
        //divide the number of votes that each party gets by the quota.
        for (Party party: parties) {
            //seat allocation first round
            int firstAllocatedSeats = party.getNumVotes() / this.quota;
            party.setNumAllocatedSeats(firstAllocatedSeats);
            remainingSeats -= firstAllocatedSeats;
        }

        //Distribute remaining seats by comparing the largest remainder of votes 
        //for each party in a round-robin fashion.
        while (remainingSeats > 0){
            int largestRemainingVotes = -1;
            ArrayList<Party> largestVoteParties = new ArrayList<Party>();

            //Checks remaining seats for each party and 
            //figures out which party has the largest remaining votes.
            for (Party party: parties) {
                //Calculate remaining votes.
                int remainingVotes = party.getNumVotes() - this.quota * party.getNumAllocatedSeats();
                //If current remaining votes are larger than current largest, update the largest votes.
                if (remainingVotes > largestRemainingVotes) {
                    largestRemainingVotes = remainingVotes;
                    largestVoteParties.clear();
                    largestVoteParties.add(party);
                //If it is same as the current largest, 
                //record this party as one of the largest votes parties.
                } else if (remainingVotes == largestRemainingVotes) {
                    largestVoteParties.add(party);
                }
            }

            //If there is only one party that has the largest votes, give the seat to that party.
            if (largestVoteParties.size() == 1) {
                Party allocatedParty = largestVoteParties.get(0);
                allocatedParty.setNumAllocatedSeats(allocatedParty.getNumAllocatedSeats() + 1);
                remainingSeats -= 1;
            //If there are multiple parties that have the largest votes, 
            //randomly picks one party to seat allocation.
            } else if (largestVoteParties.size() > 1) {
                //Coin toss to determine seat allocation
                Party allocatedParty = coinToss(largestVoteParties);
                allocatedParty.setNumAllocatedSeats(allocatedParty.getNumAllocatedSeats() + 1);
                remainingSeats -= 1;
            }
        }
    }

    /**
     * Finds the winner based on the party with the most seats.
    */
    public void findWinners() {
        //Each party clarifies the number of allocated seats based on the voting results and candidate information.
        for (Party party: parties) {
            int thisPartySeats = party.getNumAllocatedSeats();
            ArrayList<Candidate> thisPartyCandidates = party.getCandidates();
            int maxAllocation = Math.min(thisPartySeats, thisPartyCandidates.size());
            
            //Give the seat to candidate from the 
            for (int i=0; i<maxAllocation; i++) {
                this.winnerList.add(thisPartyCandidates.get(i));
            }
        } 
    }

    /**
     * Calls the Display class to display the results of the election.
     */
    public void displayResults() {
        DisplayResults results = new DisplayResults(
                "CPL", 
                this.parties.size(), 
                this.totalVotes, 
                this.totalSeats,
                this.winnerList,
                this.parties
                );
        results.displayResults();
    }

    /**
     * Calls the Audit class to create an audit file.
     */
    public void auditFile() {
        Audit auditFile = new Audit(
                "CPL",
                this.parties.size(),
                this.totalVotes,
                this.totalSeats,
                this.winnerList,
                this.parties
                );
        try {
            auditFile.audit();
        } catch (IOException e) {
            System.out.println("Fail to generate an audit file");
        }
    }

}

