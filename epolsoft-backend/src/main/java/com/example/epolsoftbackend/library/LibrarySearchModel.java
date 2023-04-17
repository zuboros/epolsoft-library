package com.example.epolsoftbackend.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibrarySearchModel {

    private String fieldName;

    private String fieldValue;
}
