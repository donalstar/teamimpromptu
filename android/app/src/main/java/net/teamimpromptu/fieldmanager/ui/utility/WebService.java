package net.teamimpromptu.fieldmanager.ui.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.teamimpromptu.fieldmanager.service.ServerFacade;
import net.teamimpromptu.fieldmanager.service.Member;
import net.teamimpromptu.fieldmanager.service.Payload;
import net.teamimpromptu.fieldmanager.service.Request;
import net.teamimpromptu.fieldmanager.service.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by donal on 4/26/16.
 */
public class WebService {
    public static final String LOG_TAG = WebService.class.getName();

    private String _url;
    private ServerFacade.ServerFacadeCallback _callback;

    public WebService(String url, ServerFacade.ServerFacadeCallback callback) {
        _url = url;
        _callback = callback;
    }

    public void execute() {
        new LongTask().execute(_url);
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class LongTask extends AsyncTask<String, Void, List<Member>> {

        private Request message;

        @Override
        protected void onPreExecute() {
            message = buildRequest();
        }

        @Override
        protected List<Member> doInBackground(String... params) {
            String url = params[0];

            try {
                return executePostRequest(url, message);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Member> result) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("members", result);

            _callback.onSuccess(attributes);
        }

    }


    /**
     * @return
     */
    private Request buildRequest() {
        String tableName = "TeamMember";

        Payload payload = new Payload();
        payload.setSelectType("ALL_ATTRIBUTES");

        Request request = new Request();

        request.setOperation("list");

        request.setTableName(tableName);

        request.setPayload(payload);

        return request;
    }

    /**
     * @param url
     * @param request
     * @return
     * @throws Exception
     */
    private List<Member> executePostRequest(String url, Request request) throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending multipart/form-data
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Request> requestEntity = new HttpEntity<Request>(request, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Make the network request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        String body = response.getBody();

        Response results = new ObjectMapper().readValue(body, Response.class);

        return Arrays.asList(results.items);
    }
}
