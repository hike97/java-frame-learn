package entity.dto;

import lombok.Data;

/**
 * @author hike97
 * @create 2021-06-05 14:30
 * @desc
 **/
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;
}
