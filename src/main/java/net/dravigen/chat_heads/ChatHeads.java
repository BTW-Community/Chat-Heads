package net.dravigen.chat_heads;

import api.AddonHandler;
import api.BTWAddon;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHeads extends BTWAddon {
	static Map<String, ResourceLocation> storedSkins = new HashMap<>();
	private static ChatHeads instance;
	private static int i;
	
	public ChatHeads() {
		super();
	}
	
	public static void updateSkins() {
		if (i++ >= 40) {
			for (GuiPlayerInfo playerInfo : (List<GuiPlayerInfo>) Minecraft.getMinecraft().thePlayer.sendQueue.playerInfoList) {
				if (!storedSkins.containsKey(playerInfo.name)) {
					ResourceLocation skin = AbstractClientPlayer.getLocationSkin(playerInfo.name);
					ThreadDownloadImageData downloadImageSkin = AbstractClientPlayer.getDownloadImageSkin(skin,
																										  playerInfo.name);
					
					if (downloadImageSkin.isTextureUploaded()) {
						storedSkins.put(playerInfo.name, skin);
					}
				}
			}
			
			i = 0;
		}
	}
	
	public static void drawPlayerHead(Gui gui, int x, int y, String nickname, float opacity) {
		GL11.glPushMatrix();
		ResourceLocation skin = storedSkins.get(nickname);
		
		if (skin == null) {
			skin = AbstractClientPlayer.getLocationSkin(nickname);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
		GL11.glColor4d(1, 1, 1, opacity);
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(0.25f, 0.25f, 0.25f);
		GL11.glEnable(GL11.GL_BLEND);
		
		gui.zLevel += 100.0f;
		gui.drawTexturedModalRect(0, 0, 32, 32, 32, 32);
		gui.zLevel -= 100.0f;
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void initialize() {
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
	}
}
