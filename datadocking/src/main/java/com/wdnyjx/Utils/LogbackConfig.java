package com.wdnyjx.Utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * 让 logback 的 level 更花里胡哨
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/13 19:59
 * @Version:v0.0.1
 */
public class LogbackConfig extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        switch (level.toInt()){
            case Level.DEBUG_INT:
                return ANSIConstants.BLUE_FG;
            case Level.INFO_INT:
                return ANSIConstants.GREEN_FG;
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            case Level.ERROR_INT:
                return ANSIConstants.RED_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
