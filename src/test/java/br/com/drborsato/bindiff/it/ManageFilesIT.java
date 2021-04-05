package br.com.drborsato.bindiff.it;

import br.com.drborsato.bindiff.BindiffApplication;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BindiffApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManageFilesIT {

    private static final String EXISTED_PATH = "/v1/diff/1/left";
    private static final FileId EXISTED_FILE_ID = new FileId(1l, Side.LEFT);
    private static final String CREATING_PATH = "/v1/diff/5/right";
    private static final FileId CREATING_FILE_ID = new FileId(5l, Side.RIGHT);
    private static final String UPDATING_PATH = "/v1/diff/4/right";
    private static final FileId UPDATING_FILE_ID = new FileId(4l, Side.RIGHT);
    private static final String REMOVING_PATH = "/v1/diff/4/left";
    private static final String NOT_EXISTED_PATH = "/v1/diff/8/right";
    private static final String DATA = "YWJj";
    private static final String REQUEST_DATA = "{\"data\": \"" + DATA + "\"}";

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void getAFile_whenFileExists_thenReceives200() throws IOException {
        HttpUriRequest request = new HttpGet(getRootUrl() + EXISTED_PATH);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        BinFile responseFile = retrieveFileFromResponse(response);
        assertEquals(EXISTED_FILE_ID.getId(), responseFile.getFileId().getId());
        assertEquals(EXISTED_FILE_ID.getSide(), responseFile.getFileId().getSide());
        assertEquals(DATA, responseFile.getData());
    }

    @Test
    public void getAFile_whenFileNotExists_thenReceives404() throws IOException {
        HttpUriRequest request = new HttpGet(getRootUrl() + NOT_EXISTED_PATH);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void createAFile_whenFileNotExists_thenReceives201() throws IOException {
        HttpPost post = new HttpPost(getRootUrl() + CREATING_PATH);
        setRequest(post);
        HttpResponse response = HttpClientBuilder.create().build().execute(post);

        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
        BinFile responseFile = retrieveFileFromResponse(response);
        assertEquals(CREATING_FILE_ID.getId(), responseFile.getFileId().getId());
        assertEquals(CREATING_FILE_ID.getSide(), responseFile.getFileId().getSide());
        assertEquals(DATA, responseFile.getData());
    }

    @Test
    public void createAFile_whenFileExists_thenReceives409() throws IOException {
        HttpPost post = new HttpPost(getRootUrl() + EXISTED_PATH);
        setRequest(post);
        HttpResponse response = HttpClientBuilder.create().build().execute(post);

        assertEquals(HttpStatus.SC_CONFLICT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void updateAFile_whenFileExists_thenReceives200() throws IOException {
        HttpPut put = new HttpPut(getRootUrl() + UPDATING_PATH);
        setRequest(put);
        HttpResponse response = HttpClientBuilder.create().build().execute(put);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        BinFile responseFile = retrieveFileFromResponse(response);
        assertEquals(UPDATING_FILE_ID.getId(), responseFile.getFileId().getId());
        assertEquals(UPDATING_FILE_ID.getSide(), responseFile.getFileId().getSide());
        assertEquals(DATA, responseFile.getData());
    }

    @Test
    public void updateAFile_whenFileNotExists_thenReceives404() throws IOException {
        HttpPut put = new HttpPut(getRootUrl() + NOT_EXISTED_PATH);
        setRequest(put);
        HttpResponse response = HttpClientBuilder.create().build().execute(put);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void deleteAFile_whenFileExists_thenReceives204() throws IOException {
        HttpUriRequest request = new HttpDelete(getRootUrl() + REMOVING_PATH);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void deleteAFile_whenFileNotExists_thenReceives404() throws IOException {
        HttpUriRequest request = new HttpDelete(getRootUrl() + NOT_EXISTED_PATH);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    private void setRequest(HttpEntityEnclosingRequestBase req) throws UnsupportedEncodingException {
        StringEntity stringEntity = new StringEntity(REQUEST_DATA);
        req.setHeader("Content-Type", "application/json");
        req.setEntity(stringEntity);
    }

    private BinFile retrieveFileFromResponse(HttpResponse response) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, BinFile.class);
    }

}
