package org.odesamama.mcd.domain.enums;

/**
 * Created by starnakin on 25.09.2015.
 */
public enum Permissions {

    USER_READ(400), USER_WRITE(600), GROUP_READ(440), GROUP_WRITE(660), OTHER_READ(444), OTHER_WRITE(666);

    /**
     * user,group,other
     */
    private int code;

    private Permissions(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Permissions valueOf(int code) {
        for (Permissions e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
