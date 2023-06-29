package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import java.util.Base64;

public class ForgivingBase64Decoder {

	private boolean debug = false;

	// see also https://www.baeldung.com/java-base64-encode-and-decode
	// [System.Text.Encoding]::ASCII.GetString([System.Convert]::FromBase64String("dGVzdCBkYXRhDQo="))
	// test data
	//
	// [System.Text.Encoding]::ASCII.GetString([System.Convert]::FromBase64String("dGVzdCBkYXRhDQo"))
	// Exception calling "FromBase64String" with "1" argument(s):
	// "Invalid length for a Base-64 char array or string."
	// echo 'dGVzdCBkYXRhDQo'> a
	// base64 -d a
	// test data
	// base64: invalid input
	// java -cp target\example-commandline-parser-0.6.0-SNAPSHOT.jar
	// example.ForgivingBase64Decoder dGVzdCBkYXRhDQo
	// Result: test data

	public static void main(String[] args) {
		System.err
				.println("Result: " + new String(Base64.getDecoder().decode(args[0])));
	}
}
