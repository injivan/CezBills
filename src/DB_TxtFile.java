import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


 
public class DB_TxtFile {
	 
	private ArrayList<OneCezAcaunt> arAcaunts = new ArrayList<OneCezAcaunt>();
	private OneCezAcaunt OneAcount=null;
	private int currentAccount =-1;
	public DB_TxtFile() {
		try {
			readFile();
		} catch (Exception e) {
				
		}		
	}
	public int muve(int index) {
		currentAccount=-1;
		if (index>=0 && index<=arAcaunts.size()){
			currentAccount=index;
			OneAcount = arAcaunts.get(index);
		}
		return currentAccount;
	}
	public String Row(int index) {
		String ret="";
		switch (index) {
		case 1:
			ret= OneAcount.getID();
			break;
		case 2:
			ret= OneAcount.getPIN();
			break;
		case 3:
			ret= OneAcount.getText();
			break;
		}
		return ret;
	}
	public int getCount() {
		return arAcaunts.size();		
	}
	private  void readFile() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("Acaunts.in"));
		StringTokenizer token;
		String strLine;
		//Read File Line By Line
        while ((strLine = br.readLine()) != null) {
        	token = new StringTokenizer(strLine);
        	OneCezAcaunt oca = new OneCezAcaunt( token.nextToken(),token.nextToken(),token.nextToken() );
        	arAcaunts.add(oca);        	 
		}		
		br.close();
	}
}

class OneCezAcaunt{
	private String ID="";
	private String PIN="";
	private String Text="";
	
	 

	//public  OneCezAcaunt(){};
		
	public OneCezAcaunt(String ID, String PIN, String Text) {
		this.ID=ID;
		this.PIN=PIN;
		this.Text=Text;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}	
}
 