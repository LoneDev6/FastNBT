const fs = require('fs');

let skip = [
    "v1_20_R3", // Because it has some changes in NbtIo methods signature and I can't auto generate.
    "v1_17_R1", // Base module.
]

function handle(name, prevVersion, newVersion)
{
    let content = fs.readFileSync(`./fastnbt_nms_${prevVersion}/src/main/java/dev/lone/fastnbt/nms/nbt/${name}_${prevVersion}.java`).toString();
    content = content.replaceAll(prevVersion, newVersion)

    try {
        let dir = `./fastnbt_nms_${newVersion}/src/main/java/dev/lone/fastnbt/nms/nbt/`
        let path = `${dir}${name}_${newVersion}.java`;
        if (!fs.existsSync(dir)){
            fs.mkdirSync(dir, { recursive: true });
        }
        // Remove read-only from file if exists.
        if(fs.existsSync(path)) {
            fs.chmodSync(path, "666");
        }
        fs.writeFileSync(path, content, 'utf8');
        // Set file read-only
        fs.chmodSync(path, "444");
    }
    catch (e) {
        console.error(e);
    }
}

function handleGroup(newVersion){
    console.log(`Generating NMS for ${newVersion}`)
    // Spigot -> Mojang mapping seems to be the same for all classes,
    // so I can be safe skipping generation for these classes.
    // handle("CompoundTag", "v1_17_R1", newVersion)
    // handle("ListTag", "v1_17_R1", newVersion)
    // handle("NbtIo", "v1_17_R1", newVersion)

    // Package for the Bukkit class CraftItemStack is always different on each version,
    // so I have to generate this file for all versions.
    handle("CraftItemStack", "v1_17_R1", newVersion)
}

const lines = fs.readFileSync('./pom.xml').toString().split("\n");
for (let i in lines) {
    let line = lines[i]
    if(line.includes("<module>")) {
        const regex = new RegExp('<module>fastnbt_nms_(.*R[0-9])<', 'gm')
        const matches = regex.exec(line);
        if(!matches || matches.length < 2) {
            continue;
        }

        if(skip.includes(matches[1])) {
            continue;
        }

        handleGroup(matches[1])
    }
}