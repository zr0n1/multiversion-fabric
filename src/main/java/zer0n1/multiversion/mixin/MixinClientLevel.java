package zer0n1.multiversion.mixin;

import net.minecraft.client.level.ClientLevel;
import net.minecraft.level.Level;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.network.ClientPlayNetworkHandler;
import net.minecraft.tileentity.TileEntityBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zer0n1.multiversion.protocol.ProtocolManager;
import zer0n1.multiversion.protocol.packet.UpdateTileEntity0x3BPacket_P2;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level
{
    private boolean field_184;
    @Shadow private ClientPlayNetworkHandler netHandler;

    public MixinClientLevel(DimensionData dimensionData, String name, Dimension dimension, long seed) {
        super(dimensionData, name, dimension, seed);
    }

    @Override
    public void method_203(int i, int j, int k, TileEntityBase tile) {
        if(field_184) {
            return;
        }
        if(ProtocolManager.version() < 7) {
            netHandler.sendPacket(new UpdateTileEntity0x3BPacket_P2(i, j, k, tile));
        }
    }
}
