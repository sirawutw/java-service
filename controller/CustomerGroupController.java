package cat.mnp.om.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cat.mnp.om.adaptor.Manager;

@Controller
public class CustomerGroupController {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerGroupController.class);
	
	@PostMapping({"/getCustomerGroup"})
	@ResponseBody
	public String doGetCustomerGroup(@RequestParam(value="order_id") String order_id) {
		Manager adaptor = new Manager();
	    ArrayList dataList = new ArrayList();
	    HashMap dataMap = new HashMap();
	    dataMap.put("order_id", order_id);
	    dataList.add(0, dataMap);
		ArrayList rs_result = adaptor.doProcess("getCustomerGroup", dataList);
		System.out.println(rs_result);
	    Gson gson = new Gson();
	    return gson.toJson(rs_result);
	}
}
