package YccStudio.WebCam;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

//import static org.bytedeco.opencv.global.opencv_core.*;
//import static org.bytedeco.opencv.global.opencv_imgproc.*;
//import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

public class Smoother {
    public static void smooth(String filename) {
        Mat image=org.bytedeco.opencv.global.opencv_imgcodecs.imread(filename);
        if(image!=null) {
            org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(image, image, new Size(3, 3), 0);
            org.bytedeco.opencv.global.opencv_imgcodecs.imwrite(filename, image);
        }
    }
}
