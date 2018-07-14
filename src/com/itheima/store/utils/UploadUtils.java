package com.itheima.store.utils;

import java.util.Random;
import java.util.UUID;

public class UploadUtils {
	/**
	 * 鑾峰彇鏂囦欢鐪熷疄鍚嶇О
	 * 鐢变簬娴忚鍣ㄧ殑涓嶅悓鑾峰彇鐨勫悕绉板彲鑳戒负:c:/upload/1.jpg鎴栬�1.jpg 
	 * 鏈�粓鑾峰彇鐨勪负  1.jpg
	 * @param name 涓婁紶涓婃潵鐨勬枃浠跺悕绉�
	 * @return	鐪熷疄鍚嶇О
	 */
	public static String getRealName(String name){
		//鑾峰彇鏈�悗涓�釜"/"
		int index = name.lastIndexOf("\\");
		return name.substring(index+1);
	}
	
	
	/**
	 * 鑾峰彇闅忔満鍚嶇О
	 * @param realName 鐪熷疄鍚嶇О
	 * @return uuid 闅忔満鍚嶇О
	 */
	public static String getUUIDName(String realName){
		//realname  鍙兘鏄� 1.jpg   涔熷彲鑳芥槸  1
		//鑾峰彇鍚庣紑鍚�
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	
	/**
	 * 鑾峰彇鏂囦欢鐩綍,鍙互鑾峰彇256涓殢鏈虹洰褰�
	 * @return 闅忔満鐩綍
	 */
	public static String getDir(){
		String s="0123456789ABCDEF";
		Random r = new Random();
		return "/"+s.charAt(r.nextInt(16))+"/"+s.charAt(r.nextInt(16));
	}
	
	public static void main(String[] args) {
		//String s="G:\\day17-鍩虹鍔犲己\\resource\\1.jpg";
		String s="1.jgp";
		String realName = getRealName(s);
		System.out.println(realName);
		
		String uuidName = getUUIDName(realName);
		System.out.println(uuidName);
		
		String dir = getDir();
		System.out.println(dir);
	}
}
