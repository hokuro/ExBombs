/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.missile.EntityMissile;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.inventory.ContainerMissile;
import mod.exbombs.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class GuiMissile extends ContainerScreen<ContainerMissile> {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/missilegui.png");
	private World world;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private TextFieldWidget textX;
	private TextFieldWidget textZ;
	public EntityMissile missile;

	public GuiMissile(ContainerMissile container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		world = inv.player.world;
		this.missile = container.getMissile();
	}

	@Override
	public void init() {
		this.imgWidth = 205;
		this.imgHeight = 176;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttons.clear();
		this.addButton(new Button(this.width / 2 - 75, this.imgTop + 110, 150, 20, "Launch",(bt)->{
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
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new StringTextComponent("Missile Target out of range!"),false);
					} else {
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new StringTextComponent("Missile launching!"),false);
						MessageHandler.SendMessage_MissileLaunchClient(missile.getEntityId(), x, z);
					}
				} catch (Exception exception) {
					if ((exception instanceof NumberFormatException)) {
						ExBombsMinecraftHelper.getPlayer().sendStatusMessage(new StringTextComponent("Illegal input!"),false);
					}
				} finally {
					Minecraft.getInstance().displayGuiScreen(null);
					Minecraft.getInstance().mouseHelper.grabMouse();
				}
			}
		));
		this.textX = new TextFieldWidget(this.font, this.width / 2 + 5, this.imgTop + 50, 60, 14, "");
		this.textX.setMaxStringLength(8);
		this.textZ = new TextFieldWidget(this.font, this.width / 2 + 5, this.imgTop + 75, 60, 14, "");
		this.textZ.setMaxStringLength(8);

		this.children.add(textX);
		this.children.add(textZ);
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		blit(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);

		this.textX.render(mouseX,mouseY,partialTicks);
		this.textZ.render(mouseX,mouseY,partialTicks);
		this.font.drawString("X Coordinate:", this.width / 2 - 70, this.imgTop + 53, 4210752);
		this.font.drawString("Z Coordinate:", this.width / 2 - 70, this.imgTop + 78, 4210752);
		for (int index = 0; index < this.buttons.size(); index++) {
			Widget guibutton = this.buttons.get(index);
			guibutton.render(mouseX, mouseY, partialTicks);// .drawButton(this.mc, i, j, f);
		}
		drawCenteredStringWithoutShadow(this.font, "Missile:", this.width / 2, this.imgTop + 15, 4210752);
	}

	protected void drawCenteredStringWithoutShadow(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	public boolean mouseClicked(double i, double j, int k) {
		super.mouseClicked(i, j, k);
		this.textX.mouseClicked(i, j, k);
		this.textZ.mouseClicked(i, j, k);
		return false;
	}

//	@Override
//	public boolean charTyped(char c, int key){
//		if (key == 1) {
//			Minecraft.getInstance().displayGuiScreen(null);
//			Minecraft.getInstance().mouseHelper.grabMouse();;
//		}
//		if (this.textX.isFocused()) {
//			return this.textX.charTyped(c,key);
//		}
//		if (this.textZ.isFocused()) {
//			return this.textZ.charTyped(c,key);
//		}
//		return true;
//	}

	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
			return true;
		} else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
			return false;
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
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
}
