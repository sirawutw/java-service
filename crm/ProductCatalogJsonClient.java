package cat.util.crm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cat.mnp.exception.CatMnpException;

public class ProductCatalogJsonClient {
	private static final Logger logger = LoggerFactory.getLogger(ProductCatalogJsonClient.class);
	private String productCatalogUrlPrefix;
	private Map<String, String> tokenHeaderMap;

	public ProductCatalogJsonClient(String productCatalogUrlPrefix, String eportalUrlPrefix, String clientId, String clientSecret) throws IOException, CatMnpException {
		super();
		this.productCatalogUrlPrefix = productCatalogUrlPrefix;
		tokenHeaderMap = new EPortalJsonClient(eportalUrlPrefix).authen(clientId, clientSecret);
	}
	//for run test
	public static void main(String[] args) throws IOException, CatMnpException {
		System.out.println("ACTION");
		String eportalUrlPrefix = "http://localhost:18082";
		String clientId = "vvg2NxCu-dMGhfmq"; // from register
		String clientSecret = "c8nxPbGeiT2NJl7k8mjLohFSBKmNrafp61Q-M5LsxM1VaMmEGYri+flWN7DSw2KzLnMb5yzDVPFWtLroKx3ijA=="; // from register
		String productCatalogUrlPrefix = "http://localhost:18444";
		ProductCatalogJsonClient productCatalogJsonClient = new ProductCatalogJsonClient(productCatalogUrlPrefix, eportalUrlPrefix, clientId, clientSecret);
		productCatalogJsonClient.getAllOfferingsPO();
//		productCatalogJsonClient.getAllOfferingsSO("51006146");
	}
	
	/** 1. Product Catalog PO API'S: */
	public List<Map<String, Object>> getAllOfferingsPO() throws IOException, CatMnpException {
		String url = productCatalogUrlPrefix + "/router/cat/com/action/api/queryOfferingList/getAllOfferings";
		Map jsonPayloadMap = new LinkedHashMap<>();
		Map jsonPayloadMapDetail = new LinkedHashMap<>();
		//jsonPayloadMap.put("id", ""); // mockup
		//jsonPayloadMap.put("objectId", "51006149"); // mockup
		jsonPayloadMap.put("objectType", "PO"); // mockup
		jsonPayloadMap.put("outputType", "F"); // mockup
		//jsonPayloadMap.put("poID", "51004470"); // mockup
		jsonPayloadMap.put("channel", "MNP"); // mockup
		/*jsonPayloadMap.put("serviceType", "A"); // mockup
		jsonPayloadMap.put("offeringType", "I"); // mockup
		jsonPayloadMap.put("role", ""); // mockup
		jsonPayloadMap.put("group", ""); // mockup
		jsonPayloadMap.put("msisdn", ""); // mockup
		jsonPayloadMapDetail.put("id", ""); // mockup
		jsonPayloadMapDetail.put("niceNumberFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("niceNumberLevel", "1"); // mockup
		jsonPayloadMapDetail.put("contractFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("catEmpFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("retiredCatEmpFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("fixSpeedFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("billingServDiscFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("topupSimFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("allowSwapUsageFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("15digitsFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("fnFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("cugFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("poolUsageFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("communityFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("rolloverFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("prorateFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("touristSimFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("offeringList_Req_id", ""); // mockup
		jsonPayloadMapDetail.put("bonusRewardFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("hasRC", "Y"); // mockup*/
		//jsonPayloadMap.put("filter", jsonPayloadMapDetail); // mockup
		List<Map<String, Object>> r = post(url, toJsonStr(jsonPayloadMap));
		System.out.println("r=" + r);
		return r;
	}
	
	/** 2. Product Catalog SO API'S: */
	public List<Map<String, Object>> getAllOfferingsSO(String po) throws IOException, CatMnpException {
		String url = productCatalogUrlPrefix + "/router/cat/com/action/api/queryOfferingList/getAllOfferings";
		Map jsonPayloadMap = new LinkedHashMap<>();
		Map jsonPayloadMapDetail = new LinkedHashMap<>();
//		jsonPayloadMap.put("id", ""); // mockup
//		jsonPayloadMap.put("objectId", "51006149"); // mockup
		jsonPayloadMap.put("objectType", "SO"); // mockup
		jsonPayloadMap.put("outputType", "F"); // mockup
	    jsonPayloadMap.put("poID", po); // mockup
		jsonPayloadMap.put("channel", "MNP"); // mockup
		/*jsonPayloadMap.put("serviceType", "A"); // mockup
		jsonPayloadMap.put("offeringType", "I"); // mockup
		jsonPayloadMap.put("role", ""); // mockup
		jsonPayloadMap.put("group", ""); // mockup
		jsonPayloadMap.put("msisdn", ""); // mockup
		jsonPayloadMapDetail.put("id", ""); // mockup
		jsonPayloadMapDetail.put("niceNumberFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("niceNumberLevel", "1"); // mockup
		jsonPayloadMapDetail.put("contractFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("catEmpFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("retiredCatEmpFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("fixSpeedFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("billingServDiscFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("topupSimFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("allowSwapUsageFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("15digitsFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("fnFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("cugFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("poolUsageFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("communityFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("rolloverFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("prorateFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("touristSimFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("offeringList_Req_id", ""); // mockup
		jsonPayloadMapDetail.put("bonusRewardFlag", "Y"); // mockup
		jsonPayloadMapDetail.put("hasRC", "Y"); // mockup*/
		//jsonPayloadMap.put("filter", jsonPayloadMapDetail); // mockup
		List<Map<String, Object>> r = post(url, toJsonStr(jsonPayloadMap));
		System.out.println("r=" + r);
		return r;
	}

	private void addRequestInfo(HttpPost request, String jsonStrPayload) {
		Map<String, String> headerMap = new LinkedHashMap<>(); // authen headers
		headerMap.putAll(tokenHeaderMap);
		headerMap.put("ep-accountname", "cat");
		headerMap.put("ep-appname", "com");

		headerMap.forEach((k, v) -> request.setHeader(k, v)); // add biz header
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		if (jsonStrPayload != null) {
			request.setEntity(new StringEntity(jsonStrPayload, "UTF-8"));
		} else {
			request.setEntity(new StringEntity("{}", "UTF-8"));
		}
	}

	private List<Map<String, Object>> post(String url, String jsonStrPayload) throws IOException, CatMnpException {
		System.out.println("url=" + url);
		System.out.println("jsonStrPayload=" + jsonStrPayload);

		HttpPost request = new HttpPost(url);
		HttpClient client = HttpClientBuilder.create().build();
		addRequestInfo(request, jsonStrPayload);

		HttpResponse response = client.execute(request); // raw, api
		String jsonStrResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("jsonStrResponse=" + jsonStrResponse);
		jsonStrResponse = jsonStrResponse.replaceAll(",\\s*\\[\\]", ",{}");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);// normalize to List
		//mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
		
		List<Map<String, Object>> jsonMapList = mapper.readValue(jsonStrResponse, new TypeReference<List<Map<String, Object>>>() {
		});
		// validate result
		int returnCode = response.getStatusLine().getStatusCode();
		if (!(returnCode == HttpStatus.SC_OK)) {
			String msg = "HttpStatus must = 200 but " + returnCode + " for " + url;
			throw new CatMnpException(msg);
		}
		if (!jsonMapList.isEmpty()) {
			Map<String, Object> jsonMap = jsonMapList.get(0);
			if ("500".equalsIgnoreCase((String) jsonMap.get("status"))) { // this is follow doc, need to revise
				throw new CatMnpException("expect arrays result but got " + jsonMap);
			}
		}

		return jsonMapList;
	}

	private static String toJsonStr(Object payLoad) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(payLoad);
	}

}
