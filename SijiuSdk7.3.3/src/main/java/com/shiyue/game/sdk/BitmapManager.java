package com.shiyue.game.sdk;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
/**
 * 异步线程加载图片工具类
 * 使用说明：
 * BitmapManager bmpManager;
 * bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpManager.loadBitmap(imageURL, imageView);
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-6-25
 */
public class BitmapManager {  
	  
    public static HashMap<String, SoftReference<Bitmap>> cache;  
    private static ExecutorService pool;  
    private static Map<ImageView, String> imageViews;  
    private Bitmap defaultBmp;  
    
    static {  
        cache = new HashMap<String, SoftReference<Bitmap>>();  
        pool = Executors.newFixedThreadPool(5);  //固定线程池
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    }  
    
    public BitmapManager(){}
    
    public BitmapManager(Bitmap def) {
    	this.defaultBmp = def;
    }
    
    /**
     * 设置默认图片
     * @param bmp
     */
    public void setDefaultBmp(Bitmap bmp) {  
    	defaultBmp = bmp;  
    }   
  
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView) {  
   	loadBitmap(url, imageView, this.defaultBmp, 0, 0);
    }
    public void loadBitmap2(String url, ImageView imageView) {  
        	loadBitmap(url, imageView, this.defaultBmp, 50, 50);
        }
	
    /**
     * 加载图片-可设置加载失败后显示的默认图片
     * @param url
     * @param imageView
     * @param defaultBmp
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {  
    	loadBitmap(url, imageView, defaultBmp, 0, 0);
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height 
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp, int width, int height) {  
        imageViews.put(imageView, url);  
       Bitmap bitmap = getBitmapFromCache(url);  
   
        if (bitmap != null) {  
			//显示缓存图片
            imageView.setImageBitmap(bitmap);  
        } else {  
//        	//加载SD卡中的图片缓存
//        	String filename = FileUtils.getFileName(url);
//        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
//    		File file = new File(filepath);
//    		if(file.exists()){
//				//显示SD卡中的图片缓存
//    			Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(), filename);
//        		imageView.setImageBitmap(bmp);
//        	}else{
				//线程加载网络图片
        		imageView.setImageBitmap(defaultBmp);
        		queueJob(url, imageView, width, height);
//        	}
        }  
    }  
    
  /*  public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp, int width, int height) {  
        imageViews.put(imageView, url);  
       Bitmap bitmap = getBitmapFromCache(url);  
   
        if (bitmap != null) {  
			//显示缓存图片
            imageView.setImageBitmap(bitmap);  
        } else {  
//        	//加载SD卡中的图片缓存
//        	String filename = FileUtils.getFileName(url);
//        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
//    		File file = new File(filepath);
//    		if(file.exists()){
//				//显示SD卡中的图片缓存
//    			Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(), filename);
//        		imageView.setImageBitmap(bmp);
//        	}else{
				//线程加载网络图片
        		imageView.setImageBitmap(defaultBmp);
        		queueJob(url, imageView, width, height);
//        	}
        }  
    }  */
  
    /**
     * 从缓存中获取图片
     * @param url
     */
    public Bitmap getBitmapFromCache(String url) {  
    	Bitmap bitmap = null;
        if (cache.containsKey(url)) {  
            bitmap = cache.get(url).get();  
        }  
        return bitmap;  
    }  
    
    /**
     * 从网络中加载图片
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void queueJob(final String url, final ImageView imageView, final int width, final int height) {  
        /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                        imageView.setImageBitmap((Bitmap) msg.obj);  
                        try {
                        	//向SD卡中写入图片缓存
							ImageUtils.saveImage(imageView.getContext(), FileUtils.getFileName(url), (Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    } 
                }  
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height);  
                handler.sendMessage(message);  
            }  
        });  
    } 
  
    /**
     * 下载图片-可指定显示图片的高宽
     * @param url
     * @param width
     * @param height
     */
    private Bitmap downloadBitmap(String url, int width, int height) {   
        Bitmap bitmap = null;
        try {
			//http加载图片
			bitmap = ApiClient.getNetBitmap(url);
			//bitmap = toRoundCorner(bitmap, 3);
			if(width > 0 && height > 0) {
				//指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
				
			} 
			//放入缓存
			cache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bitmap;  
    }
    
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
    	
    	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
    	Canvas canvas = new Canvas(output); 

    	final int color = 0xff424242; 
    	final Paint paint = new Paint(); 
    	final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
    	final RectF rectF = new RectF(rect); 
    	final float roundPx = pixels; 

    	paint.setAntiAlias(true); 
    	canvas.drawARGB(0, 0, 0, 0); 
    	paint.setColor(color); 
    	canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 

    	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
    	canvas.drawBitmap(bitmap, rect, rect, paint); 

    	return output; 
    }

}