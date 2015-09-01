import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

 

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.widgets.Label;


public class billcez {
    
	/*	This class opeen Cez site and get the user bill
	 * 	
	 * 		VB6 project text for my attention
	 * 		strPostData = "kn=" & klNom & "&ok=" & otKod & "&timestamp=" & DateDiff("s", CDate("1/1/1970 12:00:00 AM"), Now) * 1000
	 * 		strHedar = "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.0.10) Gecko/2009042316 Firefox/3.0.10 (.NET CLR 4.0.20506)"
	 * 		Call Inet1.Execute("http://info.cezelectro.bg/scripts/info.dll/details?", "POST", strPostData, strHedar)
    */
											  //"http://info.cezelectro.bg/scripts/info.dll/details?";
	private static final String  CEZ_BILL_URL = "https://cezelectro.bg/scripts/info.dll/details?";
	private static final String  KLI_NOM = "kn=";
	private static final String  KLI_KOD = "ok=";
	private static final String  TIM_STM = "timestamp=";
	private static final String  USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.0.10) Gecko/2009042316 Firefox/3.0.10 (.NET CLR 4.0.20506)";
	
	
	public billcez() {}
	public void GetMyBill(String KliNom, String KliPIN) {
		try {
			CezHtmlParser pars = new CezHtmlParser();
			pars.ParseHTML((docResp(KliNom,KliPIN)),KliPIN ); 
			
			String sName = KliNom.substring(KliNom.length()-5);// + "_" + pars.getPeriod(0) ;
			 
			
			String sLink1 = pars.getLink(0);
			if (sLink1.length()>0){
				sLink1=sLink1.replace("pdf", "png");			
				downloadUrl(sLink1, sName+".png");
			}
			//Now have to get the bill file	
			//System.out.println("" + pars.getsLink(0));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void downloadUrl(String sUrl, String pathname) {
		if (sUrl.length()>0){
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			FileOutputStream myFileOutputStream = new FileOutputStream(pathname);

			URL toDownload = new URL(sUrl);

			byte[] chunk = new byte[50*1024];
			
			
			InputStream stream = toDownload.openStream();
			int bytesRead;	
			int offset =0;
			while ((bytesRead = stream.read(chunk)) != -1) {
				outputStream.write(chunk, 0, bytesRead);
				//offset += bytesRead;
				//System.out.println("bytesRead = " + bytesRead + " offset = " + offset);				
			}
			//System.out.println("bytesRead = " + bytesRead + " offset = " + offset);
			outputStream.writeTo(myFileOutputStream);
			outputStream.close();
			stream.close();
			myFileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		}
	}

	private static String docResp(String kn, String ok) throws Exception {
		
		String sUrlParam =  KLI_NOM + kn + "&" + KLI_KOD + ok + "&" + TIM_STM + Calendar.getInstance().getTime();
		sUrlParam = KLI_NOM + kn + "&" + KLI_KOD + ok +  "&useget=1&kl=2015";
		
		URLConnection cn1 = MyConn();
		
		//if RequestMethod("POST") then 
		sendRequest(cn1, sUrlParam);
		// 
		
		return get_the_response(cn1);
		
		
	}
	
	private static URLConnection MyConn() throws Exception {
		URL url1 = new URL(CEZ_BILL_URL);
		URLConnection conn1 = url1.openConnection();
		
		//add request header
		//conn1.setRequestMethod("POST");
		conn1.setRequestProperty("User-Agent", USER_AGENT);
		conn1.setRequestProperty("Accept",	"text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		conn1.setRequestProperty("Accept-Language", "bg-BG,en-US,en;q=0.5");
		conn1.setRequestProperty("Accept-Encoding", "gzip,deflate");
		conn1.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		conn1.setRequestProperty("Keep-Alive", "300");
		conn1.setRequestProperty("Proxy-Connection", "keep-alive");
		conn1.setRequestProperty("Referer", CEZ_BILL_URL);
		conn1.setRequestProperty("Pragma", "no-cache");
		conn1.setRequestProperty("Cache-Control", "no-cache");
		
		return conn1;
			
	}
	private static void sendRequest(URLConnection conn, String urlParameters) throws IOException{
		//Send request "POST"
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream ());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
	}
	
	private static String get_the_response(URLConnection conn){
        // Get the response
        //String sOut="";
        StringBuffer sBuf=new StringBuffer();
        try{ 
            BufferedReader rd = null;
            InputStream myInputStream = conn.getInputStream();
            
            if (conn.getHeaderField("Content-Encoding")!=null && conn.getHeaderField("Content-Encoding").equals("gzip")){
                myInputStream = new GZIPInputStream(myInputStream);            
            }
            rd = new BufferedReader(new InputStreamReader(myInputStream ));

        	String line="";
            while ((line = rd.readLine()) != null) {
                //sOut=sOut+line;
                sBuf.append(line);
            }                  
            rd.close();
            myInputStream.close();
        }catch (Exception e) {
             e.getMessage();
             System.err.println("Unexpected exception");
             e.printStackTrace();
        }
        //return sOut;
        return sBuf.toString();
    }
	
	
}