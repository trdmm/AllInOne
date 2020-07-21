package com.wdnyjx.Entity.gjhn;

import lombok.Data;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Entity.gjhn
 * @author:OverLord
 * @Since:2020/6/15 19:45
 * @Version:v0.0.1
 */
@Data
public class WorkArea {
    private String id;
    private String tmlId;
    private String machineId;
    //timestamp
    private String jobStartTime;
    private String jobEndTime;
    private String grossArea;
    private int province;
}
