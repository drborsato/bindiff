package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.exception.BinDataConverterException;
import br.com.drborsato.bindiff.exception.BinFileConflictException;
import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.exception.DiffFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.RequestData;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.service.BinDiffService;
import br.com.drborsato.bindiff.service.BinFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BinDiffController.class)
class BinDiffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BinFileService fileService;

    @MockBean
    private BinDiffService diffService;

    @Test
    void getFile_thenReturns200() throws Exception {
        BinFile expectedFile = new BinFile(new FileId(1l, Side.LEFT), "YWJjaG");
        when(fileService.getBinFile(1, Side.LEFT)).thenReturn(expectedFile);
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1/left")).
                andExpect(status().isOk()).
                andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedFile));
    }

    @Test
    void getFile_whenNotFound_thenReturns404() throws Exception {
        when(fileService.getBinFile(1, Side.LEFT)).thenThrow(BinFileNotFoundException.class);
        mockMvc.perform(get("/v1/diff/1/left")).
                andExpect(status().isNotFound());
    }

    @Test
    void getFile_whenInvalidSide_thenReturns400() throws Exception {
        mockMvc.perform(get("/v1/diff/1/up")).
                andExpect(status().isBadRequest());
    }

    @Test
    void createNewFile_thenReturns201() throws Exception {
        BinFile expectedFile = new BinFile(new FileId(1l, Side.LEFT), "YWJjaG");
        RequestData requestData = new RequestData("YWJjaG");
        when(fileService.createBinFile(1, Side.LEFT, "YWJjaG")).thenReturn(expectedFile);
        MvcResult mvcResult = mockMvc.perform(post("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isCreated()).
                andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedFile));
    }

    @Test
    void createNewFile_whenConflict_thenReturns409() throws Exception {
        RequestData requestData = new RequestData("YWJjaG");
        when(fileService.createBinFile(1, Side.LEFT, "YWJjaG")).thenThrow(BinFileConflictException.class);
        mockMvc.perform(post("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isConflict());
    }

    @Test
    void createNewFile_whenInvalidData_thenReturns400() throws Exception {
        RequestData requestData = new RequestData("abc;abc");
        when(fileService.createBinFile(1, Side.LEFT, "abc;abc")).thenThrow(BinDataConverterException.class);
        mockMvc.perform(post("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isBadRequest());
    }

    @Test
    void createNewFile_whenInvalidSide_thenReturns400() throws Exception {
        RequestData requestData = new RequestData("YWJjaG");
        mockMvc.perform(post("/v1/diff/1/up").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isBadRequest());
    }

    @Test
    void createNewFile_whenInvalidRequest_thenReturns400() throws Exception {
        String requestData = "YWJjaG";
        mockMvc.perform(post("/v1/diff/1/left").
                contentType("application/json").
                content(requestData)).
                andExpect(status().isBadRequest());
    }

    @Test
    void updateFile_thenReturns200() throws Exception {
        BinFile expectedFile = new BinFile(new FileId(1l, Side.LEFT), "YWJjaG");
        RequestData requestData = new RequestData("YWJjaG");
        when(fileService.updateBinFile(1, Side.LEFT, "YWJjaG")).thenReturn(expectedFile);
        MvcResult mvcResult = mockMvc.perform(put("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isOk()).
                andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedFile));
    }

    @Test
    void updateFile_whenNotFound_thenReturns404() throws Exception {
        RequestData requestData = new RequestData("YWJjaG");
        when(fileService.updateBinFile(1, Side.LEFT, "YWJjaG")).thenThrow(BinFileNotFoundException.class);
        mockMvc.perform(put("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isNotFound());
    }

    @Test
    void updateFile_whenInvalidData_thenReturns400() throws Exception {
        RequestData requestData = new RequestData("abc;abc");
        when(fileService.updateBinFile(1, Side.LEFT, "abc;abc")).thenThrow(BinDataConverterException.class);
        mockMvc.perform(put("/v1/diff/1/left").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isBadRequest());
    }

    @Test
    void updateFile_whenInvalidSide_thenReturns400() throws Exception {
        RequestData requestData = new RequestData("YWJjaG");
        mockMvc.perform(put("/v1/diff/1/up").
                contentType("application/json").
                content(objectMapper.writeValueAsString(requestData))).
                andExpect(status().isBadRequest());
    }

    @Test
    void updateFile_whenInvalidRequest_thenReturns400() throws Exception {
        String requestData = "YWJjaG";
        mockMvc.perform(put("/v1/diff/1/left").
                contentType("application/json").
                content(requestData)).
                andExpect(status().isBadRequest());
    }

    @Test
    void deleteFile_thenReturns204() throws Exception {
        Mockito.doNothing().when(fileService).deleteBinFile(1, Side.LEFT);
        MvcResult mvcResult = mockMvc.perform(delete("/v1/diff/1/left")).
                andExpect(status().isNoContent()).
                andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEmpty();
    }

    @Test
    void deleteFile_whenNotFound_thenReturns404() throws Exception {
        Mockito.doThrow(BinFileNotFoundException.class).when(fileService).deleteBinFile(1, Side.LEFT);
        mockMvc.perform(delete("/v1/diff/1/left")).
                andExpect(status().isNotFound());
    }

    @Test
    void deleteFile_whenInvalidSide_thenReturns400() throws Exception {
        mockMvc.perform(delete("/v1/diff/1/up")).
                andExpect(status().isBadRequest());
    }

    @Test
    void getDiff_thenReturns200() throws Exception {
        Diff expectedFile = new Diff(true, Collections.emptyList());
        when(diffService.getDiff(1l)).thenReturn(expectedFile);
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1/")).
                andExpect(status().isOk()).
                andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedFile));
    }

    @Test
    void getDiff_whenFileNotFound_thenReturns400() throws Exception {
        when(diffService.getDiff(1l)).thenThrow(DiffFileNotFoundException.class);
        mockMvc.perform(get("/v1/diff/1/")).
                andExpect(status().isBadRequest());
    }

    @Test
    void getDiff_whenInvalidData_thenReturns400() throws Exception {
        when(diffService.getDiff(1l)).thenThrow(BinDataConverterException.class);
        mockMvc.perform(get("/v1/diff/1/")).
                andExpect(status().isBadRequest());
    }


}