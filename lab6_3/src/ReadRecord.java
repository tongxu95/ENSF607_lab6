import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A simple class that reads the music record.
 * 
 * Reads in a .ser file and outputs a .txt file.
 * 
 * @version 1.0
 * @author Shiyu Zhou
 * @since Nov 6, 2020
 */
public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     * opens an ObjectInputStream using a FileInputStream
     * 
     * @param name the file name of .ser file
     */
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several records.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while ( true )
            {
            	// TO BE COMPLETED BY THE STUDENTS
            	record = (MusicRecord)input.readObject();
            	//display record contents on screen
            	System.out.println(record.getYear() + "     " + record.getSongName() + "     " + record.getSingerName() + "     " + record.getPurchasePrice());
            }   // END OF WHILE
        }
        // ADD NECESSARY catch CLAUSES HERE
        catch(EOFException e) {
        	System.out.println("End Of File");
        }
        catch(IOException e) {
        	System.err.println("IO Error");
        }
        catch(ClassNotFoundException e) {
        	System.err.println("ClassNotFound Error");
        }

    }           // END OF METHOD 
    
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        //d.readObjectsFromFile("mySongs.ser");
        d.readObjectsFromFile("allSongs.ser");
    }
}
