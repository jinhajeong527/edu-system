package com.myapp.edu.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOption {

    RECENTLY_ADDED("createdDate"),

    MOST_ENROLLED("currentEnrollment"),

    HIGHEST_ENROLLMENT_RATE("enrollmentRate");

    private final String field;

}
