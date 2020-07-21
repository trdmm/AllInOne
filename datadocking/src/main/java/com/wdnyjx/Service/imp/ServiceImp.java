package com.wdnyjx.Service.imp;

import com.wdnyjx.Entity.Counts;
import com.wdnyjx.Entity.DB.MachineAndTml;
import com.wdnyjx.Entity.gjhn.MachineryInfo;
import com.wdnyjx.Entity.gjhn.WorkArea;
import com.wdnyjx.Mapper.master.MySQLMapper;
import com.wdnyjx.Mapper.slave.PostgreSQLMapper;
import com.wdnyjx.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Service.imp
 * @author:OverLord
 * @Since:2020/6/13 9:51
 * @Version:v0.0.1
 */
@Service
public class ServiceImp implements IService {
    @Autowired
    MySQLMapper mySQLMapper;
    @Autowired
    PostgreSQLMapper postgreSQLMapper;

    @Override
    public List<MachineryInfo> listAll(String parentIds) {
        return mySQLMapper.listAll(parentIds);
    }
    @Override
    public List<WorkArea> getWorkRecords(Timestamp stime, Timestamp etime){
        return postgreSQLMapper.getWorkRecords(stime,etime);
    }

    /**
     * 获取终端的id和code的对应关系
     * @return
     */
/*    @Override
    public List<TmlAndId> listTmls(){
        return mySQLMapper.listTmls();
    }*/

    /**
     * 获取车辆的frameNum和type的对应关系
     * @return
     */
/*    @Override
    public List<FrameAndType> listMachines(){
        return mySQLMapper.listMachines();
    }*/
    /**
     * 获取车辆的作业面积需要的信息
     * @return
     */
    @Override
    public List<MachineAndTml> listMachineAndTml(){
        return mySQLMapper.listMachineAndTml();
    }

    @Override
    public List<WorkArea> getSingleWorkRecords(String frameNum,Timestamp stime, Timestamp etime){
        return postgreSQLMapper.getSingleWorkRecords(frameNum,stime,etime);
    }

    @Override
    public Counts getCounts(String frameNum){
        return postgreSQLMapper.getCounts(frameNum);
    }
}
