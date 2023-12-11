package com.polytech.courses.equipmentservice.config;

//@Configuration
public class EmbeddedMongoConfig {


    private static final String IP = "localhost";
    private static final int PORT = 28017;

   /* @Bean
    public ImmutableMongod embeddedMongoConfiguration() throws IOException {

        return ImmutableMongod.builder()
            .net(Net.of(IP, PORT, true))
            .build();

        return new MongodConfigBuilder()
            .version(Version.V4_0_2)
            .net(new Net(IP, PORT, Network.localhostIsIPv6()))
            .build();
    }*/
}