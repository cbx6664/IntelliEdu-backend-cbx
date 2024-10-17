package com.team6.intelliedumodel.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateQuestionRequest implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * Question List (JSON)
     */
    private List<QuestionContent> questions;

    /**
     * Application ID
     */
    private Long appId;

    private static final long serialVersionUID = 635214537138480251L;
}