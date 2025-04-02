package karashokleo.l2hostility.compat.emi;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PageManager<T>
{
    public final List<T> entries;
    public final int pageSize;
    public int currentPage;

    public PageManager(List<T> entries, int pageSize)
    {
        this.entries = entries;
        this.pageSize = pageSize;
    }

    public void scroll(int delta)
    {
        this.currentPage += delta;
        int totalPages = (this.entries.size() - 1) / this.pageSize + 1;
        if (this.currentPage < 0)
        {
            this.currentPage = totalPages - 1;
        }

        if (this.currentPage >= totalPages)
        {
            this.currentPage = 0;
        }
    }

    @Nullable
    public T get(int offset)
    {
        offset += this.pageSize * this.currentPage;
        return offset < this.entries.size() ? this.entries.get(offset) : null;
    }
}
