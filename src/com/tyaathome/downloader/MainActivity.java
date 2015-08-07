package com.tyaathome.downloader;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	//start°´Å¥
	private Button btnStart = null;
	//Ïß³Ì³Ø
	private ExecutorService downloader = Executors.newFixedThreadPool(getNumCores()*2);
	
	private String PATH_BASE = null;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	public static int nCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStart = (Button) findViewById(R.id.start);
		btnStart.setOnClickListener(this);
		getStorage();
		PATH_BASE = PATH_BASE + "downloader" + File.separator;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.start: {
			int i = 0;
			nCount = 0;
			for(z = 0; z <= 4; z++){
				for(x = 0; x <= 12; x++) {
					for(y = 0; y <= 7; y++) {
						DownloaderAsyncTask task = new DownloaderAsyncTask();
						task.executeOnExecutor(downloader, PATH_BASE, x, y, z);
						i++;
					}
				}
			}
			
			break;
		}
		}
		
	}
	
    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }
    
	private String getStorage() {
		if (PATH_BASE == null) {
			List<String> list = new ArrayList<String>();
			list.add(Environment.getExternalStorageDirectory() + "/");
			list.add(Environment.getExternalStorageDirectory() + "0/");
			list.add(Environment.getExternalStorageDirectory() + "1/");
			list.add(Environment.getExternalStorageDirectory() + "2/");
			list.add("/mnt/usb/sdb1/");
			list.add("/mnt/usb/sda1/");
			list.add("/mnt/usb/sdc1/");
			list.add("/mnt/usb/sdd1/");
			list.add("/sdcard/");
			list.add("/sdcard0/");
			list.add("/sdcard1/");
			list.add("/sdcard2/");
			list.add("/storage/sdcard/");
			list.add("/storage/sdcard0/");
			list.add("/storage/sdcard1/");
			list.add("/storage/sdcard2/");

			File file = null;
			for (int i = 0; i < list.size(); i++) {
				file = new File(list.get(i));
				if (file.canWrite()) {
					PATH_BASE = list.get(i);
					break;
				}
			}
			if (PATH_BASE == null) {
				for (int i = 0; i < list.size(); i++) {
					file = new File(list.get(i));
					if (file.exists()) {
						PATH_BASE = list.get(i);
						break;
					}
				}
			}

			PATH_BASE += getPackageName()
					+ "/";
		}

		return PATH_BASE;
	}
}
