package danny8208.hungerrings.util;

import net.minecraftforge.fml.ModList;

public enum SupportingMods {
    CURIOS("curios");

     private final String modid;
     private final boolean loaded;

     SupportingMods(String modid) {
         this.modid = modid;
         this.loaded = ModList.get() != null && ModList.get().getModContainerById(modid).isPresent();
     }

    public boolean isLoaded() {
        return this.loaded;
    }
}
