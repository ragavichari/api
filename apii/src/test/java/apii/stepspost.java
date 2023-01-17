package apii;


	


	import org.junit.runner.Request;


	import gherkin.formatter.model.DataTableRow;
	import io.cucumber.java.en.Given;
	import io.cucumber.java.en.Then;
	import io.cucumber.java.en.When;
	import io.restassured.RestAssured;
	import io.restassured.builder.RequestSpecBuilder;
	import io.restassured.builder.ResponseSpecBuilder;
	import io.restassured.http.ContentType;
	import io.restassured.path.json.JsonPath;
	import io.restassured.response.Response;
	import io.restassured.response.ResponseBody;
	import io.restassured.specification.RequestSpecification;
	import io.restassured.specification.ResponseSpecification;
	import utils.Excelreader;
	
	import static io.restassured.RestAssured.*;
	import static org.junit.Assert.assertEquals;

	import java.io.IOException;
	import java.util.HashMap;
	import java.util.LinkedHashMap;
	import java.util.List;
	import java.util.Map;

	import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
	import org.hamcrest.Matchers.*;
	import org.json.simple.JSONObject;
	import org.junit.Assert;
	public class stepspost  extends baseclass {
		String baseURL = "https://lms-backend-service.herokuapp.com/lms/";
		Request req;
		Response response;
		RequestSpecification reqSpec;
		RequestSpecification res;
		
		//ResponseSpecification resSpec;
		RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
		
		//ResponseSpecBuilder resSpecBuilder = new ResponseSpecBuilder();
		
//		@Given("User with URL {string}\\(Create new record)")
//		public void user_with_url_create_new_record(String dataTable) {
//			reqSpecBuilder.setBaseUri(baseURL).setAccept("application/json").setContentType("application/json");
//			
//			reqSpec = reqSpecBuilder.build();
//			res =  given().spec(reqSpec);
//			System.out.println("Given");
//		}
	//
//		@When("{string} request is made")
//		public void request_is_made(String dataTable) {
//		   //JSONObject postbody=new JSONObject();
//		   //postbody.post("programName","023");
//		   
//		   
	//	
	//	
//		}
	//	
	//
//		@Then("Status code should be {int} AND user should see a success message with all the fields {string}")
//		public void status_code_should_be_and_user_should_see_a_success_message_with_all_the_fields(Integer int1, String string) {
//		    
//		}
	//
	//
	//
	//}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//@Given("User with URL {string}\\(Create new record).")
	//public void user_with_url_create_new_record(String string)  {
//		reqSpecBuilder.setBaseUri(baseURL).setAccept("application/json").setContentType("application/json");
//		reqSpec = reqSpecBuilder.build();
//		res =  given().spec(reqSpec);
	//}
	//
	//@When("I add {string}{string}{string} post request is made")
	//public void i_add_post_request_is_made(String programName, String programDescription, String programStatus) {
	//   
	//}
	//
	//@Then("Status code should be {int} AND user should see a success message with all the fields {string}.")
	//public void status_code_should_be_and_user_should_see_a_success_message_with_all_the_fields(Integer int1, String string) {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	//}
	//}
		
		TestContext testContext;

		HashMap<Integer, Response> postOutputMap = new HashMap<Integer, Response>();
		HashMap<Integer, String> ValidateBodyMessages = new HashMap<Integer, String>();
		public void program_postRequest(TestContext testContext) {
			this.testContext = testContext;
			
		}
		@Given("User with URL {string}\\(Create new record).")
		public void user_with_url_create_new_record(String string) {
			reqSpecBuilder.setBaseUri(baseURL).setAccept("application/json").setContentType("application/json");
			reqSpec = reqSpecBuilder.build();
			res =  given().spec(reqSpec);
		}

		@When("I add sheetname\"Sheet1\" and endpoint as {string}")
		public void i_add_sheetname_sheet1_and_endpoint_as(String sheetName) { {
		    
		    
		 
		    Excelreader reader = new Excelreader();
			List<Map<String, String>> testData;
			try {
				testData = reader.getData(USERS_EXCEL_FILE, sheetName);			
				// Post all data from excel file
				for (int i = 0; i<testData.size()-1; i++) {
					String jsonData = postJSONData(testData.get(i), i);
					//Logs.Log.info(" POST jsonData " + jsonData );
					//testContext.response = post(testContext.requestSpec, endpoint, jsonData);
					// logging messages
					addPostResponse(testContext.response, i+1);
					// Store all responses 
					postOutputMap.put(i, testContext.response);
				}

			} catch (InvalidFormatException e) {
				//Logs.Log.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				//Logs.Log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		}
		
		@Then("Status code should be {int} AND user should see a success message with all the fields Successfully created.")
		public void status_code_should_be_and_user_should_see_a_success_message_with_all_the_fields_successfully_created(Integer code) {
			for (int i = 0; i < postOutputMap.size() ; i++) {
				postOutputMap.get(i).then().assertThat().statusCode(code);
				//Logs.Log.info(" Post request row "+ (i+1) + " status code : "  + testContext.response.getStatusCode());
				//assertEquals(code, postOutputMap.get(i).getStatusCode());
			}
		}



		private String postJSONData(Map<String,String> testData, int rowNum) {
			JSONObject requestParams = new JSONObject();
			requestParams.put("programName", testData.get("programName"));
			requestParams.put("programDescription", testData.get("programDescription"));
			requestParams.put("programStatus", testData.get("programStatus"));
			//requestParams.put("creationTime", testData.get("$isoTimestamp"));
			//Logs.Log.debug(requestParams.toJSONString());
			ValidateBodyMessages.put(rowNum, testData.get("ValidateBody"));
			return requestParams.toJSONString();		
		}

		private void addPostResponse(Response response, int rowNum) {
			ResponseBody responseBody = response.getBody();
			if(responseBody.asString() != "") {
				JsonPath jPath = responseBody.jsonPath();
				if (jPath != null && jPath.get("Message") != null) {
					String morbidityTestId = "";
					if (jPath.get("MorbidityTestId") != null) {
						morbidityTestId = jPath.get("MorbidityTestId").toString();
					}
				if (morbidityTestId!="") {
						//Logs.Log.info("Row : " +rowNum +"  --->  " + morbidityTestId +" : " +  jPath.get("Message").toString());
				}else {
						System.out.println("Error Message");
						//Logs.Log.info("Row : " +rowNum +"  --->  " + "Error Message : " +  jPath.get("Message").toString());				
				}
				}
			}
		}
	}


