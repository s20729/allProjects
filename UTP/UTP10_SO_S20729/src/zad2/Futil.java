package zad2;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
public class Futil {
    private static Path outputPath;
    private static FileChannel outputFileChannel;
    private static FileChannel inputFileChannel;
    private static ByteBuffer buffer;

    public static void processDir(String dirName, String resultFileName) {
        outputPath = Paths.get(dirName + "\\" + resultFileName);
        try {
            outputFileChannel = FileChannel.open(outputPath, new OpenOption[] {StandardOpenOption.CREATE, StandardOpenOption.APPEND});
            Files.walk(Paths.get(dirName), FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .filter(e -> !e.getFileName().toString().equals(resultFileName))
                    .forEach(e -> {

                        try {
                            inputFileChannel = FileChannel.open(e);
                            buffer = ByteBuffer.allocate((int) (new File(e.toString()).length()));
                            buffer.clear();
                            inputFileChannel.read(buffer);
                        } catch (IOException e1) {
                            System.err.println("processDir -> walkFileTree stream -> map -> IOException1");
                            e1.printStackTrace();
                        }
                        buffer.flip();
                        CharBuffer charBuffer = Charset.forName("Cp1250").decode(buffer);
                        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);

                        while(byteBuffer.hasRemaining()) {
                            try {
                                outputFileChannel.write(byteBuffer);
                            } catch (IOException e1) {
                                System.err.println("processDir -> walkFileTree stream -> map -> IOException2");
                                e1.printStackTrace();
                            }
                        }
                        System.out.println(e);
                    });

        } catch (IOException e) {
            System.err.println("processDir -> IOException !");
            e.printStackTrace();
        }
    }
}
