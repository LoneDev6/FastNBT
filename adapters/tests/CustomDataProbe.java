import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

/** Runs the same public-API checks standalone and through ItemsAdder's library loader. */
public final class CustomDataProbe extends JavaPlugin
{
    private int passed;
    private int failed;

    private interface Check { void run() throws Exception; }

    private void check(String name, Check operation)
    {
        try
        {
            operation.run();
            passed++;
            getLogger().info("FASTNBT_CHECK " + name + " PASS");
        }
        catch (Throwable error)
        {
            failed++;
            if (error instanceof InvocationTargetException)
                error = error.getCause();
            getLogger().log(java.util.logging.Level.SEVERE, "FASTNBT_CHECK " + name + " FAIL", error);
        }
    }

    private static Object call(Object target, String method, Object... arguments) throws Exception
    {
        Class<?>[] types = new Class<?>[arguments.length];
        for (int i = 0; i < types.length; i++)
            types[i] = arguments[i] instanceof Integer ? int.class : arguments[i].getClass();
        return target.getClass().getMethod(method, types).invoke(target, arguments);
    }

    private static void require(boolean condition, String message)
    {
        if (!condition) throw new AssertionError(message);
    }

    private interface Edit { void run(Object tag) throws Exception; }

    private static void modify(Class<?> nitem, ItemStack item, Edit edit) throws Exception
    {
        Consumer<Object> callback = tag -> {
            try { edit.run(tag); }
            catch (RuntimeException error) { throw error; }
            catch (Exception error) { throw new RuntimeException(error); }
        };
        nitem.getMethod("modifyCustomData", ItemStack.class, Consumer.class).invoke(null, item, callback);
    }

    private static Object data(Class<?> nitem, ItemStack item) throws Exception
    {
        return nitem.getConstructor(ItemStack.class).newInstance(item);
    }

    private void batchChecks(ClassLoader loader)
    {
        for (boolean craft : new boolean[]{false, true})
            check("batch-snapshot-rollback-" + (craft ? "craft" : "bukkit"), () -> {
                Class<?> nitem = Class.forName("beer.devs.fastnbt.nms.nbt.NItem", true, loader);
                Class<?> ntype = Class.forName("beer.devs.fastnbt.nms.nbt.NBTType", true, loader);
                ItemStack item = new ItemStack(Material.PAPER, 3);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Batch item");
                meta.setLore(Collections.singletonList("preserved"));
                item.setItemMeta(meta);
                if (craft) item = (ItemStack) call(data(nitem, item), "getItem");
                modify(nitem, item, tag -> {
                    call(tag, "setInt", "counter", 11);
                    call(call(tag, "getOrAddCompound", "nested"), "setInt", "value", 5);
                });
                ItemStack before = item.clone();
                Object[] retained = new Object[4];
                int[] array = {1, 2, 3};
                modify(nitem, item, tag -> {
                    retained[0] = tag;
                    call(tag, "setInt", "counter", 12);
                    retained[1] = call(tag, "getOrAddCompound", "nested");
                    call(retained[1], "setInt", "value", 7);
                    Object type = ntype.getField("String").get(null);
                    retained[2] = tag.getClass().getMethod("getOrAddList", String.class, ntype).invoke(tag, "list", type);
                    call(retained[2], "addString", "original");
                    tag.getClass().getMethod("setIntArray", String.class, int[].class).invoke(tag, "array", array);
                    retained[3] = call(tag, "getOrAddCompound", "replace");
                    call(retained[3], "setInt", "old", 1);
                    Object replacement = call(tag, "addCompound", "replace");
                    call(replacement, "setInt", "new", 2);
                    call(retained[3], "setInt", "stale", 3);
                });
                Object current = data(nitem, item);
                require(Integer.valueOf(12).equals(call(current, "getInt", "counter")), "batch committed without save");
                require(Integer.valueOf(7).equals(call(call(current, "getCompound", "nested"), "getInt", "value")), "nested commit");
                require(Boolean.FALSE.equals(call(call(current, "getCompound", "replace"), "hasKey", "stale")), "replaced view reattached");
                require(Integer.valueOf(11).equals(call(data(nitem, before), "getInt", "counter")), "pre-existing clone counter changed");
                require(Integer.valueOf(5).equals(call(call(data(nitem, before), "getCompound", "nested"), "getInt", "value")), "pre-existing clone nested data changed");
                call(retained[0], "setInt", "counter", 99);
                call(retained[1], "setInt", "value", 99);
                call(retained[2], "addString", "escaped");
                array[0] = 99;
                current = data(nitem, item);
                require(Integer.valueOf(12).equals(call(current, "getInt", "counter")), "escaped root changed committed item");
                require(Integer.valueOf(7).equals(call(call(current, "getCompound", "nested"), "getInt", "value")), "escaped nested view changed item");
                Object type = ntype.getField("String").get(null);
                Object list = current.getClass().getMethod("getList", String.class, ntype).invoke(current, "list", type);
                require(Integer.valueOf(1).equals(call(list, "size")), "escaped list changed item");
                require(((int[])call(current, "getIntArray", "array"))[0] == 1, "escaped input array changed item");
                RuntimeException marker = new RuntimeException("abort batch");
                try {
                    modify(nitem, item, tag -> {
                        call(tag, "setInt", "counter", 100);
                        call(call(tag, "getOrAddCompound", "nested"), "setInt", "value", 100);
                        throw marker;
                    });
                    throw new AssertionError("Callback exception swallowed");
                } catch (InvocationTargetException error) {
                    require(error.getCause() == marker, "callback error identity changed");
                }
                current = data(nitem, item);
                require(Integer.valueOf(12).equals(call(current, "getInt", "counter")), "failed callback modified counter");
                require(Integer.valueOf(7).equals(call(call(current, "getCompound", "nested"), "getInt", "value")), "failed callback modified nested data");
                Object copy = data(nitem, item);
                if (!craft) {
                    call(call(copy, "getCompound", "nested"), "setInt", "value", 8);
                    Object copiedList = copy.getClass().getMethod("getList", String.class, ntype).invoke(copy, "list", type);
                    call(copiedList, "addString", "copy only");
                    ((int[])call(copy, "getIntArray", "array"))[0] = 8;
                    current = data(nitem, item);
                    require(Integer.valueOf(7).equals(call(call(current, "getCompound", "nested"), "getInt", "value")), "Bukkit nested conversion alias before save");
                    Object originalList = current.getClass().getMethod("getList", String.class, ntype).invoke(current, "list", type);
                    require(Integer.valueOf(1).equals(call(originalList, "size")), "Bukkit list conversion alias before save");
                    require(((int[])call(current, "getIntArray", "array"))[0] == 1, "Bukkit array conversion alias before save");
                }
                call(copy, "setInt", "converted", 1);
                if (!craft) {
                    require(Boolean.FALSE.equals(call(data(nitem, item), "hasKey", "converted")), "Bukkit conversion is live before save");
                    call(copy, "save");
                }
                require(Integer.valueOf(1).equals(call(data(nitem, item), "getInt", "converted")), "ordinary setters/save regressed");
                if (!craft) {
                    current = data(nitem, item);
                    require(Integer.valueOf(8).equals(call(call(current, "getCompound", "nested"), "getInt", "value")), "nested conversion did not save");
                    Object savedList = current.getClass().getMethod("getList", String.class, ntype).invoke(current, "list", type);
                    require(Integer.valueOf(2).equals(call(savedList, "size")), "list conversion did not save");
                    require(((int[])call(current, "getIntArray", "array"))[0] == 8, "array conversion did not save");
                }
                require(item.getAmount() == 3 && item.getType() == Material.PAPER, "material/amount changed");
                require("Batch item".equals(item.getItemMeta().getDisplayName()), "name changed");
                require(Collections.singletonList("preserved").equals(item.getItemMeta().getLore()), "lore changed");
                ItemStack emptyData = new ItemStack(Material.PAPER);
                if (craft) emptyData = (ItemStack) call(data(nitem, emptyData), "getItem");
                modify(nitem, emptyData, tag -> call(tag, "setInt", "only", 1));
                modify(nitem, emptyData, tag -> {
                    for (Object key : new ArrayList<>((Set<?>) call(tag, "getKeys"))) call(tag, "remove", key);
                });
                require(Boolean.TRUE.equals(call(data(nitem, emptyData), "isEmpty")), "empty batch not cleared");
                try {
                    modify(nitem, new ItemStack(Material.AIR), tag -> { throw new AssertionError("AIR callback ran"); });
                    throw new AssertionError("AIR accepted");
                } catch (InvocationTargetException error) {
                    require(error.getCause() instanceof IllegalArgumentException, "wrong AIR error");
                }
            });
    }

    private void runChecks(ClassLoader loader, boolean consumer)
    {
        check("custom-data-read-write-update-clear", () -> {
            Class<?> nitem = Class.forName("beer.devs.fastnbt.nms.nbt.NItem", true, loader);
            ItemStack item = new ItemStack(Material.PAPER, 3);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("FastNBT vehicle");
            meta.setLore(Collections.singletonList("unrelated lore"));
            item.setItemMeta(meta);

            Object data = nitem.getConstructor(ItemStack.class).newInstance(item);
            Object nested = call(data, "getOrAddCompound", "itemsadder");
            call(nested, "setString", "namespace", "fastnbt_test");
            call(nested, "setString", "id", "vehicle");
            call(data, "setInt", "fuel", 12);
            call(data, "save");

            data = nitem.getConstructor(ItemStack.class).newInstance(item);
            nested = call(data, "getCompound", "itemsadder");
            require("vehicle".equals(call(nested, "getString", "id")), "nested ID read");
            require(Integer.valueOf(12).equals(call(data, "getInt", "fuel")), "scalar read");
            call(nested, "setString", "id", "updated_vehicle");
            call(data, "setInt", "fuel", 7);
            call(data, "save");

            data = nitem.getConstructor(ItemStack.class).newInstance(item);
            require("updated_vehicle".equals(call(call(data, "getCompound", "itemsadder"),
                    "getString", "id")), "live nested update");
            require(Integer.valueOf(7).equals(call(data, "getInt", "fuel")), "scalar replacement");
            call(data, "remove", "fuel");
            call(data, "remove", "itemsadder");
            call(data, "save");

            data = nitem.getConstructor(ItemStack.class).newInstance(item);
            require(Boolean.FALSE.equals(call(data, "hasKey", "fuel")), "scalar removal");
            require(Boolean.FALSE.equals(call(data, "hasKey", "itemsadder")), "compound removal");
            require(item.getType() == Material.PAPER && item.getAmount() == 3, "material/count preserved");
            require("FastNBT vehicle".equals(item.getItemMeta().getDisplayName()), "name preserved");
            require(Collections.singletonList("unrelated lore").equals(item.getItemMeta().getLore()), "lore preserved");

            Class<?> nbt = Class.forName("beer.devs.fastnbt.nms.nbt.NBT", true, loader);
            Object adapter = nbt.getField("item").get(null);
            if (Bukkit.getBukkitVersion().startsWith("1.20.6")
                    || Bukkit.getBukkitVersion().startsWith("1.21")
                    || Bukkit.getBukkitVersion().startsWith("26."))
            {
                Field cached = adapter.getClass().getDeclaredField("FIELD_CUSTOM_DATA_TAG");
                cached.setAccessible(true);
                Field field = (Field) cached.get(null);
                getLogger().info("FASTNBT_MEMBER " + adapter.getClass().getName() + " "
                        + field.getDeclaringClass().getName() + "." + field.getName() + " " + field.getType().getName());
            }
        });

        batchChecks(loader);

        if (consumer)
        {
            for (String id : new String[]{"t9:recipe_input", "t9:test_vehicle"})
                check("custom-stack-identity:" + id, () -> {
                    Class<?> type = Class.forName("dev.lone.itemsadder.api.CustomStack", true, loader);
                    Object custom = type.getMethod("getInstance", String.class).invoke(null, id);
                    require(custom != null, id + " registered");
                    ItemStack item = (ItemStack) call(custom, "getItemStack");
                    Object restored = type.getMethod("byItemStack", ItemStack.class).invoke(null, item);
                    require(restored != null && id.equals(call(restored, "getNamespacedID")), "CustomStack identity");
                });
        }
        getLogger().info("FASTNBT_COMPLETE passed=" + passed + " failed=" + failed);
    }

    @Override
    public void onEnable()
    {
        org.bukkit.plugin.Plugin consumer = Bukkit.getPluginManager().getPlugin("ItemsAdder");
        ClassLoader loader = consumer == null ? getClass().getClassLoader() : consumer.getClass().getClassLoader();
        Bukkit.getScheduler().runTaskLater(this, () -> runChecks(loader, consumer != null), consumer == null ? 1L : 200L);
    }
}
