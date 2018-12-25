package connection;

import com.google.gson.Gson;

public class GsonHelper {
	private static Gson mGson;

	public static Gson getGsonInstance() {
		if (mGson == null)
			mGson = new Gson();
		return mGson;
	}
}
