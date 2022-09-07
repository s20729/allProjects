package Zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.*;

public class Visit extends SimpleFileVisitor<Path> {
    FileChannel writeChannel;
    Charset cp1250 = Charset.forName("Cp1250");
    Charset utf_8 = StandardCharsets.UTF_8;

    public Visit(String result){
        try {
            this.writeChannel = FileChannel.open(Paths.get(result), EnumSet.of(CREATE,WRITE,TRUNCATE_EXISTING));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeFile(ByteBuffer byteBuffer, FileChannel readChannel) throws IOException {
        byteBuffer.clear();
        readChannel.read(byteBuffer);
        byteBuffer.flip();
        CharBuffer charBuffer = cp1250.decode(byteBuffer);
        ByteBuffer byteBufferDecode = utf_8.encode(charBuffer);
        while (byteBufferDecode.hasRemaining()){
            writeChannel.write(byteBufferDecode);
        }
        readChannel.close();
    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            FileChannel readChannel = FileChannel.open(file);
ByteBuffer byteBuffer = ByteBuffer.allocate((int) attrs.size());
writeFile(byteBuffer,readChannel);
        return FileVisitResult.CONTINUE;

    }
}
