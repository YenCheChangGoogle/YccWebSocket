package YccStudio.WebCam;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class WebCamDemo {
    public static void main(String[] args) throws Exception {
        CanvasFrame canvas=new CanvasFrame("WebCam Demo");
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        VideoInputFrameGrabber webcam=new VideoInputFrameGrabber(0); // 1 for next camera

        FrameGrabber grabber=webcam;

        grabber.start();
        Frame img;
        while(true) {
            img=grabber.grab();
            if(img!=null) {
                canvas.showImage(img);
            }
        }
    }
}
