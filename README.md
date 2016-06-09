# Basic Authentication Infinite Loop #

There seems to be an Infinite Loop occuring when authenticating using HttpURLConnection on Android with devices running Android 4.x.

## Test Steps

I'm gonna assume you already have node installed and running all this on a OSX.

### Server Steps

1. ifconfig | grep "inet " | grep -v 127.0.0.1
2. git clone https://github.com/http-auth/http-auth.git
3. cd http-auth
4. npm install
5. cd example
6. node basic.js

This will run it on localhost at port 1337. The first command will tell you what your computer's IP Address is.

#### Android Steps

The Project is in the BasicAuthInfiniteLoop folder.

1. Import project into Android Studio
2. In the MainActivity.java, change the URL to your computer's IP Address.
3. Have Android device connect to the same wifi network
4. Run code in Android (Running Android 4.X) and Press Button
5. See the logs on CLI by running ```adb logcat -s "InfiniteLoop"``` and 😭.

You can change the ```Boolean badCredentials = true;``` value to ```false``` to see that it works (with no loop) when the credentials are correct.

You will see a non-stop end of the logs as follows:-
```
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
D/InfiniteLoop( 4292): U:JohnConner P:testpass
```

## Solution

There seems to already be a issue ticket that's been filed. https://code.google.com/p/android/issues/detail?id=7058
Solution is provided by "pccraftcojp-firstrepo" here https://code.google.com/p/android/issues/detail?id=7058#c4

The solution is
```java
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class HttpAuthenticator extends Authenticator {
	HttpAuthenticator(String id, String password) {
		mID = id;
		mPassword = password;
	}
	private int mRetryCount = 0;
	private int mMaxRetryCount = 1;
	private String mID;
	private String mPassword;
	public void clearretryCount() {
		mRetryCount = 0;
	}
	public void setRetryCount(int count) {
		mRetryCount = count;
	}
	public final int getRetryCount() {
		return mRetryCount;
	}
	public void setMaxRetryCount(int count) {
		mMaxRetryCount = count;
	}
	public final int getMaxRetryCount() {
		return mMaxRetryCount;
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		if (mRetryCount < mMaxRetryCount) {
			mRetryCount++;
			return new PasswordAuthentication(mID , mPassword.toCharArray());
		}
		return null;
	}
}
```


#### License
This will be under Apache-2.0.  
Repo by ashcoding.