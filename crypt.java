import java.io.FileReader;

public class crypt {
    public static void main(String[] args) throws Exception {
        try(FileReader reader = new FileReader("C:\Users\User\IdeaProjects\cryptographer\src\text.txt)")){
            //reader.close();
            //int[] words = ;
            //for (int i = 0; i < reader.)
        }


    }

    public static byte[] encode(String pText, String pKey) {
        byte[] txt = pText.getBytes();
        byte[] key = pKey.getBytes();
        byte[] res = new byte[pText.length()];
        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }
        return res;
    }

    public static String decode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();
        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }
        return new String(res);
    }

}
