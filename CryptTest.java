import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CryptTest {
    @Test
    public void xorTest() throws IOException { //проверка того, что xor работает правильно
        Crypt crypt =
                new Crypt(
                        "0123456",
                        null,
                        "file.txt",
                        "file.txt.xor"
                );
        crypt.start();
        byte[] res = Files.readAllBytes(Paths.get("file.txt.xor"));
        assertEquals("H", new String(res));
    }

    @Test
    public void emptyFileTest() throws IOException { //проверка работы с пустым файлом (здесь же проверяем работу encode)
        Crypt crypt =
                new Crypt(
                        "0123456",
                        null,
                        "empty.txt",
                        "empty.txt.xor"
                );
        Crypt decodeCrypt =
                new Crypt(null,
                        "0123456",
                        "empty.txt.xor",
                        "empty.txt.xor.xor"
                );
        crypt.start();
        decodeCrypt.start();
        byte[] result = Files.readAllBytes(Paths.get("empty.txt"));
        byte[] expect = Files.readAllBytes(Paths.get("empty.txt.xor.xor"));
        assertEquals(new String(expect), new String(result));
    }

    @Test
    public void normalFileTest() throws IOException { //проверка работы с обычным файлом
        Crypt crypt =
                new Crypt(
                        "0123456",
                        null,
                        "normalFile.txt",
                        "normalFile.txt.xor"
                );
        Crypt decodeCrypt =
                new Crypt(null,
                        "0123456",
                        "normalFile.txt.xor",
                        "normalFile.txt.xor.xor"
                );
        crypt.start();
        decodeCrypt.start();
        byte[] result = Files.readAllBytes(Paths.get("normalFile.txt"));
        byte[] expect = Files.readAllBytes(Paths.get("normalFile.txt.xor.xor"));
        assertEquals(new String(expect), new String(result));
    }

    @Test
    public void bigfFileTest() throws IOException { //проверка работы с большим файлом
        Crypt crypt =
                new Crypt(
                        "0123456",
                        null,
                        "bigFile.txt",
                        "bigFile.txt.xor"
                );
        Crypt decodeCrypt =
                new Crypt(null,
                        "0123456",
                        "bigFile.txt.xor",
                        "bigFile.txt.xor.xor"
                );
        crypt.start();
        decodeCrypt.start();
        byte[] result = Files.readAllBytes(Paths.get("bigFile.txt"));
        byte[] expect = Files.readAllBytes(Paths.get("bigFile.txt.xor.xor"));
        assertEquals(new String(expect), new String(result));
    }

    @Test
    void noKey(TestInfo testInfo) {//нет ключа
        final Crypt crypt = new Crypt(
                null,
                null,
                "file.txt",
                "file.txt.xor");
        IOException exception = assertThrows(IOException.class, () -> {
            crypt.start();
        });
        assertEquals("No decode or encode key", exception.getMessage());
    }

    @Test
    void twoKeys(TestInfo testInfo) {//нет ключа
        final Crypt crypt = new Crypt(
                "0123456",
                "0123456",
                "file.txt",
                "file.txt.xor");
        IOException exception = assertThrows(IOException.class, () -> {
            crypt.start();
        });
        assertEquals("Two codes", exception.getMessage());
    }

    @Test
    void noFile(TestInfo testInfo) {//нет файла
        final Crypt crypt = new Crypt(
                "0123456",
                null,
                "cat.txt",
                null);
        assertThrows(FileNotFoundException.class, crypt::start);
    }
// xor тест, показывающий, что он рботает правильно, потом сравнение текст.тхт с текст.тхт.хор.хор
    //пустой, оч большой, обычный
}