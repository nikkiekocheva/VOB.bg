package util.validation;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.RgbToBgr;
import org.jcodec.scale.Transform;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class VideoFrameExtracter {
	public File createThumbnailFromVideo(File file, int frameNumber) throws IOException, JCodecException {
        Picture frame = FrameGrab.getFrameFromFile(file, frameNumber);
        File tempFile = File.createTempFile("C:\\Users\\Asus\\Desktop\\videos\\thumb_" + frameNumber + file.getName().replaceAll("(.+)\\..+", "$1"), ".png");
        ImageIO.write(toBufferedImage(frame), "png", tempFile);
        return tempFile;
    }
    // this method is from Jcodec AWTUtils.java.
    private BufferedImage toBufferedImage(Picture src) {
        if (src.getColor() != ColorSpace.RGB) {
        	Transform transform = ColorUtil.getTransform(src.getColor(), ColorSpace.RGB);
            if (transform == null) {
                throw new IllegalArgumentException("Unsupported input colorspace: " + src.getColor());
            }
            Picture out = Picture.create(src.getWidth(), src.getHeight(), ColorSpace.RGB);
            transform.transform(src, out);
            new RgbToBgr().transform(out, out);
            src = out;
        }
        BufferedImage dst = new BufferedImage(src.getCroppedWidth(), src.getCroppedHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        if (src.getCrop() == null)
            toBufferedImage2(src, dst);
        else
            toBufferedImageCropped(src, dst);
        return dst;
    }
    // this method is from Jcodec AWTUtils.java.
    private void toBufferedImage2(Picture src, BufferedImage dst) {
        byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        byte[] srcData = src.getPlaneData(0);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (srcData[i] + 128);
        }
    }
    // this method is from Jcodec AWTUtils.java.
    private static void toBufferedImageCropped(Picture src, BufferedImage dst) {
        byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        byte[] srcData = src.getPlaneData(0);
        int dstStride = dst.getWidth() * 3;
        int srcStride = src.getWidth() * 3;
        for (int line = 0, srcOff = 0, dstOff = 0; line < dst.getHeight(); line++) {
            for (int id = dstOff, is = srcOff; id < dstOff + dstStride; id += 3, is += 3) {
                data[id] = (byte) (srcData[is] + 128);
                data[id + 1] = (byte) (srcData[is + 1] + 128);
                data[id + 2] = (byte) (srcData[is + 2] + 128);
            }
            srcOff += srcStride;
            dstOff += dstStride;
        }
    }
}
