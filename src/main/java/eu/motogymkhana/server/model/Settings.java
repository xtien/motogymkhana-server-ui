package eu.motogymkhana.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by christine on 15-2-16.
 */
public class Settings {

	public static final String ID = "_id";
	public static final String NUMBER_OF_ROUNDS_COUNT_FOR_TITLE = "number_of_rounds_that_count_for_title";
	public static final String PERCENTAGE_FOR_GREEN_BIB = "percentage_for_green_bib";
	public static final String PERCENTAGE_FOR_BLUE_BIB = "percentage_for_blue_bib";
	public static final String NUMBER_OF_RESULTS_FOR_SEASON_RESULT = "number_of_results_for_season_result";
	public static final String NUMBER_OF_RESULTS_FOR_BIB = "number_of_results_for_bib";
	public static final String COUNTRY = "country";
	public static final String SEASON = "season";

    @JsonProperty(COUNTRY)
    private Country country;
    
    @JsonProperty(SEASON)
     private int season;
    
    @JsonProperty(NUMBER_OF_ROUNDS_COUNT_FOR_TITLE)
     private int numberOfRoundsCountForTitle;

    @JsonProperty(PERCENTAGE_FOR_GREEN_BIB)
    private int percentageForGreenBib;

    @JsonProperty(PERCENTAGE_FOR_BLUE_BIB)
    private int percentageForBlueBib;

    @JsonProperty(NUMBER_OF_RESULTS_FOR_SEASON_RESULT)
    private int numberOfResultsForSeasonResult = 6;

    @JsonProperty(NUMBER_OF_RESULTS_FOR_BIB)
    private int numberOfResultsForBib =4;

    public int getPercentageBlue() {
        return percentageForBlueBib;
    }

    public int getPercentageGreen() {
        return percentageForGreenBib;
    }

    public int getNumberOfResultsForSeasonResult() {
        return numberOfResultsForSeasonResult;
    }

    public int getRoundsForBib() {
        return numberOfResultsForBib;
    }

    public void setPercentageBlue(int s) {
        percentageForBlueBib = s;
    }

    public void setPercentageGreen(int s) {
        percentageForGreenBib = s;
    }

    public void setNumberOfRoundsForBib(int i) {
        numberOfResultsForBib = i;
    }

    public void setNumberOfRoundsForSeasonResult(int i) {
        numberOfResultsForSeasonResult = i;
    }

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	public void merge(Settings settings) {
		this.percentageForBlueBib = settings.getPercentageBlue();
		this.percentageForGreenBib = settings.getPercentageGreen();
		this.numberOfResultsForBib = settings.getRoundsForBib();
		this.numberOfResultsForSeasonResult = settings.getNumberOfResultsForSeasonResult();
	}
}
