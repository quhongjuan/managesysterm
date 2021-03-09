package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Monitor {
    private int monitorId;
    private int studentId;
    private String studentName;
    private int paperId;
    private String status;
    private int qieping;
    private int xiping;
    private int likai;
    private int duoren;
    private String describes;

}
