package br.com.deliverytracker.xmpp;

import java.util.Arrays;

import org.jivesoftware.smack.util.stringencoder.Base64;
import org.jivesoftware.smack.util.stringencoder.Base64.Encoder;

public class Base64Utils {
	
 static void init() {
		Base64.setEncoder(new Encoder() {
			
			@Override
			public String encodeToString(byte[] input, int offset, int len) {
				return java.util.Base64.getEncoder().encodeToString(Arrays.copyOfRange(input, offset, len));
			}
			
			@Override
			public byte[] encode(byte[] input, int offset, int len) {
				return java.util.Base64.getEncoder().encode(Arrays.copyOfRange(input, offset, len));
			}
			
			@Override
			public byte[] decode(byte[] input, int offset, int len) {
				return java.util.Base64.getDecoder().decode(Arrays.copyOfRange(input, offset, len));
			}
			
			@Override
			public byte[] decode(String string) {
				return java.util.Base64.getDecoder().decode(string);
			}
		});

	}

}
