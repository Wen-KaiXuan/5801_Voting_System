package src;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

/**
 * This file tests the createUniqueFile method of the Audit class
 * @author Crystal Wen
 */
public class TestAudit {
    
    @Test
    public void testNewFileNameCreateUniqueFile(){
        Audit audit = new Audit("CPL", 9, 3, 
                                 new ArrayList<Candidate>(), new ArrayList<Party>());
        String filename = audit.createUniqueFile("newName.txt");
        assertEquals(filename, "newName.txt");
    }

    @Test
    public void testExistingFileNameCreateUniqueFile(){
        Audit audit = new Audit("CPL", 9, 3, 
                                 new ArrayList<Candidate>(), new ArrayList<Party>());
        String filename = audit.createUniqueFile("testAudit.txt");
        assertEquals(filename, "testAudit-1.txt");
    }

    @Test
    public void testMultipleExistingFileNameCreateUniqueFile(){
        Audit audit = new Audit("CPL", 9, 3, 
                                 new ArrayList<Candidate>(), new ArrayList<Party>());
        String filename = audit.createUniqueFile("testMultiAudit.txt");
        assertEquals(filename, "testMultiAudit-2.txt");
    }
}
