package com.example.crocotask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackStatusRequest {
    @NotBlank
    private String channel;
    @NotBlank
    private String status;
    private LocalDate sentAt;

}
