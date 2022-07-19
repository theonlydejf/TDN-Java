package com.dkrcmar.tdn.base;

import com.dkrcmar.tdn.core.TDNParserCollection;
import com.dkrcmar.tdn.core.TDNParserSettings;
import com.dkrcmar.tdn.core.TDNRootParser;

public class DefaultTDNParserSettings extends TDNParserSettings
{

	public DefaultTDNParserSettings()
	{
		super
		(
            '|',
            ':',
            ';',
            '\\',
            new TDNParserCollection
            (
                new ArrayParser(),
                new BooleanParser(),
                new FloatParser(),
                new IntegerParser(),
                new StringParser(),
                new TDNRootParser()
            )
        );
	}

}
