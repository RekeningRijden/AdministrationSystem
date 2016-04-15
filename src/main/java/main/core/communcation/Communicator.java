package main.core.communcation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import main.domain.simulation.CarTracker;
import main.domain.simulation.TrackingPeriod;

/**
 * @author Sam
 */
public class Communicator {

    /**
     * The test url of the Movementsystem api.
     */
    private static final String BASE_URL_TEST = "http://localhost:8080/MovementSystem/api/trackers";

    /**
     * The production url of the Movementsystem api.
     */
    private static final String BASE_URL_PRODUCTION = "http://movement.s63a.marijn.ws/api/trackers";

    public static List<CarTracker> getAllCartrackers() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_TEST);
        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        Gson gson = new Gson();
        return gson.fromJson(responseString, new TypeToken<List<CarTracker>>() {
        }.getType());
    }

    /**
     * Retrieve the id's of all the @{code CarTrackers}.
     *
     * @return a List of Longs.
     * @throws IOException when an IOException occurs.
     */
    public static List<Long> getAllCartrackerIds() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_TEST + "/ids");
        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        Gson gson = new Gson();
        return gson.fromJson(responseString, new TypeToken<List<Long>>() {
        }.getType());
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
        HttpGet get = new HttpGet(BASE_URL_TEST
                + "/"
                + id
                + "/movements/_byperiod?startDate="
                + startDate
                + "&endDate="
                + endDate);

        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        Gson gson = new Gson();
        return gson.fromJson(responseString, new TypeToken<List<TrackingPeriod>>() {
        }.getType());
    }
}


