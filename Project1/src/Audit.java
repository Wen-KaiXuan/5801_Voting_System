package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class saves the election results to a txt file.
 * @author Lysong Seang
 * @author Shunichi Sawamura
 */

public class Audit {
    private String electionType;
    private int numParties, numBallots, numSeats, quota;
    private ArrayList<Candidate> winnerList;
    private ArrayList<Party> parties;
    private String fileName;

    /**
     * object with the specified details of the election.
     * @param electionType the type of the election.
     * @param numParties   the number of parties participating in the election.
     * @param numBallots   the total number of ballots cast.
     * @param numSeats     the total number of seats available.
     * @param winnerList   a list of candidates who have won in the election.
     * @param parties      a list of parties in the election. 
     */
    public Audit(String electionType, int numParties, int numBallots, int numSeats,
        ArrayList<Candidate> winnerList, ArrayList<Party> parties){
        this.electionType = electionType;
        this.numParties = numParties;
        this.numBallots = numBallots;
        this.numSeats = numSeats;
        this.winnerList = winnerList;
        this.parties = parties;
        this.quota = numBallots / numSeats;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDateTime = now.format(formatter);
        this.fileName = "auditFile" + formattedDateTime + ".txt";
    }

    /**
     * Makes a new txt file to save all information for the election results,
     * including winners, and ballot statistics.
     * @throws IOException if an I/O error occurs while creating an audit file
     */
    public void audit() throws IOException {
        File f = new File("testing.txt");  	
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("Election type: " + electionType);
        writer.newLine();
        writer.write("Number of Parties: " + parties.size());
        writer.newLine();
        writer.write("Number of Ballots: " + numBallots);
        writer.newLine();
        writer.write("Number of Seats: " + numSeats);
        writer.newLine();
        writer.write("Quota Value: "+ quota);
        writer.newLine();
        
        //Save each party name and candidate affiliation to the audit file.
        for (Party party: parties) {
        	String partyCandidateInfo = party.getName() + ": ";  
        	//iterate candidate info in each party
        	for (Candidate candidate: party.getCandidates()) {
        		partyCandidateInfo += candidate.getName() + ", ";
        	}
        	writer.write(partyCandidateInfo);
        	writer.newLine();
        }
        
        //Save calculations of the largest remainder approach for each party to the audit file.
        for (Party party: parties) {
        	writer.write("----- " + party.getName() + " -----");
        	writer.newLine();
        	writer.write("Total Seats: " + party.getNumAllocatedSeats());
        	writer.newLine();
        	int firstAllocationSeats = party.getNumVotes()/quota;
        	String party1stSeatCalculation = "Votes:" + party.getNumVotes() + " / Quota:" + quota + " = First Allocation Seats:" + firstAllocationSeats;
        	writer.write(party1stSeatCalculation);
        	writer.newLine();
        	int remainingVotes = party.getNumVotes() - quota * firstAllocationSeats;
        	String party2ndSeatCalculation = "Remaining Votes:" + remainingVotes + " --> Second Allocation Seats:" + (party.getNumAllocatedSeats() - firstAllocationSeats);
        	writer.write(party2ndSeatCalculation);
        	writer.newLine();
        	//Save the number of votes for each candidate if election is OPL to the audit file.
        	if (electionType.equals("OPL")) {
        		for (Candidate candidate: party.getCandidates()) {
        			writer.write(candidate.getName() + " Votes: " + candidate.getNumVotes());
        			writer.newLine();
        		}
        	}
        }
        
        writer.write("*** Winner(s) ***");
        writer.newLine();
        //Save winners and its party affiliation to the audit file.
        for (Candidate winner: winnerList) {
        	writer.write(winner.getName() + " (" + winner.getParty() + ")");
        	writer.newLine();
        }
        writer.flush();
        writer.close();
        
        //Update file permission to prevent editing.
        File file = new File(fileName);
        file.setReadable(true); 
        file.setWritable(false, false);
        file.setExecutable(false, false);
        
    }
}

