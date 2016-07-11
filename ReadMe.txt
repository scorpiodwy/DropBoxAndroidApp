July 08, 2016 First Version

The dropbox API use OAuth v2.
The method to start authentication is startOAuth2Authentication(), and the method to get token is getOAuth2AccessToken().
These two methods are different with most samples found online.

APP_KEY and APP_SECRET should be changed based on your Dropbox app.
Go to https://www.dropbox.com/developers/apps, select the app you want to use in Android.
In the next page, you can find the App key and App secret, copy them and replace the first two lines in MainActivity.java.
Meanwhile, in the AndroidManifest.xml, modify the <data> to "db-{xxxxxxxxxx}".

In this sample, I implement the function that to open camera and take a picture. 
This picture will be stored on device temporarily, then uploaded to dropbox, and deleted on local device.

The functions of uploading to dropbox and deleting from local device are implemented in Upload.java.

July 10, 2016 Second version

I planned to submit the picture directly to Dropbox without having a local temporary file, but this operation needs Dropbox pro account.
As a result, I decide to give it up.
In this version, I modify the internal folder to the default camera picture folder, which is Environment.DIRECTORY_DCIM. However, we still need to add a subfolder, “100MEDIA” in this sample, to determine which camera folder we want to use.
	
