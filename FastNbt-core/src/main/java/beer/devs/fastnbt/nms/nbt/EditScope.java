package beer.devs.fastnbt.nms.nbt;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
final class EditScope
{
    private ItemStack original;
    private NItem borrowed;
    private NItem draft;
    private Object empty;
    private boolean open = true;
    private final List<Node> nodes = new ArrayList<>();
    final Node root = new Node(null, null, null);

    EditScope(ItemStack original)
    {
        this.original = original;
    }

    void check()
    {
        if (!open)
            throw new IllegalStateException("Item editor is closed");
    }

    NItem readItem()
    {
        check();
        if (draft != null)
            return draft;
        if (borrowed == null)
            borrowed = new NItem(NBT.item.readForEdit(original));
        return borrowed;
    }

    NItem writeItem()
    {
        check();
        if (draft == null)
        {
            // Validate before rebinding: a removed child must never target its replacement.
            for (Node node : nodes)
                node.read();
            draft = new NItem(NBT.item.copyForEdit(original));
            for (Node node : nodes)
                node.expected = node.resolve();
        }

        return draft;
    }

    private Object root(boolean create)
    {
        Object tag = NBT.item.editTag((create ? writeItem() : readItem()).getItem(), create);
        if (tag != null)
            return tag;
        if (empty == null)
            empty = NBT.compound.newInstance();
        return empty;
    }

    void commit()
    {
        check();
        if (draft != null)
            NBT.item.applyEdit(original, draft.getItem());
    }

    void close()
    {
        open = false;
        original = null;
        borrowed = null;
        draft = null;
        empty = null;
        for (Node node : nodes)
            node.expected = null;
        nodes.clear();
    }

    final class Node
    {
        private final Node parent;
        private final Object key;
        private Object expected;

        Node(Node parent, Object key, Object expected)
        {
            this.parent = parent;
            this.key = key;
            this.expected = expected;
        }

        Object resolve()
        {
            if (parent == null)
                return root(false);
            Object container = parent.read();
            if (key instanceof String)
                return NBT.compound.rawTag(container, (String) key);
            int index = (Integer) key;
            return index >= 0 && index < NBT.list.size(container) ? NBT.list.get(container, index) : null;
        }

        Object read()
        {
            check();
            Object value = resolve();
            if (parent != null && value != expected)
                throw new IllegalStateException("NBT view was removed, replaced or shifted");
            return value;
        }

        Object write()
        {
            if (parent != null)
                read();
            writeItem();
            return parent == null ? root(true) : read();
        }

        Node child(Object key, Object value)
        {
            Node node = new Node(this, key, value);
            nodes.add(node);
            return node;
        }
    }
}
