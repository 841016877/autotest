package com.youceedu.interf.functions;
import com.youceedu.interf.util.DateTimeUtil;

public class TimeFunction implements Function{
	
	public String execute(String[] args){
		String result = null;
		
		if(args.length == 0){
			//时间戳
			result = String.valueOf(DateTimeUtil.getTimeStamp());
		}else{
			//年月日时分秒
			result = DateTimeUtil.getDateTime();
		}
		return result;
	}

	public String getReferenceKey() {
		return "Time";
	}

	public static void main(String[] args) {
		
	}

}
