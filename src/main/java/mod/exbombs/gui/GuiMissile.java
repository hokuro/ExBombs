/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import mod.exbombs.core.ExBombs;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageMissileLaunchClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class GuiMissile extends GuiScreen {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/missileGUI.png");
	private Minecraft f;
	private World world;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private GuiTextField textX;
	private GuiTextField textZ;
	public EntityMissile missile;

	public GuiMissile(World worldObj, Minecraft minecraft, EntityMissile missile) {
		this.f = minecraft;
		this.world = worldObj;
		this.missile = missile;
	}

	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 176;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.imgTop + 110, 150, 20, "Launch"));

		this.textX = new GuiTextField(1,this.fontRendererObj, this.width / 2 + 5, this.imgTop + 50, 60, 14);
		this.textX.setMaxStringLength(8);
		this.textZ = new GuiTextField(2,this.fontRendererObj, this.width / 2 + 5, this.imgTop + 75, 60, 14);
		this.textZ.setMaxStringLength(8);

		Keyboard.enableRepeatEvents(true);
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(tex);
		drawTexturedModalRect(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);

		this.textX.drawTextBox();
		this.textZ.drawTextBox();
		this.fontRendererObj.drawString("X Coordinate:", this.width / 2 - 70, this.imgTop + 53, 4210752);
		this.fontRendererObj.drawString("Z Coordinate:", this.width / 2 - 70, this.imgTop + 78, 4210752);
		for (int index = 0; index < this.buttonList.size(); index++) {
			GuiButton guibutton = (GuiButton) this.buttonList.get(index);
			guibutton.drawButton(this.f, i, j);
		}
		drawCenteredStringWithoutShadow(this.fontRendererObj, "Missile:", this.width / 2, this.imgTop + 15, 4210752);
	}

	protected void drawCenteredStringWithoutShadow(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	public void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 0) {
			if ((this.textX.getText().isEmpty()) || (this.textZ.getText().isEmpty())) {
				return;
			}
			try {
				if (this.missile.flying) {
					return;
				}
				int x = Integer.parseInt(this.textX.getText());
				int z = Integer.parseInt(this.textZ.getText());

				double XCoord = x - this.missile.posX;
				double YCoord = z - this.missile.posZ;
				if ((Math.abs(XCoord) > 500.0D) || (Math.abs(YCoord) > 500.0D)) {
					ExBombsMinecraftHelper.getPlayer().addChatComponentMessage(new TextComponentString("Missile Target out of range!"));
				} else {
					ExBombsMinecraftHelper.getPlayer().addChatComponentMessage(new TextComponentString("Missile launching!"));
					ExBombs.INSTANCE.sendToServer(new MessageMissileLaunchClient(this.missile.getEntityId(), x, z));
				}
			} catch (Exception exception) {
				if ((exception instanceof NumberFormatException)) {
					ExBombsMinecraftHelper.getPlayer().addChatComponentMessage(new TextComponentString("Illegal input!"));
				}
			} finally {
				this.f.displayGuiScreen(null);
				this.f.setIngameFocus();
			}
		}
	}

	protected void mouseClicked(int i, int j, int k) {
		try {
			super.mouseClicked(i, j, k);
		} catch (IOException e) {

		}
		this.textX.mouseClicked(i, j, k);
		this.textZ.mouseClicked(i, j, k);
	}

	protected void keyTyped(char c, int i) {
		if (i == 1) {
			this.f.displayGuiScreen(null);
			this.f.setIngameFocus();
		}
		if (this.textX.isFocused()) {
			this.textX.textboxKeyTyped(c, i);
		}
		if (this.textZ.isFocused()) {
			this.textZ.textboxKeyTyped(c, i);
		}
	}

	public void updateScreen() {
		if (this.textX.isFocused()) {
			this.textX.updateCursorCounter();
		}
		if (this.textZ.isFocused()) {
			this.textZ.updateCursorCounter();
		}
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
