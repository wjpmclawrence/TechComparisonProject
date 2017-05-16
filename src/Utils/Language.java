package Utils;

public class Language
{
	private String	name;			// Name of this language
	private String	trainingTime;	// Amount of time it would take to be trained based on similarity
	private int		similarity;		// Similarity to the language this is being compared to
	
	public String getName ()
	{
		return name;
	}
	
	public String getTrainingTime ()
	{
		return trainingTime;
	}
	
	public int getSimilarity ()
	{
		return similarity;
	}
}
