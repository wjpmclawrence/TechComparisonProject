package Utils;

/**
 * 
 * @author Nathan Steer
 * 
 * Class that holds data about the languages: name, training time, similarity.
 * <p>
 * Data Stored:
 * 		*	name:			The name of the language
 * 		*	trainingTime:	The amount of time it would take to train an individual on this language
 * 		*	similarity:		How similar this language is to the specified language
 *
 */
public class Language implements Comparable<Language>
{
	private String	name;			// Name of this language
	private String	trainingTime;	// Amount of time it would take to be trained based on similarity
	private int		similarity;		// Similarity to the language this is being compared to
	
	public Language( String name, int similarity, String trainingTime )
	{
		this.name			= name;
		this.similarity		= similarity;
		this.trainingTime	= trainingTime;
	}
	
	public Language( String name, int similarity )
	{
		this.name			= name;
		this.similarity		= similarity;
	}
	
	
	/**
	 * Used to obtain the stored name
	 * 
	 * @return	A String containing the name of the language
	 */
	public String getName ()
	{
		return name;
	}
	
	
	/**
	 * Used to obtain the stored trainingTime
	 * 
	 * @return	A String containing the estimated time it would take to train someone
	 */
	public String getTrainingTime ()
	{
		return trainingTime;
	}
	
	
	/**
	 * Used to obtain the stored similarity
	 * 
	 * @return	An int containing the percentage similarity of this language and the specified one
	 */
	public int getSimilarity ()
	{
		return similarity;
	}


	@Override
	public int compareTo ( Language other )
	{
		return Integer.compare( other.getSimilarity(), similarity );
	}
	
	@Override
	public String toString()
	{
		return name + "		:		" + similarity;
	}
}
