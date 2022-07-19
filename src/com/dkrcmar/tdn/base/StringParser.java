package com.dkrcmar.tdn.base;

import java.io.IOException;

import com.dkrcmar.tdn.core.TDNBufferedReader;
import com.dkrcmar.tdn.core.TDNBufferedWriter;
import com.dkrcmar.tdn.core.TDNTypeParser;
import com.dkrcmar.tdn.core.TDNValue;

public class StringParser implements TDNTypeParser
{
    public final char BeginStringCharacter;
	
    public StringParser()
    {
    	this('"');
    }

    public StringParser(char beginStringCharacter)
    {
        BeginStringCharacter = beginStringCharacter;
    }
    
	@Override
	public String typeKey()
	{
		return "str";
	}

	@Override
	public TDNValue readFromStream(TDNBufferedReader reader) throws IOException
	{
		String value = reader.readValue();
        if (value.length() < 1 || value.charAt(0) != BeginStringCharacter)
            throw new IllegalArgumentException("String is not in correct format");

        return new TDNValue(value.substring(1, value.length()), this);
	}

	@Override
	public void writeToStream(TDNBufferedWriter writer, Object value) throws IOException
	{
        writer.write(BeginStringCharacter);
        writer.writeValue((String)value);
	}

}
