package mod.exbombs.particle;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public class ParticleTypeLargeSmokeEx extends ParticleType<ParticleTypeLargeSmokeEx> implements IParticleData{

	public ParticleTypeLargeSmokeEx(boolean p_i50792_1_, IDeserializer<ParticleTypeLargeSmokeEx> p_i50792_2_) {
		super(p_i50792_1_, p_i50792_2_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public ParticleType<?> getType() {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public void write(PacketBuffer buffer) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getParameters() {
		return Registry.PARTICLE_TYPE.getKey(this).toString();
	}

}
