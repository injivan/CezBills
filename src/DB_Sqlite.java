import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class DB_Sqlite {
	Connection cn = null;
	ResultSet rs = null;
	public DB_Sqlite() {
		try {
			//open connection
			Class.forName("org.sqlite.JDBC"); // make sure that Java has loaded the SQLite driver
			cn = DriverManager.getConnection("jdbc:sqlite:db_cez.db");
			
			//open Accounts table
			openAccounts();
			
		} catch (Exception e) {
			System.out.println("Exception thrown  :" + e);
		}
	}
	private void openAccounts(){
		if (!isTableExists("Accounts")){ 
			createTblAccounts();
		}
		String sql = "Select * FROM Accounts";
		try {
			Statement stat = cn.createStatement();
			rs = stat.executeQuery(sql);
		} catch (Exception e) {
		}		
	}
	
	private void createTblAccounts(){
		try {	//create Table Accounts
			Statement stat = cn.createStatement();
		    stat.executeUpdate("create table Accounts (ID TEXT, PIN TEXT, Text TEXT);");		
		} catch (Exception e) {
		}	
	}
	private boolean isTableExists(String tableName)	{
		int count=0;
		if (tableName == null){
	        return false;
	    }
	    try {
	    	Statement stat=cn.createStatement();
			String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name = '" + tableName + "'";
			ResultSet rs = stat.executeQuery(sql);
			
		    //Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
		    if (!rs.first()){
		        return false;
		    }
		    count = rs.getInt(0);
		    rs.close();
		    
		} catch (Exception e) {
			 
		}
	    return count > 0;
	    
	}
	
}
