package top.gardel.httputils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.util.TreeMap;
import java.util.Map;

/**
 * HTTP访问类
 * 可以POST,GET,Download(,Upload 未完成)
 * @author Gardel
 * @version 1.1.0
 * @package top.gardel.httputils
 * @copyright 迷雾工作室
 * @link http://www.mium.studio
 */
public class HttpRequest {
    private static String randomSting;//随机字符串
	private static CookieManager manager;
    public static int TIMEOUT = 10000;

    static {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789*_";   
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 15; i++) {
            sb.append(base.charAt(new java.util.Random().nextInt(base.length())));
        }
        randomSting = sb.toString();
		manager = new CookieManager();
		CookieHandler.setDefault(manager);
    }

    /**
     * GET方法
     * @param String urlpath 请求地址
     * @param TreeMap<String, String> paramMap 请求参数
     * @return String
     * @throws MalformedURLException,ProtocolException,IOException
     */
    public static String Get(String urlpath, TreeMap<String, String> paramMap) throws MalformedURLException,ProtocolException,HttpException,IOException {
        StringBuilder sbr = new StringBuilder();
        sbr.append(urlpath);
		if (null != paramMap) {
			sbr.append('?');
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				sbr.append(entry.getKey())
					.append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
					.append("&");
			}
			sbr.delete(sbr.lastIndexOf("&"), sbr.length());
		}
		URL url = new URL(sbr.toString());
        if (urlpath.startsWith("https://")) {
            HttpsURLConnection HttpConn = (HttpsURLConnection) url.openConnection();
            //设置参数
            //HttpConn.setDoOutput(true);
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);
            HttpConn.setRequestMethod("GET");

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
//			try {
//				URI uri = url.toURI();
//				java.util.List<HttpCookie> cookies = manager.getCookieStore().get(uri);
//				if (!cookies.isEmpty()) {
//					sbr = new StringBuilder();
//					for (HttpCookie cookie : cookies) {
//						sbr.append(cookie.getName())
//							.append('=')
//							.append(cookie.getValue())
//							.append("; ");
//					}
//					sbr.delete(sbr.lastIndexOf("; "), sbr.length());
//					HttpConn.setRequestProperty("Cookie", sbr.toString());
//				}
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			}
            HttpConn.connect();

            switch (HttpConn.getResponseCode()) {
                case HttpsURLConnection.HTTP_OK:
                    StringBuilder sb = new StringBuilder();
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(HttpConn.getInputStream(), "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine);
                    }
                    responseReader.close();
                    return sb.toString();
                case HttpsURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpsURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpsURLConnection.HTTP_FORBIDDEN:
                case HttpsURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpsURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        } else {
            HttpURLConnection HttpConn = (HttpURLConnection) url.openConnection();
            //设置参数
            //HttpConn.setDoOutput(true);
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);
            HttpConn.setRequestMethod("GET");

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
//			try {
//				URI uri = url.toURI();
//				java.util.List<HttpCookie> cookies = manager.getCookieStore().get(uri);
//				if (!cookies.isEmpty()) {
//					sbr = new StringBuilder();
//					for (HttpCookie cookie : cookies) {
//						sbr.append(cookie.getName())
//							.append('=')
//							.append(cookie.getValue())
//							.append("; ");
//					}
//					sbr.delete(sbr.lastIndexOf("; "), sbr.length());
//					HttpConn.setRequestProperty("Cookie", sbr.toString());
//				}
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			}
            HttpConn.connect();

            switch (HttpConn.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    StringBuilder sb = new StringBuilder();
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(HttpConn.getInputStream(), "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine);
                    }
                    responseReader.close();
                    return sb.toString();
                case HttpURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        }
    }
	
	/**
     * 下载方法
     * @param String urlpath 请求地址
     * @param TreeMap<String, String> paramMap 请求参数
	 * @param String filepath 文件路径
     * @return File
     * @throws MalformedURLException,ProtocolException,IOException
     */
    public static File Download(String urlpath, TreeMap<String, String> paramMap, String filepath) throws MalformedURLException,ProtocolException,HttpException,IOException {
        StringBuilder sbr = new StringBuilder();
        sbr.append(urlpath);
		if (null != paramMap) {
			sbr.append('?');
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				sbr.append(entry.getKey())
					.append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
					.append("&");
			}
			sbr.delete(sbr.lastIndexOf("&"), sbr.length());
		}
		URL url = new URL(sbr.toString());
        if (urlpath.startsWith("https://")) {
            HttpsURLConnection HttpConn = (HttpsURLConnection) url.openConnection();
            //设置参数
            //HttpConn.setDoOutput(true);
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);
            HttpConn.setRequestMethod("GET");

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
            HttpConn.connect();

            switch (HttpConn.getResponseCode()) {
                case HttpsURLConnection.HTTP_OK:
                    File file = new File(filepath);
					FileOutputStream fos = new FileOutputStream(file);
                    java.io.InputStream is = HttpConn.getInputStream();
                    byte[] b = new byte[1024];
					int len = 0;
					while((len=is.read(b)) != -1){
						fos.write(b, 0, len);
					}
					fos.flush();
                    fos.close();
					is.close();
                    return file;
                case HttpsURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpsURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpsURLConnection.HTTP_FORBIDDEN:
                case HttpsURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpsURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        } else {
            HttpURLConnection HttpConn = (HttpURLConnection) url.openConnection();
            //设置参数
            //HttpConn.setDoOutput(true);
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);
            HttpConn.setRequestMethod("GET");

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
            HttpConn.connect();

            switch (HttpConn.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    File file = new File(filepath);
					FileOutputStream fos = new FileOutputStream(file);
                    java.io.InputStream is = HttpConn.getInputStream();
                    byte[] b = new byte[1024];
					int len = 0;
					while((len=is.read(b)) != -1){
						fos.write(b, 0, len);
					}
					fos.flush();
                    fos.close();
					is.close();
                    return file;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        }
    }

    /**
     * POST方法
     * @param String urlpath 请求地址
     * @param TreeMap<String, String> paramMap 请求参数
     * @param boolean isMultipart 是否为multipart/form-data
     * @return String
     * @throws MalformedURLException,ProtocolException,IOException
     */
    public static String Post(String urlpath, TreeMap<String, String> paramMap, boolean isMultipart) throws MalformedURLException,ProtocolException,HttpException,IOException {
        URL url = new URL(urlpath);
		if (urlpath.startsWith("https://")) {
            HttpsURLConnection HttpConn = (HttpsURLConnection) url.openConnection();
            //设置参数
            HttpConn.setDoOutput(true);
            HttpConn.setDoInput(true);   //需要输入
            HttpConn.setUseCaches(false);  //不允许缓存
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);  //6秒超时
            HttpConn.setRequestMethod("POST");   //设置POST方式连接

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", (isMultipart ? "multipart/form-data; boundary=" + randomSting : "application/x-www-form-urlencoded"));
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
//			try {
//				URI uri = url.toURI();
//				java.util.List<HttpCookie> cookies = manager.getCookieStore().get(uri);
//				if (!cookies.isEmpty()) {
//					StringBuilder sbr = new StringBuilder();
//					for (HttpCookie cookie : cookies) {
//						sbr.append(cookie.getName())
//							.append('=')
//							.append(cookie.getValue())
//							.append("; ");
//					}
//					sbr.delete(sbr.lastIndexOf("; "), sbr.length());
//					HttpConn.setRequestProperty("Cookie", sbr.toString());
//				}
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			}
            HttpConn.connect();
            PrintWriter pwr = new PrintWriter(HttpConn.getOutputStream());
            if (isMultipart) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    pwr.write("--" + randomSting + "\r\n" +
                        "Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n" +//每个参数都用随机字符串分割，键值后有两个回车换行
                        entry.getValue() + "\r\n");
                }
                pwr.write("--" + randomSting + "--\r\n");//结尾仍以随机字符串结尾，也可以不要
            } else {
                StringBuilder sbr = new StringBuilder();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    sbr.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
                }
                sbr.delete(sbr.lastIndexOf("&"), sbr.length());
                pwr.write(sbr.toString());
            }
            pwr.flush();
            pwr.close();

            switch (HttpConn.getResponseCode()) {
                case HttpsURLConnection.HTTP_OK:
                    StringBuilder sb = new StringBuilder();
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(HttpConn.getInputStream(), "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine);
                    }
                    responseReader.close();
                    return sb.toString();
                case HttpsURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpsURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpsURLConnection.HTTP_FORBIDDEN:
                case HttpsURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpsURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        } else {
            HttpURLConnection HttpConn = (HttpURLConnection) url.openConnection();
            //设置参数
            HttpConn.setDoOutput(true);
            HttpConn.setDoInput(true);   //需要输入
            HttpConn.setUseCaches(false);  //不允许缓存
            HttpConn.setFollowRedirects(true);//跟踪重定向
            HttpConn.setConnectTimeout(TIMEOUT);
            HttpConn.setReadTimeout(TIMEOUT);  //6秒超时
            HttpConn.setRequestMethod("POST");   //设置POST方式连接

            //设置请求属性
            HttpConn.setRequestProperty("Content-Type", (isMultipart ? "multipart/form-data; boundary=" + randomSting : "application/x-www-form-urlencoded"));
            HttpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            HttpConn.setRequestProperty("Charset", "UTF-8");
            HttpConn.setRequestProperty("User-Agent", "HttpRequest by Gardel(MiumStudio)");
//			try {
//				URI uri = url.toURI();
//				java.util.List<HttpCookie> cookies = manager.getCookieStore().get(uri);
//				if (!cookies.isEmpty()) {
//					StringBuilder sbr = new StringBuilder();
//					for (HttpCookie cookie : cookies) {
//						sbr.append(cookie.getName())
//							.append('=')
//							.append(cookie.getValue())
//							.append("; ");
//					}
//					sbr.delete(sbr.lastIndexOf("; "), sbr.length());
//					HttpConn.setRequestProperty("Cookie", sbr.toString());
//				}
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			}
			HttpConn.connect();
            PrintWriter pwr = new PrintWriter(HttpConn.getOutputStream());
            if (isMultipart) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    pwr.write("--" + randomSting + "\r\n" +
                        "Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n" +//每个参数都用随机字符串分割，键值后有两个回车换行
                        entry.getValue() + "\r\n");
                }
                pwr.write("--" + randomSting + "--\r\n");//结尾仍以随机字符串结尾，也可以不要
            } else {
                StringBuilder sbr = new StringBuilder();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    sbr.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
                }
                sbr.delete(sbr.lastIndexOf("&"), sbr.length());
                pwr.write(sbr.toString());
            }
            pwr.flush();
            pwr.close();

            switch (HttpConn.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    StringBuilder sb = new StringBuilder();
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(HttpConn.getInputStream(), "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine);
                    }
                    responseReader.close();
                    return sb.toString();
                case HttpURLConnection.HTTP_NOT_FOUND:
                    throw new HttpException("404页面不存在");
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    throw new HttpException("服务器内部错误");
                case HttpURLConnection.HTTP_RESET:
                    throw new HttpException("交易被网关重置");
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    throw new HttpException("服务器拒绝交易");
                default:
                    throw new HttpException(HttpConn.getResponseCode(), HttpConn.getResponseMessage());
            }
        }
    }
}
