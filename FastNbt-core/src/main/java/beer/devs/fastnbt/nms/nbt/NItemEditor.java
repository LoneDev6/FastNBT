package beer.devs.fastnbt.nms.nbt;

import java.util.ArrayList;
import java.util.List;

/**
 * A scoped item editor. Views expire when the editor closes; raw tags and arrays are snapshots.
 * Reads are lazy and the first mutation creates an isolated draft. Metadata is applied once.
 */
public final class NItemEditor extends EditCompound implements AutoCloseable
{
    private final boolean callbackManaged;

    NItemEditor(EditScope scope)
    {
        this(scope, true);
    }

    NItemEditor(EditScope scope, boolean callbackManaged)
    {
        super(scope, scope.root);
        this.callbackManaged = callbackManaged;
    }

    /** Applies changes once and closes this editor, including if application fails. */
    public void save()
    {
        checkExplicitLifecycle();
        try
        {
            scope.commit();
        }
        finally
        {
            scope.close();
        }
    }

    /** Discards unsaved changes. Repeated close calls are harmless. */
    @Override
    public void close()
    {
        checkExplicitLifecycle();
        scope.close();
    }

    private void checkExplicitLifecycle()
    {
        // Callback edits must retain their commit-on-success/rollback-on-exception contract.
        if (callbackManaged)
            throw new IllegalStateException("Callback editor lifecycle is managed by NItem.edit");
    }

    public String getCustomNameJson()
    {
        return scope.readItem().getCustomNameJson();
    }

    public void setCustomNameJson(String json)
    {
        scope.writeItem().setCustomNameJson(json);
    }

    public String getItemNameJson()
    {
        return scope.readItem().getItemNameJson();
    }

    public void setItemNameJson(String json)
    {
        scope.writeItem().setItemNameJson(json);
    }

    public List<String> getLoreJson()
    {
        List<String> lore = scope.readItem().getLoreJson();
        return lore == null ? null : new ArrayList<>(lore);
    }

    public void setLoreJson(List<String> lore)
    {
        scope.writeItem().setLoreJson(lore == null ? null : new ArrayList<>(lore));
    }
}
