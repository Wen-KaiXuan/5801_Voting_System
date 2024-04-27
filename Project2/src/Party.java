package src;

import java.util.ArrayList;

/**
 * This is the Party class that stores all related information for election.
 * @author Fumisato Teranishi
 * @author Shunichi Sawamura
 */
public class Party {
    private String name;
    private int numVotes;
    private ArrayList<Candidate> candidates;
    private int numAllocatedSeats;

    /**
     * Initializes the variables of the Party class.
     * @param name       party name
     * @param numVotes   total number of votes obtained
     * @param candidates a list of candidates belong to the party
     */
    public Party(String name, int numVotes, ArrayList<Candidate> candidates){
        this.name = name;
        this.candidates = candidates;
        this.numVotes = numVotes;
        this.numAllocatedSeats = 0;
    }

    /**
     * Getting the name of the party.
     * @return name return party name
     */
    public String getName() {
        return name;
    }

    /**
     * Getting the number of obtained votes for the party.
     * @return numVotes return total number of obtained votes
     */
    public int getNumVotes() {
        return numVotes;
    }

    /**
     * Setting the number of obtained votes for the party.
     * @param numVotes update total number of obtained votes
     */
    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    /**
     * Getting the candidates list who belong to the party
     * @return candidates return a list of candidate of the party
     */
    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    /**
     * Setting the candidates list who belong to the party 
     * @param candidates update a list of candidate of the party
     */
    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    /**
     * Setting the number of allocated seats for the party
     * @param numSeats update the number of allocated seats
     */
    public void setNumAllocatedSeats(int numSeats) {
        this.numAllocatedSeats = numSeats;
    }

    /**
     * Getting the number of allocated seats for the party
     * @return numAllocatedSeats return the number of allocated seats
     */
    public int getNumAllocatedSeats() {
        return numAllocatedSeats;
    }
}
