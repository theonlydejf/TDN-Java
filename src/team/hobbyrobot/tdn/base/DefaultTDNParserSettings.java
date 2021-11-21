package team.hobbyrobot.tdn.base;

import team.hobbyrobot.tdn.core.TDNParserCollection;
import team.hobbyrobot.tdn.core.TDNParserSettings;

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
