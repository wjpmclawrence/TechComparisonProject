package DataManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author Nathan Steer
 *
 */
public class VersionManager
{
	private static Path path = Paths.get( "Version.dat" );
	
	/**
	 * Returns the servers current version number
	 * <p>
	 * Reads the version file stored on the server to find the version number,
	 * and returns this number to the caller.
	 * 
	 * @return The version number of the servers language list in the form of an int
	 */
	public static int getVersion ()
	{
		// If the file does not currently exist, create it
		if ( Files.notExists( path ) )
		{
			updateVersion();
		}
		
		// Attempt to read from the version file and return its contents
		try ( BufferedReader in = Files.newBufferedReader( path ) )
		{
			return in.read();
		}
		catch ( NoSuchFileException e )
		{
			System.out.println( "File does not exist" );
		}
		catch ( AccessDeniedException e )
		{
			System.out.println( "Access to file was denied when attempting to read" );
		}
		catch ( IOException e )
		{
			System.out.println( "Failed to read file" );
			e.printStackTrace();
		}
		
		return 0;	// This line should never be reached
	}
	
	
	/**
	 * Updates or creates the version number document
	 * <p>
	 * When called, this method will check if a version document currently exists on the server.
	 * If one does, it will get the current value stored there and increment it.
	 * If there is currently no version file, one will be created with the version number set to 1.
	 */
	public static void updateVersion ()
	{
		try
		{
			// Initialise version to 0, in case no file currently exists
			int version = 0;
			
			// If the file already exists, read the current version number from it
			if ( Files.exists( path ) )
			{
				version = getVersion();
			}
			
			// Increment the version number
			version++;
			
			// Create/update the version number file
			BufferedWriter out = Files.newBufferedWriter( path );
			out.write( version );
			out.close();
		}
		catch ( AccessDeniedException e )
		{
			System.out.println( "Access to file was denied when attempting to write" );
		}
		catch ( IOException e )
		{
			System.out.println( "Failed to Overwrite File" );
		}
	}
	
	
	/**
	 * FOR DEBUGGING PURPOSES ONLY. REMOVE BEFORE RELEASE
	 */
	public static void removeFile ()
	{
		try
		{
			Files.deleteIfExists( path );
		}
		catch ( IOException e )
		{
			System.out.println( "An error occurred when deleting file" );
		}
	}
}
