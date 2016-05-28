package main.core.communcation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.core.exception.CommunicationException;
import main.domain.Driver;
import main.domain.simulation.TrackingPeriod;

/**
 * @author Sam
 */
public final class Communicator {

    /**
     * The production url of the Movementsystem api.
     */
    private static final String BASE_URL_PRODUCTION = "http://movement.s63a.marijn.ws/api/trackers";

    /**
     * The date format used in api calls.
     */
    private static final String REGULAR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private Communicator() {
        //Utility class constructor cannot be called.
    }

    /**
     * Adds a new cartracker to the movement api
     *
     * @return The newly added cartracker
     * @throws Exception Can happen when something is wrong with (StringEntity(jsonBody) en httpClient.execute(post)
     */
    public static Long requestNewCartracker() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(BASE_URL_PRODUCTION);
        HttpResponse response = httpClient.execute(post);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(responseString);

        return json.getLong("id");
    }

    /**
     * Retrieve the id's of all the @{code CarTrackers}.
     *
     * @return a List of Longs.
     * @throws IOException when an IOException occurs.
     */
    public static List<Long> getAllCartrackerIds() throws IOException {
        return getAllCartrackerIds(1);
    }

    private static List<Long> getAllCartrackerIds(int counter) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + "/ids");

        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        Gson gson = new GsonBuilder().setDateFormat(REGULAR_DATE_FORMAT).create();

        List<Long> ids = new ArrayList<>();
        try {
            List<LongWrapper> wrappers = gson.fromJson(responseString, new TypeToken<List<LongWrapper>>() {
            }.getType());

            for (LongWrapper wrapper : wrappers) {
                ids.add(wrapper.getValue());
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, e);

            int updatedCounter = counter + 1;
            if (updatedCounter < 3) {
                getAllCartrackerIds(updatedCounter);
            } else {
                throw new CommunicationException("Gave up on connection after trying three times");
            }
        }

        return ids;
    }

    /**
     * Retrieve all the @{code TrackingPeriods} from a @{code CarTracker} with the given id,
     * which belong to a certain month.
     *
     * @param id    of the @{code CarTracker} to get get the @{code TrackingPeriods} from.
     * @param month where the @{code TrackingPeriods} belong to.
     * @param year  where the @{code TrackingPeriods} belong to.
     * @return a List of @{code CarTrackers}.
     * @throws IOException when an IOException occurs.
     */
    public static List<TrackingPeriod> getTrackingPeriodsByMonth(Long id, int month, int year) throws IOException {
        LocalDate dateInMonth = LocalDate.of(year, month, 1);

        String startDate = year + "-" + month + "01";
        String endDate = dateInMonth.withDayOfMonth(dateInMonth.lengthOfMonth()).toString();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION
                + "/"
                + id
                + "/movements/_byperiod?startDate="
                + startDate
                + "&endDate="
                + endDate);

        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder().setDateFormat(REGULAR_DATE_FORMAT).create();

        return gson.fromJson(responseString, new TypeToken<List<TrackingPeriod>>() {
        }.getType());
    }

    public static Long addDriver(Driver driver) throws Exception {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut post = new HttpPut("http://billdriver.s63a.marijn.ws/api/users");

        String jsonBody = gson.toJson(driver);
        StringEntity postingString = new StringEntity(jsonBody, StandardCharsets.UTF_8);

        post.setEntity(postingString);
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");

        HttpResponse response = httpClient.execute(post);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(responseString);

        return json.getLong("id");
    }

    /**
     * Check if a received response is not null and has the statusCode code of 200.
     *
     * @param response to check.
     * @throws CommunicationException if the response was null or the statusCode was not 200.s
     */
    private static void checkResponse(HttpResponse response) throws CommunicationException {
        if (response == null) {
            throw new CommunicationException("Response was null");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new CommunicationException("Response did not have statusCode 200, instead the code was: " + statusCode);
        }
    }
}


