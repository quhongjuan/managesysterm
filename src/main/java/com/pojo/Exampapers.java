package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exampapers {
    private int paperId;
    private int authorId;
    private String releaseTime;
    private String endTime;
    private byte[]  context;
    private String stuCode;
    private String teaCode;
    private int isVisible;
    private String title;

    public Exampapers( int authorId, String releaseTime, String endTime, byte[] context, String stuCode, String teaCode, int isVisible, String title) {
        this.authorId = authorId;
        this.releaseTime = releaseTime;
        this.endTime = endTime;
        this.context = context;
        this.stuCode = stuCode;
        this.teaCode = teaCode;
        this.isVisible = isVisible;
        this.title = title;
    }

    public Exampapers(int paperId ,String releaseTime, String endTime, byte[] context, String stuCode, String teaCode, String title) {
        this.paperId=paperId;
        this.releaseTime = releaseTime;
        this.endTime = endTime;
        this.context = context;
        this.stuCode = stuCode;
        this.teaCode = teaCode;
        this.title = title;
    }
}
