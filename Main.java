import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class Main {
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
            new Main().launch(args);
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
        Crypt crypt = new Crypt(encodeKey, decodeKey, inputFile, outputFile);
        crypt.start();
    }
}
