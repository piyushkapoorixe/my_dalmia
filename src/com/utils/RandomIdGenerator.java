package com.utils;

public class RandomIdGenerator {

	public static String getRefIDBasedOnUserId(String id)
	{
		StringBuilder randomRefId=new StringBuilder();
		int prefiZeros=10-id.length();
		randomRefId=new StringBuilder();
		if(prefiZeros>0)
		{
			for(int i=0;i<prefiZeros;i++)
			{
				randomRefId.append("0");	
			}
			return randomRefId.toString()+id;
		}
		else
		{
			return id;
		}
	}
}
