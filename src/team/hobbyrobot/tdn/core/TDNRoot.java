package team.hobbyrobot.tdn.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.io.*;

public class TDNRoot implements Iterable<Entry<String, TDNValue>>
{
	public TDNRoot()
    {
        rootData = new Hashtable<String, TDNValue>();
    }

    Hashtable<String, TDNValue> rootData;
    
    /*public TDNValue this[string key]
    {
        get
        {
            string[] path = key.Split('.');
            var root = GetRootData(path);
            return root[path.Last()];
        }
        set
        {
            string[] path = key.Split('.');
            var root = GetRootData(path, true);
            if (!root.ContainsKey(path.Last()))
                root.Add(path.Last(), value);
            else
                root[path.Last()] = value;
        }
    }*/

    /*private Hashtable<String, TDNValue> GetRootData(String[] path, boolean createNewRoots = false)
    {
        IEnumerable<string> rootPath = path.Take(path.Length - 1);

        Dictionary<string, TDNValue> currTable = rootData;
        foreach (var rootName in rootPath)
        {
            if (createNewRoots && !currTable.ContainsKey(rootName))
                currTable.Add(rootName, new TDNValue(new TDNRoot(), new TDNRootParser()));
            TDNValue newRoot = currTable[rootName];
            if (newRoot.Parser.TypeKey != new TDNRootParser().TypeKey)
                throw new ArgumentException($"Root \"{ rootName }\" in path \"{ string.Join(".", path) }\" is not a valid root!");
            currTable = ((TDNRoot)newRoot.Value).rootData;
        }

        return currTable;
    }*/

    /*public void WriteToStream(BufferedWriter s)
    {
        TDNStreamWriter sw = new TDNStreamWriter(new StreamWriter(s));

        new TDNRootParser().WriteToStream(sw, this);
    }*/

    /*public static TDNRoot ReadFromStream(Stream s)
    {
        TDNStreamReader reader = new TDNStreamReader(new StreamReader(s));
        TDNValue objVal = new TDNRootParser().ReadFromStream(reader);
        return objVal.Value as TDNRoot;
    }*/
    
    public void insertValue(String key, TDNValue value)
    {
    	rootData.put(key, value);
    }

	@Override
	public Iterator<Entry<String, TDNValue>> iterator()
	{
		return rootData.entrySet().iterator();
	}
}
