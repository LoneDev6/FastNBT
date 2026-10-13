import beer.devs.fastnbt.nms.nbt.NBTType;
import beer.devs.fastnbt.nms.nbt.ICraftItemStack;
import beer.devs.fastnbt.nms.nbt.NBT;
import beer.devs.fastnbt.nms.nbt.NCompound;
import beer.devs.fastnbt.nms.nbt.NItem;
import beer.devs.fastnbt.nms.nbt.NItemEditor;
import beer.devs.fastnbt.nms.nbt.NList;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class EditProbe extends JavaPlugin {
    private static final class CountingStack extends ItemStack {
        int commits;
        CountingStack() { super(Material.PAPER); }
        @Override public boolean setItemMeta(ItemMeta meta) { commits++; return super.setItemMeta(meta); }
    }
    private interface Check { void run() throws Exception; }
    private static final class BenchItem { final ItemStack item; String customName; String itemName; final int[] values=new int[8]; int metaCalls; BenchItem(ItemStack i){item=i;NItem n=new NItem(i);customName=n.getCustomNameJson();itemName=n.getItemNameJson();for(int j=0;j<8;j++)values[j]=n.getInt("bench"+j);if(i instanceof CountingStack c)metaCalls=c.commits;} }
    private static final com.sun.management.ThreadMXBean ALLOC=(com.sun.management.ThreadMXBean)ManagementFactory.getThreadMXBean();
    private long sequence;
    private long checksum;

    @Override public void onEnable() {
        Bukkit.getScheduler().runTaskLater(this,()->{
            try { runProbe(); getLogger().info("EDIT_PROBE COMPLETE"); }
            catch(Throwable t){getLogger().severe("EDIT_PROBE FAILED "+t);t.printStackTrace();}
            finally { Bukkit.shutdown(); }
        },40L);
    }
    private void runProbe() throws Exception {
        if(ALLOC.isThreadAllocatedMemorySupported()&&!ALLOC.isThreadAllocatedMemoryEnabled())ALLOC.setThreadAllocatedMemoryEnabled(true);
        CountingStack plain=fixture();
        check("readonly-no-commit",()->{
            int before=plain.commits;
            NItem.edit(plain,e->{e.getString("sentinel");e.getCompound("nested").getInt("value");e.getList("strings",NBTType.String).getString(0);e.getCustomNameJson();e.getLoreJson();});
            require(plain.commits==before,"read-only edit called setItemMeta");
        });
        ItemStack craft=new NItem(plain).getItem();
        check("craft-readonly",()->NItem.edit(craft,e->{require(e.getString("sentinel").equals("keep"),"craft read mismatch");}));
        check("first-write-rebind-and-nested-lists",()->{
            NItem.edit(plain,e->{
                NCompound nested=e.getCompound("nested");require(nested.getInt("value")==7,"nested initial read");
                e.setInt("rootWrite",1);nested.setString("retained","ok");
                NList strings=e.getList("strings",NBTType.String);strings.addString("second");strings.setString(0,"changed");
                NList children=e.getList("children",NBTType.Compound);NCompound child=children.getCompound(0);child.setInt("value",9);
                NList arrays=e.getOrAddList("arrays",NBTType.IntArray);int[] input={2,3};arrays.addIntArray(input);input[0]=99;int[] output=arrays.getIntArray(0);output[1]=99;
            });
            NItem now=new NItem(plain);require(now.getInt("rootWrite")==1,"root write missing");require(now.getCompound("nested").getString("retained").equals("ok"),"retained child did not rebind");
            require(now.getList("strings",NBTType.String).getString(0).equals("changed"),"root list setter");
            require(now.getList("arrays",NBTType.IntArray).getIntArray(0)[0]==2&&now.getList("arrays",NBTType.IntArray).getIntArray(0)[1]==3,"array alias");
        });
        check("array-input-output-snapshots",()->{
            int[] input={4,5};NItem.edit(plain,e->{e.setIntArray("array",input);input[0]=88;int[] read=e.getIntArray("array");read[1]=99;});
            require(Arrays.equals(new int[]{4,5},new NItem(plain).getIntArray("array")),"array input/output escaped");
        });
        check("exception-rolls-back-nbt-and-metadata",()->{
            NItem before=new NItem(plain);String name=before.getCustomNameJson(),itemName=before.getItemNameJson();List<String> lore=before.getLoreJson();
            try { NItem.edit(plain,e->{e.setInt("rollback",1);e.setCustomNameJson("{\"text\":\"changed\"}");e.setLoreJson(List.of("{\"text\":\"changed\"}"));throw new IllegalStateException("rollback marker");});throw new AssertionError("exception swallowed"); }
            catch(IllegalStateException expected){require("rollback marker".equals(expected.getMessage()),"wrong callback exception");}
            NItem after=new NItem(plain);require(!after.hasKey("rollback")&&Objects.equals(name,after.getCustomNameJson())&&Objects.equals(itemName,after.getItemNameJson())&&Objects.equals(lore,after.getLoreJson()),"failed edit changed item");
        });
        check("expired-removed-replaced-shifted-views",()->{
            NCompound[] saved=new NCompound[1];NList[] savedList=new NList[1];NItem.edit(plain,e->{saved[0]=e.getCompound("nested");savedList[0]=e.getList("strings",NBTType.String);});
            expectIllegal(()->saved[0].getInt("value"));expectIllegal(()->savedList[0].size());
            NCompound[] removed=new NCompound[1];NItem.edit(plain,e->{removed[0]=e.getCompound("nested");e.remove("nested");expectIllegal(()->removed[0].getInt("value"));e.addCompound("nested").setInt("value",7);});
            NCompound[] replaced=new NCompound[1];NItem.edit(plain,e->{replaced[0]=e.getCompound("nested");e.addCompound("nested").setInt("value",3);expectIllegal(()->replaced[0].getInt("value"));});
            NCompound[] shifted=new NCompound[1];NItem.edit(plain,e->{NList list=e.getList("children",NBTType.Compound);shifted[0]=list.getCompound(0);list.addCompound(0).setInt("value",5);expectIllegal(()->shifted[0].getInt("value"));});
        });
        check("repeated-same-value-setters",()->{
            NItem.edit(plain,e->{e.setInt("same",8);e.setInt("same",8);NCompound n=e.getOrAddCompound("nested");n.setString("repeat","x");n.setString("repeat","x");});
            require(new NItem(plain).getInt("same")==8,"same value lost");
        });
        check("nlist-mutator-overloads",()->NItem.edit(plain,EditProbe::mutateEveryListOverload));
        check("craft-nested-write-rollback-and-lists",()->craftChecks(craft));
        for (boolean nativeItem : new boolean[]{false,true}) {
            check("clone-snapshot-and-component-removal-"+nativeItem, () -> {
                ItemStack target = nativeItem ? new NItem(fixture()).getItem() : fixture();
                ItemStack clone = target.clone();
                NCompound[] snapshot = new NCompound[1];
                int[] commits = {target instanceof CountingStack ? ((CountingStack)target).commits : 0};
                NItem.edit(target, e -> {
                    snapshot[0] = new NCompound(e.getInternal());
                    snapshot[0].setString("sentinel", "snapshot-only");
                    require(e.getString("sentinel").equals("keep"), "raw snapshot changed source");
                    e.setString("sentinel", "committed");
                    e.setLoreJson(null);
                });
                NItem result = new NItem(target);
                require("committed".equals(result.getString("sentinel")), "commit absent");
                require("keep".equals(new NItem(clone).getString("sentinel")), "clone custom data changed");
                require((result.getLoreJson()==null || result.getLoreJson().isEmpty()), "removed component reappeared");
                snapshot[0].setString("sentinel", "after-close");
                require("committed".equals(new NItem(target).getString("sentinel")), "retained raw snapshot changed committed item");
                if (target instanceof CountingStack) require(((CountingStack)target).commits==commits[0]+1, "batch did not commit exactly once");
            });
        }
        check("missing-read-does-not-create-custom-data", () -> {
            CountingStack clean = new CountingStack();
            NItem.edit(clean, e -> { require(!e.hasKey("missing") && e.isEmpty(), "missing data"); require(e.getCompound("missing")==null, "missing compound"); });
            require(clean.commits==0 && !NItem.hasNBT(clean), "read created item metadata");
        });
        for (boolean nativeItem : List.of(false, true)) {
            check("explicit-lifecycle-" + nativeItem, () -> {
                CountingStack plainTarget = fixture();
                ItemStack target = nativeItem ? new NItem(plainTarget).getItem() : plainTarget;
                int before = plainTarget.commits;
                NItemEditor editor = NItem.edit(target);
                NCompound child = editor.getCompound("nested");
                NList list = editor.getList("strings", NBTType.String);
                try (editor) {
                    child.setInt("value", 42);
                    list.addString("explicit");
                    editor.setCustomNameJson("{\"text\":\"explicit\"}");
                    require(new NItem(target).getCompound("nested").getInt("value") == 7, "draft leaked before save");
                    editor.save();
                    expectIllegal(editor::save);
                    expectIllegal(() -> editor.getString("sentinel"));
                    expectIllegal(() -> child.setInt("value", 99));
                    expectIllegal(list::size);
                }
                editor.close();
                require(new NItem(target).getCompound("nested").getInt("value") == 42, "explicit save absent");
                require(new NItem(target).getList("strings", NBTType.String).size() == 2, "list save absent");
                if (!nativeItem) require(plainTarget.commits == before + 1, "explicit commit count");
                String name = new NItem(target).getCustomNameJson();
                try (NItemEditor discarded = NItem.edit(target)) {
                    discarded.setInt("discarded", 1);
                    discarded.setCustomNameJson("{\"text\":\"discarded\"}");
                }
                try (NItemEditor failed = NItem.edit(target)) {
                    failed.setInt("failed", 1);
                    throw new IllegalArgumentException("marker");
                } catch (IllegalArgumentException expected) {
                    require("marker".equals(expected.getMessage()), "wrong exception");
                }
                require(!new NItem(target).hasKey("discarded") && !new NItem(target).hasKey("failed"), "close committed");
                require(Objects.equals(name, new NItem(target).getCustomNameJson()), "close changed name");
                try (NItemEditor readonly = NItem.edit(target)) {
                    require(readonly.getCompound("nested").getInt("value") == 42, "readonly mismatch");
                    readonly.save();
                }
                if (!nativeItem) require(plainTarget.commits == before + 1, "readonly/close committed");
            });
        }
        check("callback-rejects-manual-lifecycle", () -> {
            NItem.edit(plain, e -> { expectIllegal(e::save); expectIllegal(e::close); e.setInt("managed", 1); });
            require(new NItem(plain).getInt("managed") == 1, "managed commit missing");
        });
        lazyChecks(fixture());
        if (!Boolean.getBoolean("probe.checksOnly")) benchmarks();
    }
    private CountingStack fixture(){
        CountingStack item=new CountingStack();ItemMeta meta=item.getItemMeta();meta.displayName(Component.text("old"));meta.lore(List.of(Component.text("line-a"),Component.text("line-b")));item.setItemMeta(meta);
        NItem.modifyCustomData(item,n->{n.setString("sentinel","keep");for(int j=0;j<8;j++)n.setInt("bench"+j,100+j);n.getOrAddCompound("nested").setInt("value",7);n.getOrAddList("strings",NBTType.String).addString("first");n.getOrAddList("children",NBTType.Compound).addCompound().setInt("value",1);for(int i=0;i<32;i++)n.setString("rich"+i,"0123456789abcdef-rich-benchmark-payload");});
        NItem n=new NItem(item);n.setCustomNameJson("{\"text\":\"old-custom\"}");if(NBT.dataComponents!=null)n.setItemNameJson("{\"text\":\"old-item\"}");n.setLoreJson(List.of("{\"text\":\"old-lore\"}"));n.save();item.commits=0;return item;
    }
    private static void mutateEveryListOverload(NItemEditor e){
        NList l=e.getOrAddList("all-byte",NBTType.Byte);l.addByte((byte)1);l.addByte(0,(byte)2);l.setByte(0,(byte)3);
        l=e.getOrAddList("all-short",NBTType.Short);l.addShort((short)1);l.addShort(0,(short)2);l.setShort(0,(short)3);
        l=e.getOrAddList("all-int",NBTType.Int);l.addInt(1);l.addInt(0,2);l.setInt(0,3);
        l=e.getOrAddList("all-long",NBTType.Long);l.addLong(1L);l.addLong(0,2L);l.setLong(0,3L);
        l=e.getOrAddList("all-float",NBTType.Float);l.addFloat(1f);l.addFloat(0,2f);l.setFloat(0,3f);
        l=e.getOrAddList("all-double",NBTType.Double);l.addDouble(1d);l.addDouble(0,2d);l.setDouble(0,3d);
        l=e.getOrAddList("all-byte-array",NBTType.ByteArray);byte[] ba={1};l.addByteArray(ba);l.addByteArray(0,ba);l.setByteArray(0,new byte[]{3});
        l=e.getOrAddList("all-int-array",NBTType.IntArray);int[] ia={1};l.addIntArray(ia);l.addIntArray(0,ia);l.setIntArray(0,new int[]{3});
        l=e.getOrAddList("all-long-array",NBTType.LongArray);long[] la={1};l.addLongArray(la);l.addLongArray(0,la);l.setLongArray(0,new long[]{3});
        l=e.getOrAddList("all-string",NBTType.String);l.addString("a");l.addString(0,"b");l.setString(0,"c");
        l=e.getOrAddList("all-compound",NBTType.Compound);NCompound c=l.addCompound();c.setInt("v",1);l.addCompound(0).setInt("v",2);l.addCompound(0,new NCompound().setInt("v",3));l.addCompound(new NCompound().setInt("v",4));l.setCompound(0,new NCompound().setInt("v",5));l.addItemStack(new ItemStack(Material.STONE));l.addItemStack(0,new ItemStack(Material.STONE));l.setItemStack(0,new ItemStack(Material.STONE));require(l.getItemStack(0).getType()==Material.STONE,"item list round trip failed");
        l=e.getOrAddList("all-list",NBTType.List);NList nested=l.addList();nested.addString("a");l.addList(0).addString("b");l.addList(new NList());l.addList(0,new NList());l.addList(List.of("c"));l.addList(0,List.of("d"));l.setList(0,List.of("e"));l.setList(0,new NList());
        NCompound source=new NCompound().setInt("raw",4);Object raw=source.getInternal();NList rawLists=e.getOrAddList("raw-lists",NBTType.List);rawLists.addList(List.of(raw));source.setInt("raw",5);require(rawLists.getList(0).getCompound(0).getInt("raw")==4,"raw native list input aliased source");
        l=e.getOrAddList("remove-list",NBTType.String);l.addString("x");l.remove(0);
    }
    private void craftChecks(ItemStack item)throws Exception{
        check("craft-first-write-rebind",()->NItem.edit(item,e->{NCompound child=e.getCompound("nested");e.setInt("craftRoot",1);child.setString("kept","yes");}));
        require(new NItem(item).getCompound("nested").getString("kept").equals("yes"),"craft view not rebound");
        check("craft-rollback-metadata",()->{
            NItem before=new NItem(item);String name=before.getCustomNameJson(),in=before.getItemNameJson();List<String> lore=before.getLoreJson();
            try{NItem.edit(item,e->{e.setInt("craftRollback",2);e.setCustomNameJson("{\"text\":\"bad\"}");e.setLoreJson(List.of("{\"text\":\"bad\"}"));throw new IllegalArgumentException("expected");});throw new AssertionError("exception swallowed");}
            catch(IllegalArgumentException expected){require("expected".equals(expected.getMessage()),"wrong exception");}
            NItem after=new NItem(item);require(!after.hasKey("craftRollback")&&Objects.equals(name,after.getCustomNameJson())&&Objects.equals(in,after.getItemNameJson())&&Objects.equals(lore,after.getLoreJson()),"craft rollback changed data");
        });
        check("craft-all-list-mutators",()->NItem.edit(item,EditProbe::mutateEveryListOverload));
    }
    private void check(String name,Check test)throws Exception{test.run();getLogger().info("EDIT_PROBE CHECK PASS "+name);}
    private static void require(boolean ok,String message){if(!ok)throw new AssertionError(message);}
    private static void expectIllegal(Runnable action){try{action.run();throw new AssertionError("stale view remained usable");}catch(IllegalStateException expected){}}
    private void lazyChecks(ItemStack item)throws Exception{
        ICraftItemStack delegate=NBT.item;int[] reads={0},copies={0};
        ICraftItemStack proxy=(ICraftItemStack)Proxy.newProxyInstance(ICraftItemStack.class.getClassLoader(),new Class<?>[]{ICraftItemStack.class},(p,m,a)->{
            if(m.getName().equals("readForEdit"))reads[0]++;if(m.getName().equals("copyForEdit"))copies[0]++;
            try{return m.invoke(delegate,a);}catch(InvocationTargetException e){throw e.getCause();}
        });
        try{
            NBT.item=proxy;NItem.edit(item,e->{});require(reads[0]==0&&copies[0]==0,"empty edit touched item");
            NItem.edit(item,e->require(e.getCompound("nested").getInt("value")==7,"lazy read failed"));require(reads[0]==1&&copies[0]==0,"read-only edit copied item");
            reads[0]=copies[0]=0;NItem.edit(item,e->{e.setInt("lazy-a",1);e.setString("lazy-b","two");e.setBoolean("lazy-c",true);});require(copies[0]==1,"multiple writes did not create exactly one draft");
        }finally{NBT.item=delegate;}
        getLogger().info("EDIT_PROBE LAZY PASS empty=0/0 readonly=1/0 first-write-copies="+copies[0]);
    }
    private void benchmarks()throws Exception{
        Map<String,List<BenchItem>> pools = new HashMap<>();
        Files.createDirectories(getDataFolder().toPath());try(BufferedWriter csv=Files.newBufferedWriter(getDataFolder().toPath().resolve("edit-benchmark.csv"))){
            csv.write("phase,round,order,workload,variant,operations,nanos,ns_op,bytes_op,set_item_meta_calls,checksum,gc_count,gc_millis\n");List<String> cases=new ArrayList<>();for(String v:List.of("bukkit","craft"))for(String w:List.of("names-legacy","names-edit","writes-legacy","writes-edit","reads-legacy","reads-edit"))cases.add(w+"-"+v);Random random=new Random(Long.getLong("benchmark.seed",9262026L));
            for (String name : cases) {
                List<BenchItem> pool = new ArrayList<>();
                for (int i=0;i<32;i++) pool.add(new BenchItem(name.endsWith("bukkit") ? fixture() : new NItem(fixture()).getItem()));
                pools.put(name,pool);
            }
            for(int round=0;round<13;round++){List<String> order=new ArrayList<>(cases);Collections.shuffle(order,random);for(int i=0;i<order.size();i++)sample(csv,round<5?"warmup":"measure",round<5?round:round-5,i,order.get(i),pools.get(order.get(i)),100);}
        }
        getLogger().info("EDIT_PROBE BENCHMARK checksum="+checksum+" itemsPerVariant="+32);
    }
    private void sample(BufferedWriter csv,String phase,int round,int order,String workload,List<BenchItem>pool,int millis)throws Exception{
        int split=workload.lastIndexOf('-');String name=workload.substring(0,split),variant=workload.substring(split+1);
        long gc0=gc(false), gcMillis0=gc(true);
        long thread=Thread.currentThread().getId(),a0=ALLOC.isThreadAllocatedMemoryEnabled()?ALLOC.getThreadAllocatedBytes(thread):0,start=System.nanoTime(),ops=0;
        do{for(BenchItem item:pool){operation(name,item);ops++;}}while(System.nanoTime()-start<TimeUnit.MILLISECONDS.toNanos(millis));
        long nanos=System.nanoTime()-start,bytes=ALLOC.isThreadAllocatedMemoryEnabled()?ALLOC.getThreadAllocatedBytes(thread)-a0:0;long gcCount=gc(false)-gc0,gcMillis=gc(true)-gcMillis0;verifyBench(pool,name,ops);int metaCalls=0;
        for(BenchItem item:pool)if(item.item instanceof CountingStack c){metaCalls+=c.commits-item.metaCalls;item.metaCalls=c.commits;}
        if (variant.equals("bukkit")) require(metaCalls==ops*(name.equals("names-legacy")?(NBT.dataComponents==null?1:2):name.startsWith("reads-")?0:1), "unexpected commit count");
        csv.write(String.format(Locale.ROOT,"%s,%d,%d,%s,%s,%d,%d,%.2f,%.2f,%d,%d,%d,%d%n",phase,round,order,name,variant,ops,nanos,(double)nanos/ops,(double)bytes/ops,metaCalls,checksum,gcCount,gcMillis));csv.flush();
    }
    private static long gc(boolean millis) {
        long total=0;
        for(java.lang.management.GarbageCollectorMXBean bean:ManagementFactory.getGarbageCollectorMXBeans()) total+=Math.max(0,millis?bean.getCollectionTime():bean.getCollectionCount());
        return total;
    }
    private void operation(String name,BenchItem bench){ItemStack item=bench.item;int n=(int)(++sequence);
        switch(name){
            case "names-legacy"->{NItem old=new NItem(item);old.setCustomNameJson("{\"text\":\"n"+n+"\"}");if(NBT.dataComponents!=null)old.setItemNameJson("{\"text\":\"i"+n+"\"}");}
            case "names-edit"->NItem.edit(item,e->{e.setCustomNameJson("{\"text\":\"n"+n+"\"}");if(NBT.dataComponents!=null)e.setItemNameJson("{\"text\":\"i"+n+"\"}");});
            case "writes-legacy"->{NItem old=new NItem(item);for(int j=0;j<8;j++)old.setInt("bench"+j,n+j);old.save();}
            case "writes-edit"->NItem.edit(item,e->{for(int j=0;j<8;j++)e.setInt("bench"+j,n+j);});
            case "reads-legacy"->{NItem old=new NItem(item);for(int j=0;j<8;j++)checksum+=old.getInt("bench"+j);}
            case "reads-edit"->NItem.edit(item,e->{for(int j=0;j<8;j++)checksum+=e.getInt("bench"+j);});
        }
    }
    private void verifyBench(List<BenchItem> pool,String workload,long ops){
        int batches=(int)(ops/pool.size());require(ops%pool.size()==0,"partial benchmark fixture batch");long last=sequence-pool.size();
        for(int i=0;i<pool.size();i++){BenchItem b=pool.get(i);NItem n=new NItem(b.item);if(workload.startsWith("names-")){long value=last+i+1;b.customName="{\"text\":\"n"+value+"\"}";if(NBT.dataComponents!=null)b.itemName="{\"text\":\"i"+value+"\"}";}if(workload.startsWith("writes-")){long value=last+i+1;for(int j=0;j<8;j++)b.values[j]=(int)value+j;}require(b.item.getType()==Material.PAPER&&b.item.getAmount()==1,"benchmark material/amount changed");require("keep".equals(n.getString("sentinel")),"benchmark seed changed");for(int j=0;j<32;j++)require("0123456789abcdef-rich-benchmark-payload".equals(n.getString("rich"+j)),"benchmark rich seed changed");require(net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson().deserialize(n.getCustomNameJson()).equals(net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson().deserialize(b.customName)),"benchmark name mismatch: "+workload+" expected="+b.customName+" actual="+n.getCustomNameJson());require(NBT.dataComponents==null || net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson().deserialize(n.getItemNameJson()).equals(net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson().deserialize(b.itemName)),"benchmark item name mismatch");for(int j=0;j<8;j++)require(n.getInt("bench"+j)==b.values[j],"benchmark scalar mismatch");}
    }
}
