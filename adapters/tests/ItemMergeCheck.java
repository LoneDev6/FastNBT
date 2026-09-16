package beer.devs.fastnbt.nms.nbt.impl;

import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public final class ItemMergeCheck
{
    private static final DataComponentType<String> EXTRA_COMPONENT =
            DataComponentType.<String>builder().persistent(Codec.STRING).build();

    public static void main(String[] args)
    {
        PrintStream originalError = System.err;
        try
        {
            run(args);
        }
        catch (Throwable failure)
        {
            failure.printStackTrace(originalError);
            System.exit(1);
        }
    }

    private static void run(String[] args) throws Exception
    {
        check(args.length == 1, "Expected the adapter target version");
        SharedConstants.tryDetectVersion();
        installServer(args[0].substring(1).replace('_', '.'));
        Bootstrap.bootStrap();
        bindStoneComponents();

        Object adapter = Class.forName(ItemMergeCheck.class.getPackageName() + ".CraftItemStack_" + args[0])
                .getDeclaredConstructor().newInstance();
        Method merge = adapter.getClass().getMethod("merge", ItemStack.class, ItemStack.class);

        componentMerge(adapter, merge);
        customDataMerge(adapter, merge);
        selfMerge(adapter, merge);
    }

    private static void componentMerge(Object adapter, Method merge) throws Exception
    {
        net.minecraft.world.item.ItemStack target = stack();
        net.minecraft.world.item.ItemStack source = stack();
        source.set(EXTRA_COMPONENT, "unregistered");
        invoke(adapter, merge, target, source);
        check("unregistered".equals(target.get(EXTRA_COMPONENT)), "Missing component was not copied");

        target = stack();
        source = stack();
        source.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        invoke(adapter, merge, target, source);
        check(Boolean.TRUE.equals(target.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)),
                "Named component was not copied");

        target = stack();
        source = stack();
        target.set(DataComponents.MAX_STACK_SIZE, 16);
        invoke(adapter, merge, target, source);
        check(Integer.valueOf(16).equals(target.get(DataComponents.MAX_STACK_SIZE)),
                "Source prototype default overwrote a target override");

        target = stack();
        source = stack();
        target.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
        invoke(adapter, merge, target, source);
        check(Boolean.FALSE.equals(target.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)),
                "Absent source component removed a target component");

        target = stack();
        source = stack();
        target.set(DataComponents.MAX_STACK_SIZE, 16);
        source.remove(DataComponents.MAX_STACK_SIZE);
        invoke(adapter, merge, target, source);
        check(Integer.valueOf(16).equals(target.get(DataComponents.MAX_STACK_SIZE)),
                "Removed source component removed a target component");

        mergeGliderWhenPresent(adapter, merge);
    }

    @SuppressWarnings("unchecked")
    private static void mergeGliderWhenPresent(Object adapter, Method merge) throws Exception
    {
        DataComponentType<Object> glider;
        try
        {
            glider = (DataComponentType<Object>) DataComponents.class.getField("GLIDER").get(null);
        }
        catch (NoSuchFieldException ignored)
        {
            return;
        }

        Object unit = Class.forName("net.minecraft.util.Unit").getField("INSTANCE").get(null);
        net.minecraft.world.item.ItemStack target = stack();
        net.minecraft.world.item.ItemStack source = stack();
        source.set(glider, unit);
        invoke(adapter, merge, target, source);
        check(target.has(glider), "GLIDER was not copied");
    }

    private static void customDataMerge(Object adapter, Method merge) throws Exception
    {
        net.minecraft.world.item.ItemStack target = stack();
        net.minecraft.world.item.ItemStack source = stack();
        CompoundTag sourceOnly = new CompoundTag();
        sourceOnly.putInt("source", 1);
        source.set(DataComponents.CUSTOM_DATA, CustomData.of(sourceOnly));
        invoke(adapter, merge, target, source);
        check(customData(target).contains("source"), "Custom data was not copied to an empty target");

        target = stack();
        source = stack();
        CompoundTag targetTag = new CompoundTag();
        targetTag.putInt("target", 1);
        targetTag.putInt("overlap", 1);
        CompoundTag targetNested = new CompoundTag();
        targetNested.putInt("targetNested", 1);
        targetTag.put("nested", targetNested);
        target.set(DataComponents.CUSTOM_DATA, CustomData.of(targetTag));

        CompoundTag sourceTag = new CompoundTag();
        sourceTag.putInt("source", 1);
        sourceTag.putInt("overlap", 2);
        CompoundTag sourceNested = new CompoundTag();
        sourceNested.putInt("sourceNested", 1);
        sourceTag.put("nested", sourceNested);
        source.set(DataComponents.CUSTOM_DATA, CustomData.of(sourceTag));

        invoke(adapter, merge, target, source);
        CompoundTag merged = customData(target);
        check(merged.contains("target") && merged.contains("source"), "Custom data roots were not merged");
        check(IntTag.valueOf(2).equals(merged.get("overlap")), "Source custom data did not win conflicts");
        check(merged.get("nested") instanceof CompoundTag nested
                        && nested.contains("targetNested") && nested.contains("sourceNested"),
                "Nested custom data was not merged");

        CompoundTag liveSource = source.get(DataComponents.CUSTOM_DATA).getUnsafe();
        liveSource.remove("source");
        ((CompoundTag) liveSource.get("nested")).remove("sourceNested");
        check(merged.contains("source") && ((CompoundTag) merged.get("nested")).contains("sourceNested"),
                "Merged custom data retained source references");
    }

    private static void selfMerge(Object adapter, Method merge) throws Exception
    {
        net.minecraft.world.item.ItemStack stack = stack();
        stack.set(EXTRA_COMPONENT, "self");
        CompoundTag tag = new CompoundTag();
        tag.putInt("self", 1);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        ItemStack craft = mirror(stack);
        merge.invoke(adapter, craft, craft);
        check("self".equals(stack.get(EXTRA_COMPONENT)) && customData(stack).contains("self"),
                "Self merge changed the stack");
    }

    private static net.minecraft.world.item.ItemStack stack()
    {
        return new net.minecraft.world.item.ItemStack(Items.STONE);
    }

    private static void bindStoneComponents() throws Exception
    {
        Object holder = Items.STONE.builtInRegistryHolder();
        Method isBound;
        try
        {
            isBound = holder.getClass().getMethod("areComponentsBound");
        }
        catch (NoSuchMethodException ignored)
        {
            return;
        }
        if ((boolean) isBound.invoke(holder)) return;

        // ponytail: bind only the stone default used here; use data-pack bootstrap for broader item tests.
        DataComponentMap components = DataComponentMap.builder()
                .set(DataComponents.MAX_STACK_SIZE, 64)
                .build();
        holder.getClass().getMethod("bindComponents", DataComponentMap.class).invoke(holder, components);
    }

    private static CompoundTag customData(net.minecraft.world.item.ItemStack stack)
    {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        check(data != null, "Missing custom data");
        return data.getUnsafe();
    }

    private static void invoke(Object adapter, Method merge, net.minecraft.world.item.ItemStack target,
                               net.minecraft.world.item.ItemStack source) throws Exception
    {
        merge.invoke(adapter, mirror(target), mirror(source));
    }

    private static ItemStack mirror(net.minecraft.world.item.ItemStack stack) throws Exception
    {
        try
        {
            return (ItemStack) Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack")
                    .getMethod("asCraftMirror", net.minecraft.world.item.ItemStack.class).invoke(null, stack);
        }
        catch (NoSuchMethodException ignored)
        {
            return (ItemStack) Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack")
                    .getMethod("asBukkitMirror", net.minecraft.world.item.ItemStack.class).invoke(null, stack);
        }
    }

    private static void installServer(String version)
    {
        Logger logger = Logger.getLogger(ItemMergeCheck.class.getName());
        Server server = (Server) Proxy.newProxyInstance(Server.class.getClassLoader(), new Class<?>[]{Server.class},
                (proxy, method, args) -> switch (method.getName())
                {
                    case "getName" -> "Paper";
                    case "getVersion", "getBukkitVersion", "getMinecraftVersion" -> version;
                    case "getLogger" -> logger;
                    case "toString" -> "ItemMergeCheckServer";
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "equals" -> proxy == args[0];
                    default -> throw new UnsupportedOperationException(method.toString());
                });
        Bukkit.setServer(server);
    }

    private static void check(boolean condition, String message)
    {
        if (!condition) throw new AssertionError(message);
    }
}
