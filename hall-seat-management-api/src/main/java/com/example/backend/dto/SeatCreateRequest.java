package com.example.backend.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatCreateRequest{

    @NotBlank(message = "좌석 ID는 필수입니다.")
    private String id;

    @NotBlank(message = "구역은 필수입니다.")
    private String section;

    @NotNull(message = "공연장 ID는 필수입니다.")
    private Long hallId;

    public SeatCreateRequest(){
    }

    public SeatCreateRequest(String id, String section, Long hallId){
        this.id = id;
        this.section = section;
        this.hallId = hallId;
    }

    public String getId(){
        return id;
    }

    public String getSection(){
        return section;
    }

    public Long getHallId(){
        return hallId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setSection(String section){
        this.section = section;
    }

    public void setHallId(Long hallId){
        this.hallId = hallId;
    }
}