package zad1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
public class Futil implements FileVisitor<Path>{
    private static FileChannel fileChannel;
    private String codeOfFile = "Cp1250";

    public static void processDir(String directory, String fileNameConstructor) {
        File file = new File(fileNameConstructor);
        Path path = Paths.get(file.getPath());
        try {
            fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND,StandardOpenOption.WRITE);
            Path walkFileTree = Files.walkFileTree(Paths.get(directory), new Futil());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws FileNotFoundException, IOException {
        if (attr.isRegularFile()) {
            FileChannel inputFileChannel = FileChannel.open(file);
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) attr.size());
            byteBuffer.clear();
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
            CharBuffer charBuffer = Charset.forName(codeOfFile).decode(byteBuffer);
            ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode(charBuffer);
            while(byteBuffer1.hasRemaining())
                fileChannel.write(byteBuffer1);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
