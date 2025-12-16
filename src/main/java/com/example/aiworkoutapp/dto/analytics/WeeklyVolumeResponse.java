package com.example.aiworkoutapp.dto.analytics;

import java.time.OffsetDateTime;
import java.util.List;

public record WeeklyVolumeResponse(List<WeekPoint> weeks) {
    public record WeekPoint(OffsetDateTime weekStart, double volume, long sets, long reps) {}
}
