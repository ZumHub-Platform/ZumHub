package com.server.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestMappingServiceHandler {

    private MappingService mappingService;

    @BeforeEach
    public void setUp() {
        mappingService = new MappingService();
      /*  Assertions.assertTrue(mappingService.registerMapping("/test", new MappingHandler<String>() {
            @Override
            public StringResponse handle(Request request) {
                return StringResponse.OK_RESPONSE;
            }
        }));*/
    }

    @Test
    @DisplayName("Test valid basic mapping")
    public void testValidBasicMapping() {
       /* Assertions.assertEquals(StringResponse.OK_RESPONSE.getStatus(),
                mappingService.route(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/test"), "/test"
                ).getStatus());*/
    }

    @Test
    @DisplayName("Test invalid mapping")
    public void testInvalidBasicMapping() {
       /* Assertions.assertNotEquals(StringResponse.OK_RESPONSE.getStatus(),
                mappingService.route(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/test"), "/test1"
                ).getStatus());*/
    }
}
