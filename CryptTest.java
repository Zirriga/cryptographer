import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CryptTest {
    @Test
    public void withEmptyFile() throws IOException{
        Crypt crypt =
                new Crypt(
                        "0123456",
                        null,
                        "file.txt",
                        "file.txt.xor"
                );
        crypt.start();
        byte[] res = Files.readAllBytes(Paths.get("file.txt.xor"));
        assertEquals("H#E`", res);
    }
// xor тест, показывающий, что он рботает правильно, потом сравнение текст.тхт с текст.тхт.хор.хор
    //пустой, оч большой, обычный
}