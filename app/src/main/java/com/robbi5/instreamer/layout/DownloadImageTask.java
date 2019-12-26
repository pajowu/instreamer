package com.robbi5.instreamer.layout;

import java.net.URL;
import android.util.Log;
import android.os.AsyncTask;
import java.io.InputStream;
import android.util.Base64;
import android.content.SharedPreferences;
import java.io.ByteArrayOutputStream;

class DownloadImageTask extends AsyncTask<String, Void, String> {
  SharedPreferences mPref;

  public DownloadImageTask(SharedPreferences pref) {
      this.mPref = pref;
  }

  protected String doInBackground(String... urls) {
      String urldisplay = urls[0];
      try {
        InputStream image_stream = new java.net.URL(urldisplay).openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int r = image_stream.read(buffer);
            if (r == -1) break;
            out.write(buffer, 0, r);
        }
        byte[] image_bytes = out.toByteArray();
        String image_b64_string = Base64.encodeToString(image_bytes, Base64.DEFAULT);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("no_hdmi_image", image_b64_string);
        editor.apply();
        return image_b64_string;
      } catch (Exception e) {
          Log.e("INSTREAMER", "Error", e);
          e.printStackTrace();
      }
      Log.i("INSTREAMER", "Image loaded successfully");
      return "";
  }
}
