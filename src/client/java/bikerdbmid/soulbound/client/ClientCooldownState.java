package bikerdbmid.soulbound.client;

public class ClientCooldownState {
    public static boolean active = false;
    public static int remaining = 0;
    public static int total = 1;

    public static void set(int remaining, int total) {
        active = total > 0 && remaining > 0;
        ClientCooldownState.remaining = remaining;
        ClientCooldownState.total = Math.max(total, 1);
    }

    public static void tick() {
        if (remaining > 0) {
            remaining--;
            if (remaining == 0) active = false;
        }
    }
}
