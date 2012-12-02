package com.example.sqlitetest;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class SimpleData
{
	@DatabaseField(generatedId = true)
	int id ;
	
	@DatabaseField
	String string;

	@Override
	public String toString()
	{
		return "id = "+id+"\t:"+string;
	}

	public String getString()
	{
		return string;
	}

	public void setString(String string)
	{
		this.string = string;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	public void changeValue(String string) {
		this.string = string;
	}
	
}
