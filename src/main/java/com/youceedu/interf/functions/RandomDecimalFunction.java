package com.youceedu.interf.functions;

public class RandomDecimalFunction implements Function{

	public String execute(String[] args){
		//初始化返回值
		String result = null;

		if(args.length == 0){
			double d = Math.random();
			result = String.valueOf(d);
		}
		return result;
	}

	public String getReferenceKey() {
		//excel函数表达式写法为${__Random(,)}
		return "Random";
	}
	
	public static void main(String[] args){
		
	}
	
}
