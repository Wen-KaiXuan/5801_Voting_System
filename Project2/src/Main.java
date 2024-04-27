package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Main class of this voting system program.
 * It receives the ballot file, fetches basic ballot info, and handles program
 * execution.
 * 
 * @author Lysong Seang
 * @author Shunichi Sawamura
 */
public class Main {

    /**
     * Runs voting system based on the given information.
     * 
     * @param electionType type of the election
     * @param totalVotes   total number of votes in election
     * @param totalSeats   total number of allocated seats in election
     * @param ballots      array of array of strings storing ballots info
     * @param parties      a list of parties in election
     * @param candidates   a list of candidates in election
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void runElection(String electionType, int totalVotes, int totalSeats, ArrayList<String[]> ballots,
            ArrayList<Party> parties, ArrayList<Candidate> candidates) throws IOException {
        // If election type is CPL, create CPL object and runs voting system
        if (electionType.equals("CPL")) {
            CPL cpl = new CPL(totalVotes, totalSeats, parties, ballots);
            cpl.calculateQuota();
            cpl.voteCounting();
            cpl.allocateSeats();
            cpl.findWinners();
            cpl.auditFile("CPL");
            cpl.displayResults("CPL");
        // If election type is OPL, create OPL object and runs voting system
        } else if (electionType.equals("OPL")){
            OPL opl = new OPL(totalVotes, totalSeats, parties, ballots, candidates);
            opl.calculateQuota();
            opl.voteCounting();
            opl.allocateSeats();
            opl.findWinners();
            opl.auditFile("OPL");
            opl.displayResults("OPL");
        // If election type is MPO, create MPO object and runs voting system 
        } else if(electionType.equals("MPO")) {
            MPO mpo = new MPO(totalVotes, totalSeats, parties, ballots, candidates);
            mpo.calculateQuota();
            mpo.voteCounting();
            mpo.allocateSeats();
            mpo.findWinners();
            mpo.displayResults("MPO");
        // If election type is MV, create MV object and runs voting system 
        } else {
            MV mv = new MV(totalVotes, totalSeats, parties, ballots, candidates);
            mv.calculateQuota();
            mv.voteCounting();
            mv.allocateSeats();
            mv.findWinners();
            mv.displayResults("MV");
        }
    }

    /**
     * Read the contents of the given file to get basic ballot information.
     * 
     * @param files An array of the names of the given ballot files
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void readBallotFile(String[] files) throws IOException {
        try {
            FileReader fileReader = new FileReader(files[0]);
            BufferedReader reader = new BufferedReader(fileReader);
            String electionType = reader.readLine();
            reader.close();

            // Checks the election type of the file(s).
            if (electionType.equals("CPL"))
                readCPL(files);
            else if (electionType.equals("OPL"))
                readOPL(files);
            else {
                readMPOMV(files);
            }

        // If the file is not found, show the following output.
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    /**
     * Reads and extracts the necessary information from CPL-type election ballot
     * file(s).
     * 
     * @param files An array of the names of the given ballot files
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void readCPL(String[] files) throws IOException {
        ArrayList<Party> parties = new ArrayList<Party>();
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        String electionType = "";
        int totalSeats = 0;
        int numCandidateOrParties = 0;
        int totalVotes = 0;

        try {
            // Reads and extracts the necessary data from each ballot file.
            for (int i = 0; i < files.length; i++) {
                FileReader fileReader = new FileReader(files[i]);
                BufferedReader reader = new BufferedReader(fileReader);
                electionType = reader.readLine();
                totalSeats = Integer.parseInt(reader.readLine());
                int currVotes = Integer.parseInt(reader.readLine());
                totalVotes += currVotes;
                numCandidateOrParties = Integer.parseInt(reader.readLine());

                // For each party, store all candidates and party infos with objects.
                for (int j = 0; j < numCandidateOrParties; j++) {

                    // If this is the first file being read, then the Arraylist of parties
                    // and their respective candidates are initialized.
                    if (i == 0) {
                        ArrayList<Candidate> thisPartyCandidates = new ArrayList<Candidate>();
                        String[] split = reader.readLine().split(",");
                        String partyn = split[0];
                        // Store candidates info for this party.
                        for (int k = 1; k < split.length; k++) {
                            String name = split[k];
                            Candidate candidate = new Candidate(name, partyn, 0);
                            thisPartyCandidates.add(candidate);
                        }
                        Party party = new Party(partyn, 0, thisPartyCandidates);
                        parties.add(party);

                    // Otherwise, those lines will be skipped.
                    } else {
                        reader.readLine();
                    }

                }

                // Adds the lines that contains the ballots to a data structure.
                for (int j = 0; j < currVotes; j++) {
                    ballots.add(reader.readLine().split(","));
                }
                reader.close();
            }
            runElection(electionType, totalVotes, totalSeats, ballots, parties, new ArrayList<Candidate>());


        //If the file is not found, show the following output.
        } catch (FileNotFoundException e){
            System.out.println("File Not Found.");
        } catch (NumberFormatException e) {
            System.out.println("Inappropriate File Provided.");
        }  
    }

    /**
     * Reads and extracts the necessary information from OPL-type election ballot
     * file(s).
     * 
     * @param files An array of the names of the given ballot files
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void readOPL(String[] files) throws IOException {
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();
        ArrayList<Party> parties = new ArrayList<Party>();
        ArrayList<String> partyNames = new ArrayList<String>();
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        String electionType = "";
        int totalSeats = 0;
        int numCandidateOrParties = 0;
        int totalVotes = 0;

        try {
            // Reads and extracts the necessary data from each ballot file.
            for (int i = 0; i < files.length; i++) {
                FileReader fileReader = new FileReader(files[i]);
                BufferedReader reader = new BufferedReader(fileReader);
                electionType = reader.readLine();
                totalSeats = Integer.parseInt(reader.readLine());
                int currVotes = Integer.parseInt(reader.readLine());
                totalVotes += currVotes;
                numCandidateOrParties = Integer.parseInt(reader.readLine());

                // If this is the first file being read, then the Arraylist of parties and
                // candidates are initialized.
                if (i == 0) {
                    // For each candidate, store name and party affiliation with Candidate object.
                    for (int j = 0; j < numCandidateOrParties; j++) {
                        String[] split = reader.readLine().split(",");
                        String partyn = split[0];
                        String name = split[1];
                        Candidate candidate = new Candidate(name, partyn, 0);
                        candidates.add(candidate);
                        // save party name if it has not stored yet
                        if (!partyNames.contains(partyn)) {
                            partyNames.add(partyn);
                        }
                    }
                    // For each party, store info with Party object.
                    for (String partyname : partyNames) {
                        ArrayList<Candidate> thisPartyCandidates = new ArrayList<Candidate>();
                        for (Candidate can : candidates) {
                            if (partyname.equals(can.getParty())) {
                                thisPartyCandidates.add(can);
                            }
                        }
                        Party party = new Party(partyname, 0, thisPartyCandidates);
                        parties.add(party);
                    }

                // Otherwise, these lines are skipped.
                } else {
                    for (int j = 0; j < numCandidateOrParties; j++) {
                        reader.readLine();
                    }
                }

                // Adds the lines that contains the ballots to a data structure.
                for (int j = 0; j < currVotes; j++) {
                    ballots.add(reader.readLine().split(","));
                }
                reader.close();
            }
            runElection(electionType, totalVotes, totalSeats, ballots, parties, candidates);

        //If the file is not found, show the following output.
        } catch (FileNotFoundException e){
            System.out.println("File Not Found.");
        } catch (NumberFormatException e) {
            System.out.println("Inappropriate File Provided.");
        }
    }

    public static void readMPOMV(String[] files) throws IOException {
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();
        ArrayList<Party> parties = new ArrayList<Party>();
        ArrayList<String> partyNames = new ArrayList<String>();
        ArrayList<String[]> ballots = new ArrayList<String[]>();
        String electionType = "";
        int totalSeats = 0;
        int numCandidateOrParties = 0;
        int totalVotes = 0;
        String line;

        try {
            // Reads and extracts the necessary data from each ballot file.
            for (int i = 0; i < files.length; i++) {
                FileReader fileReader = new FileReader(files[i]);
                BufferedReader reader = new BufferedReader(fileReader);
                electionType = reader.readLine();
                totalSeats = Integer.parseInt(reader.readLine());
                numCandidateOrParties = Integer.parseInt(reader.readLine());
                String[] candidateInfo = reader.readLine().split(", (?=\\[)");
                while ((line = reader.readLine()) != null && line.trim().isEmpty());
                int currVotes = Integer.parseInt(line);
                totalVotes += currVotes;

                // If this is the first file being read, then the Arraylist of parties and
                // candidates are initialized.
                if (i == 0) {
                    for (int j = 0; j < numCandidateOrParties; j++) {
                        // replace the [ and ] with empty string
                        String candidateDetails = candidateInfo[j].replaceAll("[^A-Za-z,]", "");
                        // now we can split it again without brackets
                        String[] info = candidateDetails.split(",");
                        String name = info[0];
                        String party = info[1];
                        Candidate candidate = new Candidate(name, party, 0);
                        candidates.add(candidate);

                        if (!partyNames.contains(party)) {
                            partyNames.add(party);
                        }
                    }

                    //Inititializes the arraylist of parties
                    for (String partyname : partyNames) {
                        ArrayList<Candidate> thisPartyCandidates = new ArrayList<Candidate>();
                        for (Candidate can : candidates) {
                            if (partyname.equals(can.getParty())) {
                                thisPartyCandidates.add(can);
                            }
                        }
                        Party party = new Party(partyname, 0, thisPartyCandidates);
                        parties.add(party);
                    }
                }
                
                // Adds the lines that contains the ballots to a data structure.
                for (int j = 0; j < currVotes; j++) {
                    ballots.add(reader.readLine().split(","));
                }

                reader.close();
            }
            runElection(electionType, totalVotes, totalSeats, ballots, parties, candidates);
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (NumberFormatException e) {
            System.out.println("Inappropriate File provided.");
        }
    }

    /**
     * This will be implemented first in this voting system program. It receives
     * the file name and runs the whole system.
     * 
     * @param args can receive the ballot file name from command line argument
     * @throws IOException if an I/O error occurs while reading the ballot file
     */
    public static void main(String[] args) throws IOException {
        String[] filenames;
        File[] files;
        Scanner scanner = new Scanner(System.in);

        // File name given from command line argument.
        if (args.length > 0) {
            filenames = args;
        // Ask file name by text prompt.
        } else {
            System.out.print("Please enter your file name(s) (separate each file name with a space): ");
            filenames = scanner.nextLine().split(" ");
        }
        files = new File[filenames.length];

        //Makes every filename into a File object and adding it to the files array
        for(int i = 0; i < files.length; i++) {
            files[i] = new File(filenames[i]);
            
            int index = filenames[i].lastIndexOf('.');
            String extension = "";
            //If the file name has a file extension, then we take the file extension.
            if(index > 0) extension = filenames[i].substring(index + 1);

            //If the given file name is not found, keep asking for the file name
            while (!files[i].exists() || files[i].isDirectory() || !extension.equals("csv")) {
                System.out.println("File " + (i + 1) + ": '" + filenames[i] + "' is not valid or not found");

                System.out.print("Please enter your file name: ");
                filenames[i] = scanner.nextLine();

                index = filenames[i].lastIndexOf('.');
                //If the file name has a file extension, then we take the file extension.
                if(index > 0) extension = filenames[i].substring(index + 1);

                files[i] = new File(filenames[i]);

            }
        }
        scanner.close();

        // Try to open the given file, fetch the ballots info, and clarify the results.
        try {
            readBallotFile(filenames);
        // If it fails to open, stop the process
        } catch (NullPointerException e) {
            System.out.println("Failed To Open the File.");
        }
        System.out.println();
        System.out.println("End the Process");
    }

}
