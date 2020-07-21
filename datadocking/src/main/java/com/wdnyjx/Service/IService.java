package com.wdnyjx.Service;

import com.wdnyjx.Entity.Counts;
import com.wdnyjx.Entity.DB.FrameAndType;
import com.wdnyjx.Entity.DB.MachineAndTml;
import com.wdnyjx.Entity.DB.TmlAndId;
import com.wdnyjx.Entity.gjhn.MachineryInfo;
import com.wdnyjx.Entity.gjhn.WorkArea;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Service
 * @author:OverLord
 * @Since:2020/6/13 9:51
 * @Version:v0.0.1
 */
public interface IService {
    List<MachineryInfo> listAll(String parentIds);

    List<WorkArea> getWorkRecords(Timestamp stime, Timestamp etime);

/*    List<TmlAndId> listTmls();

    List<FrameAndType> listMachines();*/

    List<MachineAndTml> listMachineAndTml();

    List<WorkArea> getSingleWorkRecords(String frameNum, Timestamp stime, Timestamp etime);

    Counts getCounts(String frameNum);
}
