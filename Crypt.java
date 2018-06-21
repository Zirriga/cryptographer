import java.io.*;

import org.kohsuke.args4j.*;

public class Crypt {

    public byte[] key (String codeKey){
        byte[] byteKey = codeKey.getBytes();
        byte[] key = new byte[byteKey.length/2 + byteKey.length % 2];
        for (int i = 0; i < byteKey.length; i++) {
            if (byteKey[i] > 0x29 && byteKey[i] < 0x3a) {
                byteKey[i] -= 0x30;
            } else if (byteKey[i] > 0x40 && byteKey[i] < 0x47) {
                byteKey[i] = (byte)(byteKey[i] - 0x40 + 0x9);
            } else {
                System.err.println("Invalid Key");
            }
        }
        for (int i = 0; i < key.length; i++) {
            key[i] = byteKey[i*2];
            key[i] = (byte)(key[i] << 4);
            if (i * 2 + 1 < byteKey.length) key[i] += byteKey[i*2 + 1];
        }
        return codeKey.getBytes();
    }

    @Option(name = "-c", usage = "Encode it")
    private String encodeKey;

    @Option(name = "-d", usage = "Decode it")
    private String decodeKey;

    @Option(name = "-o", usage = "Output file")
    private String outputFile;

    @Argument(usage = "Input file", required = true)
    private String inputFile;

    public static void main(String[] args) {
        try {
            new Crypt().launch(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.exit(-1);
        }


        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out;
        if (outputFile != null) {
            out = new FileOutputStream(outputFile);

        } else {
            out = new FileOutputStream(inputFile + ".xor");
        }

        if (encodeKey != null || decodeKey != null) {
            byte[] key = key(encodeKey);
            if (key == null) key = key(decodeKey);
            byte[] line = new byte[key.length];
            while (in.read(line) != -1) {
                out.write(encodeAndDecode(line, key));
            }
        } else {
            System.err.println("No decode or encode key");
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
