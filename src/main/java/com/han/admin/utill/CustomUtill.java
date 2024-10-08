package com.han.admin.utill;

import org.springframework.util.ObjectUtils;

/**
 * null, empty 체크 등 유틸 - 김시우
 * 
 */
public class CustomUtill {
	
	// nullorempty 체크
	static public boolean isNullOrEmpty(String str) 
	{
		if(str == null || str.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static public boolean isNullOrEmpty(Object obj) 
	{
		if(obj == null || ObjectUtils.isEmpty(obj))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 객체를 순회하여, DTO, List 객체에 한하여 하위 Field 정보를 Buffer에 담음.
	 * 
	 * @param subObject
	 * @return
	 */
	static public String toStringObject(Object subObject)
	{
		return CustomStringUtill.toString(subObject);
	}
}


