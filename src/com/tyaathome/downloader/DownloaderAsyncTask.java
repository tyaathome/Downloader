package com.tyaathome.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DownloaderAsyncTask extends AsyncTask<Object, Void, Void>{

	private String mURL = "http://182.92.172.198/img/http?fn1=2a38a4a9316c49e5a833517c45d31070&fn2=8c3039bd5842dca3d944faab91447818_outPut";
	private Bitmap bitmap = null;
	
	@Override
	protected Void doInBackground(Object... params) {
        String PATH = (String) params[0];
        int x = (Integer) params[1];
        int y = (Integer) params[2];
        int z = (Integer) params[3];
        try {
			String urlPath = URLDecoder.decode(mURL + "&param=" + x + "|" + y + "|" + z + "|256", HTTP.UTF_8);
			URL url = new URL(urlPath);
			InputStream is = url.openStream();
			bitmap = BitmapFactory.decodeStream(is);
			saveBitmap(PATH, x, y, z, bitmap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void saveBitmap(String path, int x, int y, int z, Bitmap bm) {
		try {
			path = path + z + File.separator;
			String name = x + "|" + y + ".jpg";
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File(path, name);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
