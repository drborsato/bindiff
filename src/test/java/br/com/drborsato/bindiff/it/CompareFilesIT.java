package br.com.drborsato.bindiff.it;

import br.com.drborsato.bindiff.BindiffApplication;
import br.com.drborsato.bindiff.model.Diff;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BindiffApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompareFilesIT {

    private static final String EQUAL_FILE = "/v1/diff/1/";
    private static final String DIFFERENT_SIZE_FILE = "/v1/diff/2/";
    private static final String DIFFERENT_FILE = "/v1/diff/3/";

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void compareFiles_whenFileIsEqual_thenReceives200() throws IOException {
        HttpUriRequest request = new HttpGet(getRootUrl() + EQUAL_FILE);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        Diff responseDiff = retrieveFileFromResponse(response);
        assertTrue(responseDiff.isEqual());
        assertNotNull(responseDiff.getOffsetDiff());
        assertEquals(0, responseDiff.getOffsetDiff().size());
    }

    @Test
    public void compareFiles_whenSizeIsDifferent_thenReceives200() throws IOException {
        HttpUriRequest request = new HttpGet(getRootUrl() + DIFFERENT_SIZE_FILE);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        Diff responseDiff = retrieveFileFromResponse(response);
        assertFalse(responseDiff.isEqual());
        assertNotNull(responseDiff.getOffsetDiff());
        assertEquals(0, responseDiff.getOffsetDiff().size());
    }

    @Test
    public void compareFiles_whenFileIsDifferent_thenReceives200() throws IOException {
        HttpUriRequest request = new HttpGet(getRootUrl() + DIFFERENT_FILE);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        Diff responseDiff = retrieveFileFromResponse(response);
        assertFalse(responseDiff.isEqual());
        assertNotNull(responseDiff.getOffsetDiff());
        assertEquals(2, responseDiff.getOffsetDiff().size());

        //left file = abc
        //right file = cba
        assertEquals(0, responseDiff.getOffsetDiff().get(0).getPosition());
        assertEquals((byte) 'a', responseDiff.getOffsetDiff().get(0).getLeft());
        assertEquals((byte) 'c', responseDiff.getOffsetDiff().get(0).getRight());
        assertEquals(2, responseDiff.getOffsetDiff().get(1).getPosition());
        assertEquals((byte) 'c', responseDiff.getOffsetDiff().get(1).getLeft());
        assertEquals((byte) 'a', responseDiff.getOffsetDiff().get(1).getRight());
    }

    private Diff retrieveFileFromResponse(HttpResponse response) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, Diff.class);
    }

}
