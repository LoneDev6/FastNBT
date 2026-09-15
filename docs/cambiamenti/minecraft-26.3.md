# Supporto Minecraft 26.3

FastNBT 1.4.24 aggiunge l'adapter esatto per Minecraft 26.3 e riconosce la versione senza ripiegare su adapter precedenti.

L'adapter include le API NBT, i data component e la serializzazione codec degli `ItemStack` di 26.3. La conversione da item NMS a Bukkit viene risolta una volta all'avvio con la firma specifica della piattaforma: Paper espone `asBukkitMirror`, mentre Spigot espone `asCraftMirror`. Anche la decodifica codec usa questo handle su una copia dell'item NMS, quindi restituisce un oggetto Bukkit indipendente ed evita il descrittore Paper-only `asBukkitCopy(ItemInstance)` su Spigot.

L'artefatto locale verificato è `beer.devs:FastNbt-jar:1.4.24` con SHA-256 `38bf7061fb2e97b589ca033fff2d34ed6a4a2e82d7b9eebd3af3adfba8d0a51f`.

Verifiche eseguite:

- compilazione dell'adapter `fastnbt_nms_v26_3`;
- test di `FastNbt-core`;
- creazione dello shadow jar;
- smoke runtime su Spigot 26.3 e Paper 26.3 build 3;
- round trip dei custom data, semantica mirror e decodifica codec con controllo che la copia decodificata non condivida l'handle nativo originale.
