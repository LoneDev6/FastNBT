const fs = require('fs');
const path = require('path');

const args = process.argv.slice(2);
if (args.length === 0) {
    console.error("Usage: node script.js <nmsFolder>");
    process.exit(1);
}

const nmsFolder = args[0];
const srcFolder = path.join(nmsFolder, "src/");

if (!fs.existsSync(srcFolder)) {
    console.error(`Error: Source folder '${srcFolder}' does not exist.`);
    process.exit(1);
}

const mojangmapFolder = `${nmsFolder}_mojangmap`;
const spigotmapFolder = `${nmsFolder}_spigotmap`;

function handleRecursiveFiles(srcFolder, callback) {
    fs.readdir(srcFolder, (err, files) => {
        if (err) {
            console.error(`Error reading directory '${srcFolder}':`, err);
            return;
        }

        files.forEach(file => {
            const srcFile = path.join(srcFolder, file);

            fs.lstat(srcFile, (err, stats) => {
                if (err) {
                    console.error(`Error reading file/directory '${srcFile}':`, err);
                    return;
                }

                if (stats.isDirectory()) {
                    // Recursively process subdirectory
                    handleRecursiveFiles(srcFile, callback);
                } else if (stats.isFile()) {
                    // Process the file
                    fs.readFile(srcFile, 'utf8', (err, data) => {
                        if (err) {
                            console.error(`Error reading file '${srcFile}':`, err);
                            return;
                        }

                        callback(data, srcFile);
                    });
                }
            });
        });
    });
}

handleRecursiveFiles(srcFolder, (data, file) => {
    console.log(`Processing file: ${file}`);

    if (!fs.lstatSync(file).isFile()) {
        console.log(`Skipping directory: ${file}`);
        return;
    }

    fs.readFile(file, 'utf8', (err, data) => {
        if (err) {
            console.error(`Error reading file ${file}:`, err);
            return;
        }

        copyFileToFolder(data.replaceAll("nbt.impl", 'nbt.impl_mojangmap'), file.replaceAll(nmsFolder, mojangmapFolder).replaceAll("impl", 'impl_mojangmap'));

        let spigotMapData = data.replaceAll("nbt.impl", 'nbt.impl_spigotmap');
        // Versions after 1.21.4 will use the Spigot specialsource-maven-plugin instead of the paper one.
        // So I have to make sure that the code is using the correct package and remove paper-only shit.
        if(file.includes("1_21_5")) {
            spigotMapData = spigotMapData.replaceAll("org.bukkit.craftbukkit", "org.bukkit.craftbukkit.v1_21_R4")
            spigotMapData = spigotMapData.replaceAll("if(IS_FIELD_HANDLE_PUBLIC) return craftItemStack.handle;", "")
        }
        else if(file.includes("1_21_6")) {
            spigotMapData = spigotMapData.replaceAll("org.bukkit.craftbukkit", "org.bukkit.craftbukkit.v1_21_R5")
            spigotMapData = spigotMapData.replaceAll("if(IS_FIELD_HANDLE_PUBLIC) return craftItemStack.handle;", "")
        }
        copyFileToFolder(spigotMapData, file.replaceAll(nmsFolder, spigotmapFolder).replaceAll("impl", 'impl_spigotmap'));
    });
});

function copyFileToFolder(data, destinationFile) {
    const destinationFolder = path.dirname(destinationFile);
    if (!fs.existsSync(destinationFolder)) {
        fs.mkdirSync(destinationFolder, { recursive: true });
    }

    fs.writeFile(destinationFile, data, (err) => {
        if (err) {
            console.error(`Error writing file ${destinationFile}:`, err);
            return;
        }
        console.log(`File copied to: ${destinationFile}`);
    });
}
