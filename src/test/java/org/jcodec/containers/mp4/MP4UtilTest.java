package org.jcodec.containers.mp4;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

import org.jcodec.common.AutoFileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.jcodec.containers.mp4.MP4Util.Atom;
import org.junit.Test;

public class MP4UtilTest {
    @Test
    public void testReadWriteIphoneMp4() throws Exception {
        File f = new File("zhuker/1D158634-69DF-4C7F-AB6F-CCC83F04FEDB/1.mp4");
        ByteBuffer read = ByteBuffer.allocate(64 * 1024);
        MP4Util.parseMovie(f).write(read);
        read.flip();

        Atom atom = MP4Util.findFirstAtom("moov", new AutoFileChannelWrapper(f));
        MappedByteBuffer written = NIOUtils.map(f);
        written.position((int) atom.getOffset());
        written.limit((int) (written.position() + atom.getHeader().getSize()));

        assertTrue(read.equals(written));
    }
}
