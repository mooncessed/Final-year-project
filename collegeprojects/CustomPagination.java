package com.cloudability.pricing;
import com.google.common.collect.ImmutableList;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class CustomPagination {
    public static <T> PaginationResult<T> a(List<? extends T> data, Optional<Integer> offset, Optional<Integer> limit) {
        List<? extends T> paginatedData;
        int totalData = data.size();
        boolean isAllDataRequested = !offset.isPresent() && !limit.isPresent();

        if (isAllDataRequested) {
            paginatedData = data;
        } else {
            int startIndex = offset.orElse(0);
            int endIndex = startIndex + limit.orElse(0);

            if (startIndex < 0 || startIndex >= totalData || endIndex < 0 || endIndex > totalData || startIndex >= endIndex) {
                // Return a PaginationResult with the paginated data as null and the appropriate response status
                return new PaginationResult<>(null, Response.Status.BAD_REQUEST);
            }

            paginatedData = data.subList(startIndex, endIndex);
        }
        return new PaginationResult<>(ImmutableList.copyOf(paginatedData), Response.Status.OK);
    }
}
