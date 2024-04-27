package src;

import java.util.ArrayList;
import java.io.BufferedReader;

/**
 * The CPL class inherits the Election class to run a CPL type election.
 * @author Crystal Wen
 */
public class CPL extends Election{
    public CPL(int totalVotes, int totalSeats, ArrayList<Party> parties, BufferedReader br){
        super(totalVotes, totalSeats, parties, br);
    }
}

