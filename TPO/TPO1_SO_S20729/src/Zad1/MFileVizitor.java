package Zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class MFileVizitor extends SimpleFileVisitor<Path> {
    private FileChannel rez;
    FileChannel in;
    long fileSize;
    ByteBuffer buf;
    CharBuffer charbuf;
    ByteBuffer bytebuf;
    Charset inCharset  = Charset.forName("Cp1250");
    Charset outCharset = Charset.forName("UTF-8");
    MFileVizitor(Path resultFileName) throws IOException {
        rez = FileChannel.open(resultFileName, EnumSet.of(CREATE, APPEND));
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        in = FileChannel.open(file);
        fileSize = in.size();
        buf = ByteBuffer.allocate((int) fileSize);
        in.read(buf);
        buf.flip();
        charbuf = inCharset.decode(buf);
        bytebuf = outCharset.encode(charbuf);
        while (bytebuf.hasRemaining()){
            rez.write(bytebuf);
        }
        in.close();
        buf.clear();
        return FileVisitResult.CONTINUE;
    }
}
