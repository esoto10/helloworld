package com.dis.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Encriptacion {
	

	    public static String MD2 = "MD2";
	    public static String MD5 = "MD5";
	    public static String SHA1 = "SHA-1";
	    public static String SHA256 = "SHA-256";
	    public static String SHA384 = "SHA-384";
	    public static String SHA512 = "SHA-512";
	    private static final String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";

	    /***
	     * Convierte un arreglo de bytes a String usando valores hexadecimales
	     * @param digest arreglo de bytes a convertir
	     * @return String creado a partir de <code>digest</code>
	     */
	    private static String toHexadecimal(byte[] digest){
	        String hash = "";
	        for(byte aux : digest) {
	            int b = aux & 0xff;
	            if (Integer.toHexString(b).length() == 1) hash += "0";
	            hash += Integer.toHexString(b);
	        }
	        return hash;
	    }

	    /***
	     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
	     * @param message texto a encriptar
	     * @param algorithm algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	     * @return mensaje encriptado
	     */
	    public static String getStringMessageDigest(String message, String algorithm){
	        byte[] digest = null;
	        byte[] buffer = message.getBytes();
	        try {
	            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
	            messageDigest.reset();
	            messageDigest.update(buffer);
	            digest = messageDigest.digest();
	        } catch (NoSuchAlgorithmException ex) {
	            System.out.println("Error creando Digest");
	        }
	        return toHexadecimal(digest);
	    }
	    
	    /***
	     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
	     * @param length longitud del texto a generar
	     * @param algorithm algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	     * @return string aleatorio encriptado
	     */
	    public static String tokenGenerator(int length, String algorithm){
	    	Random rand = new Random(System.currentTimeMillis());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int pos = rand.nextInt(charset.length());
	            sb.append(charset.charAt(pos));
	        }
	        return getStringMessageDigest(sb.toString(),"MD5");
	    }
	    
	    public static int randomNumbers(int min, int max){
	    	return min + (int)(Math.random() * ((max - min) + 1));
	    }
	    
	    
	

}
