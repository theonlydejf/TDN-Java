package com.dkrcmar.tdn.base;

import java.io.IOException;

import com.dkrcmar.tdn.core.TDNBufferedReader;
import com.dkrcmar.tdn.core.TDNBufferedWriter;
import com.dkrcmar.tdn.core.TDNTypeParser;
import com.dkrcmar.tdn.core.TDNValue;

public class BooleanParser implements TDNTypeParser
{

	@Override
	public String typeKey()
	{
		return "bln";
	}

	@Override
	public TDNValue readFromStream(TDNBufferedReader reader) throws IOException
	{
		String value = reader.readValue();
		
		return new TDNValue(value.toLowerCase().equals("true"), this);
	}

	@Override
	public void writeToStream(TDNBufferedWriter writer, Object value) throws IOException
	{
        writer.writeValue(String.valueOf((boolean)value).toLowerCase());
	}

}
