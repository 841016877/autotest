package com.youceedu.interf.functions;
import org.apache.commons.codec.digest.DigestUtils;
public class Md5Function implements Function{

	public String execute(String[] args){
		//初始化
		String result = null;
		
		if(args.length > 0 ){
			result = DigestUtils.md5Hex(args[0]);
		}
		return result;
	}

	public String getReferenceKey() {
		return "Md5";
	}

	public static void main(String[] args) {
		//65d45a11d8c37030cd2b396df31932b8
		String result = DigestUtils.md5Hex("15034$%&*(@#￥@#￥@￥85325!@$@@#$^*^&*");
		System.out.println(result);
	}

}
