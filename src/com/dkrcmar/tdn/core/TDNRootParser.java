package com.dkrcmar.tdn.core;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map.Entry;

import com.dkrcmar.tdn.core.TDNBufferedReader;
import com.dkrcmar.tdn.core.TDNBufferedWriter;
import com.dkrcmar.tdn.core.TDNControlCharacter;
import com.dkrcmar.tdn.core.TDNRoot;
import com.dkrcmar.tdn.core.TDNTypeParser;
import com.dkrcmar.tdn.core.TDNValue;

public class TDNRootParser implements TDNTypeParser
{

	@Override
	public String typeKey()
	{
		// TODO Auto-generated method stub
		return "obj";
	}

	@Override
	public TDNValue readFromStream(TDNBufferedReader reader) throws IOException
	{
		TDNRoot obj = new TDNRoot();
        Entry<String, TDNValue> readData = readKeyValuePair(reader);

        while (readData != null)
        {
            obj.insertValue(readData.getKey(), readData.getValue());
            readData = readKeyValuePair(reader);
        }

        return new TDNValue(obj, this);
	}
	
    private Entry<String, TDNValue> readKeyValuePair(TDNBufferedReader reader) throws IOException
    {
        int firstChar = reader.read();
        if (firstChar < 0 || reader.lastReadControlCharacter() == TDNControlCharacter.VALUE_SAPERATOR)
            return null;

        reader.queueCharacter((char)firstChar);
        String type = reader.readType();
        String key = reader.readKey();
        TDNValue value = reader.settings.parsers().get(type).readFromStream(reader);

        return new AbstractMap.SimpleEntry<String, TDNValue>(key, value);
    }

	@Override
	public void writeToStream(TDNBufferedWriter writer, Object value) throws IOException
	{
        TDNRoot obj = (TDNRoot)value;
        
        for(Entry<String, TDNValue> notatedValue : obj)
        {
        	writer.writeType(notatedValue.getValue().parser().typeKey());
            writer.writeKey(notatedValue.getKey());
            notatedValue.getValue().parser().writeToStream(writer, notatedValue.getValue().value);
        }
        writer.writeValue("");
	}

}
