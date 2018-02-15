package top.gardel.test;

public class MainActivity extends android.app.Activity 
{
	android.widget.TextView tv;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		tv = (android.widget.TextView) findViewById(R.id.mainTextView1);
		top.gardel.httputils.HttpUtils.Get(this, "http://www.example.com", null, new top.gardel.httputils.EventHander() {
				@Override
				public void onSuccess(String result) {
					tv.setText(result);
				}

				@Override
				public void onSuccess(java.io.File file) {
					// TODO: Implement this method
				}

				@Override
				public void onFailed(String msg) {
					android.widget.Toast.makeText(getApplicationContext(), msg, android.widget.Toast.LENGTH_SHORT).show();
				}
			});
    }
}
