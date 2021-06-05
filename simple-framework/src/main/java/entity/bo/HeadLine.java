package entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author hike97
 * @create 2021-06-05 12:45
 * @desc
 **/
@Data
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
