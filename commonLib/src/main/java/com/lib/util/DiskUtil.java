package com.lib.util;

import android.content.Context;
import android.os.Environment;

import com.lib.LibApplication;

import java.io.File;

public class DiskUtil {
	private static DiskUtil diskUtil;

	public static final String FILENAME = LibApplication.getCommonLibContext().getPackageName();

	public static DiskUtil getInstance() {
		if (diskUtil == null) {
			synchronized (DiskUtil.class){
				if (diskUtil == null) {
					diskUtil = new DiskUtil();
				}
			}
		}
		return diskUtil;
	}

	public void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	public void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}

	public void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/com.xxx.xxx/files下的内容
	 * @param context
	 */
	public void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 * @param context
	 */
	public void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 * @param filePath
	 */
	public void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * 清除本应用所有的数据
	 * @param context
	 * @param filepath
	 */
	public void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 * @param directory
	 */
	private void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}
