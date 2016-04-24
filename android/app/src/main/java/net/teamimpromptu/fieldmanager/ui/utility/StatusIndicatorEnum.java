package net.teamimpromptu.fieldmanager.ui.utility;


import net.rubicon.indepth.R;
import net.teamimpromptu.fieldmanager.db.StatusEnum;

/**
 * job status indicator images
 */
public enum StatusIndicatorEnum {
    UNKNOWN(StatusEnum.UNKNOWN, null, null),
    NORMAL(StatusEnum.NORMAL, R.drawable.green_pin_50, R.drawable.green_dot),
    GUARDED(StatusEnum.GUARDED, R.drawable.blue_pin_50, R.drawable.blue_dot),
    SERIOUS(StatusEnum.SERIOUS, R.drawable.yellow_pin_50, R.drawable.yellow_dot),
    CRITICAL(StatusEnum.CRITICAL, R.drawable.red_pin_50, R.drawable.red_dot);

    private final StatusEnum _status;
    private final Integer _mapImageResourceId;
    private final Integer _listImageResourceId;

    StatusIndicatorEnum(StatusEnum status, Integer mapImageResourceId, Integer listImageResourceId) {
        _status = status;
        _mapImageResourceId = mapImageResourceId;
        _listImageResourceId = listImageResourceId;
    }


    public Integer getMapImageResourceId() {
        return _mapImageResourceId;
    }

    public Integer getListImageResourceId() {
        return _listImageResourceId;
    }

    /**
     * @param status
     * @return
     */
    public static Integer getMapImageResourceForStatus(StatusEnum status) {
        StatusIndicatorEnum match = getMatchForStatus(status);

        return (match == null) ? null : match.getMapImageResourceId();
    }

    /**
     * @param status
     * @return
     */
    public static Integer getListImageResourceForStatus(StatusEnum status) {
        StatusIndicatorEnum match = getMatchForStatus(status);

        return (match == null) ? null : match.getListImageResourceId();
    }

    /**
     * @param status
     * @return
     */
    public static StatusIndicatorEnum getMatchForStatus(StatusEnum status) {
        StatusIndicatorEnum match = null;

        for (StatusIndicatorEnum value : values()) {
            if (value._status.equals(status)) {
                match = value;
                break;
            }
        }

        return match;
    }
}


