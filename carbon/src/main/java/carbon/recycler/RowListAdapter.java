package carbon.recycler;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcin on 2016-06-10.
 */

public class RowListAdapter<Type> extends ListAdapter<RowViewHolder, Type> {
    private Map<Class, Integer> types = new HashMap<>();
    private List<RowFactory> factories = new ArrayList<>();

    public RowListAdapter(Class type, RowFactory factory) {
        addFactory(type, factory);
    }

    public RowListAdapter(List<Type> items, RowFactory factory) {
        super(items);
        addFactory(items.get(0).getClass(), factory);
    }

    public void addFactory(Class type, RowFactory factory) {
        types.put(type, types.size());
        factories.add(factory);
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Row row = factories.get(viewType).create(viewGroup);
        RowViewHolder viewHolder = new RowViewHolder(row.getView());
        viewHolder.setRow(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RowViewHolder holder, final int position) {
        Type data = getItem(position);
        Row row = holder.getRow();
        row.bind(data);
        row.getView().setOnClickListener(view -> fireOnItemClickedEvent(holder.getAdapterPosition()));
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(getItem(position).getClass());
    }
}

