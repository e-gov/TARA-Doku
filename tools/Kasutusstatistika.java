/*

Programm moodustab järgmise aruande:
---
TARA autentimisteenus
Kasutusstatistika aruanne

<periood>

|----------------|------------------------+
| klientrakendus  | ID-kaardiga | m-ID-ga | kokku |
|----------------------------------------
| <nimetus1>      +  <arv>      | <arv>   | <arv> |
| <nimetus2>      |  ...        | ...     | ...   |
| ...

Edukaid autentimisi: <arv>
Ebaedukaid autentimisi: <arv>

Aruanne koostatud. <kuupäev>
---

Programmi sisendiks on:
- kasutusstatistika logifailid, vastavalt https://e-gov.github.io/TARA-Doku/Statistika kirjeldatud struktuurile. Näiteks:

2017-12-08 12:03:42;openIdDemo;MOBILE_ID;START_AUTH;
2017-12-08 12:03:42;openIdDemo;MOBILE_ID;ERROR;NOT_ACTIVATED
2017-12-08 12:05:19;openIdDemo;ID_CARD;START_AUTH;

- seadistusparameetrid (vt p 3).

3. Skripti seadistused
- periood, mille kohta aruanne koostatakse. Peab olema võimalik anda kuu või nädal. Võib ka algus- ja lõpupäeva määramisega.

4. Väljund peab olema tekstina, mida saab copy-paste abil tavalisse e-kirja lisada ja aruande lugejale saata. Ei pea olema tingimata joonitud tabelina.

*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Script {
	
	public static void main(String[] args) throws IOException {		
		// Initilazing parameters and contants
		String configText = "";
		int aq = 0;
		String startDate = "";
		String endDate = "";
		String stats = "";
		String outString = "";
		int unsuccesfulAuth = 0;
		String clientId = "";
		int clientIdIndex1 = 0;
		int clientIdIndex2 = 0;
		int total = 0;
		int numberOfLines = 0;
		ArrayList <String>clientIdList = new ArrayList<String>();
		ArrayList <Integer>idCard = new ArrayList<Integer>();
		ArrayList <Integer> mobileId = new ArrayList<Integer>();
		int a = 0;
		int newLineIndex = 0;
		String totalLog = "";
		ArrayList <String> fileNames = new ArrayList <String>();
		
		// Reader for the config file		
		BufferedReader we = new BufferedReader(new FileReader("config.txt"));		
		
		try {
		    StringBuilder am = new StringBuilder();
		    String line2 = we.readLine();

		    while (line2 != null) {
		        am.append(line2);
		        am.append(System.lineSeparator());
		        line2 = we.readLine();
		    }
		    String everything2 = am.toString();
			System.out.println(everything2);
			String startDateEX= everything2.substring(1 + (everything2.indexOf(":")), everything2.indexOf("\n"));
			aq = everything2.indexOf("\n");
			String endDateEX = everything2.substring((everything2.indexOf(":", aq)+1) , (everything2.indexOf("\n", aq+1) ));

			startDate = startDateEX; 
			endDate = endDateEX;
			System.out.println("START: " + startDate);
			System.out.println("END: " + endDate);
		
			//Getting all file names that are in the same directory as the script
		
			String directory = System.getProperty("user.dir");
			directory = directory.replace('\\', '/');
			File folder = new File(directory);
			File[] listOfFiles;
			listOfFiles = folder.listFiles();
			int nullpoint = listOfFiles.length;

			for (int i = 0; i < nullpoint; i++) {
				if (listOfFiles[i].isFile()) {
					String nameFile = listOfFiles[i].getName();
					if( nameFile.compareTo(startDate) >= 0 && nameFile.compareTo(endDate)<=0 ) {
						fileNames.add(listOfFiles[i].getName());
						System.out.println("In time range: " + listOfFiles[i].getName());
					}
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
  
			for(int w = 0; w<fileNames.size(); w++) {
				try(BufferedReader br = new BufferedReader(new FileReader(""+ fileNames.get(w) ))) {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					
					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
						numberOfLines++;
					}
					totalLog = sb.toString() + totalLog;		   
				}
			}
		
		
			outString=totalLog;
			System.out.println(outString);
		
			//Sorting out the successful authentication and errors
		
			for(int u = 0; u < numberOfLines;u++) {	
				int line = outString.indexOf("\n", newLineIndex);
				String oneLine = outString.substring(newLineIndex, line);
				clientIdIndex1 = oneLine.indexOf(";", 0);
				clientIdIndex2 = oneLine.indexOf(";",clientIdIndex1+1 );
				clientId = oneLine.substring(clientIdIndex1 + 1, clientIdIndex2);
				newLineIndex = line + 1 ;
				String upperLine = oneLine.toUpperCase();

				if(clientIdList.contains(clientId) == true) {				
					if(upperLine.contains("ERROR") || upperLine.contains("error")) {
						unsuccesfulAuth++;
					}
					if(upperLine.contains("SUCCESSFUL_AUTH")) {
						int ex = clientIdList.indexOf(clientId);
						if(upperLine.contains("ID_CARD")|| upperLine.contains("IDCARD")) {
							int er = (int) idCard.get(ex);
							idCard.set(ex, er+1);	
						}				
						if(upperLine.contains("MOBILE_ID") || upperLine.contains("MOBILEID")) {
							int br = (int) mobileId.get(ex);
							mobileId.set(ex,  br+1);
						}
					}
				}
		
				if(clientIdList.contains(clientId) == false) {
					if(upperLine.contains("ERROR") || upperLine.contains("error")) {
						unsuccesfulAuth++;
					}
					clientIdList.add(clientId);
					if(upperLine.contains("SUCCESSFUL_AUTH")) {
						if(upperLine.contains("ID_CARD")|| upperLine.contains("IDCARD")) {
							idCard.add(1);
							mobileId.add(0);
						}
						if(upperLine.contains("MOBILE_ID")|| upperLine.contains("MOBILEID")) {
							mobileId.add(1);
							idCard.add(0);
						}
					}
					else {
						idCard.add(0);
						mobileId.add(0);
					}			
				}
			}
			//Printing out results(client id, succesful auths and unsuccesful)
			int sizeList = clientIdList.size();
		 
			for(int q = 0; q<sizeList; q++) {
				total =(int) mobileId.get(q) + (int)idCard.get(q);
			
				stats=("KlientID: "+ clientIdList.get(q) + "\n" + "Edukaid ID-kaardiga: " + idCard.get(q) +  "\n" + "Edukaid mobiili-ID-ga: " + mobileId.get(q) + "\n" + "| Kokku: " + total +  "\n" + "\n") + stats ;

				System.out.println("Klient: "+ clientIdList.get(q) + "| Edukaid ID-kaardiga: " + idCard.get(q) + " |Edukaid mobiili-ID-ga: " + mobileId.get(q) + "| Kokku: " + total);
			}
	
			System.out.println("Ebaedukaid autentimisi kokku: " + String.valueOf(unsuccesfulAuth));
			System.out.println("Date and time of write: ");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
			System.out.println(timeStamp);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			stats = stats +  "\n" + "Ebaedukaid kokku: "+ String.valueOf(unsuccesfulAuth) + "\n" + "Date and time of write: " + timeStamp;
			String content = outString;
					
			//Write results to txt file			
			String path = "stats_uusim.txt";
			Files.write( Paths.get(path), stats.getBytes(), StandardOpenOption.CREATE);
		}
		
		finally {
			we.close();
		}		
	}	

	// Method for reading files
	public static String reader() throws IOException {
		String example = "s";
		String totalLog = "";
		ArrayList <String>fileNames = new ArrayList<String>();
		File folder = new File("");
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				fileNames.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
	    }	    
	    for(int w = 0; w<fileNames.size(); w++) {
			try(BufferedReader br = new BufferedReader(new FileReader(""+ fileNames.get(w) ))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				totalLog = sb.toString() + totalLog;		   
			}			
		}
	    return totalLog;	
	}
}
