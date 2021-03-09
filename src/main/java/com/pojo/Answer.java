package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private  int answerId;
    private int paperId;
    private int studentId;
    private String studentName;
    private byte[] answerContext;
    private String score;
    private String scoreDescribe;
    private String monitorMsg;

    public Answer(int paperId, int studentId, String studentName, byte[] answerContext) {
        this.paperId = paperId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.answerContext = answerContext;
    }
}
