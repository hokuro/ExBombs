/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.EntityMissile;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class GuiMissile extends GuiScreen {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/missilegui.png");
	private World world;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private GuiTextField textX;
	private GuiTextField textZ;
	public EntityMissile missile;

	public GuiMissile(World worldObj,EntityMissile missile) {
		this.world = worldObj;
		this.missile = missile;
	}

	@Override
	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 176;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		GuiButton b1 = new GuiButton(0, this.width / 2 - 75, this.imgTop + 110, 150, 20, "Launch"){
			public void onClick(double mousex, double mousey){
				if ((textX.getText().isEmpty()) || (textZ.getText().isEmpty())) {
					return;
				}
				try {
					if (missile.flying) {
						return;
					}
					int x = Integer.parseInt(textX.getText());
					int z = Integer.parseInt(textZ.getText());

					double XCoord = x - missile.posX;
					double YCoord = z - missile.posZ;
					if ((Math.abs(XCoord) > 500.0D) || (Math.abs(YCoord) > 500.0D)) {
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Missile Target out of range!"),false);
					} else {
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Missile launching!"),false);
						MessageHandler.SendMessage_MissileLaunchClient(missile.getEntityId(), x, z);
					}
				} catch (Exception exception) {
					if ((exception instanceof NumberFormatException)) {
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Illegal input!"),false);
					}
				} finally {
					Minecraft.getInstance().displayGuiScreen(null);
					Minecraft.getInstance().mouseHelper.grabMouse();
				}
			}
		};

		this.buttons.clear();
		this.buttons.add(b1);

		this.textX = new GuiTextField(1,this.fontRenderer, this.width / 2 + 5, this.imgTop + 50, 60, 14);
		this.textX.setMaxStringLength(8);
		this.textZ = new GuiTextField(2,this.fontRenderer, this.width / 2 + 5, this.imgTop + 75, 60, 14);
		this.textZ.setMaxStringLength(8);
		this.children.add(b1);
		this.mc.keyboardListener.enableRepeatEvents(true);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		drawTexturedModalRect(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);

		this.textX.drawTextField(mouseX,mouseY,partialTicks);
		this.textZ.drawTextField(mouseX,mouseY,partialTicks);
		this.fontRenderer.drawString("X Coordinate:", this.width / 2 - 70, this.imgTop + 53, 4210752);
		this.fontRenderer.drawString("Z Coordinate:", this.width / 2 - 70, this.imgTop + 78, 4210752);
		for (int index = 0; index < this.buttons.size(); index++) {
			GuiButton guibutton = (GuiButton) this.buttons.get(index);
			guibutton.render(mouseX, mouseY, partialTicks);// .drawButton(this.mc, i, j, f);
		}
		drawCenteredStringWithoutShadow(this.fontRenderer, "Missile:", this.width / 2, this.imgTop + 15, 4210752);
	}

	protected void drawCenteredStringWithoutShadow(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

//	public void actionPerformed(GuiButton guibutton) {
//		if (guibutton.id == 0) {
//			if ((this.textX.getText().isEmpty()) || (this.textZ.getText().isEmpty())) {
//				return;
//			}
//			try {
//				if (this.missile.flying) {
//					return;
//				}
//				int x = Integer.parseInt(this.textX.getText());
//				int z = Integer.parseInt(this.textZ.getText());
//
//				double XCoord = x - this.missile.posX;
//				double YCoord = z - this.missile.posZ;
//				if ((Math.abs(XCoord) > 500.0D) || (Math.abs(YCoord) > 500.0D)) {
//					ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Missile Target out of range!"),false);
//				} else {
//					ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Missile launching!"),false);
//					Mod_ExBombs.INSTANCE.sendToServer(new MessageMissileLaunchClient(this.missile.getEntityId(), x, z));
//				}
//			} catch (Exception exception) {
//				if ((exception instanceof NumberFormatException)) {
//					ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new TextComponentString("Illegal input!"),false);
//				}
//			} finally {
//				Minecraft.displayGuiScreen(null);
//				Minecraft.setIngameFocus();
//			}
//		}
//	}

	@Override
	public boolean mouseClicked(double i, double j, int k) {
		super.mouseClicked(i, j, k);
		this.textX.mouseClicked(i, j, k);
		this.textZ.mouseClicked(i, j, k);
		return false;
	}

//	@Override
//	 public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
//		if (p_keyPressed_1_ == 256) {
//			Minecraft.getInstance().displayGuiScreen(null);
//			Minecraft.getInstance().mouseHelper.grabMouse();;
//		}
////		if (this.textX.isFocused()) {
////			this.charTyped(p_keyPressed_1_, p_keyPressed_2_,p_keyPressed_3_);
////		}
////		if (this.textZ.isFocused()) {
////			this.textZ.keyPressed(p_keyPressed_1_, p_keyPressed_2_,p_keyPressed_3_);
////		}
//		return true;
//	}

	@Override
	public boolean charTyped(char c, int key){
		if (key == 1) {
			Minecraft.getInstance().displayGuiScreen(null);
			Minecraft.getInstance().mouseHelper.grabMouse();;
		}
		if (this.textX.isFocused()) {
			return this.textX.charTyped(c,key);
		}
		if (this.textZ.isFocused()) {
			return this.textZ.charTyped(c,key);
		}
		return true;
	}

	@Override
	public void tick() {
		if (this.textX.isFocused()) {
			this.textX.tick();
		}
		if (this.textZ.isFocused()) {
			this.textZ.tick();
		}
	}

	public void onGuiClosed() {
		this.mc.keyboardListener.enableRepeatEvents(false);
	}
}
