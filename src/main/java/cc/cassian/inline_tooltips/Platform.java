package cc.cassian.inline_tooltips;

//? fabric {
import cc.cassian.inline_tooltips.fabric.FabricPlatformImpl;
//?}
import java.nio.file.Path;
//? neoforge {
/*import cc.cassian.inline_tooltips.neoforge.NeoforgePlatformImpl;
*///?}

public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}


    boolean isModLoaded(String modid);
    String loader();

    Path getConfigDir();
}
