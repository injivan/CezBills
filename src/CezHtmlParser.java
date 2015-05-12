import java.io.FileWriter;
import java.util.ArrayList;

public class CezHtmlParser {
	
	private static ArrayList<oneBill> al = new ArrayList<oneBill>();

	public static String getLink(int Index) {
		String s1="";
		if (Index >=0 && Index<= al.size()){
			s1=al.get(Index).sLink;
		}
		return s1;
	}
	public static String getPeriod(int Index) {
		String s1="";
		if (Index >=0 && Index<= al.size()){
			s1=al.get(Index).sPeriod;
		}
		return s1;
	}
	public static int getCountBils() {
		return al.size();
	}
	
	public CezHtmlParser() {
	};

	/**
	 * @param sHtml
	 *            Here I want to divide sHtml string by tags with information
	 *            for one month electrify bill
	 */
	public void ParseHTML(String sHtml) {
		// find "<td> <a href="
		String str1 = Str1(sHtml, "<td> <a href=");
		String str2 = Str1(sHtml, "</tr>");
		int[] poz1 = new int[2];
		poz1[0] = 0;
		printOut(sHtml);
		// System.out.println(sHtml);
		do {
			// Get all lines with bill url by months, and save them in ArrayList
			GetStrinfFromHTML(poz1, sHtml, str1, str2);
		} while (poz1[0] > -1);

		// Print the ArrayList for my uses
		//for (int i = 0; i < al.size(); i++) {
	//		System.out.println(al.get(i).sDate);
		//}
		
		

	}

	private void printOut(String out) {
		FileWriter fw = null;
		try {
			fw = new FileWriter("Test.txt");
			fw.write(out);
			fw.close();
		} catch (Exception e) {
			e.getMessage();
			System.err.println("Unexpected exception");
			e.printStackTrace();
		}
	}

	private String Str1(String sHtml, String b) {
		String a = "";
		if (sHtml.indexOf(b) > -1) {
			return b;
		}
		return a;
	}

	private void GetStrinfFromHTML(int[] lStartPoz, String sDataIn,
			String sStartString, String sEndString) {

		int lonStart, lonEnd;
		// 1. Find start string position
		lonStart = sDataIn.indexOf(sStartString, lStartPoz[0]);
		lStartPoz[0] = -1;
		if (lonStart > -1) {
			// 2. Move to end of start string.
			lonStart = lonStart + sStartString.length();
			// 3. Find end string.
			lonEnd = sDataIn.indexOf(sEndString, lonStart);
			if (lonEnd > -1) {
				// 4. Extract data between start and end strings.
				parse1(sDataIn.substring(lonStart, lonEnd));

				lStartPoz[0] = lonEnd + 1;
				// sDataIn = sDataIn.substring(lonEnd + sEndString.length());
			}
		}
		 
	}

	/**
	 * @param sTag
	 * @return Srting with ">>" data delimiter the out string will contain
	 *         period, sum, and link to the invoice
	 */
	private void parse1(String sTag) {
		// "http://info.cezelectro.bg/scripts/pcl.php?in=XX27YY&doc_id=110390969&format=pdf&un=310187248713&pw=F4317C"
		// onclick="return hs.htmlExpand(this, { objectType: 'iframe', objectLoadTime: 'after',  objectHeight: 2200 } )"
		// ><img src="http://info.cezelectro.bg/pay/pdf.jpg" border="0"
		// width="17" height="17" ></a> 31.7.2014/0152183297</td><td> AE.
		// AIA?AE? IO 20.06.2014 AI 18.07.2014</td><td align="right">
		// 92,21</td><td align="right">25.8.2014</td>
		 
		String[] arr1 = sTag.split("\"");
		String sLink = arr1[1];

		arr1 = sTag.split("\\s*</td>\\s*");
		
		String sDate = formatData(arr1[1], 21); 
		String sSum = formatData(arr1[2], 18);
		String sPer = formatData(arr1[3], 18);
		
		al.add(new oneBill(sDate, sSum, sLink,sPer));
		
	}
	private String formatData(String s1,int len){
		String a=""; 
		if (len<s1.length())
			a= s1.substring(len).trim();
		return a;
	}

}

class oneBill {
	public String sLink = "";
	public String sPeriod = "";
	public String sSum = "";
	public String sDate = "";

	public oneBill(String sDate, String sSum, String sLink,String sPeriod) {
		this.sSum = sSum;
		this.sDate = sDate;
		this.sLink = sLink;
		this.sPeriod=sPeriod.replace(".", "_");
	}
}
