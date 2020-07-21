package com.wdnyjx.Mapper.slave;

import com.wdnyjx.Entity.Counts;
import com.wdnyjx.Entity.gjhn.WorkArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author overlord
 * @since 2020-05-13
 */
@Repository
public interface PostgreSQLMapper {
    List<WorkArea> getWorkRecords(@Param("stime") Timestamp stime, @Param("etime") Timestamp etime);

    List<WorkArea> getSingleWorkRecords(@Param("frameNum") String frameNum, @Param("stime") Timestamp stime, @Param("etime") Timestamp etime);

    Counts getCounts(@Param("frameNum") String frameNum);
}
