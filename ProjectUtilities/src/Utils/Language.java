package Utils;

import java.io.Serializable;

/**
 * Holds data about the languages: name, training time, similarity.
 * <p>
 * Data Stored:
 * <p>
 * name: The name of the language
 * <p>
 * trainingTime: The amount of time it would take to train an individual on this language
 * <p>
 * similarity: How similar this language is to the specified language
 * 
 * @author Nathan Steer
 *
 */
public class Language implements Comparable<Language>, Serializable
{
	private static final long	serialVersionUID	= 1L;
	private String				name;			// Name of this language
	private String				trainingTime;	// Amount of time it would take to be trained based on similarity
	private String				uses;			// A description of what this language can be used for
	private int					similarity;		// Similarity to the language this is being compared to
	
	public Language( String name, int similarity, String trainingTime, String uses )
	{
		this.name = name;
		this.similarity = similarity;
		this.trainingTime = trainingTime;
		this.uses = uses;
	}
	
	public Language( String name, int similarity )
	{
		this.name = name;
		this.similarity = similarity;
	}
	
	/**
	 * Used to obtain the stored name
	 * 
	 * @return A String containing the name of the language
	 */
	public String getName ()
	{
		return name;
	}
	
	/**
	 * Used to obtain the stored trainingTime
	 * 
	 * @return A String containing the estimated time it would take to train someone
	 */
	public String getTrainingTime ()
	{
		return trainingTime;
	}
	
	/**
	 * Used to obtain the stored similarity
	 * 
	 * @return An int containing the percentage similarity of this language and the specified one
	 */
	public int getSimilarity ()
	{
		return similarity;
	}
	
	/**
	 * Used to obtain the description of this languages uses
	 * 
	 * @return	A String containing the uses of this language
	 */
	public String getUses ()
	{
		return uses;
	}
	
	/**
	 * Comparator method, allowing Languages to be compared to one another by similarity
	 */
	@Override
	public int compareTo ( Language other )
	{
		return Integer.compare( other.getSimilarity(), similarity );
	}
	
	/**
	 * String override method, allowing Language objects to be output as a meaningful string
	 */
	@Override
	public String toString ()
	{
		return System.lineSeparator() + "	" + name + "	:	" + similarity + "%" + "	:	" + trainingTime + "	:	" + uses;
	}
}
