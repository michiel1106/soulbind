package bikerdbmid.soulbound.api.content;

import java.util.*;

public enum EDistType {
    // High is worse

    LOW(10, 20, 1),
    MEDIUM(20, 30, 2),
    HIGH(40, 50, 3)
    ;

    final int lowEnd;
    final int highEnd;
    final int index;

    EDistType(int lowEnd, int highEnd, int index) {
        this.lowEnd = lowEnd;
        this.highEnd = highEnd;
        this.index = index;
    }


    public static Optional<EDistType> forDistance(double distance) {
        EDistType best = null;
        for (EDistType type : values()) {
            if (distance >= type.lowEnd && (best == null || type.lowEnd > best.lowEnd)) {
                best = type;
            }
        }
        return Optional.ofNullable(best);
    }
}
