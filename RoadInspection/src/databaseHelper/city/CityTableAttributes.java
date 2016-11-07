package databaseHelper.city;

public class CityTableAttributes {
	
	// To prevent class from being instantiated
	private CityTableAttributes(){
		
	}
	
	// Name of table
    protected static final String TABLE_NAME = "CITY_TABLE";

    // Names of the columns
    protected static final String ID = "ID";
    protected static final String CITY_NAME = "CITY_NAME";
    protected static final String DEVIATION = "DEVIATION";
    protected static final String LAT_MAX = "LAT_MAX";
    protected static final String LAT_MIN = "LAT_MIN";
    protected static final String LNG_MAX = "LNG_MAX";
    protected static final String LNG_MIN = "LNG_MIN";
}
