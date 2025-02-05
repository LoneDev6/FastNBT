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
        copyFileToFolder(data.replaceAll("nbt.impl", 'nbt.impl_spigotmap'), file.replaceAll(nmsFolder, spigotmapFolder).replaceAll("impl", 'impl_spigotmap'));
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
