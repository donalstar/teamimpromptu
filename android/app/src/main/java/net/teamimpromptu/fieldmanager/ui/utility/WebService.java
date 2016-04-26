package net.teamimpromptu.fieldmanager.ui.utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by donal on 4/26/16.
 */
public class WebService {
    public static final String LOG_TAG = WebService.class.getName();

    private String _url;
    private String _params;

    public WebService(String url, String params) {
        _url = url;
        _params = params;
    }

    public void execute() {
        new LongOperation().execute(_url);
    }


    private class LongOperation extends AsyncTask<String, Void, Void> {

        private String content;
        private String error = null;


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL  where to send data
                URL url = new URL(urls[0]);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(_params);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                content = getResponseAsString(reader);
            } catch (Exception ex) {
                error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }

            /*****************************************************/
            return null;
        }


        private String getResponseAsString(BufferedReader reader) throws IOException {
            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + " ");

            }

            return sb.toString();
        }

        protected void onPostExecute(Void unused) {

            Log.i(LOG_TAG, "JSON RESP " + content);


        }

    }

}
