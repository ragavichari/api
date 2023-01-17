package apii;


	

	import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

	



	public class baseclass {
		public static final String USERS_EXCEL_FILE = System.getProperty("user.dir") + "\\src\\test\\resources\\exceldata\\testdata.xls";
		public static String postUserJSONData(Map<String,String> testData, int rownumber) {
			JSONObject body_json = new JSONObject();
			
			
			
			Map<String,String> program = new HashMap<String,String>();
			
			
			body_json.put("programName",testData.get("programName"));
			body_json.put("programDescription",testData.get("programDescription"));
			body_json.put("programStatus",testData.get("programStatus"));
			

			//Logs.Log.debug(body_json.toJSONString());
			return body_json.toJSONString();		
		} 
	}




