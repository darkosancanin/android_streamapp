package com.idarko.streamapp.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.idarko.streamapp.model.HttpResult;

public abstract class HttpManager {
	protected HttpResult getHtml(String url) {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 4096);
			String htmlLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((htmlLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(htmlLine);
			}
			bufferedReader.close(); 
			String html = stringBuilder.toString();
			if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				return new HttpResult(html);
			}
			else{
				String twitterError = getTwitterErrorMessage(html);
				if(twitterError != null){
					return HttpResult.CreateHttpResultWithException(twitterError);
				}
				else {
					return HttpResult.CreateHttpResultWithException("Failed to connect. Please try again.");
				}
			}
		}catch (IOException e) {
			Log.e("StreamApp", e.getMessage(), e);
			return HttpResult.CreateHttpResultWithException("Failed to connect. Please try again.");
		}
		finally {
			if(httpURLConnection != null)
				httpURLConnection.disconnect();
	   }
	}

	private String getTwitterErrorMessage(String html){
		return null;
	}
}
