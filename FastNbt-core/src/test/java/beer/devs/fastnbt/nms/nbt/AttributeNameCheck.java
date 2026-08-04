package beer.devs.fastnbt.nms.nbt;

import beer.devs.fastnbt.nms.Version;
import org.junit.Test;

public final class AttributeNameCheck
{
    @Test
    public void compatibility()
    {
        assert AttributeName.values().length == 40;
        assert AttributeOperation.ADD.getId() == 0;
        assert AttributeOperation.MULTIPLY_BASE.getId() == 1;
        assert AttributeOperation.MULTIPLY_TOTAL.getId() == 2;
        assert AttributeName.of("generic.movement_speed") == AttributeName.MOVEMENT_SPEED;
        assert AttributeName.of("minecraft:movement_speed") == AttributeName.MOVEMENT_SPEED;
        assert AttributeName.of("attackDamage") == AttributeName.ATTACK_DAMAGE;
        assert AttributeName.of("generic.attackDamage") == AttributeName.ATTACK_DAMAGE;
        assert AttributeName.of("minecraft:generic.attackDamage") == AttributeName.ATTACK_DAMAGE;
        assert AttributeName.of("horse.jumpStrength") == AttributeName.JUMP_STRENGTH;

        assert AttributeName.MOVEMENT_SPEED.getName(Version.v1_20_6)
                .equals("minecraft:generic.movement_speed");
        assert AttributeName.MOVEMENT_SPEED.getName(Version.v1_21_3)
                .equals("minecraft:movement_speed");
        assert AttributeName.JUMP_STRENGTH.getName(Version.v1_19_R3)
                .equals("minecraft:horse.jump_strength");
        assert AttributeName.JUMP_STRENGTH.getName(Version.v1_20_5)
                .equals("minecraft:generic.jump_strength");
        assert AttributeName.JUMP_STRENGTH.getName(Version.v1_21_3)
                .equals("minecraft:jump_strength");

        assert !AttributeName.MAX_ABSORPTION.isAvailable(Version.v1_20_R1);
        assert AttributeName.MAX_ABSORPTION.isAvailable(Version.v1_20_R2);
        assert !AttributeName.NAME_TAG_DISTANCE.isAvailable(Version.v26_1_2);
        assert AttributeName.NAME_TAG_DISTANCE.isAvailable(Version.v26_2);

        try
        {
            AttributeName.NAME_TAG_DISTANCE.getName(Version.v26_1_2);
            throw new AssertionError("Unavailable attribute accepted");
        }
        catch (IllegalArgumentException expected) {}
    }
}
