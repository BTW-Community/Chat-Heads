package net.dravigen.chat_heads.mixin;

import net.dravigen.chat_heads.ChatHeads;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMx extends Gui {
	
	@Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FontRenderer;drawStringWithShadow(Ljava/lang/String;III)I"))
	private int draw(FontRenderer instance, String s, int x, int y, int color) {
		List<String> playerNames = new ArrayList<>();
		
		for (GuiPlayerInfo playerInfo : (List<GuiPlayerInfo>) Minecraft.getMinecraft().thePlayer.sendQueue.playerInfoList) {
			playerNames.add(playerInfo.name);
		}
		
		boolean b2 = s.contains("<") && s.contains(">");
		boolean b3 = (s.startsWith("[") || s.startsWith("<")) && b2;
		boolean b4 = s.startsWith("[") && !b2 && s.contains("[") && s.contains("]");
		
		int offset = 1;
		
		if (b3 || b4) {
			try {
				int xStart = 0;
				String nickname = b3 ? s.split(">")[0].split("<")[1] : b4 ? s.split("]")[0].split("\\[")[1] : s;
				String prefix = "<";
				String msg = s;
				
				if (s.startsWith("[") && b2) {
					String nameInBracket = s.split("]")[0].split("\\[")[1];
					
					if (playerNames.contains(nameInBracket)) {
						nickname = nameInBracket;
						prefix = "\\[";
						xStart = instance.getStringWidth(prefix);
						msg = s.split(prefix)[1];
					}
					else {
						msg = s.split("<")[1];
						xStart = instance.getStringWidth(s.split("<")[0]);
					}
				}
				else if (s.startsWith("[") ) {
					prefix = "\\[";
					xStart = instance.getStringWidth("[");
					msg = s.split(prefix)[1];
				}
				else if (s.startsWith("<")) {
					msg = s.split("<")[1];
					xStart = instance.getStringWidth("<");
				}
				
				if (playerNames.contains(nickname)) {
					ChatHeads.drawPlayerHead(this, offset + xStart, y, nickname, (float) (color >> 24 & 0xFF) / 255.0f);
					
					instance.drawStringWithShadow(prefix.replace("\\", ""), x, y, color);
					return instance.drawStringWithShadow(msg, x + 10 + offset + xStart, y, color);
					
				}
			} catch (Exception ignored) {
			}
		}
		
		return instance.drawStringWithShadow(s, x + offset, y, color);
	}
}
