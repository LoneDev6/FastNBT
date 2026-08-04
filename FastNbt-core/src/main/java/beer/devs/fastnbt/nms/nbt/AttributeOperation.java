package beer.devs.fastnbt.nms.nbt;

public enum AttributeOperation
{
    ADD(0),
    MULTIPLY_BASE(1),
    MULTIPLY_TOTAL(2);

    final int id;

    AttributeOperation(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    static AttributeOperation byId(int id)
    {
        switch (id)
        {
            case 0: return ADD;
            case 1: return MULTIPLY_BASE;
            case 2: return MULTIPLY_TOTAL;
            default: throw new IllegalArgumentException("Operation must be between 0 and 2.");
        }
    }
}
