import java.io.*;

public class Crypt {
    private String encodeKey;
    private String decodeKey;
    private String inputFile;
    private String outputFile;

    public Crypt(String encodeKey, String decodeKey, String inputFile, String outputFile) {
        this.encodeKey = encodeKey;
        this.decodeKey = decodeKey;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public byte[] key(String codeKey) {
        byte[] byteKey = codeKey.getBytes();
        byte[] key = new byte[byteKey.length / 2 + byteKey.length % 2];
        for (int i = 0; i < byteKey.length; i++) {
            if (byteKey[i] > 0x29 && byteKey[i] < 0x3a) {
                byteKey[i] -= 0x30;
            } else if (byteKey[i] > 0x40 && byteKey[i] < 0x47) {
                byteKey[i] = (byte) (byteKey[i] - 0x40 + 0x9);
            } else {
                System.err.println("Invalid Key");
            }
        }
        for (int i = 0; i < key.length; i++) {
            key[i] = byteKey[i * 2];
            key[i] = (byte) (key[i] << 4);
            if (i * 2 + 1 < byteKey.length) key[i] += byteKey[i * 2 + 1];
        }
        return key;
    }


    public void start() throws IOException {
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out;
        if (outputFile != null) {
            out = new FileOutputStream(outputFile);

        } else {
            out = new FileOutputStream(inputFile + ".xor");
        }
        if (encodeKey != null && decodeKey != null) throw new IOException("Two codes");
        if (encodeKey != null || decodeKey != null) {
            byte[] key;
            if (encodeKey != null) {
                key = key(encodeKey);
            } else {
                key = key(decodeKey);
            }
            byte sym;
            byte res;
            int index = 0;
            while ((sym = (byte) in.read()) != -1) {
                res = (byte) (sym ^ key[index]);
                index++;
                index %= key.length;
                out.write(res);
            }
        } else {
            throw new IOException("No decode or encode key");
        }
    }

    static byte[] encodeAndDecode(byte[] pText, byte[] pKey) {
        byte[] txt = pText;
        byte[] res = new byte[pText.length];
        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ pKey[i % pKey.length]);
        }
        return res;
    }
}
