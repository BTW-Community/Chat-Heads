package net.dravigen.chat_heads.mixin;

import net.dravigen.chat_heads.ChatHeads;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMx extends Gui {
	@Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FontRenderer;drawStringWithShadow(Ljava/lang/String;III)I", ordinal = 2))
	private int drawHeadInList(FontRenderer instance, String name, int x, int y, int color) {
		ChatHeads.drawPlayerHead(this, x + 1, y, name, 255);
		
		return instance.drawStringWithShadow(name, x + 11, y, color);
	}
}

