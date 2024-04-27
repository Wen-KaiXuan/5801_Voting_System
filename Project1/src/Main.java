package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Main class of this voting system program. 
 * It receives the ballot file, fetches basic ballot info, and handles program execution. 
 * @author Lysong Seang
 * @author Shunichi Sawamura
 */
public class Main {
    
    /**
     * Runs voting system based on the given information.
     * @param electionType type of the election
     * @param totalVotes   total number of votes in election
     * @param totalSeats   total number of allocated seats in election
     * @param parties      a list of parties in election
     * @param reader       buffered reader to continue reading the given file
     * @param candidates   a list of candidates in election
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void runElection(String electionType, int totalVotes, int totalSeats, 
                            ArrayList<Party> parties, BufferedReader reader, ArrayList<Candidate> candidates) throws IOException {
        //If election type is CPL, create CPL object and runs voting system
        if (electionType.equals("CPL")) {
            CPL cpl = new CPL(totalVotes, totalSeats, parties, reader);
            cpl.calculateQuota();
            cpl.voteCounting();
            cpl.allocateSeats();
            cpl.findWinners();
            cpl.auditFile();
            cpl.displayResults();
        //If election type is OPL, create OPL object and runs voting system
        }else {
            OPL opl = new OPL(totalVotes, totalSeats, parties, reader, candidates);
            opl.calculateQuota();
            opl.voteCounting();
            opl.allocateSeats();
            opl.findWinners();
            opl.auditFile();
            opl.displayResults();
        }
    }

    /**
     * Read the contents of the given file to get basic ballot information.
     * @param file         name of the given ballot file
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void readBallotFile(String file) throws IOException {
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();
        ArrayList<Party> parties = new ArrayList<Party>();
        ArrayList<String> partyNames = new ArrayList<String>();
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String electionType = reader.readLine();
            int totalSeats = Integer.parseInt(reader.readLine());
            int totalVotes= Integer.parseInt(reader.readLine());
            int numCandidateOrParties = Integer.parseInt(reader.readLine());

            //If the election type is OPL
            if (electionType.equals("OPL")){
                //For each candidate, store name and party affiliation with Candidate object.
                for (int i=0; i< numCandidateOrParties; i++ ){
                    String [] split = reader.readLine().split(",");
                    String partyn = split[0];
                    String name = split[1];
                    Candidate candidate = new Candidate(name, partyn, 0);
                    candidates.add(candidate);
                    //save party name if it has not stored yet
                    if (!partyNames.contains(partyn)) {
                        partyNames.add(partyn);
                    }
                }
                //For each party, store info with Party object.
                for (String partyname: partyNames) {
                    ArrayList<Candidate> thisPartyCandidates = new ArrayList<Candidate>();
                    for (Candidate can: candidates) {
                        if (partyname.equals(can.getParty())) {
                            thisPartyCandidates.add(can);
                        }
                    }
                    Party party = new Party(partyname, 0, thisPartyCandidates);
                    parties.add(party);
                }
            //If the election type is CPL
            } else if (electionType.equals("CPL")) {
                //For each party, store all candidates and party infos with objects.
                for (int i=0; i<numCandidateOrParties; i++){
                    ArrayList<Candidate> thisPartyCandidates = new ArrayList<Candidate>();
                    String [] split = reader.readLine().split(",");
                    String partyn = split[0];
                    //Store canidates info for this party.
                    for (int j=1; j< split.length; j++){
                        String name = split[j];
                        Candidate candidate = new Candidate(name, partyn, 0);
                        thisPartyCandidates.add(candidate);
                    }
                    Party party = new Party(partyn, 0, thisPartyCandidates);
                    parties.add(party);
                }
            }
            runElection(electionType, totalVotes, totalSeats, parties, reader, candidates);
        //If the file is not found, show the following output.
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }

    /**
     * This will be implemented first in this voting system program. It receives
     * the file name and runs the whole system.
     * @param args         can receive the ballot file name from command line argument
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void main(String[] args) throws IOException {
        String filename;
        //File name given from command line argument.
        if (args.length > 0) {
            filename = args[0];
        //Ask file name by text prompt.
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your file name: ");
            filename = scanner.nextLine();
        }
        
        File file = new File(filename);
        //If the given file name is not found, keep asking the file name
        while (!file.exists()) {
        	System.out.println("File Not Found");
        	Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your file name: ");
            filename = scanner.nextLine();
            file = new File(filename);
        }

        //Try to open the given file, fetch the ballots info, and clarify the results.
        try {
            readBallotFile(filename);
        //If it fails to open, stop the process
        } catch (NullPointerException e) {
            System.out.println();
        }
        System.out.println();
        System.out.println("End the Process");
    }

}

