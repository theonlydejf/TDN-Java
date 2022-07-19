package com.dkrcmar.tdn.base;

import java.io.IOException;

import com.dkrcmar.tdn.core.TDNBufferedReader;
import com.dkrcmar.tdn.core.TDNBufferedWriter;
import com.dkrcmar.tdn.core.TDNTypeParser;
import com.dkrcmar.tdn.core.TDNValue;

public class ArrayParser implements TDNTypeParser
{
    private static final IntegerParser intParser = new IntegerParser();

	@Override
	public String typeKey()
	{
		return "arr";
	}

	@Override
	public TDNValue readFromStream(TDNBufferedReader reader) throws IOException
	{
        int itemCnt = (int)intParser.readFromStream(reader).value;
        String itemType = reader.readValue();
        TDNTypeParser itemParser = reader.settings.parsers().get(itemType);

        Object[] array = new Object[itemCnt];

        for (int i = 0; i < itemCnt; i++)
            array[i] = itemParser.readFromStream(reader).value;

        return new TDNValue(new TDNArray(array, itemParser), this);
	}

	@Override
	public void writeToStream(TDNBufferedWriter writer, Object value) throws IOException
	{
		if(!(value instanceof TDNArray))
            throw new IllegalArgumentException("Value is not type of TDNArray!");

        TDNArray arr = (TDNArray)value;
        intParser.writeToStream(writer, arr.value.length);
        writer.writeValue(arr.itemParser.typeKey());
        for(Object obj : arr)
        	arr.itemParser.writeToStream(writer, obj);
	}

}
