const fs = require('fs');

const BASE_MODULE_NAME = "v1_17_R1";

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
    if(newVersion === "v1_20_R4") // Total shit
        return;

    console.log(`Generating NMS for ${newVersion}`)
    handle("CompoundTag", "v1_17_R1", newVersion)
    handle("CraftItemStack", "v1_17_R1", newVersion)
    handle("ListTag", "v1_17_R1", newVersion)

    // Because it has some changes in NbtIo methods signature and I can't auto generate.
    if(newVersion !== "v1_20_R3")
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

        if(matches[1] === BASE_MODULE_NAME) {
            continue;
        }

        handleGroup(matches[1])
    }
}