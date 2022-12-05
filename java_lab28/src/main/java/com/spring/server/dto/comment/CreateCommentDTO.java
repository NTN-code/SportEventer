package com.spring.server.dto.comment;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCommentDTO {
    @NotBlank(message = "Введите комментарий")
    private String comment;
    @NotNull(message = "Введите id пользователя")
    private Long userId;

    //region Getter and Setter

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //endregion
}
