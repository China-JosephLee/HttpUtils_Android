package top.gardel.httputils;

import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;
import android.app.Activity;

public class HttpUtils {
	public static void Get(final Activity context, final String urlpath, final TreeMap<String, String> paramMap, final EventHander event) {
		new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final String res = HttpRequest.Get(urlpath, paramMap);
						context.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									event.onSuccess(res);
								}
							});
					} catch (IOException e) {
						event.onFailed(e.getMessage());
					}
				}
		}).start();
	}

	public static void Download(final Activity context, final String urlpath, final TreeMap<String, String> paramMap, final String filepath, final EventHander event) {
		new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final java.io.File res = HttpRequest.Download(urlpath, paramMap, filepath);
						context.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									event.onSuccess(res);
								}
							});
					} catch (IOException e) {
						event.onFailed(e.getMessage());
					}
				}
			}).start();
	}

	public static void Post(final Activity context, final String urlpath,final TreeMap<String, String> paramMap, final boolean isMultipart, final EventHander event) {
		new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final String res = HttpRequest.Post(urlpath, paramMap, isMultipart);
						context.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									event.onSuccess(res);
								}
							});
					} catch (IOException e) {
						event.onFailed(e.getMessage());
					}
				}
			}).start();
	}
}
