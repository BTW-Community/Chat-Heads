package net.dravigen.chat_heads.mixin;

import net.dravigen.chat_heads.ChatHeads;
import net.minecraft.src.EntityClientPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityClientPlayerMP.class)
public abstract class EntityClientPlayerMPMx {
	@Inject(method = "onUpdate", at = @At("HEAD"))
	public void onUpdate(CallbackInfo ci) {
		ChatHeads.updateSkins();
	}
}
