package cat.mnp.om.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cat.mnp.exception.CatMnpException;
import cat.util.crm.InventoryJsonClient;
import cat.util.crm.ProductCatalogJsonClient;

@Controller
public class ProductCatalogController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductCatalogController.class);
	public static List<Map<String, Object>> rs_result_tmp_PO = null;
	public static HashMap<String, List<Map<String, Object>>> rs_result_tmp_SO = new HashMap<String, List<Map<String, Object>>>();
	
	@PostMapping({ "/productcatalogPO" })
	@ResponseBody
	public String productcatalogPO() throws UnsupportedOperationException, IOException, CatMnpException {
		System.out.println("Hello");
		String eportalUrlPrefix = "http://localhost:18082";
		String clientId = "vvg2NxCu-dMGhfmq"; // from register
		String clientSecret = "c8nxPbGeiT2NJl7k8mjLohFSBKmNrafp61Q-M5LsxM1VaMmEGYri+flWN7DSw2KzLnMb5yzDVPFWtLroKx3ijA=="; // from register
		String productCatalogUrlPrefix = "http://localhost:18444";
		List<Map<String, Object>> rs_result = null;
		try {
			ProductCatalogJsonClient productCatalogJsonClient = new ProductCatalogJsonClient(productCatalogUrlPrefix, eportalUrlPrefix, clientId, clientSecret);
			//productCatalogJsonClient.alloffers();
			rs_result = productCatalogJsonClient.getAllOfferingsPO();
			synchronized (this) {
				rs_result_tmp_PO = rs_result;
			}
		} catch (Exception err) {
			System.out.println("error PO ok");
			rs_result = rs_result_tmp_PO;
		}
		Gson gson = new Gson();
     	return gson.toJson(rs_result);
	}
	
	@PostMapping({ "/productcatalogSO" })
	@ResponseBody
	public String productcatalogSO(@RequestParam("po") String po) throws UnsupportedOperationException, IOException, CatMnpException {
		System.out.println("Hello");
		String eportalUrlPrefix = "http://localhost:18082";
		String clientId = "vvg2NxCu-dMGhfmq"; // from register
		String clientSecret = "c8nxPbGeiT2NJl7k8mjLohFSBKmNrafp61Q-M5LsxM1VaMmEGYri+flWN7DSw2KzLnMb5yzDVPFWtLroKx3ijA=="; // from register
		String productCatalogUrlPrefix = "http://localhost:18444";
		
		List<Map<String, Object>> rs_result = null;
		try {
			ProductCatalogJsonClient productCatalogJsonClient = new ProductCatalogJsonClient(productCatalogUrlPrefix, eportalUrlPrefix, clientId, clientSecret);
			rs_result = productCatalogJsonClient.getAllOfferingsSO(po);
			synchronized (this) {
				rs_result_tmp_SO.put(po, rs_result);
			}
		} catch (Exception err) {
			System.out.println("error SO ok : "+po);
			rs_result = rs_result_tmp_SO.get(po);
		}
		Gson gson = new Gson();
		System.out.println("------------"+rs_result);
     	return gson.toJson(rs_result);
	}
}
