package com.amazon.jonnu.csgotracker.service.alexa;

import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.jonnu.csgotracker.service.alexa.model.SettingsRequest;

class AlexaSettingsImplTest {

    private AlexaSettings fixture;

    @BeforeEach
    void before() {
        fixture = new AlexaSettingsImpl();
    }

    @Test
    void thingy() {

        SettingsRequest request = SettingsRequest.builder()
                .endpoint("https://api.eu.amazonalexa.com")
                .apiToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmYwMzI4ZmE5LTU2NzctNDcwMS05NzM4LWI4NjA4ZGYzOWJhYyIsImV4cCI6MTU0NDg5MzE4MywiaWF0IjoxNTQ0ODg5NTgzLCJuYmYiOjE1NDQ4ODk1ODMsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUY1VVhaQ01EQldMNTRBWUtQSVFDSVZSRU5BSlFQTkpVWElERFBMVkZKUUhKV0FBUExBVjdIUEU1U0tUVU9QUDdLTEpZNUlZRFhYT0tBQ01IVENOQ1FXNFQzRUI3NUE3Q0tJVjNCUFVUTVRaVFhTTzdCWE9STTI3T1hIR1VHWkdYSTVPRldSQjROQzNBS0FQMjNOTVJGR0s2TTY3R1lON1lRQ05OV1RJVldNWERSUkxGSEJYTSIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFINFNZNUtJQUZRMkVTU0dBVUpJTEhQSVJXUDUyUlZFSTdFSllYN1pGUFVDV1JEQVJSM0gyWEVEVDJaQ0RITjY3UlFEM01FUTM3MktBU1hFSFpUVjY0WUcyWDNST1FHQ0VIR1ZORU8zUDJNTUJCQkxUNDZVSEZCNEM2WkNKQVJMSVlJVjNKRVkyM0hWQUVaRlAzTVNUTDRPVVdHQjNOU1NHV0JNQ0VQVFZYMlk2Qk9FWVBJQ0RRWkRONEFURlo2TVpHRUtNM0YzSkI3V1BaWSJ9fQ.AqFOio2_8aaAWLm_Gv-ihoPTD6BQX4qPK-0BEf13zXFIsljQOX_B6m9d5Ju8uMrY1aKBwUpUFsGWs-_PDuQXwt-estljYHcofDVMeG7pxP2faixStMRgsqH9puDdkCCqisT8omCNPWBCMqZXGssOhMkp7bo25r3V_U2tn3Ubo1FJSP_qV_HPGsgRH5xh8mkJc2uHK8TKrDKG22H1Cv6qVlcUYw4c7gYdJaxZJ-Qr-7tQMVorD4z7XOPArMe6zps_2ETyxcyaS8M3c-VGvHFFBQjPlO305XgQ_wg9InwBepUHrb6DKRZ19kc8AINHQuC8xebjdtVlBVmvqV__F5fdug")
                .deviceId("amzn1.ask.device.AF5UXZCMDBWL54AYKPIQCIVRENAJQPNJUXIDDPLVFJQHJWAAPLAV7HPE5SKTUOPP7KLJY5IYDXXOKACMHTCNCQW4T3EB75A7CKIV3BPUTMTZTXSO7BXORM27OXHGUGZGXI5OFWRB4NC3AKAP23NMRFGK6M67GYN7YQCNNWTIVWMXDRRLFHBXM")
                .build();

        TimeZone zone = fixture.getTimeZone(request)
                .orElse(TimeZone.getDefault());

        System.out.println(zone);
    }

}
