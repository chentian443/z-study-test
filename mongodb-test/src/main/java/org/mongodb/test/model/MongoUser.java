package org.mongodb.test.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MongoUser {

    @Id
    private int id;

    @ApiModelProperty(value = "登录用户")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "激活码")
    private String validateCode;

    @ApiModelProperty(value = "性别：1男2女0保密")
    private int gender = 0;

    @ApiModelProperty(value = "电话")
    private Long phone = 0L;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl = "";

    @ApiModelProperty(value = "地址")
    private String address = "";

    @ApiModelProperty(value = "注册时间(时间戳)")
    private Long registerTime = 0L;
}
