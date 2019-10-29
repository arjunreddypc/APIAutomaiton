package test.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnHashMap {
	
		  
	
	
	public static void main(String args[]) {
		
		List<String> al1=new ArrayList<>();
		List<String> al2=new ArrayList<>();
		
		
		List<String> emailList = new ArrayList<String>();
		  emailList.add("a");
		  emailList.add("a");
		  emailList.add("b");
		List<String> eventList = new ArrayList<String>();
		  eventList.add("1");
		  eventList.add("2");
		  eventList.add("3");
		HashMap<String, List<String>>	hm=new HashMap<>();
		hm.put("emailList", emailList);
		hm.put("eventlist", eventList);
		
		
		
		
	}

}
