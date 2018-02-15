package top.gardel.httputils;

public interface EventHander {
	public void onSuccess(String result);
	public void onSuccess(java.io.File file);
	public void onFailed(String msg);
}
