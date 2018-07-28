package fr.thorgal.ciphyor;

import com.google.common.io.BaseEncoding;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ciphyor {

    //We will use Secure Hash Algorithm 3 (SHA-3-512) with BouncyCastle in order to generate our hashes
    private static SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
    //The alphabet which contains all the characters (we will cipher the base32 so we do not need more)
    private static List<Character> alphabet = Arrays.asList(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );


    public static String encode(String key, String input) {

        int salt = generateSalt(key);

        //I'll replace the java Random by another cryptographically secure deterministic pseudo-random number generator (CSPRNG)
        //because Java uses a linear congruency generator that does not good quality random

        //The random number generator is initialized with the seed
        Random random = new Random(ByteBuffer.wrap(getHash(key, salt)).getInt());

        //we encode the message to base32 using Guava
        String message = BaseEncoding.base32().encode(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder output = new StringBuilder();
        for (char c : message.toCharArray())
            if (c != '=')
                output.append(getChar(alphabet.indexOf(c) + random.nextInt(alphabet.size())));

        String encryptedSalt = BaseEncoding.base32().encode(ByteBuffer.allocate(4).putInt(salt).array()).replace("=", "");
        return encryptedSalt + "" + output.toString();
    }


    public static String decode(String key, String input) {

        //The first 7 characters are the salt
        int salt = ByteBuffer.wrap(
                BaseEncoding.base32().decode(
                        String.format("%.7s", input)
                )
        ).getInt();
        String cipheredMessage = input.replaceFirst(
                String.format("%.7s", input), "");

        //We recover the seed from the salt and the key and we intialize the generator with it
        Random random = new Random(ByteBuffer.wrap(getHash(key, salt)).getInt());

        StringBuilder decipheredOutput = new StringBuilder();
        for (char c : cipheredMessage.toCharArray())
            decipheredOutput.append(getChar(alphabet.indexOf(c) - random.nextInt(alphabet.size())));

        return new String(BaseEncoding.base32().decode(decipheredOutput.toString()), StandardCharsets.UTF_8);
    }


    private static int generateSalt(String key) {
        int keyHash = ByteBuffer.wrap(digestSHA3.digest(key.getBytes())).getInt();

        int randomNumberOrigin = keyHash < 0
                ? Integer.MIN_VALUE + keyHash
                : Integer.MIN_VALUE;
        int randomNumberBound = keyHash > 0
                ? Integer.MAX_VALUE - keyHash
                : Integer.MAX_VALUE;

        return new Random().ints(randomNumberOrigin, randomNumberBound).findFirst().getAsInt();
    }

    private static byte[] getHash(String key, int salt) {
        int keyHash = ByteBuffer.wrap(digestSHA3.digest(key.getBytes())).getInt();
        return digestSHA3.digest(ByteBuffer.allocate(4).putInt(keyHash + salt).array());
    }

    private static char getChar(int position) {
        if (position < 0)
            position += alphabet.size();
        else if (position >= alphabet.size())
            position -= alphabet.size();
        return alphabet.get(position);
    }

}
