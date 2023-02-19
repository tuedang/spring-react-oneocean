package com.oneocean.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneocean.api.vessel.domain.Position;
import com.oneocean.api.vessel.domain.Vessel;
import com.oneocean.api.vessel.domain.VesselPosition;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VesselRepository {
    private static final String DATA_SOURCE_FILE = "/data/TestData.json";
//    private static final String DATA_SOURCE_FILE = "/data/TestData_collision.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    private static List<VesselPosition> STATIC_VESSEL_DATA;

    public List<VesselPosition> getVesselPositions() {
        if (STATIC_VESSEL_DATA == null) {
            STATIC_VESSEL_DATA = parseVesselPositionsData();
        }
        return STATIC_VESSEL_DATA;
    }

    public List<VesselPosition> parseVesselPositionsData() {
        try {
            String jsonText = IOUtils.toString(this.getClass().getResourceAsStream(DATA_SOURCE_FILE), "UTF-8");
            Map<String, Map> jsonData = objectMapper.readValue(jsonText, HashMap.class);
            List<Map> vessels = (List) jsonData.get("vessels");

            List<VesselPosition> vesselPositions = new ArrayList<>();
            for (Map map : vessels) {
                String name = (String) map.get("name");
                List<Map> positions = (List) map.get("positions");
                for (Map<String, Object> position : positions) {
                    int x = (Integer) position.get("x");
                    int y = (Integer) position.get("y");
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) position.get("timestamp"));
                    Instant instant = zonedDateTime.toInstant();

                    VesselPosition vesselPosition = VesselPosition.builder()
                            .vessel(new Vessel(name))
                            .time(instant)
                            .position(new Position(x, y))
                            .build();
                    vesselPositions.add(vesselPosition);
                }
            }
            return vesselPositions;
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }


}
