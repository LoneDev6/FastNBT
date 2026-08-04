package beer.devs.fastnbt.nms.nbt;

import beer.devs.fastnbt.nms.Version;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Version-independent names of vanilla Minecraft attributes.
 */
public enum AttributeName
{
    MAX_HEALTH(Version.v1_17_R1, "generic.max_health"),
    FOLLOW_RANGE(Version.v1_17_R1, "generic.follow_range"),
    KNOCKBACK_RESISTANCE(Version.v1_17_R1, "generic.knockback_resistance"),
    MOVEMENT_SPEED(Version.v1_17_R1, "generic.movement_speed"),
    FLYING_SPEED(Version.v1_17_R1, "generic.flying_speed"),
    ATTACK_DAMAGE(Version.v1_17_R1, "generic.attack_damage"),
    ATTACK_KNOCKBACK(Version.v1_17_R1, "generic.attack_knockback"),
    ATTACK_SPEED(Version.v1_17_R1, "generic.attack_speed"),
    ARMOR(Version.v1_17_R1, "generic.armor"),
    ARMOR_TOUGHNESS(Version.v1_17_R1, "generic.armor_toughness"),
    LUCK(Version.v1_17_R1, "generic.luck"),
    SPAWN_REINFORCEMENTS(Version.v1_17_R1, "zombie.spawn_reinforcements"),
    JUMP_STRENGTH(Version.v1_17_R1, "horse.jump_strength"),

    MAX_ABSORPTION(Version.v1_20_R2, "generic.max_absorption"),

    BLOCK_BREAK_SPEED(Version.v1_20_5, "player.block_break_speed"),
    BLOCK_INTERACTION_RANGE(Version.v1_20_5, "player.block_interaction_range"),
    ENTITY_INTERACTION_RANGE(Version.v1_20_5, "player.entity_interaction_range"),
    FALL_DAMAGE_MULTIPLIER(Version.v1_20_5, "generic.fall_damage_multiplier"),
    GRAVITY(Version.v1_20_5, "generic.gravity"),
    SAFE_FALL_DISTANCE(Version.v1_20_5, "generic.safe_fall_distance"),
    SCALE(Version.v1_20_5, "generic.scale"),
    STEP_HEIGHT(Version.v1_20_5, "generic.step_height"),

    BURNING_TIME(Version.v1_21, "generic.burning_time"),
    EXPLOSION_KNOCKBACK_RESISTANCE(Version.v1_21, "generic.explosion_knockback_resistance"),
    MINING_EFFICIENCY(Version.v1_21, "player.mining_efficiency"),
    MOVEMENT_EFFICIENCY(Version.v1_21, "generic.movement_efficiency"),
    OXYGEN_BONUS(Version.v1_21, "generic.oxygen_bonus"),
    SNEAKING_SPEED(Version.v1_21, "player.sneaking_speed"),
    SUBMERGED_MINING_SPEED(Version.v1_21, "player.submerged_mining_speed"),
    SWEEPING_DAMAGE_RATIO(Version.v1_21, "player.sweeping_damage_ratio"),
    WATER_MOVEMENT_EFFICIENCY(Version.v1_21, "generic.water_movement_efficiency"),

    TEMPT_RANGE(Version.v1_21_3),

    CAMERA_DISTANCE(Version.v1_21_6),
    WAYPOINT_RECEIVE_RANGE(Version.v1_21_6),
    WAYPOINT_TRANSMIT_RANGE(Version.v1_21_6),

    AIR_DRAG_MODIFIER(Version.v26_2),
    BELOW_NAME_DISTANCE(Version.v26_2),
    BOUNCINESS(Version.v26_2),
    FRICTION_MODIFIER(Version.v26_2),
    NAME_TAG_DISTANCE(Version.v26_2);

    private static final Map<String, AttributeName> LOOKUP = createLookup();

    private final Version since;
    private final String modernId;
    private final String legacyId;

    AttributeName(Version since)
    {
        this(since, null);
    }

    AttributeName(Version since, String legacyName)
    {
        this.since = since;
        String modernName = name().toLowerCase(Locale.ROOT);
        this.modernId = "minecraft:" + modernName;
        this.legacyId = "minecraft:" + (legacyName == null ? modernName : legacyName);
    }

    public boolean isAvailable()
    {
        return isAvailable(Version.get());
    }

    public boolean isAvailable(Version version)
    {
        return version != Version.UNKNOWN && version.ordinal() >= since.ordinal();
    }

    public String getName()
    {
        return getName(Version.get());
    }

    public String getName(Version version)
    {
        Objects.requireNonNull(version, "version");
        if (!isAvailable(version))
            throw new IllegalArgumentException("Attribute " + name() + " is not available in Minecraft " + version.getName());

        if (this == JUMP_STRENGTH
                && version.ordinal() >= Version.v1_20_5.ordinal()
                && version.ordinal() < Version.v1_21_3.ordinal())
            return "minecraft:generic.jump_strength";
        return version.ordinal() >= Version.v1_21_3.ordinal() ? modernId : legacyId;
    }

    public static AttributeName of(String name)
    {
        AttributeName attribute = find(name);
        if (attribute == null)
            throw new IllegalArgumentException("Unknown vanilla attribute: " + name);
        return attribute;
    }

    static AttributeName find(String name)
    {
        if (name == null)
            return null;
        return LOOKUP.get(normalize(name));
    }

    private static Map<String, AttributeName> createLookup()
    {
        Map<String, AttributeName> names = new HashMap<>();
        for (AttributeName attribute : values())
        {
            names.put(normalize(attribute.modernId), attribute);
            if (!attribute.legacyId.equals(attribute.modernId))
                names.put(normalize(attribute.legacyId), attribute);
        }
        names.put("generic.jump_strength", JUMP_STRENGTH);
        names.put("spawn_reinforcements_chance", SPAWN_REINFORCEMENTS);
        names.put("zombie.spawn_reinforcements_chance", SPAWN_REINFORCEMENTS);

        alias(names, MAX_HEALTH, "maxHealth", "generic.maxHealth", "Max Health");
        alias(names, SPAWN_REINFORCEMENTS, "spawnReinforcements", "zombie.spawnReinforcements", "Spawn Reinforcements Chance");
        alias(names, JUMP_STRENGTH, "jumpStrength", "horse.jumpStrength", "Jump Strength");
        alias(names, FOLLOW_RANGE, "followRange", "generic.followRange", "Follow Range");
        alias(names, KNOCKBACK_RESISTANCE, "knockbackResistance", "generic.knockbackResistance", "Knockback Resistance");
        alias(names, MOVEMENT_SPEED, "movementSpeed", "generic.movementSpeed", "Movement Speed");
        alias(names, FLYING_SPEED, "flyingSpeed", "generic.flyingSpeed", "Flying Speed");
        alias(names, ATTACK_DAMAGE, "attackDamage", "generic.attackDamage");
        alias(names, ATTACK_KNOCKBACK, "attackKnockback", "generic.attackKnockback");
        alias(names, ATTACK_SPEED, "attackSpeed", "generic.attackSpeed");
        alias(names, ARMOR_TOUGHNESS, "armorToughness", "generic.armorToughness");
        return names;
    }

    private static void alias(Map<String, AttributeName> names, AttributeName attribute, String... aliases)
    {
        for (String alias : aliases)
            names.put(normalize(alias), attribute);
    }

    private static String normalize(String name)
    {
        String normalized = name.toLowerCase(Locale.ROOT);
        return normalized.startsWith("minecraft:") ? normalized.substring("minecraft:".length()) : normalized;
    }
}
