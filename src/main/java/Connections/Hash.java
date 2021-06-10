package Connections;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class Hash {

    public String generateHash(String string) {
        return Hex.toHexString(new SHA3.Digest512().digest(string.getBytes()));
    }
}
 