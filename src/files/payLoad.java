package files;

public class payLoad {

	public static String getPostData() {
		String b="{"+

  "\"location\": {"+

    "\"lat\": -33.8669710,"+

    "\"lng\": 151.1958750"+

  "},"+

  "\"accuracy\": 50,"+

  "\"name\": \"Google Shoes!\","+

  "\"phone_number\": \"(02) 9374 4000\","+

  "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+

  "\"types\": [\"shoe_store\"],"+

  "\"website\": \"http://www.google.com.au/\","+

  "\"language\": \"en-AU\""+

"}";
		return b;
	}
	
	public static String AddBook(String isbn, String aisle) {
		
		String payload="{\n" + 
				"\n" + 
				"\"name\":\"Learn Appium Automation with Java\",\n" + 
				"\"isbn\":\""+isbn+"\",\n" + 
				"\"aisle\":\""+aisle+"\",\n" + 
				"\"author\":\"John foe\"\n" + 
				"}";
		
		return payload;
	}
	
	
	
}
