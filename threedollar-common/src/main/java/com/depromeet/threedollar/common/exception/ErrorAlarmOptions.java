package com.depromeet.threedollar.common.exception;

public enum ErrorAlarmOptions {

    ON,
    OFF;

    public boolean isSetAlarm() {
        return this.equals(ErrorAlarmOptions.ON);
    }

}
