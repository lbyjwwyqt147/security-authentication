package pers.ljy.background.web.controller.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class CCCC {

	public static void main(String[] args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new NameValuePair("ProjectId","1111111"));
    	params.add(new NameValuePair("Collection","1"));
    	params.add(new NameValuePair("Latefee","2"));
    	params.add(new NameValuePair("Mobile","110"));

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("resource", "1111122222");
        contentMap.put("overdue", 	"1100.00");
    	params.add(new NameValuePair("ParamString",JSONObject.toJSON(contentMap).toString()));
        
    	System.out.println(JSONObject.toJSONString(params));
    	
    	
    	
    	
    	String customerPhone = "33438869236:";
        if(StringUtils.isNotBlank(customerPhone)){
        	customerPhone = customerPhone.trim();
        	System.out.println(customerPhone);
     	   if (customerPhone.length() > 11 ) {
     		   customerPhone = customerPhone.substring(0, 11);
			   }
        }
        System.out.println(customerPhone);
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
        
        
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        Map<String, String> amap = new HashMap<String, String>();
        amap.put("userName", "a");
        amap.put("phone", "q");

        list.add(amap);
        Map<String, String> bmap = new HashMap<String, String>();
        bmap.put("phone", "b");
        bmap.put("userName", "c");

        list.add(bmap);
        
    	StringBuffer phoneBuffer = new StringBuffer();
    	StringBuffer userBuffer = new StringBuffer();
    	int i = 1;
    	for (Map<String, String> map : list) {  
	    	for (Map.Entry<String, String> sendUserMap : map.entrySet()) {
		        if(sendUserMap.getKey().equals("phone")){
		        	if(StringUtils.isNotBlank(sendUserMap.getValue())){
		        		 phoneBuffer.append(sendUserMap.getValue());
		        		 if(i < list.size()){
		        			 phoneBuffer.append(",");
		        		 }
		        	}
		        }
		        if(sendUserMap.getKey().equals("userName")){
		        	if(StringUtils.isNotBlank(sendUserMap.getValue())){
		        		userBuffer.append(sendUserMap.getValue());
		        		if(i < list.size()){
		        			userBuffer.append(",");
		        		 }
		        	}
		        }
			}
	    	i++;
	    } 
        
    	System.out.println(phoneBuffer.toString());
    	System.out.println(userBuffer.toString());
        
	}

}
