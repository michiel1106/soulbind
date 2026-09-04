package bikerdbmid.soulbound.components.IComponents;

import org.jspecify.annotations.*;
import org.ladysnake.cca.api.v3.component.*;

import java.util.*;

public interface ISoulDataComponent extends Component {
    SoulData getValue();


    public class SoulData {
        public static final SoulData EMPTY = new SoulData(null, List.of(), List.of());



        @Nullable public UUID uuid;
        public List<String> buffs;
        public List<String> debuffs;

        public SoulData(@Nullable UUID uuid, List<String> buffs, List<String> debuffs) {
            this.uuid = uuid;
            this.buffs = buffs;
            this.debuffs = debuffs;
        }

        public void setUuid(@Nullable UUID uuid) {
            this.uuid = uuid;
        }

        public void setBuffs(List<String> buffs) {
            this.buffs = new ArrayList<>(buffs);
        }

        public void setDebuffs(List<String> debuffs) {
            this.debuffs = new ArrayList<>(debuffs);
        }
    }
}
