package src;

import java.util.ArrayList;

/**
 * This class is responsible for managing and displaying the results of an election.
 * It includes details such as the type of election, the number of parties,
 * ballots, seats, and the winners list.
 * This class also calculates the quota based on the number of ballots and
 * seats, and provides functionality
 * to display detailed election results including the percentage of votes and
 * the allocation of seats among candidates.
 * 
 * @author Lysong Seang
 */
public class DisplayResults {
    private String electionType;
    private int numParties, numBallots, numSeats, quota;
    private ArrayList<Candidate> winnerList;
    private ArrayList<Party> parties;

    /**
     * Object with the specified details of the election.
     * 
     * @param electionType the type of the election.
     * @param numParties   the number of parties participating in the election.
     * @param numBallots   the total number of ballots cast.
     * @param numSeats     the total number of seats available.
     * @param winnerList   a list of candidates who have won in the election.
     * @param parties      a list of parties in the election.
     */
    public DisplayResults(String electionType, int numParties, int numBallots, int numSeats,
            ArrayList<Candidate> winnerList, ArrayList<Party> parties) {
        this.electionType = electionType;
        this.numParties = numParties;
        this.numBallots = numBallots;
        this.numSeats = numSeats;
        this.winnerList = winnerList;
        this.quota = numBallots / numSeats;
        this.parties = parties;

    }

    /**
     * Displays the results of the election. This method prints detailed information
     * about the election,
     * including the type of election, number of parties, candidates, seats,
     * ballots, and quota.
     * It also displays the winners and the number of seats allocated to each party,
     * as well as the percentage of votes
     * received by each party and candidate.
     */
    public void displayResults() {
        System.out.println("----Election Results----\n");
        System.out.println("Election type: " + electionType);
        System.out.println("Number of Parties: " + parties.size());
        int count = 0;

        // loop through parties arry to get the total number of candidates
        for (int i = 0; i < parties.size(); i++) {
            int numCandidate = parties.get(i).getCandidates().size();
            count += numCandidate;
        }
        System.out.println("Number of Candidates: " + count);

        System.out.println("Number of Seats: " + numSeats);
        System.out.println("Number of Ballots: " + numBallots);
        System.out.println("Number of Quota : " + this.quota);

        // print in CPL style
        if (electionType.equals("CPL")) {
            // loop throgh array of parties to get the seat of each party
            for (int i = 0; i < parties.size(); i++) {
                int numberVote = parties.get(i).getNumAllocatedSeats();
                System.out.println(parties.get(i).getName() + ": Number of Seats: " + numberVote);
                System.out.print("*** Winner(s): ");

                // print N/A if the winnerList is less than or equal zero
                if (winnerList.size() <= 0) {
                    System.out.print(" N/A ***");
                } else {

                    // Loop through winner list to get the name of the winner
                    for (int j = 0; j < winnerList.size() - 1; j++) {
                        System.out.print(winnerList.get(j).getName() + ", ");
                    }
                    System.out.print(winnerList.get(winnerList.size() - 1).getName());
                }
                System.out.print(" ***\n");
                System.out.println("Number of Votes: " + parties.get(i).getNumVotes());
                System.out.println("% of votes: " + (parties.get(i).getNumVotes() / (double) numBallots) * 100);

                ArrayList<Candidate> arrayName = parties.get(i).getCandidates();
                System.out.print("Candidate(s): ");
                // Loop through the arrayname to get the name of Candidates in each party
                for (int k = 0; k < arrayName.size(); k++) {
                    System.out.print(arrayName.get(k).getName() + ", ");
                }

                System.out.println();
                System.out.println("_____________________________________________\n");
            }

        } else {

            // print out in OPL style
            if (electionType.equals("OPL")) {
                System.out.println("***** Winner *****\n");
                // print out the name of the winner, % of the total votes and the nubmer of
                //
                // votes each candidate gets
                for (int i = 0; i < winnerList.size(); i++) {
                    System.out.println((i + 1) + ". " + winnerList.get(i).getName() + " ( % of number of toal votes " +
                            (winnerList.get(i).getNumVotes() / (double) numBallots) * 100 + " | number of votes "
                            + winnerList.get(i).getNumVotes() + ")");

                }
                System.out.println("\n");
                System.out.println("***** Candidate *****\n");
                // loop through parties list to get the number of seats each party get
                for (int j = 0; j < parties.size(); j++) {
                    ArrayList<Candidate> arrayName = parties.get(j).getCandidates();
                    System.out.print(
                            parties.get(j).getName() + " Won: " + parties.get(j).getNumAllocatedSeats() + " seat(s)\n");
                    System.out.print("Candidate: ");

                    // loop through arrayName list to get the name of candidate in that party
                    for (int k = 0; k < arrayName.size() - 1; k++) {
                        System.out.print(arrayName.get(k).getName() + ", ");
                    }
                    System.out.print(arrayName.get(arrayName.size() - 1).getName() + "\n");
                    System.out.println("_____________\n");
                }

            }
        }
    }
}

