package dev.lone.fastnbt;

import dev.lone.fastnbt.nbt.NBT;
import dev.lone.fastnbt.nbt.NBTTypeId;
import dev.lone.fastnbt.nbt.NFile;
import dev.lone.fastnbt.nbt.NTagList;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        NBT.init(this);
        new Test().register();

        Bukkit.getPluginManager().registerEvents(this, this);

        try
        {
            File file = new File(getDataFolder(), "test.nbt");
            file.getParentFile().mkdirs();
            NFile nFile = new NFile(file);
            String wowman = nFile.getString("wowman");

            nFile.setString("wowman", "nice kok");

            NTagList list = nFile.getOrAddList("list", NBTTypeId.String);
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");
            list.add(0, "wow");

            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                NTagList asd = nFile.getOrAddList("asd", NBTTypeId.String);
                asd.add(0, "asdasd");
                asd.add(0, "asdasd");
                asd.add(0, "asdasd");
                asd.add(0, "asdasd");
                list.add(0, "test");
            });

            Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
                try
                {
                    nFile.save();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }, 60L);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
