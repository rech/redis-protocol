package redis.reply;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import redis.RedisProtocol;

/**
* Created by IntelliJ IDEA.
* User: sam
* Date: 7/29/11
* Time: 10:23 AM
* To change this template use File | Settings | File Templates.
*/
public class BulkReply implements Reply<ChannelBuffer> {
  public static final char MARKER = '$';
  private final ChannelBuffer bytes;

  public BulkReply(ChannelBuffer bytes) {
    this.bytes = bytes;
  }

  @Override
  public ChannelBuffer data() {
    return bytes;
  }

  public String asAsciiString() {
    if (bytes == null) return null;
    return bytes.toString(Charsets.US_ASCII);
  }

  public String asUTF8String() {
    if (bytes == null) return null;
    return bytes.toString(Charsets.UTF_8);
  }

  public String asString(Charset charset) {
    if (bytes == null) return null;
    return bytes.toString(charset);
  }

  @Override
  public void write(ChannelBuffer os) throws IOException {
    os.writeByte(MARKER);
    os.writeBytes(RedisProtocol.toBytes(bytes.capacity()));
    os.writeBytes(CRLF);
    os.writeBytes(bytes);
    os.writeBytes(CRLF);
  }
}
