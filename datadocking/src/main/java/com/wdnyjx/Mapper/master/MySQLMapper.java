package com.wdnyjx.Mapper.master;

import com.wdnyjx.Entity.DB.CdMachine;
import com.wdnyjx.Entity.DB.FrameAndType;
import com.wdnyjx.Entity.DB.MachineAndTml;
import com.wdnyjx.Entity.DB.TmlAndId;
import com.wdnyjx.Entity.gjhn.MachineryInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author overlord
 * @since 2020-05-13
 */
@Repository
public interface MySQLMapper {

    List<MachineryInfo> listAll(String parentIds);

    List<TmlAndId> listTmls();

    List<FrameAndType> listMachines();
    List<MachineAndTml> listMachineAndTml();
}
