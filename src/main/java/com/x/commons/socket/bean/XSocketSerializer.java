package com.x.commons.socket.bean;

import com.x.commons.socket.core.ISocketSerializer;
import com.x.commons.util.bean.ByteArray;
import com.x.commons.util.bean.New;
import io.netty.buffer.ByteBuf;

import java.util.List;

import static com.x.commons.socket.bean.XSocketProtocol.END;
import static com.x.commons.socket.bean.XSocketProtocol.START;

/**
 * @Desc
 * @Date 2020-03-28 16:37
 * @Author AD
 */
public class XSocketSerializer implements ISocketSerializer<XSocketProtocol> {
    
    @Override
    public ByteBuf encode(XSocketProtocol prtc, ByteBuf buf) throws Exception {
        buf.writeByte(prtc.getStart());
        buf.writeInt(prtc.getLength());
        buf.writeBytes(prtc.getData());
        buf.writeByte(prtc.getCheck());
        buf.writeByte(prtc.getEnd());
        return buf;
    }
    
    @Override
    public List<XSocketProtocol> decode(ByteBuf buf) throws Exception {
        List<XSocketProtocol> prtcs = New.list();
        while (buf.isReadable()) {
            int index = buf.readerIndex();
            byte b = buf.readByte();
            if (b == START) {
                if (buf.readableBytes() < 4) {
                    buf.readerIndex(index);
                    break;
                } else {
                    int len = buf.readInt();
                    if (buf.readableBytes() < len + 2) {
                        buf.readerIndex(index);
                        break;
                    } else {
                        byte sumCheck = 0;
                        ByteArray out = New.byteArray();
                        while (len-- > 0) {
                            byte data = buf.readByte();
                            out.write(data);
                            sumCheck += data;
                        }
                        byte check = buf.readByte();
                        if (sumCheck == check) {
                            byte end = buf.readByte();
                            if (end == END) {
                                prtcs.add(new XSocketProtocol(out.get(), sumCheck));
                            } else {
                                buf.readerIndex(++index);
                                out.close();
                            }
                        } else {
                            buf.readerIndex(++index);
                            out.close();
                        }
                    }
                }
            }
        }
        return prtcs;
    }
    
}
