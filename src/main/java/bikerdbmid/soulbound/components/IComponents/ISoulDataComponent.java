package bikerdbmid.soulbound.components.IComponents;

import org.jspecify.annotations.*;
import org.ladysnake.cca.api.v3.component.*;

import java.util.*;

public interface ISoulDataComponent extends Component {
    SoulData getValue();


    public class SoulData {
        public static final SoulData EMPTY = new SoulData(null, "", "");



        @Nullable public UUID uuid;
        public String effect;
        public String ability;

        public SoulData(@Nullable UUID uuid, String effect, String ability) {
            this.uuid = uuid;
            this.effect = effect;
            this.ability = ability;
        }

        public void setUuid(@Nullable UUID uuid) {
            this.uuid = uuid;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public void setAbility(String ability) {
            this.ability = ability;
        }
    }
}
