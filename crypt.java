import java.io.*;

import org.kohsuke.args4j.*;

public class crypt {
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
            new crypt().launch(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.getMessage();
        }


        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out;
        if (outputFile != null) {
            out = new FileOutputStream(outputFile);

        } else {
            out = new FileOutputStream(inputFile + ".xor");
        }

        if (encodeKey != null) {
            byte[] line = new byte[encodeKey.length()];
            while (in.read(line) != -1) {
                out.write(encode(line, encodeKey));
            }

        } else if (decodeKey != null) {
            byte[] line = new byte[decodeKey.length()];
            while (in.read(line) != -1) {
                out.write(encode(line, decodeKey));
            }
        } else {
            System.err.println("No decode or encode key");
        }
    }

    public byte[] encode(byte[] pText, String pKey) {
        byte[] txt = pText;
        byte[] key = pKey.getBytes();
        byte[] res = new byte[pText.length];
        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }
        return res;
    }

    public byte[] decode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();
        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }
        return res;
    }

}
