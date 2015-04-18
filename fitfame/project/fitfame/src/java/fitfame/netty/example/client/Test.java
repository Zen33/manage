package fitfame.netty.example.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import fitfame.security.utils.AesUtils;

import javax.crypto.NoSuchPaddingException;

public class Test {

	public static void main(String[] args) throws NoSuchPaddingException, GeneralSecurityException, IOException {
		
		
		String key = "1234567890lkdier";
		String origin = "Hello world !!";
		
		
		String aesEncrypt = AesUtils.encrypt(origin);
		System.out.println("encrypte :" + aesEncrypt);
		
		String decrypted = AesUtils.decrypt(aesEncrypt);
		System.out.println("decrypted :" + decrypted);
				

	}
}
