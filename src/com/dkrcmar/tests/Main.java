package com.dkrcmar.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import com.dkrcmar.tdn.base.*;
import com.dkrcmar.tdn.core.*;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		final String host = "localhost";
		final int portNumber = 11111;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);

		Socket socket = new Socket(host, portNumber);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		System.out.println("DONE!");
		while(true)
		{
			System.out.println("Waiting for root...");
			TDNRoot root = TDNRoot.readFromStream(br);
			System.out.println("New root recieved:");
			printRoot(root);
			root.writeToStream(bw);
		}
	}

	static void localTest() throws IOException
	{

		File streamFile = new File("temp.txt");

		TDNRootParser parser = new TDNRootParser();

		TDNRoot root1_2 = new TDNRoot();
		root1_2.insertValue("sHodnota", new TDNValue("root_1>2", new StringParser()));

		TDNRoot root1 = new TDNRoot();
		TDNArray array = new TDNArray(new Object[] { root1_2, root1_2 }, new TDNRootParser());
		root1.insertValue("arrHodnota", new TDNValue(array, new ArrayParser()));
		root1.insertValue("sHodnota", new TDNValue("ahoj", new StringParser()));
		root1.insertValue("iHodnota", new TDNValue(69, new IntegerParser()));
		root1.insertValue("fHodnota", new TDNValue(69.420f, new FloatParser()));
		root1.insertValue("bHodnota", new TDNValue(true, new BooleanParser()));
		root1.insertValue("root1_2", new TDNValue(root1_2, new TDNRootParser()));

		TDNRoot obj = new TDNRoot();
		obj.insertValue("rootHodnota", new TDNValue(root1, new TDNRootParser()));
		obj.insertValue("iHodnota", new TDNValue(420, new IntegerParser()));

		TDNRoot obj1 = new TDNRoot();
		obj1.put("iHodnota", new TDNValue(420, new IntegerParser()));
		obj1.put("rootHodnota.arrHodnota", new TDNValue(array, new ArrayParser()));
		obj1.put("rootHodnota.sHodnota", new TDNValue("ahoj", new StringParser()));
		obj1.put("rootHodnota.iHodnota", new TDNValue(69, new IntegerParser()));
		obj1.put("rootHodnota.fHodnota", new TDNValue(69.420f, new FloatParser()));
		obj1.put("rootHodnota.bHodnota", new TDNValue(true, new BooleanParser()));
		obj1.put("rootHodnota.root1_2.sHodnota", new TDNValue("root_1>2", new StringParser()));

		System.out.println("== original: ==");
		printRoot(obj);
		System.out.println("== original copy: ==");
		printRoot(obj1);

		if (!streamFile.exists())
		{
			streamFile.createNewFile();
		}

		FileWriter fw = new FileWriter(streamFile);
		BufferedWriter bw = new BufferedWriter(fw);

		TDNBufferedWriter writer = new TDNBufferedWriter(bw, new DefaultTDNParserSettings());

		parser.writeToStream(writer, obj);

		BufferedReader br = new BufferedReader(new FileReader("temp.txt"));
		TDNBufferedReader reader = new TDNBufferedReader(br, new DefaultTDNParserSettings());
		TDNRoot newObj = (TDNRoot) parser.readFromStream(reader).value;

		System.out.println("== from file: ==");
		printRoot(newObj);
	}

	static void printRoot(TDNRoot root)
	{
		System.out.println("(");
		for (Entry<String, TDNValue> val : root)
		{
			System.out.print(val.getKey());
			System.out.print(": ");
			if (val.getValue().value instanceof TDNRoot)
			{
				printRoot((TDNRoot) val.getValue().value);
				continue;
			}
			if (val.getValue().value instanceof TDNArray)
			{
				System.out.println("[");
				TDNArray arr = (TDNArray) val.getValue().value;
				for (Object item : arr)
				{
					System.out.print(",");
					if (arr.itemParser.typeKey().equals(new TDNRootParser().typeKey()))
						printRoot((TDNRoot) item);
					else
						System.out.println(item);
					continue;
				}
				System.out.println("]");
				continue;
			}
			System.out.println(val.getValue().value);
		}
		System.out.println(")");
	}

}
