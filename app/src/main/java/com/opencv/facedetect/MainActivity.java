package com.opencv.facedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.BOWImgDescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private double max_size = 1024;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView myImageView;
    private Bitmap selectbp;

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCVLoader", "init failed");
        }
    }

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        String p1 = Environment.getExternalStorageDirectory()
                .getAbsolutePath()+ "/test/1.jpg";
        String p2 = Environment.getExternalStorageDirectory()
                .getAbsolutePath()+ "/test/2.jpg";
        match(p1,p2);
    }

    private boolean match(String imgPath1, String imgPath2) {
        //加载图片
        File p1 = new File(imgPath1);
        File p2 = new File(imgPath2);
        if (!p1.exists() || !p2.exists()) return false;
        Mat srcA = Imgcodecs.imread(imgPath1);
        Mat srcB = Imgcodecs.imread(imgPath2);

        //寻找关键点
        FastFeatureDetector fd = FastFeatureDetector.create(40,true,FastFeatureDetector.TYPE_9_16);
        MatOfKeyPoint keyPointA = new MatOfKeyPoint();
        MatOfKeyPoint keyPointB = new MatOfKeyPoint();
        fd.detect(srcA, keyPointA);
        fd.detect(srcB, keyPointB);

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath1);
        imageView.setImageBitmap(bitmap);
//
//        //计算关键点的特征
//
//        BOWImgDescriptorExtractor de = BOWImgDescriptorExtractor.__fromPtr__(10);
//        Mat descriptionA = new Mat();
//        Mat descriptionB = new Mat();
//        de.compute(srcA, keyPointA, descriptionA);
//        de.compute(srcB, keyPointB, descriptionB);
//
//        //匹配两张图片关键点的特征
//        List<MatOfDMatch> matches = new ArrayList<>();
//        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
//        matcher.radiusMatch(descriptionA, descriptionB, matches, 200f);
//
//        //画图显示匹配结果（演示用，非必须，可注释）
//        Mat dst = new Mat();
//        Features2d.drawMatches(srcA, keyPointA, srcB, keyPointB, matches.get(0), dst);
//        Bitmap bitmap = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_4444);
//        Utils.matToBitmap(dst, bitmap);
//        imageView.setImageBitmap(bitmap);
//
//        //根据匹配率判断是否匹配(以下处理不科学，无视掉）
//        int total = Math.min(keyPointA.rows(), keyPointB.rows());
//        int matchedNum = 0;
//        for (MatOfDMatch match : matches) {
//            if (match.rows() != 0) matchedNum++;
//        }
//        float ratio = matchedNum * 1.0f / total;
//        if (ratio > 0.75f) return true;
        return false;
    }
}
