package src;

import java.util.ArrayList;

/**
 * This class is responsible for managing and displaying the results of an
 * election.
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
    private int numBallots, numSeats, quota;
    private ArrayList<Candidate> winnerList;
    private ArrayList<Party> parties;

    /**
     * Object with the specified details of the election.
     * 
     * @param electionType the type of the election.
     * @param numBallots   the total number of ballots cast.
     * @param numSeats     the total number of seats available.
     * @param winnerList   a list of candidates who have won in the election.
     * @param parties      a list of parties in the election.
     */
    public DisplayResults(String electionType, int numBallots, int numSeats,
            ArrayList<Candidate> winnerList, ArrayList<Party> parties) {
        this.electionType = electionType;
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

        // Loop through parties arry to get the total number of candidates
        for (int i = 0; i < parties.size(); i++) {
            int numCandidate = parties.get(i).getCandidates().size();
            count += numCandidate;
        }
        System.out.println("Number of Candidates: " + count);
        System.out.println("Number of Seats: " + numSeats);
        System.out.println("Number of Ballots: " + numBallots);

        // Print in CPL style
        if (electionType.equals("CPL")) {
            displayCPL();
        // Print out in OPL style
        } else if(electionType.equals("OPL")) {
            displayOPL();
        // Print out in MPO style
        } else if(electionType.equals("MPO")) {
            displayMPO();
        // Print out in MV style
        } else{
            displayMV();
        }
    }

    /**
     * Displays the results and statistics of a CPL election.
     */
    public void displayCPL() {
        System.out.println("Number of Quota : " + this.quota + "\n");

        // Loop throgh array of parties to get the seat of each party
        for (int i = 0; i < parties.size(); i++) {
            int numberVote = parties.get(i).getNumAllocatedSeats();
            System.out.println(parties.get(i).getName() + ": Number of Seats: " + numberVote);
            System.out.print("*** Winner(s): ");

            // Loop through winner list to get the name of the winner
            for (int j = 0; j < winnerList.size(); j++) {
                if (winnerList.get(j).getParty().equals(parties.get(i).getName())) {
                    System.out.print(winnerList.get(j).getName() + ", ");
                }
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
    }

    /**
     * Displays the results and statistics of a OPL election.
     */
    public void displayOPL() {
        System.out.println("Number of Quota : " + this.quota + "\n");
        System.out.println("***** Winner *****\n");

        // Print out the name of the winner, % of the total votes and the number of
        // Votes each candidate gets
        for (int i = 0; i < winnerList.size(); i++) {
            System.out.println((i + 1) + ". " + winnerList.get(i).getName() + " ( % of number of total votes " +
                    (winnerList.get(i).getNumVotes() / (double) numBallots) * 100 + " | number of votes "
                    + winnerList.get(i).getNumVotes() + ")");

        }
        System.out.println("\n");
        System.out.println("***** Candidate *****\n");
        // Loop through parties list to get the number of seats each party get
        for (int j = 0; j < parties.size(); j++) {
            ArrayList<Candidate> arrayName = parties.get(j).getCandidates();
            System.out.print(
                    parties.get(j).getName() + " Won: " + parties.get(j).getNumAllocatedSeats() + " seat(s)\n");
            System.out.print("Candidate: ");

            // Loop through arrayName list to get the name of candidate in that party
            for (int k = 0; k < arrayName.size() - 1; k++) {
                System.out.print(arrayName.get(k).getName() + ", ");
            }
            System.out.print(arrayName.get(arrayName.size() - 1).getName() + "\n");
            System.out.println("_____________\n");
        }
    }

    /**
     * Displays the results and statistics of a MPO election.
     */
    public void displayMPO() {
        System.out.println("***** Winners *****\n");

        // Print out the name of the winner, % of the total votes and the number of
        // Votes each candidate gets
        for (int i = 0; i < winnerList.size(); i++) {
            System.out.println((i + 1) + ". " + winnerList.get(i).getName() + " ( % of number of total votes " +
                    (winnerList.get(i).getNumVotes() / (double) numBallots) * 100 + " | number of votes "
                    + winnerList.get(i).getNumVotes() + ")");
        }
        System.out.println("\n");

        System.out.println("\n");
        System.out.println("***** Candidates *****\n");
        // Loops through each party to get the number of seats won by each candidate.
        for(Party p : parties){
            // Loops through each candidate of the party to get the number of seats won by each candidate.
            for(Candidate c : p.getCandidates()) {
                System.out.println(c.getName() + ": " + c.getNumSeats() + " seat(s)" + " ( % of number of total votes " +
                (c.getNumVotes() / (double) numBallots) * 100 + " | number of votes "
                + c.getNumVotes() + ")");
            }
        }
    }

    /**
    * Displays the results and statistics of a MV election.
    */
    public void displayMV() {
        int totalVotes = 0;
        for(Party p : parties) {
            for(Candidate c : p.getCandidates()){
                totalVotes += c.getNumVotes();
            }
        }

        System.out.println("Number of Votes: " + totalVotes);
        System.out.println("***** Winner *****\n");
        // Print out the name of the winner, % of the total votes and the number of
        // Votes each candidate gets
        for (int i = 0; i < winnerList.size(); i++) {
            System.out.println((i + 1) + ". " + winnerList.get(i).getName() + " ( % of number of total votes " +
            (winnerList.get(i).getNumVotes() / (double) numBallots) * 100 + " | number of votes "
            + winnerList.get(i).getNumVotes() + ")");

        }
        System.out.println("\n");

        System.out.println("***** Candidate *****\n");
        // Loops through each party to get the number of seats won by each candidate.
        for(Party p : parties){
            // Loops through each candidate of the party to get the number of seats won by each candidate.
            for(Candidate c : p.getCandidates()) {
                System.out.println(c.getName() + " Won: " + c.getNumSeats() + " seat(s)" + " ( % of number of total votes " +
                (c.getNumVotes() / (double) totalVotes) * 100 + " | number of votes "
                + c.getNumVotes() + ")");
            }
        }
    }
}

