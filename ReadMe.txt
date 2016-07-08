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

Next version

I plan to jump over the step of storing picture on local device and directly upload it to dropbox after it is taken.
