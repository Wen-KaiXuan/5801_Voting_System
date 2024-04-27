package src;

/**
 * This is the Candidate class that stores all candidate information for election
 * @author Fumisato Teranishi
 * @author Shunichi Sawamura
 */
public class Candidate {
    private String name;
    private String party;
    private int numVotes;
    private int numSeats;

     /**
     * Initialize the variables of Canddiate class.
     * @param name     candidate name
     * @param party    party that candidate belongs to
     * @param numVotes total number of obtained votes
     */
    public Candidate(String name, String party, int numVotes) {
        this.name = name;
        this.party = party;
        this.numVotes = numVotes;
        this.numSeats = 0;
    }

    /**
     * Return the candidate name.
     * @return name return the candidate name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the party name that the candidate belongs.
     * @return party return the party name
     */
    public String getParty() {
        return this.party;
    }

    /**
     * Return the number of obtained votes for the candidate.
     * @return numVotes return the number of obtained votes
     */
    public int getNumVotes() {
        return this.numVotes;
    }

    /**
     * Set the number of obtained votes for this candidate.
     * @param voteNum update the number of obtained votes
     */
    public void setNumVotes(int voteNum) {
        this.numVotes = voteNum;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
}
