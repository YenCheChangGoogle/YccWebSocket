package YccStudio.WebCam;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;

public class WebCamDemo3 {
    public static void main(String[] args) throws Exception {
        CanvasFrame canvas=new CanvasFrame("WebCam Demo");
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        VideoInputFrameGrabber webcam=new VideoInputFrameGrabber(0); // 1 for next camera
        FrameGrabber grabber=webcam;

        grabber.start();

        OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();

        Frame frame;
        while(true) {
            frame=grabber.grab();
            if(frame!=null) {
                canvas.showImage(frame);
                IplImage img=converter.convert(frame);

                BufferedImage bi=IplImageToBufferedImage(img);

                ImageIO.write(bi, "jpg", new File("C:/Test/Y.jpg"));

                canvas.waitKey(1000);
            }
        }
    }

    public static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter=new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter=new Java2DFrameConverter();
        Frame frame=grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
    }
}
