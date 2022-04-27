package com.server.mapping;

import com.server.request.Request;
import com.server.response.Response;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestMappingService {

    private MappingService mappingService;

    @BeforeEach
    public void setUp() {
        mappingService = new MappingService();
        Assertions.assertTrue(mappingService.registerMapping("/test", new Mapping<String>() {
            @Override
            public Response<String> handle(Request request) {
                return Response.OK_RESPONSE;
            }
        }));
    }

    @Test
    @DisplayName("Test valid basic mapping")
    public void testValidBasicMapping() {
        Assertions.assertEquals(Response.OK_RESPONSE.getStatus(),
                mappingService.route(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/test"), "/test"
                ).getStatus());
    }

    @Test
    @DisplayName("Test invalid mapping")
    public void testInvalidBasicMapping() {
        Assertions.assertNotEquals(Response.OK_RESPONSE.getStatus(),
                mappingService.route(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/test"), "/test1"
                ).getStatus());
    }
}
