const fs = require('fs');

let skip = [
    "v1_20_R3", // Because it has some changes in NBTStreamTools methods signature and I can't auto generate.
    "v1_17_R1", // Base module.
]

function handle(name, prevVersion, newVersion)
{
    let content = fs.readFileSync(`./fastnbt_nms_${prevVersion}/src/main/java/dev/lone/fastnbt/nms/nbt/${name}_${prevVersion}.java`).toString();
    content = content.replaceAll(prevVersion, newVersion)

    try {
        let path = `./fastnbt_nms_${newVersion}/src/main/java/dev/lone/fastnbt/nms/nbt/${name}_${newVersion}.java`;
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
    handle("CompoundTag", "v1_17_R1", newVersion)
    handle("CraftItemStack", "v1_17_R1", newVersion)
    handle("ListTag", "v1_17_R1", newVersion)
    handle("NbtIo", "v1_17_R1", newVersion)
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