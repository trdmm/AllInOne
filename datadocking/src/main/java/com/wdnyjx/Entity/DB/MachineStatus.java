package com.wdnyjx.Entity.DB;

import lombok.Data;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Entity.DB
 * @author:OverLord
 * @Since:2020/6/13 10:06
 * @Version:v0.0.1
 */
@Data
public class MachineStatus {
    private String frame_number;
    private String tml_number;
    private int status;
    private String install_user;
    private String install_user_show;
    private String install_time;
}
